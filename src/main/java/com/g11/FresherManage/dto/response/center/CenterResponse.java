package com.g11.FresherManage.dto.response.center;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CenterResponse {
    private int workingId;
    private String workingName;
    private String workingType;
    private String workingStatus;
}
