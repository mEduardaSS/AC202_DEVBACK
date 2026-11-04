
package com.example.ac2.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ac2.dto.AnimalDTO;
import com.example.ac2.entity.Animal;
import com.example.ac2.entity.Tutor;
import com.example.ac2.exception.RecursoNaoEncontradoException;
import com.example.ac2.repository.AnimalRepository;
import com.example.ac2.repository.TutorRepository;
import com.example.ac2.service.interfaces.AnimalService;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository repository;
    private final TutorRepository tutorRepository;

    public AnimalServiceImpl(
        AnimalRepository repository,
        TutorRepository tutorRepository) {

        this.repository = repository;
        this.tutorRepository = tutorRepository;
    }

    private AnimalDTO converter(Animal a) {
        return new AnimalDTO(
            a.getId(), a.getNome(), a.getEspecie(),
            a.getRaca(), a.getDataNascimento(),
            a.getTutor().getId()
        );
    }

    @Override
    public AnimalDTO cadastrar(AnimalDTO dto) {
        Tutor tutor = tutorRepository.findById(dto.tutorId())
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Tutor não encontrado"));

        Animal animal = new Animal();
        animal.setNome(dto.nome());
        animal.setEspecie(dto.especie());
        animal.setRaca(dto.raca());
        animal.setDataNascimento(dto.dataNascimento());
        animal.setTutor(tutor);

        return converter(repository.save(animal));
    }

    @Override
    public List<AnimalDTO> listar() {
        return repository.findAll()
            .stream().map(this::converter).toList();
    }

    @Override
    public AnimalDTO buscar(Long id) {
        Animal animal = repository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Animal não encontrado"));

        return converter(animal);
    }

    @Override
    public List<AnimalDTO> listarPorTutor(Long tutorId) {
        if (!tutorRepository.existsById(tutorId)) {
            throw new RecursoNaoEncontradoException("Tutor não encontrado");
        }

        return repository.findByTutorId(tutorId)
            .stream().map(this::converter).toList();
    }

    @Override
    @Transactional
    public AnimalDTO atualizar(Long id, AnimalDTO dto) {
        Animal animal = repository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Animal não encontrado"));

        Tutor tutor = tutorRepository.findById(dto.tutorId())
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Tutor não encontrado"));

        animal.setNome(dto.nome());
        animal.setEspecie(dto.especie());
        animal.setRaca(dto.raca());
        animal.setDataNascimento(dto.dataNascimento());
        animal.setTutor(tutor);

        return converter(repository.save(animal));
    }

    @Override
    public void excluir(Long id) {
        Animal animal = repository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Animal não encontrado"));

        repository.delete(animal);
    }
}
