package com.cyra.exam.service;

import com.cyra.exam.dao.LoginHistoryDao;
import com.cyra.exam.domain.UserCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoginHistoryService {

    @Autowired
    private LoginHistoryDao loginHistoryDao;

    public List<LocalDate> getUniqueDates() {
        return loginHistoryDao.getUniqueDates();
    }

    public List<String> getUsersByLoginDate(Optional<LocalDate> start, Optional<LocalDate> end){
        return loginHistoryDao.getUsersByLoginDate(start, end);
    }

    public List<UserCount> getUserAndLoggedTimes(Optional<LocalDate> start, Optional<LocalDate> end, MultiValueMap<String, String> queryMap) {
        return loginHistoryDao.getUserAndLoggedTimes(start, end, queryMap);
    }
}
