package com.g11.FresherManage.exception.file;

import com.g11.FresherManage.exception.base.NotFoundException;

public class FileEmptyException extends NotFoundException {
    public FileEmptyException() {
        setMessage("File is empty");
    }
}
