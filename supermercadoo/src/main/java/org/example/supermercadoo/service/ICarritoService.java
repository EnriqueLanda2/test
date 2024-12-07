package org.example.supermercadoo.service;

import org.example.supermercadoo.model.CarritoProducto;
import org.example.supermercadoo.model.Cliente;
import org.example.supermercadoo.response.CarritoResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICarritoService {

    public ResponseEntity<CarritoResponseRest> crear(CarritoProducto carritoProducto);;
    public ResponseEntity<CarritoResponseRest> eliminar(Long id);
}
