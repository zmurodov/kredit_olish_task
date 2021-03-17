package com.idproger.credit.entitiy.user_credit;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "user_credit_calcs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreditCalc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long currentMonth;

    @Column(precision = 15, scale = 2)
    private BigDecimal loanBalance; // credit qoldig'i

    @Column(precision = 15, scale = 2)
    private BigDecimal mainDebt; // asosiy qarz


    @Column(precision = 15, scale = 2)
    private BigDecimal interest; // foiz

    @Column(precision = 15, scale = 2)
    private BigDecimal payoutAmount; // to`lov

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "userCreditId")
    private UserCredit userCredit;
}
