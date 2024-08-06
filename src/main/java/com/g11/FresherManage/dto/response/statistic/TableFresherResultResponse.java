package com.g11.FresherManage.dto.response.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableFresherResultResponse {
    private Double test1;
    private Double test2;
    private Double test3;
    private Double avg;
    private String username;
    private String marketname;
    private String centername;
}
