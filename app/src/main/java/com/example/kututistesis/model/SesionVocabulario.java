package com.example.kututistesis.model;

import com.google.gson.annotations.SerializedName;

public class SesionVocabulario {
    @SerializedName("id")
    private Integer id;

    @SerializedName("paciente_id")
    private Integer paciente_id;

    @SerializedName("vocabulario_id")
    private Integer vocabulario_id;

    @SerializedName("Repeticiones")
    private Integer repeticiones;

    @SerializedName("Intentos_Buenos")
    private Integer Intentos_Buenos;

    @SerializedName("Fecha")
    private String Fecha;

    @SerializedName("Intentos_Malos")
    private Integer Intentos_Malos;

    @SerializedName("Intentos_x_Revisar")
    private Integer Intentos_x_Revisar;

    @SerializedName("completado")
    private Integer completado;

    @SerializedName("vocabulario")
    private Vocabulario vocabulario;



    public SesionVocabulario(Integer id, Integer paciente_id, Integer vocabulario_id, Integer repeticiones, Integer intentos_Buenos, String fecha, Integer intentos_Malos, Integer intentos_x_Revisar, Integer completado, Vocabulario vocabulario) {
        this.id = id;
        this.paciente_id = paciente_id;
        this.vocabulario_id = vocabulario_id;
        this.repeticiones = repeticiones;
        this.Intentos_Buenos = intentos_Buenos;
        this.Fecha = fecha;
        this.Intentos_Malos = intentos_Malos;
        this.Intentos_x_Revisar = intentos_x_Revisar;
        this.completado = completado;
        this.vocabulario = vocabulario;
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

    public Integer getVocabulario_id() {
        return vocabulario_id;
    }

    public void setVocabulario_id(Integer vocabulario_id) {
        this.vocabulario_id = vocabulario_id;
    }

    public Integer getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(Integer repeticiones) {
        this.repeticiones = repeticiones;
    }

    public Integer getIntentos_Buenos() {
        return Intentos_Buenos;
    }

    public void setIntentos_Buenos(Integer intentos_Buenos) {
        Intentos_Buenos = intentos_Buenos;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public Integer getIntentos_Malos() {
        return Intentos_Malos;
    }

    public void setIntentos_Malos(Integer intentos_Malos) {
        Intentos_Malos = intentos_Malos;
    }

    public Integer getIntentos_x_Revisar() {
        return Intentos_x_Revisar;
    }

    public void setIntentos_x_Revisar(Integer intentos_x_Revisar) {
        Intentos_x_Revisar = intentos_x_Revisar;
    }

    public Integer getCompletado() {
        return completado;
    }

    public void setCompletado(Integer completado) {
        this.completado = completado;
    }

    public Vocabulario getVocabulario() {
        return vocabulario;
    }

    public void setVocabulario(Vocabulario vocabulario) {
        this.vocabulario = vocabulario;
    }
}
