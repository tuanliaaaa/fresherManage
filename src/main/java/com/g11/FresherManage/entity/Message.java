package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMessage;
    private String message;
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account sender;
    @ManyToOne
    @JoinColumn(name = "conservation_id")
    private Conservation conservation;
    private String is_view;
    private String status;
}
