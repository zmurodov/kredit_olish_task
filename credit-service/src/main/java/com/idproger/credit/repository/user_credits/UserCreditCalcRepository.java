package com.idproger.credit.repository.user_credits;

import com.idproger.credit.entitiy.user_credit.UserCredit;
import com.idproger.credit.entitiy.user_credit.UserCreditCalc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCreditCalcRepository extends JpaRepository<UserCreditCalc, Long> {

//    List<UserCreditCalc> findByUserCreditId(Long userCreditId);
}
