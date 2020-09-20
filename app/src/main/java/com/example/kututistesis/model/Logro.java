package com.example.kututistesis.model;

import com.google.gson.annotations.SerializedName;

public class Logro {
    @SerializedName("id")
    private Integer id;

    @SerializedName("Nombre")
    private String Nombre;

    @SerializedName("descripcion")
    private String descripcion;

    public Logro(Integer id, String nombre, String descripcion) {
        this.id = id;
        Nombre = nombre;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
