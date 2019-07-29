package com.cyra.exam.controller;

import com.cyra.exam.dao.LoginHistoryDao;
import com.cyra.exam.domain.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController("/")
public class LoginHistoryController {

    @Autowired
    private LoginHistoryDao loginHistoryDao;

    @GetMapping("/dates")
    public String getLoginDates() {
        System.out.println("TEST");
        List<Date> uniqueDates = loginHistoryDao.getUniqueDates();
        System.out.println("test.... " + uniqueDates);
        return "test";
    }
}
