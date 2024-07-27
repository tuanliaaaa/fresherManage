package com.g11.FresherManage.dto.response.account;


import com.g11.FresherManage.dto.response.role.RoleResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InforAccountLoginResponse {
    private int iduser;
    private String username;
    private String avatar;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String position;
    private String is_active;
    private String curentWorking;
    private Set<RoleResponse> roles;
    public InforAccountLoginResponse(List<Object[]> results)
    {
        this.setIduser((Integer)results.get(0)[0]);
        this.setUsername((String) results.get(0)[1]);
        this.setAvatar((String) results.get(0)[2]);
        this.setFirstName((String) results.get(0)[3]);
        this.setLastName((String) results.get(0)[4]);
        this.setEmail((String) results.get(0)[5]);
        this.setPhone((String) results.get(0)[6]);
        this.setPosition((String) results.get(0)[7]);
        this.setIs_active((String) results.get(0)[8]);
        this.setCurentWorking((String) results.get(0)[9]);

        Set<RoleResponse> roles = results.stream()
                .map(result -> {
                    RoleResponse roleResponse = new RoleResponse();
                    roleResponse.setIdRole((Integer)result[10]);
                    roleResponse.setRoleName((String) result[11]);
                    return roleResponse;
                })
                .collect(Collectors.toSet());
        this.setRoles(roles);
    }
}