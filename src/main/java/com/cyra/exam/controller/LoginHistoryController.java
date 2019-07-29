package com.cyra.exam.controller;

import com.cyra.exam.service.LoginHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/")
public class LoginHistoryController {

    @Autowired
    private LoginHistoryService loginHistoryService;

    @GetMapping("/dates")
    public ResponseEntity<List<LocalDate>> getLoginDates() {
        List<LocalDate> uniqueDates = loginHistoryService.getUniqueDates();
        return new ResponseEntity<>(uniqueDates, HttpStatus.OK);
    }

//    @InitBinder
//    public void initBinder(WebDataBinder binder){
//        binder.registerCustomEditor(Date.class,
//                new CustomDateEditor(new SimpleDateFormat("yyyyMMdd"), true, 8));
//    }

    @GetMapping("/users")
    public ResponseEntity<List<String>> getUsersByLoginDate(@RequestParam(required = false) @DateTimeFormat(pattern="yyyyMMdd") LocalDate start,
                                                            @RequestParam(required = false) @DateTimeFormat(pattern="yyyyMMdd") LocalDate end) {
        Map<String, LocalDate> dateMap = new HashMap<>();

        if (start != null) {
            dateMap.put("start", start);
        }

        if (end != null) {
            dateMap.put("end", end);
        }

        List<String> users = loginHistoryService.getUsersByLoginDate(dateMap);
        return new ResponseEntity<>(users, HttpStatus.OK);


    }
}
