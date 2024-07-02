package com.g11.FresherManage.exception.File;

import com.g11.FresherManage.exception.base.NotFoundException;

public class FileLocationException extends NotFoundException {


    public FileLocationException(String message) {
        setMessage(message); // Gọi constructor của lớp cha và truyền thông điệp được chỉ định
    }
}
