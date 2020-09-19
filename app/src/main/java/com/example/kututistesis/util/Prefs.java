package com.example.kututistesis.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.example.kututistesis.model.Mascota;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;


public class Prefs {
    @Nullable
    private SharedPreferences pref;
    @Nullable
    private Context context;

    public Prefs(Context applicationContext) {
        this.context = applicationContext;
    }

    @Nullable
    public final SharedPreferences getPref() {
        return this.pref;
    }

    public final void setPref(@Nullable SharedPreferences var1) {
        this.pref = var1;
    }

    public final void saveMascota(@NotNull String key, @NotNull Mascota mascota) {

        SharedPreferences.Editor editor = (SharedPreferences.Editor) this.pref;
        String jsonString = (new Gson()).toJson(mascota);
        editor.putString(key, jsonString).apply();
    }

    @Nullable
    public final Mascota getMascota(@NotNull String key) {


        String jsonString = this.pref.getString(key, "");
        return (Mascota)(new Gson()).fromJson(jsonString, (new TypeToken() {
        }).getType());
    }

    public final void clearPreferences() {
        this.pref.edit().clear().apply();
    }

    @Nullable
    public final Context getContext() {
        return this.context;
    }

    public final void setContext(@Nullable Context var1) {
        this.context = var1;
    }

}