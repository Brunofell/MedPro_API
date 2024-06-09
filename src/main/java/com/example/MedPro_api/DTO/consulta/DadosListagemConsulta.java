package com.example.MedPro_api.DTO.consulta;

import com.example.MedPro_api.DTO.medico.DadosListagemMedico;
import com.example.MedPro_api.DTO.medico.Especialidade;
import com.example.MedPro_api.entity.consulta.Consulta;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosListagemConsulta(
    Long id,
    Long medico,
    String nome,
    Long paciente,
    Especialidade especialidade,
    LocalDateTime data
) {

    public DadosListagemConsulta(Consulta consulta){
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getMedico().getNome(), consulta.getPaciente().getId(), consulta.getMedico().getEspecialidade(),consulta.getData());
    }

}
