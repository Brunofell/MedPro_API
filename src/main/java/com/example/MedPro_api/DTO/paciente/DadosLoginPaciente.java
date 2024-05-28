package com.example.MedPro_api.DTO.paciente;


public class DadosLoginPaciente {
    private String token;
    private String tipo;
    private Long id;

    public DadosLoginPaciente(String token, String paciente, Long id) {
        this.token = token;
        this.tipo = paciente;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
    public Long getId() {
        return id;
    }
}
