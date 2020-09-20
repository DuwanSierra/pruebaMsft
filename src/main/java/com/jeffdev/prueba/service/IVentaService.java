package com.jeffdev.prueba.service;

import com.jeffdev.prueba.models.Venta;
import com.jeffdev.prueba.response.VentaResponse;
import org.springframework.stereotype.Service;
import rx.Single;

import java.util.List;

@Service
public interface IVentaService {
    Venta save(Venta venta);

    Venta findVenta(Long id);

    Single<List<VentaResponse>> findVentasClient(Long id);
}
