package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;
    private String username;
    private String password;
    private String avatar;
    private String firstName;

    private String lastName;
    private String email;
    private String phone;
    private String potition;
    private String is_active;
    @JoinColumn(name = "curentWorking_id")
    @OneToOne(cascade = CascadeType.ALL)
    private  Working curentWorking;
}
