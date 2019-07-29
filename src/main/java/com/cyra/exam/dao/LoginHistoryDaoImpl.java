package com.cyra.exam.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class LoginHistoryDaoImpl implements LoginHistoryDao {
    public static final String START_DATE = "start";
    public static final String END_DATE = "end";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<LocalDate> getUniqueDates() {
        return jdbcTemplate.queryForList("SELECT DISTINCT DATE(login_time) from login order by login_time asc", LocalDate.class);
    }

    @Override
    public List<String> getUsersByLoginDate(Map<String, LocalDate> dateMap) {
        String query;
        if (dateMap.isEmpty()) {
            query = "select distinct user from login order by user asc";
        } else if (dateMap.containsKey(START_DATE) && dateMap.containsKey(END_DATE)) {
            LocalDate startDate = dateMap.get(START_DATE);
            LocalDate endDate = dateMap.get(END_DATE);
            query = MessageFormat.format("select distinct user from login where date(login_time) between \"{0}\" and \"{1}\" order by user asc", startDate, endDate);
        } else if (dateMap.containsKey(START_DATE)) {
            LocalDate startDate = dateMap.get(START_DATE);
            query = MessageFormat.format("select distinct user from login where date(login_time) >= \"{0}\" order by user asc", startDate);
        } else {
            LocalDate endDate = dateMap.get(END_DATE);
            query = MessageFormat.format("select distinct user from login where date(login_time) <= \"{0}\" order by user asc", endDate);
        }

        return jdbcTemplate.queryForList(query, String.class);
    }
}
