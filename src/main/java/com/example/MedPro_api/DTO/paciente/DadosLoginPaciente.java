package com.example.MedPro_api.DTO.paciente;


public class DadosLoginPaciente {
    private String token;
    private boolean paciente;

    public DadosLoginPaciente(String token, boolean paciente) {
        this.token = token;
        this.paciente = paciente;
    }

    public String getToken() {
        return token;
    }

    public boolean isPaciente() {
        return paciente;
    }
}
