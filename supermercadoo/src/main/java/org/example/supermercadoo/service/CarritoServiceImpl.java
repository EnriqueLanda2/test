package org.example.supermercadoo.service;

import org.example.supermercadoo.model.CarritoProducto;
import org.example.supermercadoo.model.doa.ICarritoProductoDao;
import org.example.supermercadoo.response.CarritoResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoServiceImpl implements ICarritoService {
    private static final Logger log = LoggerFactory.getLogger(CarritoServiceImpl.class);
    @Autowired
    private ICarritoProductoDao carritoProductoDao;

    @Override
    @Transactional(readOnly = true)

    public ResponseEntity<CarritoResponseRest> crear(CarritoProducto carritoProducto) {

        log.info("Buscar por ID");
        CarritoResponseRest response = new CarritoResponseRest();
        List<CarritoProducto> list = new ArrayList<>();
        try {
            CarritoProducto carritoGuardar = carritoProductoDao.save(carritoProducto);
            if (carritoGuardar != null) {
                list.add(carritoGuardar);
                response.getCarritoResponse().setCarritoProducto(list);
                response.setMetada("Respuesta OK", "00", "Creacion Exitosa");
            } else {
                log.info("No se encontro el carrito");
                response.setMetada("Respuesta no encontrada", "-1", "Carrito no registrado");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setMetada("Respuesta FALLIDA", "-1", "Error al registrar el carrito");
            log.error("Error al guardar carrito", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<CarritoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CarritoResponseRest>(response, HttpStatus.OK);
    }


    public ResponseEntity<CarritoResponseRest> eliminar(Long id) {
        log.info("Eliminando carrito");
        CarritoResponseRest response = new CarritoResponseRest();
        List<CarritoProducto> list = new ArrayList<>();
        try {
            carritoProductoDao.deleteById(id);
            response.setMetada("Respuesta Ok", "00" ,"Eliminacion exitosa");
        }catch (Exception e) {
            response.setMetada("Error", "-1","Error al eliminar el cliente");
            log.error("Error al eliminar el carrito", e.getMessage());
            e.getStackTrace();
            return  new ResponseEntity<CarritoResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CarritoResponseRest>(response, HttpStatus.OK);
    }

}
