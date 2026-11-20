package com.br.gurpo34.maosepatas.controller;

import com.br.gurpo34.maosepatas.dto.atendimento.AtendimentoRequestDTO;
import com.br.gurpo34.maosepatas.dto.atendimento.AtendimentoResponseDTO;
import com.br.gurpo34.maosepatas.dto.atendimento.AtendimentoResumoDTO;
import com.br.gurpo34.maosepatas.enums.TipoAtendimento;
import com.br.gurpo34.maosepatas.service.AtendimentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import java.util.Map;

@RestController
@RequestMapping("/api/atendimentos")
@RequiredArgsConstructor
public class AtendimentoController {

    private final AtendimentoService atendimentoService;

    @PostMapping
    public ResponseEntity<AtendimentoResponseDTO> salvar(@Valid @RequestBody AtendimentoRequestDTO dto) {
        AtendimentoResponseDTO criado = atendimentoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<AtendimentoResumoDTO>> listarTodos() {
        return ResponseEntity.ok(atendimentoService.listarTodos());
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<AtendimentoResumoDTO>> listarPorAnimal(@PathVariable Long animalId) {
        return ResponseEntity.ok(atendimentoService.listarPorAnimal(animalId));
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<AtendimentoResumoDTO>> pesquisar(@RequestParam(required = false) String termo) {
        return ResponseEntity.ok(atendimentoService.pesquisar(termo));
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<AtendimentoResumoDTO>> filtrar(
            @RequestParam(required = false) TipoAtendimento tipo,
            @RequestParam(required = false) Boolean realizado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim
    ) {
        return ResponseEntity.ok(atendimentoService.filtrar(tipo, realizado, inicio, fim));
    }

    @GetMapping("/contadores")
    public ResponseEntity<Map<String, Long>> obterContadores() {
        return ResponseEntity.ok(atendimentoService.obterContadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtendimentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(atendimentoService.buscarPorId(id));
    }

    @PatchMapping("/{id}/realizar")
    public ResponseEntity<AtendimentoResponseDTO> marcarComoRealizado(@PathVariable Long id) {
        return ResponseEntity.ok(atendimentoService.marcarComoRealizado(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtendimentoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtendimentoRequestDTO dto
    ) {
        return ResponseEntity.ok(atendimentoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        atendimentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
