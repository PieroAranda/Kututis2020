package com.example.kututistesis.api;

import android.content.Context;

import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.model.SignInForm;
import com.example.kututistesis.model.SignUpForm;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static ApiClient instance = null;
    public static final String BASE_URL = "http://192.168.1.11:82/curso-laravel/kututis/public/api/";

    private ApiService apiService = null;

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    private ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        apiService =  retrofit.create(ApiService.class);
    }

    public Call<ResponseStatus> registrarPaciente(SignUpForm signUpForm) {
        Gson gson =  new Gson();
        return apiService.registarPaciente(gson.toJson(signUpForm));
    }

    public Call<ResponseStatus> loginPaciente(String correo, String contrasenia) {
        SignInForm signInForm = new SignInForm(correo, contrasenia);
        Gson gson =  new Gson();
        return apiService.loginPaciente(gson.toJson(signInForm));
    }

    public  Call<ResponseStatus> registroSesionPraxias(Map<String,String> request){
        Gson gson = new Gson();
        return apiService.registroSesionPraxias(gson.toJson(request));
    }
}
