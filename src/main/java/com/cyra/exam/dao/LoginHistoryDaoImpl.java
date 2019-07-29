package com.cyra.exam.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class LoginHistoryDaoImpl implements LoginHistoryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Date> getUniqueDates() {
        return jdbcTemplate.queryForList("SELECT DISTINCT DATE(login_time) from login order by login_time asc", Date.class);
    }
}
