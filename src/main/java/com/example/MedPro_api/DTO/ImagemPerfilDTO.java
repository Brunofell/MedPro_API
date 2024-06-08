package com.example.MedPro_api.DTO;

public class ImagemPerfilDTO {
    private String imagemUrl;

    public ImagemPerfilDTO(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
}
