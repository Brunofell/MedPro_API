package com.example.MedPro_api.DTO;

import com.example.MedPro_api.entity.Endereco;
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
