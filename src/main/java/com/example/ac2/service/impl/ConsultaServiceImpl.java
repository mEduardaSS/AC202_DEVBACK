
package com.example.ac2.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ac2.dto.ConsultaDTO;
import com.example.ac2.entity.Animal;
import com.example.ac2.entity.Consulta;
import com.example.ac2.entity.StatusConsulta;
import com.example.ac2.entity.Veterinario;
import com.example.ac2.exception.RecursoNaoEncontradoException;
import com.example.ac2.exception.RegraNegocioException;
import com.example.ac2.repository.AnimalRepository;
import com.example.ac2.repository.ConsultaRepository;
import com.example.ac2.repository.VeterinarioRepository;
import com.example.ac2.service.interfaces.ConsultaService;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository repository;
    private final AnimalRepository animalRepository;
    private final VeterinarioRepository veterinarioRepository;

    public ConsultaServiceImpl(
        ConsultaRepository repository,
        AnimalRepository animalRepository,
        VeterinarioRepository veterinarioRepository) {

        this.repository = repository;
        this.animalRepository = animalRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    private ConsultaDTO converter(Consulta c) {
        return new ConsultaDTO(
            c.getId(),
            c.getAnimal().getId(),
            c.getVeterinario().getId(),
            c.getLocal(),
            c.getDataHora(),
            c.getStatus()
        );
    }

    @Override
    @Transactional
    public ConsultaDTO agendar(ConsultaDTO dto) {

        Animal animal = animalRepository.findById(dto.animalId())
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Animal não encontrado"));

        Veterinario veterinario =
            veterinarioRepository.findById(dto.veterinarioId())
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Veterinário não encontrado"));

        if (dto.dataHora() == null ||
            dto.dataHora().isBefore(java.time.LocalDateTime.now())) {
            throw new RegraNegocioException(
                "A data da consulta deve ser futura");
        }

        boolean habilitado = veterinario.getEspecies().stream()
            .anyMatch(e -> e.equalsIgnoreCase(animal.getEspecie()));

        if (!habilitado) {
            throw new RegraNegocioException(
                "Veterinário não atende esta espécie");
        }

        boolean ocupado =
            repository.existsByVeterinarioIdAndDataHoraAndStatus(
                veterinario.getId(),
                dto.dataHora(),
                StatusConsulta.AGENDADA
            );

        if (ocupado) {
            throw new RegraNegocioException(
                "Veterinário já possui consulta neste horário");
        }

        Consulta consulta = new Consulta();
        consulta.setAnimal(animal);
        consulta.setVeterinario(veterinario);
        consulta.setLocal(dto.local());
        consulta.setDataHora(dto.dataHora());
        consulta.setStatus(StatusConsulta.AGENDADA);

        return converter(repository.save(consulta));
    }

    @Override
    public List<ConsultaDTO> listar() {
        return repository.findAll()
            .stream().map(this::converter).toList();
    }

    @Override
    public ConsultaDTO buscar(Long id) {
        Consulta c = repository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Consulta não encontrada"));

        return converter(c);
    }

    @Override
    public List<ConsultaDTO> listarPorAnimal(Long animalId) {
        if (!animalRepository.existsById(animalId)) {
            throw new RecursoNaoEncontradoException("Animal não encontrado");
        }

        return repository.findByAnimalId(animalId)
            .stream().map(this::converter).toList();
    }

    @Override
    @Transactional
    public ConsultaDTO cancelar(Long id) {
        Consulta c = repository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Consulta não encontrada"));

        if (c.getStatus() != StatusConsulta.AGENDADA) {
            throw new RegraNegocioException(
                "Somente consultas agendadas podem ser canceladas");
        }

        c.setStatus(StatusConsulta.CANCELADA);

        return converter(repository.save(c));
    }
}
