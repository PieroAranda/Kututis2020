package com.example.kututistesis.model;

import com.google.gson.annotations.SerializedName;

public class Medico {
    @SerializedName("id")
    private Integer id;

    @SerializedName("Correo")
    private String Correo;

    @SerializedName("Nombre")
    private String Nombre;

    @SerializedName("Apellido")
    private String Apellido;

    @SerializedName("Celular")
    private String Celular;

    public Medico(Integer id, String correo, String nombre, String apellido, String celular) {
        this.id = id;
        Correo = correo;
        Nombre = nombre;
        Apellido = apellido;
        Celular = celular;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }
}
