
package com.example.ac2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.ac2.dto.AtendimentoDTO;
import com.example.ac2.service.interfaces.AtendimentoService;

@RestController
@RequestMapping("/api/atendimentos")
public class AtendimentoController {

    private final AtendimentoService service;

    public AtendimentoController(AtendimentoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AtendimentoDTO cadastrar(
        @RequestBody AtendimentoDTO dto) {
        return service.cadastrar(dto);
    }

    @GetMapping("/animal/{animalId}")
    public List<AtendimentoDTO> listarPorAnimal(
        @PathVariable Long animalId) {
        return service.listarPorAnimal(animalId);
    }
}
