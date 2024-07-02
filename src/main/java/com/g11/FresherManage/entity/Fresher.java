package com.g11.FresherManage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Fresher extends Employee{
    @OneToOne
    @JoinColumn(name = "language_id")
    private Language language;
}
