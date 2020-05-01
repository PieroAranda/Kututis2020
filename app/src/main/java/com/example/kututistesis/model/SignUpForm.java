package com.example.kututistesis.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SignUpForm implements Serializable {
    // Ejemplo del body de un request para regsitrar un usuario
    //{"Nombre":"Iván","Apellido":"Rodríguez","Celular":"950302674","Correo":"ivanrod12@hotmail.com", "Habilitado":1,"Contrasenia":"Ciclo20202.","medico_id":1,"mascota_id":1 }
    @SerializedName("Nombre")
    @Expose
    private String nombre;
    @SerializedName("Apellido")
    @Expose
    private String apellido;
    @SerializedName("Celular")
    @Expose
    private String celular;
    @SerializedName("Correo")
    @Expose
    private String correo;
    @SerializedName("Contrasenia")
    @Expose
    private String contrasenia;
    @SerializedName("Habilitado")
    @Expose
    private int habilitado = 1;
    @SerializedName("medico_id")
    @Expose
    private int medicoId = 1;
    @SerializedName("mascota_id")
    @Expose
    private int mascotaId = 1;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(int habilitado) {
        this.habilitado = habilitado;
    }

    public int getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(int medicoId) {
        this.medicoId = medicoId;
    }

    public int getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(int mascotaId) {
        this.mascotaId = mascotaId;
    }

    @NonNull
    @Override
    public String toString() {
        return nombre + " " + apellido + " " + celular + " " + correo + " " + habilitado + " " + contrasenia + " " + medicoId + " " + mascotaId;
    }
}
