package org.example.supermercado.service;

// import jakarta.transaction.Transactional;

import org.example.supermercado.model.Cliente;
import org.example.supermercado.model.dao.IClienteDao;
import org.example.supermercado.response.ClienteResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ClienteServiceImpl implements IClienteService {
    private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);
    @Autowired
    private IClienteDao clienteDao;

    private final Queue<Cliente> clientesFila = new LinkedList<>();


    @Override
    @Transactional
    public ResponseEntity<ClienteResponseRest> crear(Cliente cliente) {

        // public ResponseEntity<CategoriaResponseRest> buscarCategorias() {
//        log.info("Buscando categorias");
        log.info("Buscar por ID");
        ClienteResponseRest response = new ClienteResponseRest();
        List<Cliente> list = new ArrayList<>();
        try {
            Cliente clienteGuardar = clienteDao.save(cliente);
            if (clienteGuardar != null) {
                list.add(clienteGuardar);
                response.getClienteResponse().setCliente(list);
                clientesFila.add(clienteGuardar);
                response.setMetada("Respuesta OK", "00", "Creacion Exitosa");
            } else {
                log.info("No se encontro la categoria");
                response.setMetada("Respuesta no encontrada", "-1", "Categoria no creada");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setMetada("Respuesta FALLIDA", "-1", "Error al crear la categoria");
            log.error("Error al guardar categorias", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.OK);


    }


    @Override
    @Transactional
    public ResponseEntity<ClienteResponseRest> agregarClienteFila(Cliente cliente) {
        ClienteResponseRest response = new ClienteResponseRest();

        try {
            // Agregamos el cliente a la fila
            clientesFila.add(cliente);
            clienteDao.save(cliente); // Guardamos el cliente en la base de datos
            response.setMetada("Respuesta OK", "00", "Cliente agregado a la fila");
        } catch (Exception e) {
            response.setMetada("Respuesta FALLIDA", "-1", "Error al agregar el cliente a la fila");
            log.error("Error al agregar el cliente a la fila", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ClienteResponseRest> atenderSiguienteCliente() {
        ClienteResponseRest response = new ClienteResponseRest();

        try {
            // Si hay clientes en la fila, atendemos al siguiente
            Cliente clienteAtendido = clientesFila.poll();  // Atiende al siguiente cliente (el primero de la fila)

            if (clienteAtendido != null) {
                // Cliente atendido correctamente
                log.info("Cliente atendido: " + clienteAtendido.getId());

                // Preparar la respuesta
                response.setMetada("Respuesta OK", "00", "Cliente atendido exitosamente");
            } else {
                // Si no hay clientes en la fila
                response.setMetada("No hay clientes", "404", "No hay clientes en la fila para atender");
            }

            // Devolver la lista de clientes restantes
            List<Cliente> clientesRestantes = new ArrayList<>(clientesFila);
            response.getClienteResponse().setCliente(clientesRestantes);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al atender al siguiente cliente", e);
            response.setMetada("Error", "-1", "Error al atender al siguiente cliente");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    @Transactional
    public ResponseEntity<ClienteResponseRest> obtenerFila(){
        ClienteResponseRest response = new ClienteResponseRest();

        return new ResponseEntity<>(HttpStatus.OK);


    }




}
