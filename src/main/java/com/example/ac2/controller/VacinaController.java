
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

import com.example.ac2.dto.VacinaDTO;
import com.example.ac2.service.interfaces.VacinaService;

@RestController
@RequestMapping("/api/vacinas")
public class VacinaController {

    private final VacinaService service;

    public VacinaController(VacinaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VacinaDTO cadastrar(@RequestBody VacinaDTO dto) {
        return service.cadastrar(dto);
    }

    @GetMapping("/animal/{animalId}")
    public List<VacinaDTO> listarPorAnimal(
        @PathVariable Long animalId) {
        return service.listarPorAnimal(animalId);
    }
}
