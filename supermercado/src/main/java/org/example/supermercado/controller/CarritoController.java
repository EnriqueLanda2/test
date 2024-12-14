package org.example.supermercado.controller;

import org.apache.coyote.Response;
import org.example.supermercado.model.Carrito;
import org.example.supermercado.response.CarritoResponseRest;
import org.example.supermercado.response.ClienteResponseRest;
import org.example.supermercado.service.ClienteServiceImpl;
import org.example.supermercado.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.supermercado.service.ICarritoService;

@RestController
@RequestMapping("/v3")
public class CarritoController {

    @Autowired
    private ICarritoService carritoService;

    @GetMapping("/carrito/cliente/{clienteId}")
    public ResponseEntity<CarritoResponseRest> buscarCarritoPorCliente(@PathVariable Long clienteId) {
        // Llama al servicio con el clienteId
        ResponseEntity<CarritoResponseRest> response = carritoService.buscarCarritoPorCliente(clienteId);
        return response;
    }
    @PostMapping("/carrito")
    public ResponseEntity<CarritoResponseRest> crearCarrito(@RequestBody Carrito request) {
        ResponseEntity<CarritoResponseRest> response = carritoService.crearCarrito(request);
        return response;
    }


    @DeleteMapping("/carrito/cliente/{clienteId}/producto/{productoId}")
    public ResponseEntity<CarritoResponseRest> eliminarCarritoPorClienteYProducto(@PathVariable Long clienteId, @PathVariable Long productoId) {
        return carritoService.eliminarCarritoPorClienteYProducto(clienteId, productoId);
    }



    @PostMapping("/carrito/deshacer")
    public ResponseEntity<CarritoResponseRest> deshacerCarrito() {
        ResponseEntity<CarritoResponseRest> response = carritoService.deshacerCarrito();
        return  response;
    }



    @GetMapping("/supermercado/comprar/{clienteId}")
    public ResponseEntity<CarritoResponseRest> comprarCarrito(@PathVariable Long clienteId){
        ResponseEntity<CarritoResponseRest> response = carritoService.procesarCompra(clienteId);
        return response;
    }




}