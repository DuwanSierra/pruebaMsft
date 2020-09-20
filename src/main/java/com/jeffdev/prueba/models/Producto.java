package com.jeffdev.prueba.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Table(name = "PRODUCTO")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "IDPRODUCTO")
    private Long idproducto;
    @NotNull
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    @NotNull
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @OneToMany(mappedBy = "idproducto")
    private Collection<DetalleVenta> detalleVentaCollection;

    public Producto() {
    }

    public Producto(Long idproducto) {
        this.idproducto = idproducto;
    }

    public Long getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Long idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    @JsonIgnore
    public Collection<DetalleVenta> getDetalleVentaCollection() {
        return detalleVentaCollection;
    }

    @JsonProperty
    public void setDetalleVentaCollection(Collection<DetalleVenta> detalleVentaCollection) {
        this.detalleVentaCollection = detalleVentaCollection;
    }

}
