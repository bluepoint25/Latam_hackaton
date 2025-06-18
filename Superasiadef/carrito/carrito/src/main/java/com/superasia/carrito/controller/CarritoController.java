package com.superasia.carrito.controller;

import com.superasia.carrito.entity.Carrito;
import com.superasia.carrito.entity.CarritoItem;
import com.superasia.carrito.service.CarritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/carritos")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Carrito> getCarritoByUsuario(@PathVariable Long usuarioId) {
        Optional<Carrito> carrito = carritoService.getCarritoByUsuarioId(usuarioId);
        return carrito.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/usuario/{usuarioId}")
    public Carrito createCarrito(@PathVariable Long usuarioId) {
        return carritoService.createCarrito(usuarioId);
    }

    @PostMapping("/{carritoId}/items")
    public Carrito addItem(@PathVariable Long carritoId, @RequestBody CarritoItem item) {
        return carritoService.addItem(carritoId, item);
    }

    @DeleteMapping("/{carritoId}/items/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long carritoId, @PathVariable Long itemId) {
        carritoService.removeItem(carritoId, itemId);
        return ResponseEntity.noContent().build();
    }
}