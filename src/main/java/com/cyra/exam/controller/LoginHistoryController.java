package com.cyra.exam.controller;

import com.cyra.exam.dao.LoginHistoryDao;
import com.cyra.exam.domain.Login;
import com.cyra.exam.service.LoginHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController("/")
public class LoginHistoryController {

    @Autowired
    private LoginHistoryService loginHistoryService;

    @GetMapping("/dates")
    public ResponseEntity<List<Date>> getLoginDates() {
        List<Date> uniqueDates = loginHistoryService.getUniqueDates();
        return new ResponseEntity<>(uniqueDates, HttpStatus.OK);
    }
}
