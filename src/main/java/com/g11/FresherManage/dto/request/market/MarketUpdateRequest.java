package com.g11.FresherManage.dto.request.market;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketUpdateRequest {
    @Schema(description = "Working Name", example = "Mi1")
    private String workingName;
}
