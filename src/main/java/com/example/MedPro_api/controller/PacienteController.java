package com.example.MedPro_api.controller;

import com.example.MedPro_api.DTO.DadosCadastroPaciente;
import com.example.MedPro_api.entity.Paciente;
import com.example.MedPro_api.repository.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;

    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroPaciente dados){ // valid ali valida as infoss
        // System.out.println(dados.nome());
        repository.save(new Paciente(dados)); // criamos um construtor para receber dados ao inves de escrever tudo aqui.

    }

}
