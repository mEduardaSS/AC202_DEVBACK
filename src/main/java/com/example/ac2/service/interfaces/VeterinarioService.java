
package com.example.ac2.service.interfaces;

import java.util.List;

import com.example.ac2.dto.VeterinarioDTO;

public interface VeterinarioService {
    VeterinarioDTO cadastrar(VeterinarioDTO dto);
    List<VeterinarioDTO> listar();
    VeterinarioDTO buscar(Long id);
    VeterinarioDTO atualizar(Long id, VeterinarioDTO dto);
    void excluir(Long id);
}
