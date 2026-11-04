
package com.example.ac2.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ac2.dto.TutorDTO;
import com.example.ac2.entity.Tutor;
import com.example.ac2.exception.RecursoNaoEncontradoException;
import com.example.ac2.exception.RegraNegocioException;
import com.example.ac2.repository.TutorRepository;
import com.example.ac2.service.interfaces.TutorService;

@Service
public class TutorServiceImpl implements TutorService {

    private final TutorRepository repository;

    public TutorServiceImpl(TutorRepository repository) {
        this.repository = repository;
    }

    private TutorDTO converter(Tutor t) {
        return new TutorDTO(
            t.getId(), t.getNome(), t.getCpf(),
            t.getTelefone(), t.getEndereco()
        );
    }

    @Override
    public TutorDTO cadastrar(TutorDTO dto) {
        if (repository.existsByCpf(dto.cpf())) {
            throw new RegraNegocioException("CPF já cadastrado");
        }

        Tutor tutor = new Tutor();
        tutor.setNome(dto.nome());
        tutor.setCpf(dto.cpf());
        tutor.setTelefone(dto.telefone());
        tutor.setEndereco(dto.endereco());

        return converter(repository.save(tutor));
    }

    @Override
    public List<TutorDTO> listar() {
        return repository.findAll()
            .stream().map(this::converter).toList();
    }

    @Override
    public TutorDTO buscar(Long id) {
        Tutor tutor = repository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Tutor não encontrado"));

        return converter(tutor);
    }

    @Override
    @Transactional
    public TutorDTO atualizar(Long id, TutorDTO dto) {
        Tutor tutor = repository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Tutor não encontrado"));

        repository.findByCpf(dto.cpf()).ifPresent(outro -> {
            if (!outro.getId().equals(id)) {
                throw new RegraNegocioException("CPF já utilizado");
            }
        });

        tutor.setNome(dto.nome());
        tutor.setCpf(dto.cpf());
        tutor.setTelefone(dto.telefone());
        tutor.setEndereco(dto.endereco());

        return converter(repository.save(tutor));
    }

    @Override
    public void excluir(Long id) {
        Tutor tutor = repository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Tutor não encontrado"));

        repository.delete(tutor);
    }
}
