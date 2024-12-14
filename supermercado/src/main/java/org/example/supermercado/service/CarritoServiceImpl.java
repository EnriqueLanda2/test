package org.example.supermercado.service;

import org.example.supermercado.model.Carrito;

import org.example.supermercado.model.Cliente;
import org.example.supermercado.model.Producto;
import org.example.supermercado.model.dao.ICarritoDao;

import org.example.supermercado.model.dao.IClienteDao;
import org.example.supermercado.model.dao.IProductoDao;
import org.example.supermercado.response.CarritoResponseRest;

import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CarritoServiceImpl implements ICarritoService {
    private static final Logger log = LoggerFactory.getLogger(CarritoServiceImpl.class);

    @Autowired
    private ICarritoDao carritoDao;

    @Autowired
    private IClienteDao clienteDao;

    @Autowired
    private IProductoDao productoDao;

    private final Stack<Carrito> ultimaEliminacion = new Stack<>();

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CarritoResponseRest> buscarCarritoPorCliente(Long clienteId) {
        log.info("Buscar carritos por Cliente ID: {}", clienteId);
        CarritoResponseRest response = new CarritoResponseRest();

        try {
            Optional<Cliente> cliente = clienteDao.findById(clienteId);
            if (!cliente.isPresent()) {
                log.warn("Cliente no encontrado con ID: {}", clienteId);
                response.setMetada("Error", "-1", "Cliente no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            List<Carrito> carritos = carritoDao.findByCliente(cliente.get());
            if (!carritos.isEmpty()) {
                response.getCarritoResponse().setCarrito(carritos);
                response.setMetada("Respuesta OK", "00", "Carritos encontrados");
            } else {
                log.info("No se encontraron carritos para el Cliente ID: {}", clienteId);
                response.setMetada("Error", "-1", "Carritos no encontrados");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error al buscar carritos por cliente", e);
            response.setMetada("Error", "-1", "Error interno en el servidor");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CarritoResponseRest> crearCarrito(Carrito carrito) {
        log.info("Creando carrito");
        CarritoResponseRest response = new CarritoResponseRest();
        List<Carrito> list = new ArrayList<>();

        try {
            Carrito carritoGuardar = carritoDao.save(carrito);
            if (carritoGuardar != null) {
                list.add(carritoGuardar);
                response.getCarritoResponse().setCarrito(list);
                response.setMetada("Respuesta OK", "00", "Creación Exitosa");
            } else {
                response.setMetada("Error", "-1", "Carrito no creado");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Error al crear carrito", e);
            response.setMetada("Error", "-1", "Error interno al crear el carrito");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Override
    @Transactional
    public ResponseEntity<CarritoResponseRest> eliminarCarritoPorClienteYProducto(Long clienteId, Long productoId) {
        log.info("Eliminando carrito para Cliente ID: {} y Producto ID: {}", clienteId, productoId);
        CarritoResponseRest response = new CarritoResponseRest();

        try {
            Optional<Cliente> cliente = clienteDao.findById(clienteId);
            Optional<Producto> producto = productoDao.findById(productoId);

            if (!cliente.isPresent() || !producto.isPresent()) {
                log.warn("Cliente o Producto no encontrado");
                response.setMetada("Error", "-1", "Cliente o Producto no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            List<Carrito> carritos = carritoDao.findByCliente(cliente.get());
            Carrito carritoEliminar = null;
            for (Carrito c : carritos) {
                // Cambiar el tipo de comparación para usar longValue() en lugar de equals()
                if (c.getProducto().getId() == productoId.longValue()) {
                    carritoEliminar = c;
                    break;
                }
            }

            if (carritoEliminar != null) {
                // Guardamos el carrito antes de eliminarlo
                ultimaEliminacion.push(carritoEliminar);
                log.info("Carrito a eliminar: {}", carritoEliminar.getCliente().getNombre());

                // Eliminar el carrito
                carritoDao.delete(carritoEliminar);
                response.setMetada("Respuesta OK", "00", "Carrito eliminado exitosamente");
            } else {
                response.setMetada("Error", "-1", "Carrito no encontrado para el Producto y Cliente especificados");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error al eliminar carrito", e);
            response.setMetada("Error", "-1", "Error interno al eliminar el carrito");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CarritoResponseRest> deshacerCarrito() {
        CarritoResponseRest response = new CarritoResponseRest();

        try {
            if (ultimaEliminacion.isEmpty()) {
                response.setMetada("Error", "404", "No hay eliminaciones recientes para deshacer");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Recuperar el carrito de la pila
            Carrito carritoRestaurado = ultimaEliminacion.pop();

            log.info("Carrito restaurado: {}", carritoRestaurado.getCliente().getId());
            log.info("Carrito restaurado: {}", carritoRestaurado.getProducto().getId());
            log.info("Carrito restaurado: {}", carritoRestaurado.getCantidad());
            log.info("Carrito restaurado: {}", carritoRestaurado.getId());

            carritoDao.insertarCarrito(carritoRestaurado.getId(),carritoRestaurado.getCantidad(),carritoRestaurado.getCliente().getId(), carritoRestaurado.getProducto().getId());
            response.setMetada("Respuesta OK", "00", "Eliminación deshecha exitosamente");
        } catch (Exception e) {
            log.error("Error al deshacer la eliminación", e);
            response.setMetada("Error", "-1", "Error al deshacer la eliminación");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}



