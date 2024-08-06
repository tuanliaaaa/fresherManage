package com.g11.FresherManage.dto.response.fresher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FresherResponse {
    private int idUser;
    private String username;
    private String avatar;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String position;
    private String is_active;
    private  String curentWorking;
}
