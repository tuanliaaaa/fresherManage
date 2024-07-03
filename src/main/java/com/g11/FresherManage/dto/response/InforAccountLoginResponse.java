package com.g11.FresherManage.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InforAccountLoginResponse {
    private int iduser;
    private String dtype;
    private String username;
    private String avatar;
    private Boolean is_active;
}