package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int testId;
    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;
}
