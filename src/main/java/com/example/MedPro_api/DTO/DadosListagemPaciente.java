package com.example.MedPro_api.DTO;

import com.example.MedPro_api.entity.Endereco;
import com.example.MedPro_api.entity.Paciente;

public record DadosListagemPaciente(
        Long id,

        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco
) {

    public DadosListagemPaciente(Paciente paciente){
        this( paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(), paciente.getEndereco());
    }

}
