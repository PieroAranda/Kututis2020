package com.example.kututistesis.util;

import android.app.Application;
import android.content.SharedPreferences;

public class Global extends Application {
    private Integer id_usuario;
    private Integer id_praxia;
    public static SharedPreferences sharedPref;

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId_praxia() {
        return id_praxia;
    }

    public void setId_praxia(Integer id_praxia) {
        this.id_praxia = id_praxia;
    }
}
