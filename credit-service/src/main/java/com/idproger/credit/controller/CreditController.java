package com.idproger.credit.controller;

import com.idproger.credit.dto.GetCreditRequest;
import com.idproger.credit.dto.GetCreditResponse;
import com.idproger.credit.entitiy.credit.Credit;
import com.idproger.credit.entitiy.user_credit.UserCredit;
import com.idproger.credit.entitiy.user_credit.UserCreditCalc;
import com.idproger.credit.service.CreditService;
import com.idproger.credit.service.UserCreditCalcService;
import com.idproger.credit.service.UserCreditService;
import com.idproger.credit.vo.ResponseTemplateVo;
import com.idproger.credit.vo.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequestMapping("/credits")
public class CreditController {

    private static final String USER_SERVICE_URL = "http://localhost:9001/users/";
    private final CreditService creditService;
    private final UserCreditService userCreditService;
    private final UserCreditCalcService userCreditCalcService;

    private final RestTemplate restTemplate;

    public CreditController(CreditService creditService, UserCreditService userCreditService, UserCreditCalcService userCreditCalcService, RestTemplate restTemplate) {
        this.creditService = creditService;
        this.userCreditService = userCreditService;
        this.userCreditCalcService = userCreditCalcService;
        this.restTemplate = restTemplate;
    }

    @PostMapping("")
    public Credit saveCredit(@RequestBody Credit credit) {
        return creditService.saveCredit(credit);
    }

    @GetMapping("")
    public List<Credit> getAll() {
        return creditService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Credit> getCreditById(@PathVariable("id") Long id) {
        var credit = creditService.getCreditById(id);

        if (credit == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(credit, HttpStatus.OK);
    }


    @GetMapping("/{id}/{passport}")
    public ResponseTemplateVo getCreditByUserPassport(@PathVariable("id") Long creditId,
                                                      @PathVariable("passport") String passport) {
        ResponseTemplateVo ov = new ResponseTemplateVo();
        Credit credit = creditService.getCreditById(creditId);

        User user = restTemplate.getForObject(USER_SERVICE_URL + passport, User.class);

        ov.setUser(user);
        ov.setCredit(credit);
        return ov;
    }

    @PostMapping("/get-credit")
    public ResponseEntity<GetCreditResponse> getCredit(@RequestBody GetCreditRequest request) {
        if (request == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Credit credit = creditService.getCreditById(request.getCreditId());
        User user = restTemplate.getForObject(USER_SERVICE_URL + request.getUserPassport(), User.class);

        if (credit == null || user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        double taxRate = 0.13;
        double forLiving = 0.25;

        BigDecimal userCleanSalary = user.getMonthlySalary()
                .multiply(BigDecimal.valueOf(1 - taxRate - forLiving))
                .setScale(2, RoundingMode.HALF_UP);

        double r = credit.getPercentage() / 100.0 / 12.0;
        double x = Math.pow(1 + r, request.getCreditPeriod());
        double K = r * x / (x - 1);

        BigDecimal monthlyPayment = request.getCreditAmount()
                .multiply(BigDecimal.valueOf(K))
                .setScale(2, RoundingMode.HALF_UP);

        if (monthlyPayment.compareTo(userCleanSalary) > 0) { // a > b
            // oyligi kam
            GetCreditResponse response = new GetCreditResponse();

            BigDecimal creditAmount = userCleanSalary
                    .divide(BigDecimal.valueOf(K), 2, RoundingMode.HALF_UP);

            UserCredit userCredit = new UserCredit();
            userCredit.setCreditAmount(creditAmount);
            userCredit.setMonthlyPayment(userCleanSalary);

            userCredit.setCreditId(credit.getCreditId());
            userCredit.setCreditPeriod(request.getCreditPeriod());
            userCredit.setStatus("active");
            userCredit.setUserPassportNumber(request.getUserPassport());


            List<UserCreditCalc> calcs =  userCreditCalcService.calculateUserCredit(userCredit,user,credit);
            userCredit.setCalcs(calcs);

            userCredit = userCreditService.saveUserCredit(userCredit);
            response.setUserCredit(userCredit);
            response.setMessage("Siz " + request.getCreditAmount() + " so`m kreditni ololmaysiz. Sizga " + userCredit.getCreditAmount() + " so'm kredit taklif qilolamiz.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            //yetadi

            UserCredit userCredit = new UserCredit();
            userCredit.setMonthlyPayment(monthlyPayment);
            userCredit.setCreditAmount(request.getCreditAmount());


            userCredit.setCreditId(credit.getCreditId());
            userCredit.setCreditPeriod(request.getCreditPeriod());
            userCredit.setStatus("active");
            userCredit.setUserPassportNumber(request.getUserPassport());

            List<UserCreditCalc> calcs =  userCreditCalcService.calculateUserCredit(userCredit,user,credit);

            userCredit.setCalcs(calcs);

            userCredit = userCreditService.saveUserCredit(userCredit);
            GetCreditResponse response = new GetCreditResponse("Siz bu kreditni ololasiz", userCredit);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
