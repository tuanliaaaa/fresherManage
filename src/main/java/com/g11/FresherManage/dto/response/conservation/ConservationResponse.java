package com.g11.FresherManage.dto.response.conservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConservationResponse {
    private int idConservation;
    private String conservationName;
}
