package com.example.MedPro_api.DTO.medico;

import jakarta.validation.constraints.NotNull;

public record DadosUpdateMedico (
        @NotNull
        Long id,
        String nome,
        String email,
        String senha,
        String telefone,
        String crm,
        Especialidade especialidade,
        String endereco
){
}
