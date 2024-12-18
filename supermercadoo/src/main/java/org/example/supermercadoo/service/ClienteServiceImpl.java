package org.example.supermercadoo.service;

// import jakarta.transaction.Transactional;

import org.example.supermercadoo.model.Cliente;
import org.example.supermercadoo.model.doa.IClienteDao;
import org.example.supermercadoo.response.ClienteResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements IClienteService {
    private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);
    @Autowired
    private IClienteDao clienteDao;


    @Override
    @Transactional(readOnly = true)

    public ResponseEntity<ClienteResponseRest> buscarClientes() {
        log.info("Buscando clientes");
        ClienteResponseRest response = new ClienteResponseRest();

        try {
            List<Cliente> cliente = (List<Cliente>) clienteDao.findAll();
            response.getClienteResponse().setCliente(cliente);
            response.setMetada("Respuesta OK", "00", "Respuesta exitosa");
        } catch (Exception e) {
            response.setMetada("Respuesta FALLIDA", "-1", "Respuesta fallida");
            log.error("Error al buscar clientes", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)


    public ResponseEntity<ClienteResponseRest> buscarPorId(Long id) {


        log.info("Buscar por ID");
        ClienteResponseRest response = new ClienteResponseRest();
        List<Cliente> list = new ArrayList<>();
        try {
            Optional<Cliente> cliente = clienteDao.findById(id);
            if (cliente.isPresent()) {
                list.add(cliente.get());
                response.getClienteResponse().setCliente(list);
                response.setMetada("Respuesta OK", "00", "Respuesta exitosa");
            } else {
                log.info("No se encontro el cliente");
                response.setMetada("Respuesta no encontrada", "-1", "Cliente no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetada("Respuesta FALLIDA", "-1", "Respuesta fallida");
            log.error("Error al buscar clientes", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ClienteResponseRest> crear(Cliente cliente) {

        log.info("Buscar por ID");
        ClienteResponseRest response = new ClienteResponseRest();
        List<Cliente> list = new ArrayList<>();
        try {
            Cliente clienteGuardar = clienteDao.save(cliente);
            if (clienteGuardar != null) {
                list.add(clienteGuardar);
                response.getClienteResponse().setCliente(list);
                response.setMetada("Respuesta OK", "00", "Creacion Exitosa");
            } else {
                log.info("No se encontro el cliente");
                response.setMetada("Respuesta no encontrada", "-1", "Cliente no registrado");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setMetada("Respuesta FALLIDA", "-1", "Error al registrar el cliente");
            log.error("Error al guardar clientes", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<ClienteResponseRest> actualizar(Cliente cliente, Long id) {


        log.info("Actualizando clientes");
        ClienteResponseRest response = new ClienteResponseRest();
        List<Cliente> list = new ArrayList<>();
        try {
            Optional<Cliente> clienteBuscado = clienteDao.findById(id);
            if (clienteBuscado.isPresent()) {

                clienteBuscado.get().setNombre(cliente.getNombre());


                Cliente clienteActualizar = clienteDao.save(clienteBuscado.get());
                if (clienteActualizar != null) {
                    list.add(clienteActualizar);
                    response.getClienteResponse().setCliente(list);
                    response.setMetada("Respuesta OK", "00", "Actualizacion exitosa");

                } else {
                    log.info("No se encontro el cliente");
                    response.setMetada("Respuesta no encontrada", "-1", "Cliente no actualizado");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

            } else {
                log.info("No se pudo encontrar el cliente");
                response.setMetada("Respuesta no encontrada", "-1", "Cliente no encontrado");
                return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetada("Respuesta FALLIDA", "-1", "Error al actualizar el cliente");
            log.error("Error al guardar categorias", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.OK);
    }

    public ResponseEntity<ClienteResponseRest> eliminar(Long id) {
        log.info("Eliminando cliente");
        ClienteResponseRest response = new ClienteResponseRest();
        List<Cliente> list = new ArrayList<>();
        try {
            clienteDao.deleteById(id);
            response.setMetada("Respuesta Ok", "00" ,"Eliminacion exitosa");
        }catch (Exception e) {
            response.setMetada("Error", "-1","Error al eliminar el cliente");
            log.error("Error al eliminar el cliente", e.getMessage());
            e.getStackTrace();
            return  new ResponseEntity<ClienteResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ClienteResponseRest>(response, HttpStatus.OK);
    }
}
