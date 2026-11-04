
package com.example.ac2.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ac2.entity.Consulta;
import com.example.ac2.entity.StatusConsulta;

public interface ConsultaRepository
        extends JpaRepository<Consulta, Long> {

    boolean existsByVeterinarioIdAndDataHoraAndStatus(
        Long veterinarioId,
        LocalDateTime dataHora,
        StatusConsulta status
    );

    List<Consulta> findByAnimalId(Long animalId);

    List<Consulta> findByVeterinarioId(Long veterinarioId);
}
