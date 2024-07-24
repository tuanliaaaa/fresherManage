package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class AccountConservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAccountConservation;
    @ManyToOne
    @JoinColumn(name = "conservation_id")
    private Conservation conservation;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
