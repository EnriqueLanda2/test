package org.example.supermercadoo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "carrito_producto")
public class CarritoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private int cantidad;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false) // Llave foránea hacia Cliente
    @JsonIgnore
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false) // Llave foránea hacia Producto
    private Producto producto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    }

