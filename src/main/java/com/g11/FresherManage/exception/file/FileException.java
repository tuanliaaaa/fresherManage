package com.g11.FresherManage.exception.file;

import com.g11.FresherManage.exception.base.BaseException;

import java.util.HashMap;
import java.util.Map;

public class FileException extends BaseException {
    private String message;
    private String code;
    private int status;
    private Map<String, String> params;

    public FileException(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.params = new HashMap<>();
    }
}