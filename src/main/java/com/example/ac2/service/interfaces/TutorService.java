
package com.example.ac2.service.interfaces;

import java.util.List;

import com.example.ac2.dto.TutorDTO;

public interface TutorService {
    TutorDTO cadastrar(TutorDTO dto);
    List<TutorDTO> listar();
    TutorDTO buscar(Long id);
    TutorDTO atualizar(Long id, TutorDTO dto);
    void excluir(Long id);
}
