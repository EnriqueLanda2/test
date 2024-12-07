package org.example.supermercadoo.service;

import org.example.supermercadoo.model.Cliente;
import org.example.supermercadoo.response.CarritoResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICarritoService {
    public ResponseEntity<CarritoResponseRest> buscarCategorias();
    public ResponseEntity<CarritoResponseRest> buscarPorId(Long id);
    public ResponseEntity<CarritoResponseRest> crear(Cliente cliente);
    public ResponseEntity<CarritoResponseRest> actualizar( Cliente cliente, Long id);
    public ResponseEntity<CarritoResponseRest> eliminar( Long id);
}
