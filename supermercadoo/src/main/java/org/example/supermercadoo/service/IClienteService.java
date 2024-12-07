package org.example.supermercadoo.service;


import org.example.supermercadoo.model.Cliente;
import org.example.supermercadoo.response.ClienteResponseRest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface IClienteService {

    public ResponseEntity<ClienteResponseRest> buscarClientes();
    public ResponseEntity<ClienteResponseRest> buscarPorId(Long id);
    public ResponseEntity<ClienteResponseRest> crear(Cliente cliente);

    @Transactional
    ResponseEntity<ClienteResponseRest> actualizar(Cliente cliente, Long id);
}
