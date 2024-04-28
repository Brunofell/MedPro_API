package com.example.MedPro_api.repository.paciente;

import com.example.MedPro_api.entity.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
// long atributo da chave primaria

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

}
