package org.example.supermercadoo.service;

import org.example.supermercadoo.model.doa.ICarritoProductoDao;
import org.example.supermercadoo.model.doa.IClienteDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarritoServiceImpl implements ICarritoService {
    private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);
    @Autowired
    private ICarritoProductoDao carritoProductoDao;


}
