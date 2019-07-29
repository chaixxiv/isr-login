package com.cyra.exam.service;

import com.cyra.exam.dao.LoginHistoryDao;
import com.cyra.exam.domain.UserCount;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

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

    public List<UserCount> getUserAndLoggedTimes(MultiValueMap<String, String> queryMap) {
        return loginHistoryDao.getUserAndLoggedTimes(queryMap);
    }
}
