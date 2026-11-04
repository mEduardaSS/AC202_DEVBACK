
package com.example.ac2.service.interfaces;

import java.util.List;

import com.example.ac2.dto.ConsultaDTO;

public interface ConsultaService {
    ConsultaDTO agendar(ConsultaDTO dto);
    List<ConsultaDTO> listar();
    ConsultaDTO buscar(Long id);
    List<ConsultaDTO> listarPorAnimal(Long animalId);
    ConsultaDTO cancelar(Long id);
}
