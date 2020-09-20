package com.jeffdev.prueba.impl;


import com.jeffdev.prueba.exception.CustomException;
import com.jeffdev.prueba.exception.ResourceNotFoundException;
import com.jeffdev.prueba.models.Venta;
import com.jeffdev.prueba.repository.IVentaRepository;
import com.jeffdev.prueba.response.VentaResponse;
import com.jeffdev.prueba.response.VentaWebResponse;
import com.jeffdev.prueba.service.IVentaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Single;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IVentaImple implements IVentaService {

    final IVentaRepository iVentaRepository;

    public IVentaImple(@Autowired IVentaRepository iVentaRepository) {
        this.iVentaRepository = iVentaRepository;
    }

    @Override
    public Venta save(Venta venta) {
        try {
            return iVentaRepository.save(venta);
        }
        catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }



    @Override
    public Venta findVenta(Long id) {
        return iVentaRepository.findById(id).orElse(null);
    }

    @Override
    public Single<List<VentaResponse>> findVentasClient(Long id) {
        return findVentasRepo(id).map(this::toVentaResponseList);
    }

    private List<VentaResponse> toVentaResponseList(List<Venta> ventaList) {
        return ventaList
                .stream()
                .map(this::toVentaResponse)
                .collect(Collectors.toList());
    }

    private VentaResponse toVentaResponse(Venta venta) {
        VentaResponse ventaResponse = new VentaResponse();
        BeanUtils.copyProperties(venta, ventaResponse);
        return ventaResponse;
    }

    public Single<List<Venta>> findVentasRepo(Long id) {
        return Single.create(singleSubscriber -> {
            List<Venta> listVentas = iVentaRepository.findAllByIdcliente_Idcliente(id);
            if (listVentas.isEmpty())
                singleSubscriber.onError(new ResourceNotFoundException("No se encontro cliente"));
            else {
                singleSubscriber.onSuccess(listVentas);
            }
        });
    }
}
