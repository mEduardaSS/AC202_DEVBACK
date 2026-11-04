
package com.example.ac2.service.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ac2.dto.VeterinarioDTO;
import com.example.ac2.entity.Veterinario;
import com.example.ac2.exception.RecursoNaoEncontradoException;
import com.example.ac2.exception.RegraNegocioException;
import com.example.ac2.repository.VeterinarioRepository;
import com.example.ac2.service.interfaces.VeterinarioService;

@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    private final VeterinarioRepository repository;

    public VeterinarioServiceImpl(VeterinarioRepository repository) {
        this.repository = repository;
    }

    private VeterinarioDTO converter(Veterinario v) {
        return new VeterinarioDTO(
            v.getId(), v.getNome(), v.getCrmv(),
            v.getEspecies()
        );
    }

    @Override
    public VeterinarioDTO cadastrar(VeterinarioDTO dto) {
        if (repository.existsByCrmv(dto.crmv())) {
            throw new RegraNegocioException("CRMV já cadastrado");
        }

        Veterinario v = new Veterinario();
        v.setNome(dto.nome());
        v.setCrmv(dto.crmv());
        v.setEspecies(dto.especies() == null
            ? new HashSet<>() : new HashSet<>(dto.especies()));

        return converter(repository.save(v));
    }

    @Override
    public List<VeterinarioDTO> listar() {
        return repository.findAll()
            .stream().map(this::converter).toList();
    }

    @Override
    public VeterinarioDTO buscar(Long id) {
        Veterinario v = repository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Veterinário não encontrado"));

        return converter(v);
    }

    @Override
    @Transactional
    public VeterinarioDTO atualizar(Long id, VeterinarioDTO dto) {
        Veterinario v = repository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Veterinário não encontrado"));

        repository.findByCrmv(dto.crmv()).ifPresent(outro -> {
            if (!outro.getId().equals(id)) {
                throw new RegraNegocioException("CRMV já utilizado");
            }
        });

        v.setNome(dto.nome());
        v.setCrmv(dto.crmv());
        v.setEspecies(dto.especies() == null
            ? new HashSet<>() : new HashSet<>(dto.especies()));

        return converter(repository.save(v));
    }

    @Override
    public void excluir(Long id) {
        Veterinario v = repository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Veterinário não encontrado"));

        repository.delete(v);
    }
}
