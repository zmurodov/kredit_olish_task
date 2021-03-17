package com.idproger.credit.dto;

import com.idproger.credit.entitiy.user_credit.UserCredit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCreditResponse {

    private String message;
    private UserCredit userCredit;
}
