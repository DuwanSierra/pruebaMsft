package com.jeffdev.prueba.impl;

import com.jeffdev.prueba.exception.CustomException;
import com.jeffdev.prueba.exception.ResourceNotFoundException;
import com.jeffdev.prueba.models.Cliente;
import com.jeffdev.prueba.repository.IClienteRepository;
import com.jeffdev.prueba.service.IClienteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Single;

import java.util.Optional;

@Service
public class IClienteImple implements IClienteService {

    private final IClienteRepository iClienteRepository;
    static final Logger logger = LogManager.getLogger("save-venta");

    public IClienteImple(
            @Autowired IClienteRepository IClienteRepository
    ) {
        this.iClienteRepository = IClienteRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        try {
            return iClienteRepository.save(cliente);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }
}
