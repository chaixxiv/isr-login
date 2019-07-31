package com.cyra.exam.dao;

import com.cyra.exam.mapper.UserCountRowCallbackHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class LoginHistoryDaoImpl implements LoginHistoryDao {

    private static final String START_DATE = "start";
    private static final String END_DATE = "end";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<LocalDate> getUniqueDates() {
        return jdbcTemplate.queryForList("select distinct date(login_time) from login order by login_time asc", Collections.emptyMap(), LocalDate.class);
    }

    @Override
    public List<String> getUsersByLoginDate(Optional<LocalDate> start, Optional<LocalDate> end) {
        StringBuilder query = new StringBuilder("select distinct user from login");
        MapSqlParameterSource params = new MapSqlParameterSource();
        List<String> whereCriteria = getRangeDateList(start, end, params);
        if (!whereCriteria.isEmpty()) {
            query.append(" where ");
            query.append(whereCriteria.stream().collect(Collectors.joining(" and ")));
        }
        query.append(" order by user asc");
        return jdbcTemplate.queryForList(query.toString(), params, String.class);
    }

    @Override
    public Map<String, Integer> getUserAndLoggedTimes(Optional<LocalDate> start, Optional<LocalDate> end, MultiValueMap<String, String> queryMap) {
        StringBuilder query = new StringBuilder("select distinct user, count(user) from login");
        MapSqlParameterSource params = new MapSqlParameterSource();
        List<String> whereCriteria = getRangeDateList(start, end, params);

        for (String key : queryMap.keySet()) {
            whereCriteria.add(key + " in (:" + key + ")");
            params.addValue(key, queryMap.get(key));
        }

        if (!whereCriteria.isEmpty()) {
            query.append(" where ");
            query.append(whereCriteria.stream().collect(Collectors.joining(" and ")));
        }
        query.append(" group by user");
        UserCountRowCallbackHandler rowCallbackHandler = new UserCountRowCallbackHandler();
        jdbcTemplate.query(query.toString(), params, rowCallbackHandler);
        return rowCallbackHandler.getUserCount();
    }

    private List<String> getRangeDateList(Optional<LocalDate> start, Optional<LocalDate> end, MapSqlParameterSource params) {
        List<String> whereCriteria = new ArrayList<>();
        if (start.isPresent()) {
            whereCriteria.add(" login_time >= :" + START_DATE);
            params.addValue(START_DATE, start.get());
        }
        if (end.isPresent()) {
            whereCriteria.add(" login_time < :" + END_DATE);
            params.addValue(END_DATE, end.get().plusDays(1));
        }
        return whereCriteria;
    }
}
