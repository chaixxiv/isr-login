package com.cyra.exam.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface LoginHistoryDao {
    List<LocalDate> getUniqueDates();
    List<String> getUsersByLoginDate(Map<String, LocalDate> dateMap);
}
