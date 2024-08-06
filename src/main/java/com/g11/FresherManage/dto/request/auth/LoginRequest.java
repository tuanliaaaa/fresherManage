package com.g11.FresherManage.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class LoginRequest {
    @Schema(description = "The user's username")
    @NotBlank(message = "username is mandatory")
    private String username;
    @Schema(description = "The user's password")
    @NotBlank(message = "password is mandatory")
    private String password;
}
