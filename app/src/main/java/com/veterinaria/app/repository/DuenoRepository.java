package com.veterinaria.app.repository;

import com.veterinaria.app.model.Dueno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DuenoRepository extends JpaRepository<Dueno, Long> {
    
    Optional<Dueno> findByDocumentoIdentidad(String documentoIdentidad);
    
    boolean existsByDocumentoIdentidad(String documentoIdentidad);
}