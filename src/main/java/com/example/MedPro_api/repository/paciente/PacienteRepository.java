package com.example.MedPro_api.repository.paciente;

import com.example.MedPro_api.entity.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
// long atributo da chave primaria

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    UserDetails findByEmail(String email); // ou findByLogin ?????

    boolean existsByEmail(String email);

}
