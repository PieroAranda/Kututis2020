package com.example.kututistesis.api;

import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.model.SignUpForm;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("registropaciente")
    Call<ResponseStatus> registarPaciente(@Field("json") String json);

    @FormUrlEncoded
    @POST("loginpaciente")
    Call<ResponseStatus> loginPaciente(@Field("json") String json);

    @FormUrlEncoded
    @POST("registrosesion_praxias")
    Call<ResponseStatus> registroSesionPraxias(@Field("json") String json);
}
