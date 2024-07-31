package com.g11.FresherManage.dto.response.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FresherAmountResponse
{
    private LocalDate date;
    private Long amount;
}
