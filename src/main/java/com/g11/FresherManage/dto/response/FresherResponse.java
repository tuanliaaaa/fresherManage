package com.g11.FresherManage.dto.response;

import com.g11.FresherManage.entity.Account;
import com.g11.FresherManage.entity.Language;
import com.g11.FresherManage.entity.Working;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
