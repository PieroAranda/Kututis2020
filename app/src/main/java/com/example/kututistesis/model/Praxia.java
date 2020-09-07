package com.example.kututistesis.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Praxia implements Serializable {
    @SerializedName("id")
    private Integer id;

    @SerializedName("Nombre")
    private String Nombre;

    @SerializedName("Tipo")
    private String Tipo;

    @SerializedName("Descripcion")
    private String Descripcion;

    @SerializedName("Video")
    private String Video;

    @SerializedName("imagen")
    private String imagen;

    @SerializedName("eliminado")
    private Integer eliminado;


    public Praxia(Integer id, String Nombre, String Tipo, String Descripcion, String Video, String imagen, Integer eliminado) {
        this.id = id;
        this.Nombre = Nombre;
        this.Tipo = Tipo;
        this.Descripcion = Descripcion;
        this.Video = Video;
        this.imagen = imagen;
        this.eliminado = eliminado;
    }

    public Integer getId() {
        return id;
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

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getEliminado() {
        return eliminado;
    }

    public void setEliminado(Integer eliminado) {
        this.eliminado = eliminado;
    }
}
