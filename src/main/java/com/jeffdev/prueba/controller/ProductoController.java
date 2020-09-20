package com.jeffdev.prueba.controller;


import com.jeffdev.prueba.exception.CustomException;
import com.jeffdev.prueba.exception.DeleteException;
import com.jeffdev.prueba.exception.ResourceNotFoundException;
import com.jeffdev.prueba.models.Cliente;
import com.jeffdev.prueba.models.Producto;
import com.jeffdev.prueba.service.IClienteService;
import com.jeffdev.prueba.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/producto")
public class ProductoController {

    final
    IProductoService iProductoService;

    public ProductoController(@Qualifier("IProductoImple") @Autowired IProductoService iProductoService) {
        this.iProductoService = iProductoService;
    }


    @PostMapping("/save-producto")
    public Producto saveProducto(@RequestBody Producto producto){
        return iProductoService.save(producto);
    }

    @DeleteMapping("/delete-producto")
    public void deleteProducto(@RequestParam Long id){
        iProductoService.deleteProducto(id);
        throw new DeleteException("Se elimino correctamente");
    }

    @PostMapping("/find-producto-by-id")
    public Producto findProducto(@RequestParam Long id){
        Producto producto = iProductoService.findProduct(id);
        if(producto==null){
            throw new ResourceNotFoundException("No se encontro el producto");
        }
        return producto;
    }

}
