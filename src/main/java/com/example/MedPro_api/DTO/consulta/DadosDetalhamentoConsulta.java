package com.example.MedPro_api.DTO.consulta;

import com.example.MedPro_api.DTO.medico.Especialidade;
import com.example.MedPro_api.entity.consulta.Consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(
        Long id,
        Long idMedico,
        String nomeMedico,
        Especialidade especialidade,
        Long idPaciente,
        LocalDateTime data
) {

    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getNome(), consulta.getMedico().getEspecialidade(), consulta.getPaciente().getId(), consulta.getData());
    }
}
