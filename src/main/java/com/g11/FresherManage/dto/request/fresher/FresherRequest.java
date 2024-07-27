package com.g11.FresherManage.dto.request.fresher;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FresherRequest {
    @Schema(description = "Password", example = "tuan")
    @NotNull(message = "password is not Blank")
    private String password;
    @Schema(description = "Avartar", example = "/image/example.img")
    @NotNull(message = "avatar is not Blank")
    private String avatar;
    @Schema(description = "First Name", example = "Lương Nhật")
    @NotNull(message = "firstName is not Blank")
    private String firstName;
    @Schema(description = "Last Name", example = "Tuấn")
    @NotNull(message = "lastName is not Blank")
    private String lastName;
    @Schema(description = "Email Address", example = "nhattuan44t@gmail.com")
    @NotNull(message = "email is not Blank")
    private String email;
    @Schema(description = "Phone Number", example = "0379230864")
    @NotNull(message = "phone is not Blank")
    private String phone;
}
