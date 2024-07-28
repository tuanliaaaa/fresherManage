package com.g11.FresherManage.dto.request.center;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CenterUpdateRequest {
    @Schema(description = "Working Name", example = "Mi1")
    private String workingName;
}
