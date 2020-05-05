package com.example.kututistesis.api;

import com.example.kututistesis.model.Praxias;
import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.model.SesionPraxia;
import com.example.kututistesis.model.SesionVocal;
import com.example.kututistesis.model.SignUpForm;
import com.example.kututistesis.model.Vocales;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    Call<ResponseBody> registroSesionPraxias(@Field("json") String json);

    @GET("listar_sesionpraxias")
    Call<List<SesionPraxia>> listar_sesionpraxias();

    @FormUrlEncoded
    @POST("registrosesion_vocales")
    Call<ResponseStatus> registroSesionVocales(@Field("json") String json);

    @GET("listar_sesionvocales")
    Call<List<SesionVocal>> listar_sesionvocales();

    @GET("listarpraxias")
    Call<List<Praxias>> listarpraxias();

    @GET("listarvocales")
    Call<List<Vocales>> listarvocales();

}
