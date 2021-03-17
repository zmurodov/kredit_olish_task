package com.idproger.credit.service;

import com.idproger.credit.entitiy.credit.Credit;
import com.idproger.credit.repository.credit.CreditRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditService {

    private final CreditRepository creditRepository;

    public CreditService(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    public Credit saveCredit(Credit credit) {
        return creditRepository.save(credit);
    }

    public Credit getCreditById(Long id) {
        return creditRepository.findByCreditId(id);
    }

    public List<Credit> getAll() {
        return creditRepository.findAll();
    }
}
