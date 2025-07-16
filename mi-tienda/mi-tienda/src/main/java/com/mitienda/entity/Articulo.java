package com.mitienda.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) //
    private String nombre;

    @Column(nullable = false)
    private String categoria;

    private String descripcion;
    private Double precio;
    private Integer stock;

}
