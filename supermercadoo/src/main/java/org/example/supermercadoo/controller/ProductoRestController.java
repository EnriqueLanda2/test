package org.example.supermercadoo.controller;

import org.example.supermercadoo.model.Producto;
import org.example.supermercadoo.response.ProductoResponseRest;
import org.example.supermercadoo.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2")
public class ProductoRestController {

    @Autowired
    private IProductoService productoService;
//
//    @GetMapping("/producto")
//
//    public ResponseEntity<ProductoResponseRest> consultaCat(){
//        ResponseEntity<ProductoResponseRest>  response = productoService.buscarProducto();
//        return response;
//
//    }
//
//    @GetMapping("/producto/{id}")
//    public ResponseEntity<ProductoResponseRest> consultaPorId(@PathVariable Long id){
//        ResponseEntity<ProductoResponseRest>  response = productoService.buscarPorId(id);
//        return response;
//    }
         
    @PostMapping("/producto")
    public ResponseEntity<ProductoResponseRest> crear(@RequestBody Producto request){
        ResponseEntity<ProductoResponseRest>  response = productoService.crear(request);
        return response;
    }
//
//    @PutMapping("/producto/{id}")
//    public ResponseEntity<ProductoResponseRest> actualizar(@RequestBody Producto request, @PathVariable Long id ){
//        ResponseEntity<ProductoResponseRest>  response = productoService.actualizar(request, id);
//        return response;
//    }
//
//    @DeleteMapping("/producto/{id}")
//    public ResponseEntity<ProductoResponseRest> eliminar(@PathVariable Long id){
//        ResponseEntity<ProductoResponseRest>  response = productoService.eliminar(id);
//        return response;
//    }

}