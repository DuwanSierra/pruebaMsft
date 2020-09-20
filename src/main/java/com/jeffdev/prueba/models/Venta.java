package com.jeffdev.prueba.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "VENTA")
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDVENTA")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idventa;

    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente")
    @ManyToOne
    @NotNull
    private Cliente idcliente;

    public Venta() {
    }

    public Long getIdventa() {
        return idventa;
    }

    public void setIdventa(Long idventa) {
        this.idventa = idventa;
    }

    public Cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Cliente idcliente) {
        this.idcliente = idcliente;
    }
}
