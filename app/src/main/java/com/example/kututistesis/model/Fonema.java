package com.example.kututistesis.model;

import com.google.gson.annotations.SerializedName;

public class Fonema {
    @SerializedName("id")
    private Integer id;

    @SerializedName("Imagen")
    private String Imagen;

    @SerializedName("Fonema")
    private String Fonema;

    @SerializedName("Tipo_Fonema")
    private String Tipo_Fonema;

    public Fonema(Integer id, String imagen, String fonema, String tipo_Fonema) {
        this.id = id;
        this.Imagen = imagen;
        this.Fonema = fonema;
        this.Tipo_Fonema = tipo_Fonema;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getFonema() {
        return Fonema;
    }

    public void setFonema(String fonema) {
        Fonema = fonema;
    }

    public String getTipo_Fonema() {
        return Tipo_Fonema;
    }

    public void setTipo_Fonema(String tipo_Fonema) {
        Tipo_Fonema = tipo_Fonema;
    }
}
