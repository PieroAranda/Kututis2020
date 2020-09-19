package com.example.kututistesis.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Mascota implements Serializable {
    @SerializedName("id")
    private Integer id;

    @SerializedName("Nombre")
    private String Nombre;

    @SerializedName("Color")
    private String Color;

    @SerializedName("Imagen")
    private String Imagen;

    @SerializedName("Cantidad_Dinero")
    private Integer Cantidad_Dinero;

    @SerializedName("Vida")
    private Integer Vida;

    @SerializedName("Hambre")
    private Integer Hambre;

    @SerializedName("Estado")
    private Integer Estado;

    @SerializedName("code")
    private String Error;

    public Mascota(Integer id, String Nombre, String Color, String Imagen, Integer Cantidad_Dinero, Integer Vida, Integer Hambre, Integer Estado, String Error) {
        this.id = id;
        this.Nombre = Nombre;
        this.Color = Color;
        this.Imagen = Imagen;
        this.Cantidad_Dinero = Cantidad_Dinero;
        this.Vida = Vida;
        this.Hambre = Hambre;
        this.Estado = Estado;
        this.Error = Error;
    }

    public Integer getId() {
        return id;
    }

    public Integer getHambre() {
        return Hambre;
    }

    public void setHambre(Integer hambre) {
        Hambre = hambre;
    }

    public Integer getEstado() {
        return Estado;
    }

    public void setEstado(Integer estado) {
        Estado = estado;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public Integer getCantidad_Dinero() {
        return Cantidad_Dinero;
    }

    public void setCantidad_Dinero(Integer cantidad_Dinero) {
        Cantidad_Dinero = cantidad_Dinero;
    }

    public Integer getVida() {
        return Vida;
    }

    public void setVida(Integer vida) {
        Vida = vida;
    }
}