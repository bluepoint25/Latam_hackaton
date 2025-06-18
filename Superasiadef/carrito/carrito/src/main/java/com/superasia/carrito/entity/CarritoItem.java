package com.superasia.carrito.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carrito_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    @Column(nullable = false)
    private Long productoId;

    @Column(nullable = false)
    private Integer cantidad;
}