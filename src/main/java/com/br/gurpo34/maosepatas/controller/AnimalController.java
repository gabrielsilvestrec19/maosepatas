package com.br.gurpo34.maosepatas.controller;

import com.br.gurpo34.maosepatas.dto.animal.AnimalLoteRequestDTO;
import com.br.gurpo34.maosepatas.dto.animal.AnimalRequestDTO;
import com.br.gurpo34.maosepatas.dto.animal.AnimalResponseDTO;
import com.br.gurpo34.maosepatas.dto.animal.AnimalResumoDTO;
import com.br.gurpo34.maosepatas.service.AnimalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animais")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping
    public ResponseEntity<AnimalResponseDTO> salvar(@Valid @RequestBody AnimalRequestDTO dto) {
        AnimalResponseDTO criado = animalService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PostMapping("/lote")
    public ResponseEntity<List<AnimalResponseDTO>> salvarEmLote(@Valid @RequestBody AnimalLoteRequestDTO loteDto) {
        List<AnimalResponseDTO> criados = animalService.salvarEmLote(loteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criados);
    }

    @GetMapping
    public ResponseEntity<List<AnimalResumoDTO>> listarTodos() {
        return ResponseEntity.ok(animalService.listarTodos());
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<AnimalResumoDTO>> listarPorTutor(@PathVariable Long tutorId) {
        return ResponseEntity.ok(animalService.listarPorTutor(tutorId));
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<AnimalResumoDTO>> pesquisarPorNome(@RequestParam(required = false) String nome) {
        return ResponseEntity.ok(animalService.pesquisarPorNome(nome));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AnimalRequestDTO dto
    ) {
        return ResponseEntity.ok(animalService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        animalService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
