package com.example.MedPro_api.repository.medicos;

import com.example.MedPro_api.entity.medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    List<Medico> findAllByAtivoTrue();
}
