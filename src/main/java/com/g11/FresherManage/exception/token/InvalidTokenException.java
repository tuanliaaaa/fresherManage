package com.g11.FresherManage.exception.token;

import com.g11.FresherManage.exception.base.UnauthorizedException;

public class InvalidTokenException extends UnauthorizedException {
    public InvalidTokenException(String message) {
       setMessage(message);
    }
}
