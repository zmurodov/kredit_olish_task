package com.idproger.credit.entitiy.credit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "credit_id")
    private Long creditId;
    @Column(name = "credit_name")
    private String creditName;
    @Column(name = "period_month")
    private int periodMonth;
    @Column(name = "percentage")
    private int percentage;
    @Column(name = "max_amount", precision = 15, scale = 2)
    private BigDecimal maxAmount;
}
