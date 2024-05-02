package com.example.MedPro_api.controller.consulta;

import com.example.MedPro_api.DTO.consulta.AgendaDeConsultas;
import com.example.MedPro_api.DTO.consulta.DadosAgendamentoConsulta;
import com.example.MedPro_api.DTO.consulta.DadosListagemConsulta;
import com.example.MedPro_api.repository.consulta.ConsultaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository repository;
    @Autowired
    private AgendaDeConsultas agendar;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var dto = agendar.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        repository.deleteById(id);
    }

    @GetMapping
    public List<DadosListagemConsulta> listar(){
        return repository.findAll().stream().map(DadosListagemConsulta::new).toList();
    }

}
