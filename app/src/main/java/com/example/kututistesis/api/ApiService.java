package com.example.kututistesis.api;

import com.example.kututistesis.model.ArchivoSesionFonema;
import com.example.kututistesis.model.ArchivoSesionPraxia;
import com.example.kututistesis.model.Fonema;
import com.example.kututistesis.model.Medico;
import com.example.kututistesis.model.PacienteLogro;
import com.example.kututistesis.model.Praxia;
import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.model.SesionFonema;
import com.example.kututistesis.model.SesionPraxia;
import com.example.kututistesis.model.SesionVocabulario;
import com.example.kututistesis.model.SesionVocal;
import com.example.kututistesis.model.SignUpForm;
import com.example.kututistesis.model.Vocabulario;
import com.example.kututistesis.model.Vocal;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @FormUrlEncoded
    @POST("archivos_sesion_praxia/agregar")
    Call<ResponseBody> registroArchivoSesionPraxias(@Field("sesion_praxia_id") Integer sesion_praxia_id,
                                                    @Field("Fecha") String fecha,
                                                    @Field("archivo") String archivo);


    /*@FormUrlEncoded
    @POST("registrosesion_vocales")
    Call<ResponseStatus> registroSesionVocales(@Field("json") String json);*/

    @FormUrlEncoded
    @POST("archivos_sesion_fonema/agregar")
    Call<ResponseStatus> registroArchivoSesionFonemas(@Field("sesion_fonema_id") Integer sesion_fonema_id,
                                                    @Field("Fecha") String fecha,
                                                    @Field("archivo") String archivo);


    /*@GET("listarpraxias")
    Call<List<Praxia>> listarpraxias();*/

    @GET("listar_sesionpraxiasxusuario/{id_usuario}")
    Call<List<SesionPraxia>> listar_sesionpraxiasxusuario(@Path("id_usuario") Integer id_usuario);

    /*@GET("listarvocales")
    Call<List<Vocal>> listarvocales();*/

    @GET("listar_sesionfonemasxusuario/{id_usuario}")
    Call<List<SesionFonema>> listar_sesionfonemasxusuario(@Path("id_usuario") Integer id_usuario);

    /*
    @GET("buscarxpraxiaxusuarioxfecha/{id_praxia}/{id_usuario}/{fecha}")
    Call<List<SesionPraxia>> buscarxpraxiaxusuarioxfecha(@Path("id_praxia") Integer id_praxia, @Path("id_usuario") Integer id_usuario, @Path("fecha") String fecha);
*/

    @GET("archivos_sesion_praxia/buscararchivosxsesionpraxiaidxfecha/{id_sesion_praxia}/{Fecha}")
    Call<List<ArchivoSesionPraxia>> buscararchivosxsesionpraxiaidxfecha(@Path("id_sesion_praxia") Integer id_sesion_praxia, @Path("Fecha") String fecha);


    /*
    @GET("buscarxvocalxusuarioxfecha/{id_vocal}/{id_usuario}/{fecha}")
    Call<List<SesionVocal>> buscarxvocalxusuarioxfecha(@Path("id_vocal") Integer id_vocal, @Path("id_usuario") Integer id_usuario, @Path("fecha") String fecha);
*/

    @GET("archivos_sesion_fonema/buscararchivosxsesionfonemaidxfecha/{id_sesion_fonema}/{Fecha}")
    Call<List<ArchivoSesionFonema>> buscararchivosxsesionfonemaidxfecha(@Path("id_sesion_fonema") Integer id_sesion_fonema, @Path("Fecha") String fecha);


    @GET("listarfonemas")
    Call<List<Fonema>> listarfonemas();

    @GET("vocabulario/buscarxfonemaid/{fonema_id}")
    Call<List<Vocabulario>> buscarvocabularioxfonemaid(@Path("fonema_id") Integer fonema_id);


    @GET("buscarxpraxiayusuario/{id_praxia}/{id_usuario}")
    Call<List<SesionPraxia>> buscarxpraxiayusuario(@Path("id_praxia") Integer id_praxia, @Path("id_usuario") Integer id_usuario);

    @GET("fonema/buscarxid/{id}")
    Call<List<Fonema>> buscarfonemaxid(@Path("id") Integer id_fonema);

    @GET("sesion_vocabulario/listar_sesionvocabularioxusuario/{id}")
    Call<List<SesionVocabulario>> listar_sesionvocabularioxusuario(@Path("id") Integer id);

    @FormUrlEncoded
    @PUT("sesion_fonemas/actualizarsesion_vocabulario/{id}")
    Call<ResponseStatus> actualizarsesion_vocabulario(@Path("id") Integer id, @Field("Intentos_Buenos") Integer Intentos_Buenos,
                                                      @Field("Intentos_Malos") Integer Intentos_Malos, @Field("Intentos_x_Revisar") Integer Intentos_x_Revisar,
                                                      @Field("Fecha") String Fecha, @Field("paciente_id") Integer paciente_id);

    @GET("buscarxvocabularioyusuario/{id_vocabulario}/{id_usuario}")
    Call<SesionVocabulario> buscarxvocabularioyusuario(@Path("id_vocabulario") Integer id_vocabulario, @Path("id_usuario") Integer id_usuario);

    @GET("paciente_logro/listarxusuarioid/{paciente_id}")
    Call<List<PacienteLogro>> listarlogroxusuarioid(@Path("paciente_id") Integer paciente_id);

    @FormUrlEncoded
    @POST("paciente_logro/agregar")
    Call<ResponseStatus> agregar_logro(@Field("paciente_id") Integer paciente_id, @Field("logro_id") Integer logro_id);

    @GET("paciente/buscarxid/{id}")
    Call<List<SignUpForm>> buscar_pacientexid(@Path("id") Integer paciente_id);

    @FormUrlEncoded
    @POST("paciente/actualizar_paciente/{id}")
    Call<ResponseStatus> actualizar_paciente(@Path("id") Integer paciente_id, @Field("Nombre") String nombre, @Field("Apellido")
            String apellido, @Field("Celular") String celular, @Field("Correo") String correo,
                                             @Field("Contrasenia") String contrasenia, @Field("Fecha_Inscripcion") String fecha_inscripcion,
                                             @Field("Fecha_Nacimiento") String fecha_nacimiento, @Field("Habilitado") Integer Habilitado,
                                            @Field("medico_id") Integer medico_id, @Field("mascota_id") Integer mascota_id);


    @GET("medico/buscarxid/{id}")
    Call<List<Medico>> buscar_medicoxid(@Path("id") Integer medico_id);

}
