package org.example.supermercadoo.model.doa;

import org.example.supermercadoo.model.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDao extends CrudRepository<Cliente, Long> {
}
