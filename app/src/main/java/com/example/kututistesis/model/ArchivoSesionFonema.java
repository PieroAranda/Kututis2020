package com.example.kututistesis.model;

import com.google.gson.annotations.SerializedName;

public class ArchivoSesionFonema {
    @SerializedName("id")
    private Integer id;

    @SerializedName("sesion_fonema_id")
    private Integer sesion_fonema_id;

    @SerializedName("Aprobado")
    private Integer Aprobado;

    @SerializedName("Fecha")
    private String fecha;

    @SerializedName("archivo")
    private String archivo;

    @SerializedName("Pendiente_x_Revisar")
    private Integer pendiente_x_revisar;

    public ArchivoSesionFonema(Integer sesion_fonema_id, String fecha, String archivo) {
        this.sesion_fonema_id = sesion_fonema_id;
        this.fecha = fecha;
        this.archivo = archivo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSesion_fonema_id() {
        return sesion_fonema_id;
    }

    public void setSesion_fonema_id(Integer sesion_fonema_id) {
        this.sesion_fonema_id = sesion_fonema_id;
    }

    public Integer getAprobado() {
        return Aprobado;
    }

    public void setAprobado(Integer aprobado) {
        Aprobado = aprobado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public Integer getPendiente_x_revisar() {
        return pendiente_x_revisar;
    }

    public void setPendiente_x_revisar(Integer pendiente_x_revisar) {
        this.pendiente_x_revisar = pendiente_x_revisar;
    }
}
