package com.jeffdev.prueba.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DETALLEVENTA")
public class DetalleVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "IDDETALLEVENTA")
    private Long iddetalleventa;


    @JoinColumn(name = "idproducto", referencedColumnName = "idproducto")
    @ManyToOne
    private Producto idproducto;


    public DetalleVenta() {
    }

    public DetalleVenta(Long iddetalleventa) {
        this.iddetalleventa = iddetalleventa;
    }

    public Long getIddetalleventa() {
        return iddetalleventa;
    }

    public void setIddetalleventa(Long iddetalleventa) {
        this.iddetalleventa = iddetalleventa;
    }

    public Producto getIdProducto() {
        return idproducto;
    }

    public void setIdProducto(Producto idproducto) {
        this.idproducto = idproducto;
    }
}
