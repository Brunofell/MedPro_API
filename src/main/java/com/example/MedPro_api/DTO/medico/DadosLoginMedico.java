package com.example.MedPro_api.DTO.medico;


public class DadosLoginMedico {
    private String token;
    private String tipo;

    public DadosLoginMedico(String token, String medico) {
        this.token = token;
        this.tipo = medico;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
}