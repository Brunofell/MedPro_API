package com.example.MedPro_api.DTO.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAtualizacaoConsulta(
        @NotNull
        Long idConsulta,
        @NotNull
        @Future
        LocalDateTime novaData
) {
}