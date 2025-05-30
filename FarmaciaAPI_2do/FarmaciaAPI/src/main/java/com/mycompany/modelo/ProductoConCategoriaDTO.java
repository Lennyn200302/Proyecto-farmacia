package com.mycompany.modelo;

import java.time.LocalDate;

public class ProductoConCategoriaDTO {

    private String nombreCategoria;
    private String nombreProducto;
    private double precio;
    private int stock;
    private LocalDate fechaVencimiento;

    public ProductoConCategoriaDTO() {
    }

    public ProductoConCategoriaDTO(String nombreCategoria, String nombreProducto, double precio, int stock, LocalDate fechaVencimiento) {
        this.nombreCategoria = nombreCategoria;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.stock = stock;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
