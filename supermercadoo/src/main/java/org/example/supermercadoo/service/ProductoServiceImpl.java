package org.example.supermercadoo.service;

import org.example.supermercadoo.model.Producto;
import org.example.supermercadoo.model.doa.IProductoDao;
import org.example.supermercadoo.response.ProductoResponseRest;
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
public class ProductoServiceImpl implements IProductoService {
    private static final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);
    @Autowired
    private IProductoDao productoDao;

    @Override
    @Transactional(readOnly = true)

    public ResponseEntity<ProductoResponseRest> buscarProducto() {
        log.info("Buscando categorias");
        ProductoResponseRest response = new ProductoResponseRest();

        try {
            List<Producto> productos = (List<Producto>) productoDao.findAll();
            response.getProductoResponse().setProducto(productos);
            response.setMetada("Respuesta OK", "00", "Respuesta exitosa");
        } catch (Exception e) {
            response.setMetada("Respuesta FALLIDA", "-1", "Respuesta fallida");
            log.error("Error al buscar categorias", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)


    public ResponseEntity<ProductoResponseRest> buscarPorId(Long id) {


        log.info("Buscar por ID");
        ProductoResponseRest response = new ProductoResponseRest();
        List<Producto> list = new ArrayList<>();
        try {
            Optional<Producto> producto = productoDao.findById(id);
            if (producto.isPresent()) {
                list.add(producto.get());
                response.getProductoResponse().setProducto(list);
                response.setMetada("Respuesta OK", "00", "Respuesta exitosa");
            } else {
                log.info("No se encontro la categoria");
                response.setMetada("Respuesta no encontrada", "-1", "Categoria no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetada("Respuesta FALLIDA", "-1", "Respuesta fallida");
            log.error("Error al buscar categorias", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ProductoResponseRest> crear(Producto producto) {

        // public ResponseEntity<CategoriaResponseRest> buscarCategorias() {
//        log.info("Buscando categorias");
        log.info("Buscar por ID");
        ProductoResponseRest response = new ProductoResponseRest();
        List<Producto> list = new ArrayList<>();
        try {
            Producto productoGuardar = productoDao.save(producto);
            if (productoGuardar != null) {
                list.add(productoGuardar);
                response.getProductoResponse().setProducto(list);
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
            return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ProductoResponseRest> actualizar(Producto producto, Long id) {


        log.info("Actualizando producto");
        ProductoResponseRest response = new ProductoResponseRest();
        List<Producto> list = new ArrayList<>();
        try {
            Optional<Producto> productoBuscado = productoDao.findById(id);
            if (productoBuscado.isPresent()) {

                productoBuscado.get().setNombre(producto.getNombre());
                productoBuscado.get().setPrecio(producto.getPrecio());
                productoBuscado.get().setCliente(producto.getCliente());

                Producto productoActualizar = productoDao.save(productoBuscado.get());
                if (productoActualizar != null) {
                    list.add(productoActualizar);
                    response.getProductoResponse().setProducto(list);
                    response.setMetada("Respuesta OK", "00", "Actualizacion exitosa");

                } else {
                    log.info("No se encontro la categoria");
                    response.setMetada("Respuesta no encontrada", "-1", "Categoria no creada");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

            } else {
                log.info("No se pudo encontrar la categoria");
                response.setMetada("Respuesta no encontrada", "-1", "Categoria no encontrada");
                return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetada("Respuesta FALLIDA", "-1", "Error al crear la categoria");
            log.error("Error al guardar categorias", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);
    }

    public ResponseEntity<ProductoResponseRest> eliminar(Long id) {
        log.info("Eliminando producto");
        ProductoResponseRest response = new ProductoResponseRest();
        List<Producto> list = new ArrayList<>();
        try {
            productoDao.deleteById(id);
            response.setMetada("Respuesta Ok", "00" ,"Eliminacion exitosa");
        }catch (Exception e) {
            response.setMetada("Error", "-1","Error al eliminar la categoria");
            log.error("Error al eliminar categorias", e.getMessage());
            e.getStackTrace();
            return  new ResponseEntity<ProductoResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductoResponseRest>(response, HttpStatus.OK);
    }




}
