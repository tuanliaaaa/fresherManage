package com.g11.FresherManage.dto.response.fresher;

import com.g11.FresherManage.entity.Language;
import com.g11.FresherManage.entity.Working;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FresherResponse {
    private Language language;
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String status;
    private Working working;
}
