package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Working {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int workingId;
}
