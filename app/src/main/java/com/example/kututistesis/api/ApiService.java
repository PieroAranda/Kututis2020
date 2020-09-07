package com.example.kututistesis.api;

import com.example.kututistesis.model.ArchivoSesionPraxia;
import com.example.kututistesis.model.Fonema;
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
    Call<ResponseStatus> registarPaciente(@Field("Nombre") String nombre, @Field("Apellido")
            String apellido, @Field("Celular") String celular, @Field("Correo") String correo,
                                          @Field("Contrasenia") String contrasenia, @Field("Fecha_Inscripcion") String fecha_inscripcion,
                                          @Field("Fecha_Nacimiento") String fecha_nacimiento);

    @FormUrlEncoded
    @POST("loginpaciente")
    Call<ResponseStatus> loginPaciente(@Field("Correo") String correo, @Field("Contrasenia") String contrasenia);

   /* @FormUrlEncoded
    @POST("registrosesion_praxias")
    Call<ResponseBody> registroSesionPraxias(@Field("json") String json);*/

    @POST("archivos_sesion_praxia/agregar")
    Call<ResponseBody> registroArchivoSesionPraxias(@Field("sesion_praxia_id") Integer sesion_praxia_id,
                                                    @Field("Fecha") String fecha,
                                                    @Field("archivo") String archivo);


    @FormUrlEncoded
    @POST("registrosesion_vocales")
    Call<ResponseStatus> registroSesionVocales(@Field("json") String json);

    /*@GET("listarpraxias")
    Call<List<Praxia>> listarpraxias();*/

    @GET("listar_sesionpraxiasxusuario/{id_usuario}")
    Call<List<SesionPraxia>> listar_sesionpraxiasxusuario(@Path("id_usuario") Integer id_usuario);

    @GET("listarvocales")
    Call<List<Vocal>> listarvocales();

    /*
    @GET("buscarxpraxiaxusuarioxfecha/{id_praxia}/{id_usuario}/{fecha}")
    Call<List<SesionPraxia>> buscarxpraxiaxusuarioxfecha(@Path("id_praxia") Integer id_praxia, @Path("id_usuario") Integer id_usuario, @Path("fecha") String fecha);
*/

    @GET("archivos_sesion_praxia/buscararchivosxsesionpraxiaidxfecha/{id_sesion_praxia}/{Fecha}")
    Call<List<ArchivoSesionPraxia>> buscararchivosxsesionpraxiaidxfecha(@Path("id_sesion_praxia") Integer id_sesion_praxia, @Path("fecha") String fecha);


    @GET("buscarxvocalxusuarioxfecha/{id_vocal}/{id_usuario}/{fecha}")
    Call<List<SesionVocal>> buscarxvocalxusuarioxfecha(@Path("id_vocal") Integer id_vocal, @Path("id_usuario") Integer id_usuario, @Path("fecha") String fecha);

    @GET("listarfonemas")
    Call<List<Fonema>> listarfonemas();

    @GET("vocabulario/buscarxfonemaid/{fonema_id}")
    Call<List<Vocabulario>> buscarvocabularioxfonemaid(@Path("fonema_id") Integer fonema_id);
}
