package com.g11.FresherManage.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {
    @NotBlank(message = "refreshToken is mandatory")
    @Schema(description = "This is refresh Token", example ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzIxOTY2ODgyLCJleHAiOjE3MjE5ODQ4ODJ9.kRBv--lYUYMhWtsSduWFC7nC6qcT4TTGq63LbuC_4mQ")
    private String refreshToken;
}
