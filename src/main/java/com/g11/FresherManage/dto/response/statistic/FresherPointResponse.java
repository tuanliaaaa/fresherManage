package com.g11.FresherManage.dto.response.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FresherPointResponse
{
    private Double rankPoint;
    private Integer amount;

}
