package com.example.MedPro_api.DTO.consulta;

import com.example.MedPro_api.DTO.medico.Especialidade;
import com.example.MedPro_api.entity.consulta.Consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(
        Long id,
        Long idMedico,
        String nome,
        Especialidade especialidade,
        Long idPaciente,
        String nomePaciente,  // Adicionado campo para nome do paciente
        LocalDateTime data
) {

    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getNome(), consulta.getMedico().getEspecialidade(), consulta.getPaciente().getId(), consulta.getNomePaciente(), consulta.getData());
    }
}
