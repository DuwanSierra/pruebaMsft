package com.jeffdev.prueba.service;

import com.jeffdev.prueba.models.Cliente;
import org.springframework.stereotype.Service;
import rx.Single;

@Service
public interface IClienteService {
    Cliente save(Cliente cliente);
}
