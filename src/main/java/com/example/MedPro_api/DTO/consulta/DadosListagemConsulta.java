package com.example.MedPro_api.DTO.consulta;

import com.example.MedPro_api.DTO.medico.DadosListagemMedico;
import com.example.MedPro_api.entity.consulta.Consulta;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosListagemConsulta(
    Long id,
    Long medico,
    Long paciente,
    LocalDateTime data
) {

    public DadosListagemConsulta(Consulta consulta){
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }

}
