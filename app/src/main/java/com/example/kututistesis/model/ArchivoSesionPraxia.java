package com.example.kututistesis.model;

import com.google.gson.annotations.SerializedName;

public class ArchivoSesionPraxia {
    @SerializedName("id")
    private Integer id;

    @SerializedName("sesion_praxia_id")
    private Integer sesion_praxia_id;

    @SerializedName("Aprobado")
    private Integer Aprobado;

    @SerializedName("Fecha")
    private String fecha;

    @SerializedName("archivo")
    private String archivo;

    @SerializedName("Pendiente_x_Revisar")
    private Integer pendiente_x_revisar;

    public ArchivoSesionPraxia(Integer id, Integer sesion_praxia_id, Integer aprobado, String fecha, String archivo, Integer pendiente_x_revisar) {
        this.id = id;
        this.sesion_praxia_id = sesion_praxia_id;
        Aprobado = aprobado;
        this.fecha = fecha;
        this.archivo = archivo;
        this.pendiente_x_revisar = pendiente_x_revisar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSesion_praxia_id() {
        return sesion_praxia_id;
    }

    public void setSesion_praxia_id(Integer sesion_praxia_id) {
        this.sesion_praxia_id = sesion_praxia_id;
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
