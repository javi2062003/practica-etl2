package com.practica.mdm2.mdm2.etl.model;

import java.security.Timestamp;
import java.time.LocalDateTime;

// AÑADE ESTOS IMPORTS
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // Le dice a JPA que esta clase es una tabla de BD
@Table(name = "catalogo_productos") // Conecta esta clase a la tabla "products" de tu schema.sql

public class ProductoFinal {

    @Id // Marca este campo como la clave primaria (ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // El ID se genera automáticamente por la BD
    private Integer id_producto;
    private String sku;
    private String nombre_normalizado;
    private Double precio_eur;
    private Integer id_categoria;
    private String fuente_proveedor;
    private LocalDateTime fecha_actualizacion;
    

    public ProductoFinal() {}


        public ProductoFinal(Integer id_producto, String sku, String nombre_normalizado, Double precio_eur, Integer id_categoria,
            String fuente_proveedor, LocalDateTime fecha_actualizacion) {
        this.id_producto = id_producto;
        this.sku = sku;
        this.nombre_normalizado = nombre_normalizado;
        this.precio_eur = precio_eur;
        this.id_categoria = id_categoria;
        this.fuente_proveedor = fuente_proveedor;
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public Integer getId() {
        return id_producto;
    }

    public void setId(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre_normalizado;
    }

    public void setNombre(String nombre_normalizado) {
        this.nombre_normalizado = nombre_normalizado;
    }

    public Double getPrecio() {
        return precio_eur;
    }

    public void setPrecio(Double precio_eur) {
        this.precio_eur = precio_eur;
    }

    public Integer getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Integer id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getFuenteProveedor() {
        return fuente_proveedor;
    }

    public void setFuenteProveedor(String fuente_proveedor) {
        this.fuente_proveedor = fuente_proveedor;
    }

    public LocalDateTime getFechaActualizacion() {
        return fecha_actualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }
    
}
