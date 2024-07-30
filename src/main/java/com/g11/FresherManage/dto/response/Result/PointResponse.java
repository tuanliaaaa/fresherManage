package com.g11.FresherManage.dto.response.Result;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PointResponse {
    private float Lessson1;
    private float Lessson2;
    private float Lessson3;
    private float average;
}
