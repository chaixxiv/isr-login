package com.cyra.exam.mapper;

import com.cyra.exam.domain.UserCount;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCountMapper implements RowMapper<UserCount> {

    @Override
    public UserCount mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserCount userCount = new UserCount();
        userCount.setUser(rs.getString(1));
        userCount.setCount(rs.getInt(2));
        return userCount;
    }
}
