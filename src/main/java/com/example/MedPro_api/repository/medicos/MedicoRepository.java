package com.example.MedPro_api.repository.medicos;

import com.example.MedPro_api.DTO.medico.Especialidade;
import com.example.MedPro_api.entity.medico.Medico;
import com.example.MedPro_api.entity.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    List<Medico> findAllByAtivoTrue();

    @Query("""
            select m from Medico m
            left join Consulta c on m.id = c.medico.id and c.data = :data
            where
            m.ativo = true
            and
            m.especialidade = :especialidade
            and
            c.id is null
            order by rand()
            limit 1
            """)
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

    UserDetails findByEmail(String email);

    boolean existsByEmail(String email);
}

