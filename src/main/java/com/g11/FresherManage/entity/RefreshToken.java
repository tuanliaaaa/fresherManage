package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int refreshTokenId;
    private String refreshToken;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
