
package com.example.ac2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.ac2.dto.AnimalDTO;
import com.example.ac2.service.interfaces.AnimalService;

@RestController
@RequestMapping("/api/animais")
public class AnimalController {

    private final AnimalService service;

    public AnimalController(AnimalService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalDTO cadastrar(@RequestBody AnimalDTO dto) {
        return service.cadastrar(dto);
    }

    @GetMapping
    public List<AnimalDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public AnimalDTO buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @GetMapping("/tutor/{tutorId}")
    public List<AnimalDTO> listarPorTutor(
        @PathVariable Long tutorId) {
        return service.listarPorTutor(tutorId);
    }

    @PutMapping("/{id}")
    public AnimalDTO atualizar(
        @PathVariable Long id,
        @RequestBody AnimalDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
