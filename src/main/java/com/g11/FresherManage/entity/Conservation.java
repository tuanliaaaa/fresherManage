package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Conservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConservation;
    @Column(unique=true)
    private String conservationName;
}
