package com.example.kututistesis.model;

import com.google.gson.annotations.SerializedName;

public class Fonema {
    @SerializedName("id")
    private Integer id;

    @SerializedName("Imagen")
    private String Imagen;

    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("Tipo")
    private String tipo;

    @SerializedName("Descripcion")
    private String descripcion;

    @SerializedName("eliminado")
    private String eliminado;

    public Fonema(Integer id, String imagen, String nombre, String tipo, String descripcion, String eliminado) {
        this.id = id;
        Imagen = imagen;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.eliminado = eliminado;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEliminado() {
        return eliminado;
    }

    public void setEliminado(String eliminado) {
        this.eliminado = eliminado;
    }
}
