package com.example.kututistesis.model;

import com.google.gson.annotations.SerializedName;

public class SignInForm {
    // Ejemplo de un request para la autenticación de un usuario
    // {"Correo":"ivanrod12@hotmail.com","Contrasenia":"Calculo19199."}
    @SerializedName("Correo")
    private String correo;
    @SerializedName("Contrasenia")
    private String contrasenia;

    public SignInForm(String correo, String contrasenia) {
        this.correo = correo;
        this.contrasenia = contrasenia;
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
}
