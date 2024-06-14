package com.example.MedPro_api.DTO.paciente;

public class DadosLoginPaciente {
    private String token;
    private String nomeUser;
    private String tipo;
    private Long id;

    public DadosLoginPaciente(String token, String nomeUser, String paciente, Long id) {
        this.token = token;
        this.nomeUser = nomeUser;
        this.tipo = paciente;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
    public String getNomeUser(){return nomeUser;}
    public Long getId() {
        return id;
    }
}
