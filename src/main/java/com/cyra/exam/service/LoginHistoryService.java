package com.cyra.exam.service;

import com.cyra.exam.dao.LoginHistoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoginHistoryService {

    @Autowired
    private LoginHistoryDao loginHistoryDao;

    public List<Date> getUniqueDates() {
        System.out.println( loginHistoryDao.getUniqueDates());
        return loginHistoryDao.getUniqueDates();
    }
}
