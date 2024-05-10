package com.example.MedPro_api.DTO.consulta.validacoes;

import com.example.MedPro_api.DTO.consulta.DadosAgendamentoConsulta;
import com.example.MedPro_api.infra.Exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component // ou @Service sla
public class ValidadorHorarioClinica implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var aberturaClinica= dataConsulta.getHour() < 7;
        var clinicaFechada = dataConsulta.getHour() > 18;

        if(domingo || aberturaClinica || clinicaFechada){
            throw new ValidacaoException("Consulta fora do horario de funcionamento.");
        }

    }
}
