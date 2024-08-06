package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMessage;
    private String message;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account sender;
    @ManyToOne
    @JoinColumn(name = "conservation_id")
    private Conservation conservation;
    private String is_view;
    private String status;
    private LocalDateTime createAt;
}
