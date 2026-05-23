package com.sylvie.productos.controller;

import com.sylvie.productos.entity.Producto;
import com.sylvie.productos.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // GET /api/productos -> productoss totales
    @GetMapping
    public List<Producto> listar() {
        return productoService.listarTodos();
    }

    // GET /api/productos/barras/{codigo} -> código de barras
    @GetMapping("/barras/{codigo}")
    public ResponseEntity<Producto> buscarPorBarras(@PathVariable String codigo) {
        try {
            Producto producto = productoService.buscarPorCodigoBarras(codigo);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/productos
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.guardar(producto));
    }
}