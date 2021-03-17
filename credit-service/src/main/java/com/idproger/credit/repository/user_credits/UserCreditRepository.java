package com.idproger.credit.repository.user_credits;

import com.idproger.credit.entitiy.user_credit.UserCredit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCreditRepository extends JpaRepository<UserCredit, Long> {
    UserCredit findByCreditId(Long id);

    List<UserCredit> findUserCreditByUserPassportNumber(String passport);
}
