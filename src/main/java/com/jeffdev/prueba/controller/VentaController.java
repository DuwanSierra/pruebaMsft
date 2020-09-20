package com.jeffdev.prueba.controller;


import com.jeffdev.prueba.exception.ResourceNotFoundException;
import com.jeffdev.prueba.models.Venta;
import com.jeffdev.prueba.response.BaseWebResponse;
import com.jeffdev.prueba.response.VentaResponse;
import com.jeffdev.prueba.response.VentaWebResponse;
import com.jeffdev.prueba.service.IVentaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rx.Single;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/venta")
public class VentaController {

    private final IVentaService iVentaService;


    public VentaController(@Qualifier("IVentaImple") @Autowired IVentaService iVentaService) {
        this.iVentaService = iVentaService;
    }

    @PostMapping("/save-venta")
    public Venta saveVenta(@RequestBody Venta venta) {
        return iVentaService.save(venta);
    }

    @GetMapping("/find-venta")
    public Venta findVenta(@RequestParam Long id) {
        Venta venta = iVentaService.findVenta(id);
        if(venta==null){
            throw new ResourceNotFoundException("No se encontro");
        }
        return venta;
    }

    @GetMapping(value = "/get-ventas-client", produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<ResponseEntity<BaseWebResponse<List<VentaWebResponse>>>> findCliente(@RequestParam Long id){
        return iVentaService.findVentasClient(id)
                .subscribeOn(Schedulers.io())
                .map(ventaResponse -> ResponseEntity.ok(BaseWebResponse.successWithData(toVentaWebResponse(ventaResponse))));
    }

    private List<VentaWebResponse> toVentaWebResponse(List<VentaResponse> ventaList) {
        return ventaList
                .stream()
                .map(this::toVentaWebR)
                .collect(Collectors.toList());
    }

    private VentaWebResponse toVentaWebR(VentaResponse ventaResponse) {
        VentaWebResponse ventaWebResponse = new VentaWebResponse();
        BeanUtils.copyProperties(ventaResponse, ventaWebResponse);
        return ventaWebResponse;
    }

}
