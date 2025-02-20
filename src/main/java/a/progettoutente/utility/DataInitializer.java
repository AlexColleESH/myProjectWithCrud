package a.progettoutente.utility;

import a.progettoutente.entity.*;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer {

    private final JdbcTemplate jdbcTemplate;

    public DataInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ruolo", Long.class);
        if (count == 0) {
            jdbcTemplate.execute("INSERT INTO ruolo (id_ruolo, ruolo) VALUES (1, 'ROLE_SUPERADMIN');");
            jdbcTemplate.execute("INSERT INTO ruolo (id_ruolo, ruolo) VALUES (2, 'ROLE_ADMIN');");
            jdbcTemplate.execute("INSERT INTO ruolo (id_ruolo, ruolo) VALUES (3, 'ROLE_USER');");
        }
        Long countNazione = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM nazione", Long.class);
        if (countNazione == 0) {
            importNazioneFromCsv();
        }
        Long countRegione = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM regione", Long.class);
        if (countRegione == 0) {
            importRegioneFromCsv();
        }
        Long countProvincia = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM provincia", Long.class);
        if (countProvincia == 0) {
            importProvinciaFromCsv();
        }
        Long countComune = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM comune", Long.class);
        if (countComune == 0) {
            importComuneFromCsv();
        }
        Long countCap = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM cap", Long.class);
        if (countCap == 0) {
            importCapFromCsv();
        }



    }

    private void importCapFromCsv() {
        List<Cap> capList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/static/gi_cap.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                capList.add(new Cap(values[0], values[1]));
            }
            String sql = "INSERT INTO cap (fk_codice_istat, cap) VALUES (?,?)";
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, capList.get(i).getComune().getCodiceIstat());
                    ps.setString(2, capList.get(i).getCap());
                }

                @Override
                public int getBatchSize() {
                    return capList.size();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void importComuneFromCsv() {
        List<Comune> comuneList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/static/gi_comuni.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                comuneList.add(new Comune(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7].replace(",", "."), values[8].replace(",", "."), values[9].replace(",", "."), values[10]));
            }
            String sql = "INSERT INTO comune (sigla_provincia,codice_istat,denominazione_ita_altra,denominazione_ita,denominazione_altra,flag_capoluogo,codice_belfiore,lat,lon,superficie_kmq,fk_codice_sovracomunale) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, comuneList.get(i).getSiglaProvincia());
                    ps.setString(2, comuneList.get(i).getCodiceIstat());
                    ps.setString(3, comuneList.get(i).getDenominazioneItaAltra());
                    ps.setString(4, comuneList.get(i).getDenominazioneIta());
                    ps.setString(5, comuneList.get(i).getDenominazioneAltra());
                    ps.setString(6, comuneList.get(i).getFlagCapoluogo());
                    ps.setString(7, comuneList.get(i).getCodiceBelfiore());
                    ps.setBigDecimal(8, comuneList.get(i).getLat());
                    ps.setBigDecimal(9, comuneList.get(i).getLon());
                    ps.setBigDecimal(10, comuneList.get(i).getSuperficieKmq());
                    ps.setInt(11, comuneList.get(i).getProvincia().getCodiceSovracomunale());

                }

                @Override
                public int getBatchSize() {
                    return comuneList.size();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void importNazioneFromCsv() {
        List<Nazione> nazioneList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/static/gi_nazioni.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                nazioneList.add(new Nazione(values[0], values[1], values[2], values[3]));
            }
            String sql = "INSERT INTO nazione (sigla_nazione,codice_belfiore,denominazione_nazione,denominazione_cittadinanza) VALUES (?,?,?,?)";
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, nazioneList.get(i).getSiglaNazione());
                    ps.setString(2, nazioneList.get(i).getCodiceBelfiore());
                    ps.setString(3, nazioneList.get(i).getDenominazioneNazione());
                    ps.setString(4, nazioneList.get(i).getDenominazioneCittadinanza());

                }

                @Override
                public int getBatchSize() {
                    return nazioneList.size();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void importProvinciaFromCsv() {
        List<Provincia> provinciaList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/static/gi_province.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                provinciaList.add(new Provincia(values[0], values[1], values[2], values[3],values[4], values[5].replace(",", "."), values[6]));
            }
            String sql = "INSERT INTO provincia (fk_codice_regione,sigla_provincia,denominazione_provincia,tipologia_provincia,numero_comuni,superficie_kmq,codice_sovracomunale) VALUES (?,?,?,?,?,?,?)";
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, provinciaList.get(i).getRegione().getCodiceRegione());
                    ps.setString(2, provinciaList.get(i).getSiglaProvincia());
                    ps.setString(3, provinciaList.get(i).getDenominazioneProvincia());
                    ps.setString(4, provinciaList.get(i).getTipologiaProvincia());
                    ps.setInt(5, provinciaList.get(i).getNumeroComuni());
                    ps.setBigDecimal(6, provinciaList.get(i).getSuperficieKmq());
                    ps.setInt(7, provinciaList.get(i).getCodiceSovracomunale());

                }

                @Override
                public int getBatchSize() {
                    return provinciaList.size();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void importRegioneFromCsv() {
        List<Regione> regioneList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/static/gi_regioni.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                regioneList.add(new Regione(values[0], values[1], values[2], values[3],values[4], values[5], values[6].replace(",", ".")));
            }
            String sql = "INSERT INTO regione (ripartizione_geografica,codice_regione,denominazione_regione,tipologia_regione,numero_province,numero_comuni,superficie_kmq) VALUES (?,?,?,?,?,?,?)";
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, regioneList.get(i).getRipartizioneGeografica());
                    ps.setInt(2, regioneList.get(i).getCodiceRegione());
                    ps.setString(3, regioneList.get(i).getDenominazioneRegione());
                    ps.setString(4, regioneList.get(i).getTipologiaRegione());
                    ps.setInt(5, regioneList.get(i).getNumeroProvince());
                    ps.setInt(6, regioneList.get(i).getNumeroComuni());
                    ps.setBigDecimal(7, regioneList.get(i).getSuperficieKmq());

                }

                @Override
                public int getBatchSize() {
                    return regioneList.size();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
