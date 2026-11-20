package com.br.gurpo34.maosepatas.controller;

import com.br.gurpo34.maosepatas.dto.tutor.ContatoRequestDTO;
import com.br.gurpo34.maosepatas.dto.tutor.TutorResponseDTO;
import com.br.gurpo34.maosepatas.dto.tutor.TutorResquestDTO;
import com.br.gurpo34.maosepatas.service.TutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutores")
@RequiredArgsConstructor
public class TutorController {

    private final TutorService tutorService;

    @PostMapping
    public ResponseEntity<TutorResponseDTO> salvar(@Valid @RequestBody TutorResquestDTO dto) {
        TutorResponseDTO criado = tutorService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<TutorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(tutorService.listarTodos());
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<TutorResponseDTO>> pesquisar(@RequestParam(required = false) String termo) {
        return ResponseEntity.ok(tutorService.pesquisar(termo));
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<TutorResponseDTO>> filtrarPorPossuirAnimais(
            @RequestParam(required = false) Boolean comAnimais
    ) {
        return ResponseEntity.ok(tutorService.filtrarPorPossuirAnimais(comAnimais));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tutorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutorResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TutorResquestDTO dto
    ) {
        return ResponseEntity.ok(tutorService.atualizar(id, dto));
    }

    @PostMapping("/{id}/contatos")
    public ResponseEntity<TutorResponseDTO> adicionarContato(
            @PathVariable Long id,
            @Valid @RequestBody ContatoRequestDTO dto
    ) {
        TutorResponseDTO atualizado = tutorService.adicionarContato(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(atualizado);
    }

    @DeleteMapping("/{id}/contatos/{contatoId}")
    public ResponseEntity<TutorResponseDTO> removerContato(
            @PathVariable Long id,
            @PathVariable Long contatoId
    ) {
        return ResponseEntity.ok(tutorService.removerContato(id, contatoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tutorService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
