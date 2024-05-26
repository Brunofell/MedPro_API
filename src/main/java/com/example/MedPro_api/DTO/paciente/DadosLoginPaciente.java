package com.example.MedPro_api.DTO.paciente;


public class DadosLoginPaciente {
    private String token;
    private String tipo;

    public DadosLoginPaciente(String token, String paciente) {
        this.token = token;
        this.tipo = paciente;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
}
