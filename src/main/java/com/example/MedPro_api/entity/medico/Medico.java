package com.example.MedPro_api.entity.medico;

import com.example.MedPro_api.DTO.medico.DadosCadastroMedico;
import com.example.MedPro_api.DTO.medico.DadosUpdateMedico;
import com.example.MedPro_api.DTO.medico.Especialidade;
import com.example.MedPro_api.entity.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    private Especialidade especialidade;
    @Embedded
    private Endereco endereco;
    private boolean ativo;

    public Medico(DadosCadastroMedico dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInfos(DadosUpdateMedico dados) {
        if(dados.nome() != null){
            this.nome = dados.nome();
        }
        if(dados.email() != null){
            this.email = dados.email();
        }
        if(dados.telefone() != null){
            this.telefone = dados.telefone();
        }
        if(dados.crm() != null){
            this.crm = dados.crm();
        }
        if(dados.especialidade() != null){
            this.especialidade = dados.especialidade();
        }
        if(dados.endereco() != null){
            this.endereco.atualizaEndereco(dados.endereco());
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
