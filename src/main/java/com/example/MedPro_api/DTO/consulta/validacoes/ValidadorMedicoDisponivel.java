package com.example.MedPro_api.DTO.consulta.validacoes;

import com.example.MedPro_api.DTO.consulta.DadosAgendamentoConsulta;
import com.example.MedPro_api.infra.Exception.ValidacaoException;
import com.example.MedPro_api.repository.consulta.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // ou @Service sla
public class ValidadorMedicoDisponivel implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        var medicoEstaLivre = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if(medicoEstaLivre){
            throw new ValidacaoException("Médico não está disponível nesse horário.");
        }
    }


}
