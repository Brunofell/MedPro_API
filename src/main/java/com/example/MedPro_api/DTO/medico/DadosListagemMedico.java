package com.example.MedPro_api.DTO.medico;
import com.example.MedPro_api.entity.Endereco;
import com.example.MedPro_api.entity.medico.Medico;

public record DadosListagemMedico(
        Long id,
        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        Endereco endereco
) {

    public DadosListagemMedico(Medico medico){
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(), medico.getEspecialidade(), medico.getEndereco());
    }
}
