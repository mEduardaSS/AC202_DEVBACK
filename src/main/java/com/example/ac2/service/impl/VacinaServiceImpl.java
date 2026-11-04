
package com.example.ac2.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ac2.dto.VacinaDTO;
import com.example.ac2.entity.Animal;
import com.example.ac2.entity.Vacina;
import com.example.ac2.exception.RecursoNaoEncontradoException;
import com.example.ac2.exception.RegraNegocioException;
import com.example.ac2.repository.AnimalRepository;
import com.example.ac2.repository.VacinaRepository;
import com.example.ac2.service.interfaces.VacinaService;

@Service
public class VacinaServiceImpl implements VacinaService {

    private final VacinaRepository repository;
    private final AnimalRepository animalRepository;

    public VacinaServiceImpl(
        VacinaRepository repository,
        AnimalRepository animalRepository) {

        this.repository = repository;
        this.animalRepository = animalRepository;
    }

    private VacinaDTO converter(Vacina v) {
        return new VacinaDTO(
            v.getId(),
            v.getAnimal().getId(),
            v.getNome(),
            v.getDataAplicacao(),
            v.getLote()
        );
    }

    @Override
    @Transactional
    public VacinaDTO cadastrar(VacinaDTO dto) {

        Animal animal = animalRepository.findById(dto.animalId())
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Animal não encontrado"));

        if (dto.dataAplicacao() == null ||
            dto.dataAplicacao().isAfter(java.time.LocalDate.now())) {
            throw new RegraNegocioException(
                "Data de aplicação inválida");
        }

        Vacina vacina = new Vacina();
        vacina.setAnimal(animal);
        vacina.setNome(dto.nome());
        vacina.setDataAplicacao(dto.dataAplicacao());
        vacina.setLote(dto.lote());

        return converter(repository.save(vacina));
    }

    @Override
    public List<VacinaDTO> listarPorAnimal(Long animalId) {
        if (!animalRepository.existsById(animalId)) {
            throw new RecursoNaoEncontradoException("Animal não encontrado");
        }

        return repository
            .findByAnimalIdOrderByDataAplicacaoDesc(animalId)
            .stream().map(this::converter).toList();
    }
}
