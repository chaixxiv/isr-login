package com.cyra.exam.service;

import com.cyra.exam.dao.LoginHistoryDao;
import com.cyra.exam.domain.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class LoginHistoryService {

    @Autowired
    private LoginHistoryDao loginHistoryDao;

    public List<Date> getUniqueDates() {
        return loginHistoryDao.getUniqueDates();
    }
}
