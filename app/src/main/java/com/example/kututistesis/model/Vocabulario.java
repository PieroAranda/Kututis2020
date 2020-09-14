package com.example.kututistesis.model;

import com.google.gson.annotations.SerializedName;

public class Vocabulario {

    @SerializedName("id")
    private Integer id;

    @SerializedName("Palabra")
    private String Palabra;

    @SerializedName("imagen")
    private String Imagen;

    @SerializedName("fonema_id")
    private Integer fonema_id;

    @SerializedName("eliminado")
    private Integer eliminado;

    public Vocabulario(Integer id, String palabra, String imagen, Integer fonema_id, Integer eliminado) {
        this.id = id;
        this.Palabra = palabra;
        this.Imagen = imagen;
        this.fonema_id = fonema_id;
        this.eliminado = eliminado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPalabra() {
        return Palabra;
    }

    public void setPalabra(String palabra) {
        Palabra = palabra;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public Integer getFonema_id() {
        return fonema_id;
    }

    public void setFonema_id(Integer fonema_id) {
        this.fonema_id = fonema_id;
    }
}
