package org.example.supermercado.service;


import org.example.supermercado.model.Cliente;
import org.example.supermercado.response.ClienteResponseRest;
import org.springframework.http.ResponseEntity;

public interface IClienteService {


    public ResponseEntity<ClienteResponseRest> crear(Cliente cliente);
    public ResponseEntity<ClienteResponseRest> agregarClienteFila(Cliente cliente);
    public ResponseEntity<ClienteResponseRest> atenderSiguienteCliente();
    public ResponseEntity<ClienteResponseRest> obtenerFila();

}
