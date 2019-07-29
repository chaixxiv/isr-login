package com.cyra.exam.dao;

import com.cyra.exam.domain.UserCount;
import com.cyra.exam.mapper.LoginRowMapper;
import com.cyra.exam.mapper.UserCountMapper;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        StringBuilder query = new StringBuilder();
        query.append("select distinct user from login ");
        if (!dateMap.isEmpty()) {
            query.append(" where ");
            buildLoginDateRangeQuery(dateMap, query);
        }
        query.append(" order by user asc");

        return jdbcTemplate.queryForList(query.toString(), String.class);
    }

    @Override
    public List<UserCount> getUserAndLoggedTimes(MultiValueMap<String, String> queryMap) {
        StringBuilder query = new StringBuilder();
        query.append("select distinct user, count(user) from login ");
        if (!queryMap.isEmpty()) {
            query.append(" where ");
        }

        if (queryMap.containsKey(START_DATE) || queryMap.containsKey(END_DATE)) {
            Map<String, LocalDate> dateMap = new HashMap<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            dateMap.computeIfAbsent("start", val -> queryMap.get(START_DATE) != null ? LocalDate.parse(queryMap.get(START_DATE).get(0), formatter) : null);
            dateMap.computeIfAbsent("end", val -> queryMap.get(END_DATE) != null ? LocalDate.parse(queryMap.get(END_DATE).get(0), formatter) : null);
            buildLoginDateRangeQuery(dateMap, query);
            query.append(" and ");
        }

        Iterator<Map.Entry<String, List<String>>> it = queryMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String, List<String>> pair = it.next();
            if (!START_DATE.equals(pair.getKey()) && !END_DATE.equals(pair.getKey())) {
                query.append(MessageFormat.format(" {0} in ", pair.getKey()));
                buildQueryParamsArray(queryMap.get(pair.getKey()), query);
                if (it.hasNext()) {
                    query.append(" and ");
                }
            }
        }

        query.append(" group by user");
        return jdbcTemplate.query(query.toString(), new UserCountMapper());
    }

    private void buildQueryParamsArray(List<String> queryParams, StringBuilder query) {
        query.append("( ");
        for (int i=0; i<queryParams.size(); i++) {
            query.append(MessageFormat.format("\"{0}\"", queryParams.get(i)));
            if (i != queryParams.size() - 1) {
                query.append(",");
            }
        }
        query.append(" )");
    }

    private void buildLoginDateRangeQuery(Map<String, LocalDate> dateMap, StringBuilder query) {
        if (dateMap.containsKey(START_DATE) && dateMap.containsKey(END_DATE)) {
            LocalDate startDate = dateMap.get(START_DATE);
            LocalDate endDate = dateMap.get(END_DATE);
            query.append(MessageFormat.format(" date(login_time) between \"{0}\" and \"{1}\"", startDate, endDate));
        } else if (dateMap.containsKey(START_DATE)) {
            LocalDate startDate = dateMap.get(START_DATE);
            query.append(MessageFormat.format(" date(login_time) >= \"{0}\"", startDate));
        } else if (dateMap.containsKey(END_DATE)) {
            LocalDate endDate = dateMap.get(END_DATE);
            query.append(MessageFormat.format(" date(login_time) <= \"{0}\"", endDate));
        }
    }
}
