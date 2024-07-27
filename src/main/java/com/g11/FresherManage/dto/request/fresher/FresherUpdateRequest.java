package com.g11.FresherManage.dto.request.fresher;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FresherUpdateRequest
{
    @Schema(description = "First Name", example = "Lương Nhật")
    private String firstName;
    @Schema(description = "Last Name", example = "Tuấn")
    private String lastName;
    @Schema(description = "Email Address", example = "nhattuan44t@gmail.com")
    private String email;
    @Schema(description = "Phone Number", example = "0379230864")
    private String phone;
}
