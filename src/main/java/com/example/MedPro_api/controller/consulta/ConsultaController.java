package com.example.MedPro_api.controller.consulta;

import com.example.MedPro_api.DTO.consulta.AgendaDeConsultas;
import com.example.MedPro_api.DTO.consulta.DadosAgendamentoConsulta;
import com.example.MedPro_api.DTO.consulta.DadosListagemConsulta;
import com.example.MedPro_api.entity.consulta.Consulta;
import com.example.MedPro_api.repository.consulta.ConsultaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository repository;
    @Autowired
    private AgendaDeConsultas agendar;

    @PostMapping
    @Transactional
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var dto = agendar.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public void excluir(@PathVariable Long id){
        repository.deleteById(id);
    }

    @GetMapping
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public List<DadosListagemConsulta> listar(){
        return repository.findAll().stream().map(DadosListagemConsulta::new).toList();
    }

    // Novo método para buscar consultas por ID do paciente
    @GetMapping("/paciente/{id}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<DadosListagemConsulta>> listarPorPacienteId(@PathVariable Long id) {
        List<Consulta> consultas = repository.findByPacienteId(id);
        List<DadosListagemConsulta> resultado = consultas.stream().map(DadosListagemConsulta::new).collect(Collectors.toList());
        return ResponseEntity.ok(resultado);
    }

    // Novo método para buscar consultas por ID do médico (caso necessário)
    @GetMapping("/medico/{id}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<DadosListagemConsulta>> listarPorMedicoId(@PathVariable Long id) {
        List<Consulta> consultas = repository.findByMedicoId(id);
        List<DadosListagemConsulta> resultado = consultas.stream().map(DadosListagemConsulta::new).collect(Collectors.toList());
        return ResponseEntity.ok(resultado);
    }

}
