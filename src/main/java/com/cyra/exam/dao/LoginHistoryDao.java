package com.cyra.exam.dao;

import java.util.Date;
import java.util.List;

public interface LoginHistoryDao {
    List<Date> getUniqueDates();
}
