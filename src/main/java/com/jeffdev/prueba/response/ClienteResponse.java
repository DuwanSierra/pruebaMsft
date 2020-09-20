package com.jeffdev.prueba.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ClienteResponse {
    private Long idcliente;
    private String nombre;
    private String apellido;
    private String dni;
    private Long telefono;
    private String email;
}
