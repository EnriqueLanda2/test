package org.example.supermercadoo.service;


import org.example.supermercadoo.model.Producto;
import org.example.supermercadoo.response.ProductoResponseRest;
import org.springframework.http.ResponseEntity;

public interface IProductoService {


    public ResponseEntity<ProductoResponseRest> buscarProducto();
    public ResponseEntity<ProductoResponseRest> buscarPorId(Long id);
    public ResponseEntity<ProductoResponseRest> crear(Producto producto);
    public ResponseEntity<ProductoResponseRest> actualizar( Producto producto, Long id);
    public ResponseEntity<ProductoResponseRest> eliminar( Long id);


}
