package com.idproger.credit.vo;

import com.idproger.credit.entitiy.credit.Credit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplateVo {
    private User user;
    private Credit credit;
}
