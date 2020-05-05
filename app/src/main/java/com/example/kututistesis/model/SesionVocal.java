package com.example.kututistesis.model;

import com.google.gson.annotations.SerializedName;

public class SesionVocal {
    @SerializedName("paciente_id")
    private Integer paciente_id;

    @SerializedName("vocales_id")
    private Integer vocales_id;

    @SerializedName("Aprobado")
    private Integer Aprobado;

    @SerializedName("Fecha")
    private String Fecha;

    @SerializedName("ruta")
    private String ruta;

    @SerializedName("ruta_servidor")
    private String ruta_servidor;

    public SesionVocal(Integer paciente_id, Integer vocales_id, Integer Aprobado, String Fecha, String ruta) {
        this.paciente_id = paciente_id;
        this.vocales_id = vocales_id;
        this.Aprobado = Aprobado;
        this.Fecha = Fecha;
        this.ruta = ruta;
    }


    public Integer getPaciente_id() {
        return paciente_id;
    }

    public void setPaciente_id(Integer paciente_id) {
        this.paciente_id = paciente_id;
    }

    public Integer getPraxia_id() {
        return vocales_id;
    }

    public void setPraxia_id(Integer vocales_id) {
        this.vocales_id = vocales_id;
    }

    public Integer getAprobado() {
        return Aprobado;
    }

    public void setAprobado(Integer aprobado) {
        Aprobado = aprobado;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta_servidor() {
        return ruta_servidor;
    }

    public void setRuta_servidor(String ruta_servidor) {
        this.ruta_servidor = ruta_servidor;
    }
}
