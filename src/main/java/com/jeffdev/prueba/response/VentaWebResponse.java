package com.jeffdev.prueba.response;

import com.jeffdev.prueba.models.Cliente;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
public class VentaWebResponse {
    private Long idventa;
    private Cliente idcliente;
}
