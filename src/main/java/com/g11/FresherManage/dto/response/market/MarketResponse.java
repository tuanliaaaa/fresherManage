package com.g11.FresherManage.dto.response.market;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketResponse {
    private int workingId;
    private String workingName;
    private String workingType;
    private String workingStatus;
}
