package com.cyra.exam.dao;

import com.cyra.exam.domain.UserCount;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface LoginHistoryDao {
    List<LocalDate> getUniqueDates();
    List<String> getUsersByLoginDate(Map<String, LocalDate> dateMap);
    List<UserCount> getUserAndLoggedTimes(MultiValueMap<String, String> queryMap);
}
