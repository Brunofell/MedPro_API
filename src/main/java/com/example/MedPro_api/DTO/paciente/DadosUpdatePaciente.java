package com.example.MedPro_api.DTO.paciente;

import jakarta.validation.constraints.NotNull;

public record DadosUpdatePaciente(
        @NotNull
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        DadosEndereco endereco
) {
}
