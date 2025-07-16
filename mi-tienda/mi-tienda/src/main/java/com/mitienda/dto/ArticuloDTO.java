package com.mitienda.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloDTO {
    public Long id;
    private String nombre;
    private BigDecimal precio;
    private Integer stock;
    public String categoria;
    public String descripcion;



}
