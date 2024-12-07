package org.example.supermercadoo.service;


import org.example.supermercadoo.model.Cliente;
import org.example.supermercadoo.response.ClienteResponseRest;
import org.springframework.http.ResponseEntity;

public interface IClienteService {


<<<<<<< HEAD
    public ResponseEntity<ClienteResponseRest> buscarClientes();
    public ResponseEntity<ClienteResponseRest> buscarPorId(Long id);
=======
//    public ResponseEntity<ClienteResponseRest> buscarCategorias();
//    public ResponseEntity<ClienteResponseRest> buscarPorId(Long id);
>>>>>>> 71fef304e6960bc66402b86d5d7beb37122b5b1a
    public ResponseEntity<ClienteResponseRest> crear(Cliente cliente);
//    public ResponseEntity<ClienteResponseRest> actualizar( Cliente cliente, Long id);
//    public ResponseEntity<ClienteResponseRest> eliminar( Long id);


}
