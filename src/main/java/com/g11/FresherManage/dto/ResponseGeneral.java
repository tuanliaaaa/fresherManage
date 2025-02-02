package com.g11.FresherManage.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.g11.FresherManage.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ResponseGeneral<T> {
    private int status;
    private String message;
    private T data;
    private String timestamp;

    public ResponseGeneral(T responseGeneral, T httpStatus) {
    }

    public static <T> ResponseGeneral<T> of(int status, String message, T data) {
        return of(status, message, data, DateUtils.getCurrentDateString());
    }

    public static <T> ResponseGeneral<T> ofCreated(String message, T data) {
        return of( HttpStatus.CREATED.value(), message, data, DateUtils.getCurrentDateString());
    }

    public static <T> ResponseGeneral<T> ofSuccess(String message) {
       return  of(HttpStatus.OK.value(), message, null, DateUtils.getCurrentDateString());
    }
    public static <T> ResponseGeneral<T> ofNocontent(String message) {
        return  of(HttpStatus.NO_CONTENT.value(), message, null, DateUtils.getCurrentDateString());
    }
}