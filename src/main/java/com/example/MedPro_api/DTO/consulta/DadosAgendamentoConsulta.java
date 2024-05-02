package com.example.MedPro_api.DTO.consulta;

import com.example.MedPro_api.DTO.medico.Especialidade;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        Long idMedico,
        @NotNull
        Long idPaciente,
        Especialidade especialidade,
        @NotNull
        @Future
        LocalDateTime data
) {
}
