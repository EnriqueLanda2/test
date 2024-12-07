package org.example.supermercadoo.response;

import org.example.supermercadoo.model.CarritoProducto;
import org.example.supermercadoo.model.Cliente;

import java.util.List;

public class CarritoResponse {
    private List<CarritoProducto> carritoProducto;
    public List<CarritoProducto> getCarritoProducto() {
        return carritoProducto;
    }

    public void setCarritoProducto(List<CarritoProducto> carritoProducto) {
        this.carritoProducto = carritoProducto;
    }
}
