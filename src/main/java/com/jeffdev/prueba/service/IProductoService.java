package com.jeffdev.prueba.service;

import com.jeffdev.prueba.models.Producto;
import org.springframework.stereotype.Service;

@Service
public interface IProductoService {
    Producto save(Producto producto);

    Producto findProduct(Long id);

    void deleteProducto(Long id);
}
