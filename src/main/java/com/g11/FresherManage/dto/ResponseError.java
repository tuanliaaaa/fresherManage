package com.g11.FresherManage.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.g11.FresherManage.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ResponseError <T>{
    private int status;
    private String message;
    private T error;
    private String timestamp;
    public ResponseError(T responseGeneral, T httpStatus) {
    }
    public static <T> ResponseError<T> of(int status, String message, T error) {
        return of(status, message, error, DateUtils.getCurrentDateString());
    }
}
