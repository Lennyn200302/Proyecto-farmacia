package com.mycompany.repository;

import com.mycompany.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p WHERE p.categoria.nombre = :nombreCategoria")
    List<Producto> findByNombreCategoria(@Param("nombreCategoria") String nombreCategoria);
}
