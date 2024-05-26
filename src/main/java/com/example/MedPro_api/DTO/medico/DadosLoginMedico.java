package com.example.MedPro_api.DTO.medico;


public class DadosLoginMedico {
    private String token;
    private boolean medico;

    public DadosLoginMedico(String token, boolean medico) {
        this.token = token;
        this.medico = medico;
    }

    public String getToken() {
        return token;
    }

    public boolean isMedico() {
        return medico;
    }
}