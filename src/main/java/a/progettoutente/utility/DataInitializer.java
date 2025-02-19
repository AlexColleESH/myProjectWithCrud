package a.progettoutente.utility;

import a.progettoutente.entity.Cap;
import a.progettoutente.entity.Comune;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
        Long countCap = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM cap", Long.class);
        if (countCap == 0) {
            importCapFromCsv();
        }
        Long countComune = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM comune", Long.class);
        if (countComune == 0) {
            importComuneFromCsv();
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
            String sql = "INSERT INTO cap (codice_istat, cap) VALUES (?,?)";
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, capList.get(i).getCodiceIstat());
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
            String sql = "INSERT INTO comune (sigla_provincia,codice_istat,denominazione_ita_altra,denominazione_ita,denominazione_altra,flag_capoluogo,codice_belfiore,lat,lon,superficie_kmq,codice_sovracomunale) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
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
                    ps.setInt(11, comuneList.get(i).getCodiceSovracomunale());

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
}
