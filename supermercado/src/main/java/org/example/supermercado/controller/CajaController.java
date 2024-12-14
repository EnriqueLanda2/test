package org.example.supermercado.controller;

import org.example.supermercado.model.Carrito;
import org.example.supermercado.model.Cliente;
import org.example.supermercado.response.CarritoResponseRest;
import org.example.supermercado.response.ClienteResponseRest;
import org.example.supermercado.service.ClienteServiceImpl;
import org.example.supermercado.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Queue;


@RestController
@RequestMapping("/v4")
public class CajaController {
    @Autowired
    private IClienteService clienteService;
    // POST /caja/agregar: Agrega un cliente a la fila
    @PostMapping("/cliente/fila/agregar")
    public ResponseEntity<ClienteResponseRest> crearClienteCola(@RequestBody Cliente request) {
        ResponseEntity<ClienteResponseRest> response = clienteService.agregarClienteFila(request);
        return response;
    }

    @GetMapping("/cliente/caja/atender")
    public ResponseEntity<ClienteResponseRest> atenderSiguienteCliente() {
        // Llama al servicio sin pasar un clienteId para atender al siguiente cliente de la fila
        return clienteService.atenderSiguienteCliente();
    }


    @GetMapping("/cliente/obtenerFila")
    public ResponseEntity<ClienteResponseRest> obtenerFila() {
        // Llama al servicio para obtener la fila
        ResponseEntity<ClienteResponseRest> response = clienteService.obtenerFila();
        return response;
    }



}
