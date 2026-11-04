
package com.example.ac2.service.interfaces;

import java.util.List;

import com.example.ac2.dto.AtendimentoDTO;

public interface AtendimentoService {
    AtendimentoDTO cadastrar(AtendimentoDTO dto);
    List<AtendimentoDTO> listarPorAnimal(Long animalId);
}
