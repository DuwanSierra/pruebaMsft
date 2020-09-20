package com.jeffdev.prueba.repository;

import com.jeffdev.prueba.models.Cliente;
import com.jeffdev.prueba.models.Producto;
import com.jeffdev.prueba.models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVentaRepository extends JpaRepository<Venta,Long>, JpaSpecificationExecutor<Venta> {
    List<Venta> findAllByIdcliente_Idcliente(Long idCliente);
}
