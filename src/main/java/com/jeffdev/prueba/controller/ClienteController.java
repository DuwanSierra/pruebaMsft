package com.jeffdev.prueba.controller;


import com.jeffdev.prueba.models.Cliente;
import com.jeffdev.prueba.response.BaseWebResponse;
import com.jeffdev.prueba.response.ClienteResponse;
import com.jeffdev.prueba.service.IClienteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rx.Single;
import rx.schedulers.Schedulers;

@RestController
@RequestMapping(value = "/api/cliente")
public class ClienteController {

    final
    IClienteService iClienteService;


    public ClienteController(IClienteService iClienteService) {
        this.iClienteService = iClienteService;
    }


    @PostMapping("/save-cliente")
    public Cliente saveClient(@RequestBody Cliente cliente){
        return iClienteService.save(cliente);
    }




}
