package com.example.kututistesis.model;

import com.google.gson.annotations.SerializedName;

public class PacienteLogro {
    @SerializedName("id")
    private Integer id;

    @SerializedName("paciente_id")
    private Integer paciente_id;

    @SerializedName("logro_id")
    private Integer logro_id;

    @SerializedName("logro")
    private Logro logro;

    public PacienteLogro(Integer id, Integer paciente_id, Integer logro_id, Logro logro) {
        this.id = id;
        this.paciente_id = paciente_id;
        this.logro_id = logro_id;
        this.logro = logro;
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

    public Integer getLogro_id() {
        return logro_id;
    }

    public void setLogro_id(Integer logro_id) {
        this.logro_id = logro_id;
    }

    public Logro getLogro() {
        return logro;
    }

    public void setLogro(Logro logro) {
        this.logro = logro;
    }
}
