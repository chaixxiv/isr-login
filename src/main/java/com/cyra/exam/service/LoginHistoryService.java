package com.cyra.exam.service;

import com.cyra.exam.dao.LoginHistoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class LoginHistoryService {

    @Autowired
    private LoginHistoryDao loginHistoryDao;

    public List<LocalDate> getUniqueDates() {
        return loginHistoryDao.getUniqueDates();
    }

    public List<String> getUsersByLoginDate(Map<String, LocalDate> dateMap){
        return loginHistoryDao.getUsersByLoginDate(dateMap);
    }
}
