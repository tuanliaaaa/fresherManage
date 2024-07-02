package com.g11.FresherManage.exception.File;

import com.g11.FresherManage.exception.base.NotFoundException;

public class FileEmptyException extends NotFoundException {
    public FileEmptyException() {
        setMessage("File is empty");
    }
}
