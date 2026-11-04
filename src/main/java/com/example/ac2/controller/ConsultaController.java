
package com.example.ac2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.ac2.dto.ConsultaDTO;
import com.example.ac2.service.interfaces.ConsultaService;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaDTO agendar(@RequestBody ConsultaDTO dto) {
        return service.agendar(dto);
    }

    @GetMapping
    public List<ConsultaDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ConsultaDTO buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @GetMapping("/animal/{animalId}")
    public List<ConsultaDTO> listarPorAnimal(
        @PathVariable Long animalId) {
        return service.listarPorAnimal(animalId);
    }

    @PatchMapping("/{id}/cancelar")
    public ConsultaDTO cancelar(@PathVariable Long id) {
        return service.cancelar(id);
    }
}
