package com.jeffdev.prueba.repository;

import com.jeffdev.prueba.models.Cliente;
import com.jeffdev.prueba.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto,Long>, JpaSpecificationExecutor<Producto> {
}
