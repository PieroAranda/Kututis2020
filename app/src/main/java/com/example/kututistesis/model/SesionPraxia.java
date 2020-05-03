package com.example.kututistesis.model;

import com.google.gson.annotations.SerializedName;

public class SesionPraxia {
    @SerializedName("paciente_id")
    private Integer paciente_id;

    @SerializedName("praxias_id")
    private Integer praxias_id;

    @SerializedName("Aprobado")
    private Integer Aprobado;

    @SerializedName("Fecha")
    private String Fecha;

    @SerializedName("ruta")
    private String ruta;

    public SesionPraxia(Integer paciente_id, Integer praxias_id, Integer Aprobado, String Fecha, String ruta) {
        this.paciente_id = paciente_id;
        this.praxias_id = praxias_id;
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
        return praxias_id;
    }

    public void setPraxia_id(Integer praxia_id) {
        this.praxias_id = praxia_id;
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
}
