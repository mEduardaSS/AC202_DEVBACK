
package com.example.ac2.dto;

import java.time.LocalDate;

public record VacinaDTO(
    Long id,
    Long animalId,
    String nome,
    LocalDate dataAplicacao,
    String lote
) {}
