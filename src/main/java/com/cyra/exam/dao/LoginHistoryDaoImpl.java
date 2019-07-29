package com.cyra.exam.dao;

import com.cyra.exam.domain.UserCount;
import com.cyra.exam.mapper.UserCountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class LoginHistoryDaoImpl implements LoginHistoryDao {

    public static final String START_DATE = "start";
    public static final String END_DATE = "end";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public static final DateTimeFormatter DATE_FORMAT_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public List<LocalDate> getUniqueDates() {
        return jdbcTemplate.queryForList("SELECT DISTINCT DATE(login_time) from login order by login_time asc", Collections.emptyMap(), LocalDate.class);
    }

    @Override
    public List<String> getUsersByLoginDate(Map<String, LocalDate> dateMap) {
        StringBuilder query = new StringBuilder("select distinct user from login");
        MapSqlParameterSource params = new MapSqlParameterSource();
        List<String> whereCriteria = new ArrayList<>();
        if (dateMap.containsKey(START_DATE)) {
            whereCriteria.add(" date(login_time) >= :" + START_DATE);
            params.addValue(START_DATE, dateMap.get(START_DATE));
        }
        if (dateMap.containsKey(END_DATE)) {
            whereCriteria.add(" date(login_time) <= :" + END_DATE);
            params.addValue(END_DATE, dateMap.get(END_DATE));
        }
        if (!whereCriteria.isEmpty()) {
            query.append(" where ");
            query.append(whereCriteria.stream().collect(Collectors.joining(" and ")));
        }
        query.append(" order by user asc");
        return jdbcTemplate.queryForList(query.toString(), params, String.class);
    }

    @Override
    public List<UserCount> getUserAndLoggedTimes(MultiValueMap<String, String> queryMap) {
        StringBuilder query = new StringBuilder("select distinct user, count(user) from login");
        MapSqlParameterSource params = new MapSqlParameterSource();
        List<String> whereCriteria = new ArrayList<>();
        for (String key : queryMap.keySet()) {
            if (key.equals(START_DATE)) {
                whereCriteria.add(" date(login_time) >= :" + START_DATE);
                params.addValue(START_DATE, LocalDate.parse(queryMap.getFirst(START_DATE), DATE_FORMAT_YYYYMMDD));
            } else if (key.equals(END_DATE)) {
                whereCriteria.add(" date(login_time) <= :" + END_DATE);
                params.addValue(END_DATE, LocalDate.parse(queryMap.getFirst(END_DATE), DATE_FORMAT_YYYYMMDD));
            } else {
                whereCriteria.add(key + " in (:" + key + ")");
                params.addValue(key, queryMap.get(key));
            }
        }
        if (!whereCriteria.isEmpty()) {
            query.append(" where ");
            query.append(whereCriteria.stream().collect(Collectors.joining(" and ")));
        }
        query.append(" group by user");
        return jdbcTemplate.query(query.toString(), params, new UserCountMapper());
    }
}
