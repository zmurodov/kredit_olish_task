package com.idproger.credit.controller;

import com.idproger.credit.entitiy.user_credit.UserCredit;
import com.idproger.credit.service.UserCreditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-credits")
public class UserCreditController {

    private final UserCreditService userCreditService;

    public UserCreditController(UserCreditService userCreditService) {
        this.userCreditService = userCreditService;
    }

    @RequestMapping("/{id}")
    public ResponseEntity<UserCredit> getUserCreditsById(@PathVariable("id") Long id) {
        UserCredit userCredit = userCreditService.getUserCreditsById(id);

        if (userCredit == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userCredit, HttpStatus.OK);
    }

    @GetMapping("/{passport}")
    public List<UserCredit> getUserCreditsByUserPassport(@PathVariable("passport") String passport) {
        return userCreditService.getUserCreditsByUserPassport(passport);
    }
}
