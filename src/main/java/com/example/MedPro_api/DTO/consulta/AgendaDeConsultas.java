package com.example.MedPro_api.DTO.consulta;

import com.example.MedPro_api.entity.consulta.Consulta;
import com.example.MedPro_api.repository.consulta.ConsultaRepository;
import com.example.MedPro_api.repository.medicos.MedicoRepository;
import com.example.MedPro_api.repository.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    public void agendar(DadosAgendamentoConsulta dados){




        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        var medico = medicoRepository.findById(dados.idMedico()).get();
        var consulta = new Consulta(null, medico, paciente, dados.data());


        consultaRepository.save(consulta);
    }
}
