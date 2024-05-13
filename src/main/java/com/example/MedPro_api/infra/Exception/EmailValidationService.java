package com.example.MedPro_api.infra.Exception;

import com.example.MedPro_api.repository.medicos.MedicoRepository;
import com.example.MedPro_api.repository.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailValidationService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validarEmail(String email) {
        if (medicoRepository.existsByEmail(email) || pacienteRepository.existsByEmail(email)) {
            throw new EmailDuplicadoException("Médico ou paciente com email iguais.");
        }
    }

}
