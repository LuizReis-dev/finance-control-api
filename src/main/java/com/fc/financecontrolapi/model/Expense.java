package com.fc.financecontrolapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "tb_expenses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id", nullable=false)
    private Category category;
    @Column(nullable = false)
    private Integer amount;
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Instant createdAt;
    private Instant deletedAt;
}
