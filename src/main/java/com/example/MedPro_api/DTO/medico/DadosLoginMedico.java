package com.example.MedPro_api.DTO.medico;


public class DadosLoginMedico {
    private String token;
    private String tipo;
    private Long id;

    public DadosLoginMedico(String token, String medico, Long id) {
        this.token = token;
        this.tipo = medico;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
    public Long getId(){return id; }
}