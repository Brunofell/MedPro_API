package com.example.MedPro_api.controller;

import com.example.MedPro_api.DTO.DadosCadastroPaciente;
import com.example.MedPro_api.DTO.DadosListagemPaciente;
import com.example.MedPro_api.DTO.DadosUpdatePaciente;
import com.example.MedPro_api.entity.Endereco;
import com.example.MedPro_api.entity.Paciente;
import com.example.MedPro_api.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// mensagem
@RestController
@RequestMapping("pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public void cadastrarPaciente(@RequestBody @Valid DadosCadastroPaciente dados){ // valid ali valida as infoss
        // System.out.println(dados.nome());
        repository.save(new Paciente(dados)); // criamos um construtor para receber dados ao inves de escrever tudo aqui.
    }

    @GetMapping // findAll é metodo herdado da jpaRepository
    public List<DadosListagemPaciente> listarPaciente() {
        return repository.findAll().stream().map(DadosListagemPaciente::new).toList();

        // .stream().map(DadosListagemPaciente::new) => isso aqui pq o findAll() devolve um list de Paciente e não de
        // DadosListagemPaciente, dai criamos um ocnstrutor em DadosListagemPaciente e colocamos esses metodos

        // toList() => converte para lista o resultado
    }

    @PutMapping
    @Transactional // criamos mais um dto para conter os dados de atualização do paciente
    public void atualizarPaciente(@RequestBody @Valid DadosUpdatePaciente dados){
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInfos(dados);
        // não precisa de mais nada pq a jpa detecta a mudança e afz sozinha o resto
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        repository.deleteById(id);
    }
}
