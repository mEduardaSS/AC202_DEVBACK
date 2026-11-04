
package com.example.ac2.dto;

import java.time.LocalDate;

public record AnimalDTO(
    Long id,
    String nome,
    String especie,
    String raca,
    LocalDate dataNascimento,
    Long tutorId
) {}
