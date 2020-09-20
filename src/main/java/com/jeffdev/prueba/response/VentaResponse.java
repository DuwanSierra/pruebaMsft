package com.jeffdev.prueba.response;

import com.jeffdev.prueba.models.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VentaResponse {

    private Long idventa;
    private Cliente idcliente;
}
