package com.example.MedPro_api.DTO.consulta.validacoes;

import com.example.MedPro_api.DTO.consulta.DadosAgendamentoConsulta;
import com.example.MedPro_api.infra.Exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component // ou @Service sla
public class ValidarAntecedencia implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferenca = Duration.between(agora, dataConsulta).toMinutes();

        if(diferenca < 30){
            throw new ValidacaoException("Consulta deve ser agendada com antecedência miníma de 30 minutos.");
        }
    }
}
