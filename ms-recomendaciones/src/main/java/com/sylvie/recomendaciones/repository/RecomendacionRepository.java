package com.sylvie.recomendaciones.repository;

import com.sylvie.recomendaciones.entity.Recomendacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecomendacionRepository extends JpaRepository<Recomendacion, Long> {
    List<Recomendacion> findByUsuarioId(Long usuarioId);
    List<Recomendacion> findByUsuarioIdAndCodigoBarras(Long usuarioId, String codigoBarras);
}