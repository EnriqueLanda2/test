package org.example.supermercado.model.dao;

import jakarta.transaction.Transactional;
import org.example.supermercado.model.Carrito;
import org.example.supermercado.model.Cliente;
import org.example.supermercado.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICarritoDao extends CrudRepository<Carrito, Long> {

    List<Carrito> findByCliente(Cliente cliente);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Carrito (id,cantidad, cliente_id, producto_id) VALUES (:id,:cantidad, :cliente_id, :producto_id)", nativeQuery = true)
    void insertarCarrito(@Param("id")long id , @Param("cantidad") int cantidad, @Param("cliente_id") long cliente_id,@Param("producto_id") long producto_id);


}
