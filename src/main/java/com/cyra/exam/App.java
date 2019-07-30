package com.cyra.exam;

import com.cyra.exam.domain.Login;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    //TODO: remove this and put in the test class
    @Bean
    public CommandLineRunner demoData(JdbcTemplate jdbcTemplate) {
        return args -> {
            Faker faker = new Faker();
            List<Login> loginList = new ArrayList<>();
            for (int i=1; i<100000; i++) {
                if (i % 10000 == 0) {
                    faker = new Faker();
                }

                String username = faker.name().firstName();
                loginList.add(new Login(Timestamp.valueOf(LocalDateTime.now().plusHours(i)), username, faker.color().name() + i,
                        faker.cat().name() + i, faker.dog().breed() + i, faker.pokemon().name() + i));
            }

            String sql = "INSERT INTO login " +
                    "(login_time, user, attribute1, attribute2, attribute3, attribute4) values (?, ?, ?, ?, ?, ?)";

            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Login login = loginList.get(i);
                    ps.setTimestamp(1, login.getLoginTime());
                    ps.setString(2, login.getUser());
                    ps.setString(3, login.getAttribute1());
                    ps.setString(4, login.getAttribute2());
                    ps.setString(5, login.getAttribute3());
                    ps.setString(6, login.getAttribute4());
                }

                @Override
                public int getBatchSize() {
                    return loginList.size();
                }
            });
        };
    }
}