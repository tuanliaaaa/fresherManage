package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;
    @Column(unique = true)
    private String username;
    private String password;
    private String avatar;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String position;
    private String is_active;
    private LocalDate createdAt;
    private LocalDate endAt;

    private  String curentWorking;
    public  Account(  String password, String avatar, String firstName,
                    String lastName, String email, String phone, String position
                    ) {
        this.username = email;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.avatar = avatar;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.is_active = "active";
        this.curentWorking = "";
    }
}
