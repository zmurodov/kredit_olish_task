package com.idproger.credit.service;

import com.idproger.credit.entitiy.user_credit.UserCredit;
import com.idproger.credit.repository.user_credits.UserCreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCreditService {

    private final UserCreditRepository userCreditRepository;

    public UserCreditService(UserCreditRepository userCreditRepository) {
        this.userCreditRepository = userCreditRepository;
    }

    public UserCredit getUserCreditsById(Long id) {
        return userCreditRepository.findByCreditId(id);
    }

    public List<UserCredit> getUserCreditsByUserPassport(String passport) {
        return userCreditRepository.findUserCreditByUserPassportNumber(passport);
    }

    public UserCredit saveUserCredit(UserCredit userCredit) {
        return userCreditRepository.save(userCredit);
    }
}
