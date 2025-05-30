package com.mycompany.service;

import com.mycompany.modelo.Categoria;
import com.mycompany.modelo.Producto;
import com.mycompany.repository.CategoriaRepository;
import com.mycompany.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    public Producto actualizarParcial(Long id, Map<String, Object> campos) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado con ID: " + id));

        campos.forEach((key, value) -> {
            try {
                // Usamos getDeclaredField de la clase Product
                Field campo = Producto.class.getDeclaredField(key);

                // Aseguramos que el campo sea accesible
                campo.setAccessible(true);

                // Establecemos el nuevo valor
                campo.set(producto, value);
            } catch (NoSuchFieldException e) {
                // Manejo de la excepción si no se encuentra el campo
                System.out.println("Campo no encontrado: " + key);
            } catch (IllegalAccessException e) {
                // Manejo de la excepción si no se puede acceder al campo
                e.printStackTrace();
            }
        });

        return productoRepository.save(producto);
    }

    public List<Producto> buscarPorNombreCategoria(String nombreCategoria) {
        return productoRepository.findByNombreCategoria(nombreCategoria);
    }

    @Transactional
    public Producto guardarConCategoria(Producto producto, Categoria categoria) {
        // Buscar si la categoría ya existe
        Optional<Categoria> categoriaExistente = categoriaRepository.findByNombre(categoria.getNombre());

        Categoria categoriaFinal = categoriaExistente.orElseGet(() -> categoriaRepository.save(categoria));

        producto.setCategoria(categoriaFinal);

        return productoRepository.save(producto);
    }

}
