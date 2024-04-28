package com.example.MedPro_api.DTO.medico;

import com.example.MedPro_api.DTO.paciente.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosUpdateMedico (
        @NotNull
        Long id,
        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        DadosEndereco endereco
){
}
