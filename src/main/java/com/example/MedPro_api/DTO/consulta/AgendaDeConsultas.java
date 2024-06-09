package com.example.MedPro_api.DTO.consulta;

import com.example.MedPro_api.DTO.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import com.example.MedPro_api.infra.Exception.ValidacaoException;
import com.example.MedPro_api.entity.consulta.Consulta;
import com.example.MedPro_api.entity.medico.Medico;
import com.example.MedPro_api.repository.consulta.ConsultaRepository;
import com.example.MedPro_api.repository.medicos.MedicoRepository;
import com.example.MedPro_api.repository.paciente.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe");
        }
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do medico informado não existe");
        }

        validadores.forEach(v -> v.validar(dados)); // chamando os validadores

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);
        var consulta = new Consulta(medico, paciente, dados.data());
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido.");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    @Transactional
    public DadosDetalhamentoConsulta atualizarHorarioConsulta(Long idConsulta, LocalDateTime novaData) {
        var consulta = consultaRepository.findById(idConsulta)
                .orElseThrow(() -> new ValidacaoException("Consulta não encontrada"));

        var dadosAgendamento = new DadosAgendamentoConsulta(
                consulta.getMedico().getId(),
                consulta.getPaciente().getId(),
                consulta.getMedico().getEspecialidade(),
                consulta.getMedico().getNome(),
                novaData
        );

        validadores.forEach(v -> v.validar(dadosAgendamento)); // chamando os validadores

        consulta.setData(novaData);
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

}


