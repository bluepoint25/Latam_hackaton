package com.superasia.carrito.service;

import com.superasia.carrito.entity.Carrito;
import com.superasia.carrito.entity.CarritoItem;
import com.superasia.carrito.repository.CarritoItemRepository;
import com.superasia.carrito.repository.CarritoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository carritoItemRepository;

    public CarritoService(CarritoRepository carritoRepository, CarritoItemRepository carritoItemRepository) {
        this.carritoRepository = carritoRepository;
        this.carritoItemRepository = carritoItemRepository;
    }

    public Optional<Carrito> getCarritoByUsuarioId(Long usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId);
    }

    public Carrito createCarrito(Long usuarioId) {
        Carrito carrito = new Carrito();
        carrito.setUsuarioId(usuarioId);
        return carritoRepository.save(carrito);
    }

    public Carrito addItem(Long carritoId, CarritoItem item) {
        Carrito carrito = carritoRepository.findById(carritoId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        carrito.addItem(item);
        carritoItemRepository.save(item);
        return carritoRepository.save(carrito);
    }

    public void removeItem(Long carritoId, Long itemId) {
        Carrito carrito = carritoRepository.findById(carritoId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        CarritoItem item = carritoItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item no encontrado"));
        carrito.removeItem(item);
        carritoItemRepository.delete(item);
        carritoRepository.save(carrito);
    }
}