package com.example.MedPro_api.controller.consulta;

import com.example.MedPro_api.DTO.consulta.AgendaDeConsultas;
import com.example.MedPro_api.DTO.consulta.DadosAgendamentoConsulta;
import com.example.MedPro_api.DTO.consulta.DadosDetalhamentoConsulta;
import com.example.MedPro_api.DTO.consulta.DadosListagemConsulta;
import com.example.MedPro_api.entity.consulta.Consulta;
import com.example.MedPro_api.repository.consulta.ConsultaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    private ConsultaRepository repository;
    @Autowired
    private AgendaDeConsultas agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){

        agenda.agendar(dados);

        return ResponseEntity.ok(new DadosDetalhamentoConsulta(null,null,null,null));
    }



}
