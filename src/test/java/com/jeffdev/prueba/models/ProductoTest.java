package com.jeffdev.prueba.models;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@DataJpaTest
class ProductoTest {


    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void saveProducto() {
        Producto producto = new Producto();
        producto.setPrecio(new BigDecimal("143.12"));
        producto.setNombre(null);
        producto.setIdproducto(null);
        //Campo Nombre no puede ser nulo
        Exception exception = assertThrows(PersistenceException.class, () -> {
            entityManager.persist(producto);
        });
    }

}
