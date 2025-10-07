package com.db.duckbill.web.controller;

import com.db.duckbill.service.CotacaoService;
import com.db.duckbill.web.dto.CotacaoAtivoDTO;
import com.db.duckbill.web.mapper.CotacaoAtivoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cotacoes-ativo")
public class CotacaoAtivoController {
    private final CotacaoService service;

    public CotacaoAtivoController(CotacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CotacaoAtivoDTO> salvar(@RequestBody CotacaoAtivoDTO dto) {
        com.db.duckbill.domain.entity.CotacaoAtivo saved = service.salvarCotacaoAtivo(dto.ativoId(), dto.dataRef(), dto.precoFech());
        return ResponseEntity.status(HttpStatus.CREATED).body(CotacaoAtivoMapper.toDTO(saved));
    }

    @GetMapping
    public List<CotacaoAtivoDTO> listar() {
        return service.listarCotacoesAtivo().stream().map(CotacaoAtivoMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{ativoId}/{dataRef}")
    public CotacaoAtivoDTO buscar(@PathVariable Long ativoId, @PathVariable String dataRef) {
        java.time.LocalDate data = java.time.LocalDate.parse(dataRef);
        return CotacaoAtivoMapper.toDTO(service.buscarCotacaoAtivo(ativoId, data));
    }
}
