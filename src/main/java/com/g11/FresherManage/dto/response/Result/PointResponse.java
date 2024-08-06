package com.g11.FresherManage.dto.response.Result;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PointResponse {
    private Double Lessson1;
    private Double Lessson2;
    private Double Lessson3;
    private Double average;
}
