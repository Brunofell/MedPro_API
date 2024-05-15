package com.example.MedPro_api.repository.consulta;

import com.example.MedPro_api.entity.consulta.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);

    // Método para buscar consultas pelo ID do paciente
    List<Consulta> findByPacienteId(Long pacienteId);

    // Método para buscar consultas pelo ID do médico (caso necessário)
    List<Consulta> findByMedicoId(Long medicoId);
}
