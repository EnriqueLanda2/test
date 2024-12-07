package org.example.supermercadoo.model.doa;

import org.example.supermercadoo.model.Producto;
import org.springframework.data.repository.CrudRepository;

public interface IProductoDao extends CrudRepository<Producto, Long> {
}
