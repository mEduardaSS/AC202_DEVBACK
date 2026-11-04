
package com.example.ac2.dto;

import java.util.Set;

public record VeterinarioDTO(
    Long id,
    String nome,
    String crmv,
    Set<String> especies
) {}
