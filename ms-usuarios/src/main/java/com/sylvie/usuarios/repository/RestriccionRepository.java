package com.sylvie.usuarios.repository;

import com.sylvie.usuarios.entity.Restriccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RestriccionRepository extends JpaRepository<Restriccion, Long> {
    List<Restriccion> findByUsuarioId(Long usuarioId);
    void deleteByUsuarioIdAndIngrediente(Long usuarioId, String ingrediente);
}