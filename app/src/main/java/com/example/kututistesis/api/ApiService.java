package com.example.kututistesis.api;

import com.example.kututistesis.model.Fonema;
import com.example.kututistesis.model.Mascota;
import com.example.kututistesis.model.Praxia;
import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.model.SesionPraxia;
import com.example.kututistesis.model.SesionVocal;
import com.example.kututistesis.model.Vocabulario;
import com.example.kututistesis.model.Vocal;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {


    @FormUrlEncoded
    @POST("registropaciente")
    Call<ResponseStatus> registarPaciente(@Field("Nombre") String Nombre,
                                          @Field("Apellido") String Apellido,
                                          @Field("Celular") String Celular,
                                          @Field("Correo") String Correo,
                                          @Field("Contrasenia") String Contrasenia,
                                          @Field("Fecha_Inscripcion") String Fecha_Inscripcion, //fecha del sistema
                                          @Field("Fecha_Nacimiento") String Fecha_Nacimiento); //fecha nacimiento obtener datos del modal


    @FormUrlEncoded
    @POST("loginpaciente")
    Call<ResponseStatus> loginPaciente(@Field("Correo") String Correo,
                                       @Field("Contrasenia") String Contrasenia);

    @FormUrlEncoded
    @POST("registrosesion_praxias")
    Call<ResponseBody> registroSesionPraxias(@Field("json") String json);

    @GET("listar_sesionpraxias")
    Call<List<SesionPraxia>> listar_sesionpraxias();

    //esto se reemplazará a registrosesionfonemas
    @FormUrlEncoded
    @POST("registrosesion_vocales")
    Call<ResponseStatus> registroSesionVocales(@Field("json") String json); //no funciona

    @FormUrlEncoded
    @POST("registrosesion_fonemas")
    Call<ResponseStatus> registroSesionFonemas(@Field("paciente_id") int paciente_id,
                                               @Field("Intentos_Buenos") int Intentos_Buenos,
                                               @Field("Repeticiones") int Repeticiones,
                                               @Field("fonema_id") int fonema_id,
                                               @Field("Intentos_Malos") int Intentos_Malos,
                                               @Field("Intentos_x_Revisar") int Intentos_x_Revisar);

    @GET("listar_sesionvocales")
    Call<List<SesionVocal>> listar_sesionvocales(); //no funciona ni se usa

    @GET("listarpraxias")
    Call<List<Praxia>> listarpraxias();

    @GET("listarvocales")
    Call<List<Vocal>> listarvocales(); //no funciona

    @GET("buscarxpraxiaxusuarioxfecha/{id_praxia}/{id_usuario}/{fecha}")
    Call<List<SesionPraxia>> buscarxpraxiaxusuarioxfecha(@Path("id_praxia") Integer id_praxia, @Path("id_usuario") Integer id_usuario, @Path("fecha") String fecha);

    @GET("buscarxvocalxusuarioxfecha/{id_vocal}/{id_usuario}/{fecha}")
    Call<List<SesionVocal>> buscarxvocalxusuarioxfecha(@Path("id_vocal") Integer id_vocal, @Path("id_usuario") Integer id_usuario, @Path("fecha") String fecha);

    @GET("listarfonemas")
    Call<List<Fonema>> listarfonemas();

    @GET("vocabulario/buscarxfonemaid/{fonema_id}")
    Call<List<Vocabulario>> buscarvocabularioxfonemaid(@Path("fonema_id") Integer fonema_id);

    @GET("get_mascota/{paciente_id}")
    Call<Mascota> getMascota(@Path("paciente_id") Integer mascota_id);

    @GET("alimentar_mascota/{paciente_id}")
    Call<Mascota> alimentarMascota(@Path("paciente_id") Integer mascota_id);

}

