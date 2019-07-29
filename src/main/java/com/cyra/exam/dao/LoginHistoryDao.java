package com.cyra.exam.dao;

import java.sql.Date;
import java.util.List;

public interface LoginHistoryDao {
    List<Date> getUniqueDates();
}
