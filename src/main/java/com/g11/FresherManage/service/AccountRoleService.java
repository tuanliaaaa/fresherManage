package com.g11.FresherManage.service;

import java.util.List;

public interface AccountRoleService {
    List<String> findRolesByUserLoging();
    String getUsername();
}
