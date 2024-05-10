package com.example.MedPro_api.infra.security.authPaciente;

import com.example.MedPro_api.entity.medico.Medico;
import com.example.MedPro_api.entity.paciente.Paciente;
import com.example.MedPro_api.repository.medicos.MedicoRepository;
import com.example.MedPro_api.repository.paciente.PacienteRepository;
import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // se tiver dando erro é culpa disso aqui
public class AuthenticateService implements UserDetailsService {
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tenta encontrar o usuário como médico
        Medico medico = (Medico) medicoRepository.findByEmail(username);
        if (medico != null) {
            return medico;
        }

        // Tenta encontrar o usuário como paciente
        Paciente paciente = (Paciente) pacienteRepository.findByEmail(username);
        if (paciente != null) {
            return paciente;
        }

        // Se nenhum usuário for encontrado, lança uma exceção
        throw new UsernameNotFoundException("Usuário não encontrado: " + username);
    }
}
