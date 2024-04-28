package com.example.MedPro_api.DTO.medico;

import com.example.MedPro_api.DTO.paciente.DadosEndereco;

public record DadosCadastroMedico(

        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        DadosEndereco endereco

) {

}
