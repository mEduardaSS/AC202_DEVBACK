
package com.example.ac2.dto;

import java.time.LocalDateTime;

import com.example.ac2.entity.StatusConsulta;

public record ConsultaDTO(
    Long id,
    Long animalId,
    Long veterinarioId,
    String local,
    LocalDateTime dataHora,
    StatusConsulta status
) {}
