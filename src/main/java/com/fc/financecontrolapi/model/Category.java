package com.fc.financecontrolapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "tb_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Boolean isActive;
    private Instant createdAt;
    private Instant deletedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Category(String name, String description, User user){
        this.name = name;
        this.description = description;
        this.user = user;
        this.isActive = true;
        this.createdAt = Instant.now();
    }
}
