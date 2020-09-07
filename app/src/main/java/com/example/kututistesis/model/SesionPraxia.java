package com.example.kututistesis.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Blob;

public class SesionPraxia {
    @SerializedName("id")
    private Integer id;

    @SerializedName("paciente_id")
    private Integer paciente_id;

    @SerializedName("praxias_id")
    private Integer praxias_id;

    @SerializedName("Repeticiones")
    private Integer repeticiones;

    @SerializedName("completado")
    private Integer completado;

    @SerializedName("praxia")
    private Praxia praxia;

    public SesionPraxia(Integer id, Integer paciente_id, Integer praxias_id, Integer repeticiones, Integer completado, Praxia praxia) {
        this.id = id;
        this.paciente_id = paciente_id;
        this.praxias_id = praxias_id;
        this.repeticiones = repeticiones;
        this.completado = completado;
        this.praxia = praxia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaciente_id() {
        return paciente_id;
    }

    public void setPaciente_id(Integer paciente_id) {
        this.paciente_id = paciente_id;
    }

    public Integer getPraxias_id() {
        return praxias_id;
    }

    public void setPraxias_id(Integer praxias_id) {
        this.praxias_id = praxias_id;
    }

    public Integer getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(Integer repeticiones) {
        this.repeticiones = repeticiones;
    }

    public Integer getCompletado() {
        return completado;
    }

    public void setCompletado(Integer completado) {
        this.completado = completado;
    }

    public Praxia getPraxia() {
        return praxia;
    }

    public void setPraxia(Praxia praxia) {
        this.praxia = praxia;
    }
}
