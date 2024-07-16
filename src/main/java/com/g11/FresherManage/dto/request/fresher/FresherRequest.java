package com.g11.FresherManage.dto.request.fresher;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FresherRequest {
    @NotNull(message = "avatar is not Blank")
    private String avatar;
    @NotNull(message = "firstName is not Blank")
    private String firstName;
    @NotNull(message = "lastName is not Blank")
    private String lastName;
    @NotNull(message = "email is not Blank")
    private String email;
    @NotNull(message = "phone is not Blank")
    private String phone;
}
