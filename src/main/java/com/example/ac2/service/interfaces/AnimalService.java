
package com.example.ac2.service.interfaces;

import java.util.List;

import com.example.ac2.dto.AnimalDTO;

public interface AnimalService {
    AnimalDTO cadastrar(AnimalDTO dto);
    List<AnimalDTO> listar();
    AnimalDTO buscar(Long id);
    List<AnimalDTO> listarPorTutor(Long tutorId);
    AnimalDTO atualizar(Long id, AnimalDTO dto);
    void excluir(Long id);
}
