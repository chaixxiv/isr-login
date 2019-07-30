package com.cyra.exam.mapper;

import com.cyra.exam.domain.Login;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRowMapper implements RowMapper<Login> {
    @Override
    public Login mapRow(ResultSet rs, int i) throws SQLException {
        Login login = new Login();
        login.setId(rs.getLong("id"));
        login.setLoginTime(rs.getTimestamp("login_time"));
        login.setUser(rs.getString("user"));
        login.setAttribute1(rs.getString("attribute1"));
        login.setAttribute2(rs.getString("attribute2"));
        login.setAttribute3(rs.getString("attribute3"));
        login.setAttribute4(rs.getString("attribute4"));
        return login;
    }
}
