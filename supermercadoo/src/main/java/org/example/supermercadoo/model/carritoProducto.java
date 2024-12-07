package org.example.supermercadoo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class carritoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false) // Llave foránea hacia Cliente
    @JsonIgnore // Para evitar problemas de serialización circular
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false) // Llave foránea hacia Producto
    private Producto producto;


    }

