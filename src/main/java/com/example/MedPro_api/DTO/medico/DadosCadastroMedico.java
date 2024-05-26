package com.example.MedPro_api.DTO.medico;


public record DadosCadastroMedico(

        String nome,
        String email,
        String senha,
        String telefone,
        String crm,
        Especialidade especialidade,
        String endereco

) {

}
