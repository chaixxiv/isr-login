package com.cyra.exam.mapper;

import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserCountRowCallbackHandler implements RowCallbackHandler {

    private final Map<String, Integer> userCount = new HashMap<>();

    @Override
    public void processRow(ResultSet rs) throws SQLException {
        userCount.put(rs.getString(1), rs.getInt(2));
    }

    public Map<String, Integer> getUserCount() {
        return Collections.unmodifiableMap(userCount);
    }
}
