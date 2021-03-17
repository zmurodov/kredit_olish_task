package com.idproger.credit.repository.credit;

import com.idproger.credit.entitiy.credit.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit, Long> {
    Credit findByCreditId(Long id);
}
