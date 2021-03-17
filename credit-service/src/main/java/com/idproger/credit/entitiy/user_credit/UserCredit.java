package com.idproger.credit.entitiy.user_credit;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "user_credits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userPassportNumber;
    private Long creditId;
    private int creditPeriod;

    @Column(precision = 15, scale = 2)
    private BigDecimal creditAmount;

    @Column(precision = 15, scale = 2)
    private BigDecimal monthlyPayment;
    private String status;

    @JsonManagedReference
    @OneToMany(mappedBy = "userCredit", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UserCreditCalc> calcs;
}
