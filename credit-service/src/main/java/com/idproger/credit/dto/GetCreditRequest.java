package com.idproger.credit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCreditRequest {

    private String userPassport;
    private Long creditId;
    private BigDecimal creditAmount;
    private int creditPeriod;
}
