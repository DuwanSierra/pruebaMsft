package com.jeffdev.prueba.impl;

import com.jeffdev.prueba.exception.CustomException;
import com.jeffdev.prueba.exception.DeleteException;
import com.jeffdev.prueba.models.Producto;
import com.jeffdev.prueba.repository.IProductoRepository;
import com.jeffdev.prueba.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IProductoImple implements IProductoService {

    final
    IProductoRepository iProductoRepository;

    public IProductoImple(@Autowired IProductoRepository iProductoRepository) {
        this.iProductoRepository = iProductoRepository;
    }

    @Override
    public Producto save(Producto producto) {
        try{
            return iProductoRepository.save(producto);
        }
        catch (Exception e){
            throw new CustomException("No se pudo guardar el producto");
        }
    }

    @Override
    public Producto findProduct(Long id) {
        return iProductoRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteProducto(Long id) {
        try{
            iProductoRepository.deleteById(id);
        }
        catch (Exception e){
            throw new DeleteException(e.getMessage());
        }
    }
}
