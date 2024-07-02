package com.g11.FresherManage.dto.response;

import com.g11.FresherManage.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private int idRole;
    private String roleName;
    public RoleResponse(Role role){
        this.idRole=role.getIdRole();
        this.roleName=role.getRoleName();
    }
}
