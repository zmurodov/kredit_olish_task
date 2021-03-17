package com.idproger.credit.service;

import com.idproger.credit.entitiy.credit.Credit;
import com.idproger.credit.entitiy.user_credit.UserCredit;
import com.idproger.credit.entitiy.user_credit.UserCreditCalc;
import com.idproger.credit.repository.user_credits.UserCreditCalcRepository;
import com.idproger.credit.vo.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserCreditCalcService {

    private UserCreditCalcRepository userCreditCalcRepository;

    public UserCreditCalcService(UserCreditCalcRepository userCreditCalcRepository) {
        this.userCreditCalcRepository = userCreditCalcRepository;
    }

    public UserCreditCalc saveUserCreditCalc(UserCreditCalc calc) {
        return userCreditCalcRepository.save(calc);
    }

    public List<UserCreditCalc> calculateUserCredit(UserCredit userCredit, User user, Credit credit) {

        List<UserCreditCalc> result = new ArrayList<>();

        for (int i = 0; i < userCredit.getCreditPeriod(); i++) {
            UserCreditCalc calc = new UserCreditCalc();

            BigDecimal loanbalance;
            if (result.size() >= 1) {
                loanbalance = result.get(i - 1).getLoanBalance().subtract(result.get(i - 1).getMainDebt());
            } else {
                loanbalance = userCredit.getCreditAmount();
            }
            calc.setLoanBalance(loanbalance.setScale(2, RoundingMode.HALF_UP));

            BigDecimal interest = loanbalance.multiply(BigDecimal.valueOf(credit.getPercentage() / 100.0 / 12.0)).setScale(2, RoundingMode.HALF_UP);

            BigDecimal mainDebt = userCredit.getMonthlyPayment().subtract(interest).setScale(2, RoundingMode.HALF_UP);

            BigDecimal payoutAmount = userCredit.getMonthlyPayment().setScale(2, RoundingMode.HALF_UP);

            calc.setMainDebt(mainDebt);
            calc.setInterest(interest);
            calc.setPayoutAmount(payoutAmount);
            calc.setUserCredit(userCredit);
            calc.setCurrentMonth((long) (i + 1));
            result.add(calc);
        }
        return  result;
    }
}
