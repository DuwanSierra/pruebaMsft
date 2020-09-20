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
class VentaTest {


    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void saveVenta() {
        Venta venta = new Venta();
        venta.setIdcliente(new Cliente());
        //Campo del cliente_clienteid no puede ser nulo
        Exception exception = assertThrows(PersistenceException.class, () -> {
            entityManager.persist(venta);
        });
    }

}
