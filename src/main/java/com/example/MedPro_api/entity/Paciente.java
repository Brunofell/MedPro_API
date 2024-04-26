package com.example.MedPro_api.entity;

import com.example.MedPro_api.DTO.DadosCadastroPaciente;
import com.example.MedPro_api.DTO.DadosEndereco;
import com.example.MedPro_api.DTO.DadosUpdatePaciente;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private  String cpf;
    @Embedded
    private Endereco endereco;

    public Paciente(DadosCadastroPaciente dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
//        this.endereco = new Endereco(
//                dados.endereco().logradouro(),
//                dados.endereco().numero(),
//                dados.endereco().bairro(),
//                dados.endereco().cep(),
//                dados.endereco().cidade(),
//                dados.endereco().uf(),
//                dados.endereco().complemento()
//                ); OU... cria o construtor

    }

    public void atualizarInfos(DadosUpdatePaciente dados) {
        if(dados.nome() != null){
            this.nome = dados.nome();
        }
        if(dados.email() != null){
            this.email = dados.email();
        }
        if(dados.telefone() != null){
            this.telefone = dados.telefone();
        }
        if(dados.cpf() != null){
            this.cpf = dados.cpf();
        }
        if(dados.endereco() != null){
            this.endereco.atualizaEndereco(dados.endereco());
        }
    }
}
