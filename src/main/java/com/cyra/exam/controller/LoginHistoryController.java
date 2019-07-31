package com.cyra.exam.controller;

import com.cyra.exam.domain.UserCount;
import com.cyra.exam.exception.RangeDateException;
import com.cyra.exam.service.LoginHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController("/")
public class LoginHistoryController {

    @Autowired
    private LoginHistoryService loginHistoryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginHistoryController.class);

    @GetMapping("/dates")
    public ResponseEntity<ApiResponse> getLoginDates() {
        LOGGER.info("Getting all unique dates...");
        List<LocalDate> uniqueDates = loginHistoryService.getUniqueDates();
        return new ResponseEntity<>(new ApiResponse("success", uniqueDates), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getUsersByLoginDate(@RequestParam(required = false) @DateTimeFormat(pattern="yyyyMMdd") LocalDate start,
                                                            @RequestParam(required = false) @DateTimeFormat(pattern="yyyyMMdd") LocalDate end) throws RangeDateException {
        LOGGER.info("Getting all unique users by login date");
        List<String> users = loginHistoryService.getUsersByLoginDate(Optional.ofNullable(start), Optional.ofNullable(end));
        return new ResponseEntity<>(new ApiResponse("success", users), HttpStatus.OK);
    }

    @GetMapping("/logins")
    public ResponseEntity<ApiResponse> getUserLoggedTimes(
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyyMMdd") LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyyMMdd") LocalDate end,
            @RequestParam(required = false) MultiValueMap<String, String> queryMap) throws RangeDateException {

        LOGGER.info("Getting all users and logged in times");
        queryMap.remove("start");
        queryMap.remove("end");
        List<UserCount> users = loginHistoryService.getUserAndLoggedTimes(Optional.ofNullable(start), Optional.ofNullable(end), queryMap);
        return new ResponseEntity<>(new ApiResponse("success", users), HttpStatus.OK);
    }
}
