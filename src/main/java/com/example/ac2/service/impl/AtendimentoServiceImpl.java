
package com.example.ac2.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ac2.dto.AtendimentoDTO;
import com.example.ac2.entity.Animal;
import com.example.ac2.entity.Atendimento;
import com.example.ac2.entity.Veterinario;
import com.example.ac2.exception.RecursoNaoEncontradoException;
import com.example.ac2.repository.AnimalRepository;
import com.example.ac2.repository.AtendimentoRepository;
import com.example.ac2.repository.VeterinarioRepository;
import com.example.ac2.service.interfaces.AtendimentoService;

@Service
public class AtendimentoServiceImpl implements AtendimentoService {

    private final AtendimentoRepository repository;
    private final AnimalRepository animalRepository;
    private final VeterinarioRepository veterinarioRepository;

    public AtendimentoServiceImpl(
        AtendimentoRepository repository,
        AnimalRepository animalRepository,
        VeterinarioRepository veterinarioRepository) {

        this.repository = repository;
        this.animalRepository = animalRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    private AtendimentoDTO converter(Atendimento a) {
        return new AtendimentoDTO(
            a.getId(),
            a.getAnimal().getId(),
            a.getVeterinario().getId(),
            a.getDiagnostico(),
            a.getObservacoes(),
            a.getOrientacoes(),
            a.getDataAtendimento()
        );
    }

    @Override
    @Transactional
    public AtendimentoDTO cadastrar(AtendimentoDTO dto) {

        Animal animal = animalRepository.findById(dto.animalId())
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Animal não encontrado"));

        Veterinario veterinario =
            veterinarioRepository.findById(dto.veterinarioId())
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Veterinário não encontrado"));

        Atendimento a = new Atendimento();
        a.setAnimal(animal);
        a.setVeterinario(veterinario);
        a.setDiagnostico(dto.diagnostico());
        a.setObservacoes(dto.observacoes());
        a.setOrientacoes(dto.orientacoes());
        a.setDataAtendimento(
            dto.dataAtendimento() == null
                ? LocalDateTime.now()
                : dto.dataAtendimento()
        );

        return converter(repository.save(a));
    }

    @Override
    public List<AtendimentoDTO> listarPorAnimal(Long animalId) {
        if (!animalRepository.existsById(animalId)) {
            throw new RecursoNaoEncontradoException("Animal não encontrado");
        }

        return repository
            .findByAnimalIdOrderByDataAtendimentoDesc(animalId)
            .stream().map(this::converter).toList();
    }
}
