
package com.example.ac2.service.interfaces;

import java.util.List;

import com.example.ac2.dto.VacinaDTO;

public interface VacinaService {
    VacinaDTO cadastrar(VacinaDTO dto);
    List<VacinaDTO> listarPorAnimal(Long animalId);
}

