package com.example.mamma_erp.controllers;

import com.example.mamma_erp.entities.periodos_entrega.PeriodosEntrega;
import com.example.mamma_erp.entities.periodos_entrega.PeriodosEntregaRepository;
import com.example.mamma_erp.entities.periodos_entrega.PeriodosEntregaRequestDTO;
import com.example.mamma_erp.entities.periodos_entrega.PeriodosEntregaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/periodos_entrega")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PeriodosEntregaController {

    @Autowired
    private PeriodosEntregaRepository periodosEntregaRepository;

    @GetMapping
    public List<PeriodosEntregaResponseDTO> listarTodos() {
        List<PeriodosEntrega> periodos = periodosEntregaRepository.findAll();
        return periodos.stream().map(PeriodosEntregaResponseDTO::new).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeriodosEntregaResponseDTO> buscarPorId(@PathVariable Long id) {
        Optional<PeriodosEntrega> periodo = periodosEntregaRepository.findById(id);
        return periodo.map(value -> ResponseEntity.ok(new PeriodosEntregaResponseDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Busca todos os grupos de produtos com paginação
    @GetMapping("/busca")
    public Page<PeriodosEntregaResponseDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return periodosEntregaRepository.findAll(pageable).map(PeriodosEntregaResponseDTO::new);
    }

    // Busca paginada por nome (contém, ignorando maiúsculas e minúsculas)
    @GetMapping("/busca-por-descricao")
    public Page<PeriodosEntregaResponseDTO> findByDescricaoContainingIgnoreCase(
            @RequestParam String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return periodosEntregaRepository.findByDescricaoContainingIgnoreCase(nome, pageable);
    }

    @PostMapping
    public ResponseEntity<PeriodosEntregaResponseDTO> criarPeriodoEntrega(@RequestBody PeriodosEntregaRequestDTO dto) {
        PeriodosEntrega novoPeriodo = new PeriodosEntrega();
        novoPeriodo.setDescricao(dto.descricao());
        novoPeriodo.setHorarioInicio(dto.horarioInicio());
        novoPeriodo.setHorarioFim(dto.horarioFim());
        periodosEntregaRepository.save(novoPeriodo);
        return ResponseEntity.ok(new PeriodosEntregaResponseDTO(novoPeriodo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeriodosEntregaResponseDTO> atualizarPeriodoEntrega(@PathVariable Long id, @RequestBody PeriodosEntregaRequestDTO dto) {
        Optional<PeriodosEntrega> periodoOptional = periodosEntregaRepository.findById(id);
        if (periodoOptional.isPresent()) {
            PeriodosEntrega periodo = periodoOptional.get();
            periodo.setDescricao(dto.descricao());
            periodo.setHorarioInicio(dto.horarioInicio());
            periodo.setHorarioFim(dto.horarioFim());
            periodosEntregaRepository.save(periodo);
            return ResponseEntity.ok(new PeriodosEntregaResponseDTO(periodo));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPeriodoEntrega(@PathVariable Long id) {
        if (periodosEntregaRepository.existsById(id)) {
            periodosEntregaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
