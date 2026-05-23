package com.sylvie.productos.service;

import com.sylvie.productos.entity.Ingrediente;
import com.sylvie.productos.entity.Producto;
import com.sylvie.productos.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto buscarPorCodigoBarras(String codigoBarras) {
        return productoRepository.findByCodigoBarras(codigoBarras)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + codigoBarras));
    }

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }
}