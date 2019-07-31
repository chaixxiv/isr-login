package com.cyra.exam.dao;

import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LoginHistoryDao {
    List<LocalDate> getUniqueDates();
    List<String> getUsersByLoginDate(Optional<LocalDate> start, Optional<LocalDate> end);
    Map<String, Integer> getUserAndLoggedTimes(Optional<LocalDate> start, Optional<LocalDate> end, MultiValueMap<String, String> queryMap);
}
