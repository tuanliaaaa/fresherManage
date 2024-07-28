package com.g11.FresherManage.dto.request.market;

import com.g11.FresherManage.entity.Working;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketRequest
{
    @Schema(description = "Center Name", example = "Mi21")
    @NotNull(message = "workingName is not Blank")
    private String workingName;
}