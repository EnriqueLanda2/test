package org.example.supermercadoo.response;

import org.example.supermercadoo.model.Producto;

import java.util.List;

public class ProductoResponse {

    private List<Producto> producto;
    public List<Producto> getProducto() {
        return producto;
    }

    public void setProducto(List<Producto> producto) {
        this.producto = producto;
    }

}
