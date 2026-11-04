
package com.example.ac2.dto;

import java.time.LocalDateTime;

public record AtendimentoDTO(
    Long id,
    Long animalId,
    Long veterinarioId,
    String diagnostico,
    String observacoes,
    String orientacoes,
    LocalDateTime dataAtendimento
) {}
