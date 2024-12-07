package org.example.supermercadoo.controller;

import org.example.supermercadoo.model.Cliente;
import org.example.supermercadoo.response.ClienteResponseRest;
import org.example.supermercadoo.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class ClienteRestController {

    @Autowired
    private IClienteService clienteService;

    @PostMapping("/cliente")
    public ResponseEntity<ClienteResponseRest> crear(@RequestBody Cliente request){
        ResponseEntity<ClienteResponseRest>  response = clienteService.crear(request);
        return response;
    }

}