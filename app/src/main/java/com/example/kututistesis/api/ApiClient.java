package com.example.kututistesis.api;

import com.example.kututistesis.model.ArchivoSesionFonema;
import com.example.kututistesis.model.ArchivoSesionPraxia;
import com.example.kututistesis.model.Fonema;
import com.example.kututistesis.model.Praxia;
import com.example.kututistesis.model.ResponseStatus;
import com.example.kututistesis.model.SesionFonema;
import com.example.kututistesis.model.SesionPraxia;
import com.example.kututistesis.model.SesionVocal;
import com.example.kututistesis.model.SignInForm;
import com.example.kututistesis.model.SignUpForm;
import com.example.kututistesis.model.Vocabulario;
import com.example.kututistesis.model.Vocal;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class ApiClient {
    private static ApiClient instance = null;
    public static final String BASE_HOST_URL = "http://192.168.1.13:80/curso-laravel/kututis/";
    public static final String BASE_URL = BASE_HOST_URL + "public/api/";
    public static final String BASE_STORAGE_IMAGE_URL = BASE_HOST_URL + "storage/app/images/";

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
        return apiService.registarPaciente(signUpForm.getNombre(), signUpForm.getApellido(), signUpForm.getCelular(),
                signUpForm.getCorreo(), signUpForm.getContrasenia(), signUpForm.getFecha_inscripcion(), signUpForm.getFecha_nacimiento());
    }

    public Call<ResponseStatus> loginPaciente(String correo, String contrasenia) {
        return apiService.loginPaciente(correo, contrasenia);
    }

    /*public Call<ResponseBody> registroSesionPraxias(SesionPraxia request){
        Gson gson = new Gson();
        String json = gson.toJson(request);
        return apiService.registroSesionPraxias(json);
    }*/

    public Call<ResponseBody> registroArchivoSesionPraxias(ArchivoSesionPraxia archivoSesionPraxia){
        return apiService.registroArchivoSesionPraxias(archivoSesionPraxia.getSesion_praxia_id(), archivoSesionPraxia.getFecha(),
                archivoSesionPraxia.getArchivo());
    }

    /*public Call<List<SesionPraxia>> listar_sesionpraxias(){
        return apiService.listar_sesionpraxias();
    }*/

    /*public Call<ResponseStatus> registroSesionVocales(SesionVocal request){
        Gson gson = new Gson();
        String json = gson.toJson(request);
        return apiService.registroSesionVocales(json);
    }*/

    public Call<ResponseStatus> registroArchivoSesionFonemas(ArchivoSesionFonema archivoSesionFonema){
        return apiService.registroArchivoSesionFonemas(archivoSesionFonema.getSesion_fonema_id(), archivoSesionFonema.getFecha(),
                archivoSesionFonema.getArchivo());
    }

    /*public Call<List<SesionVocal>> listar_sesionvocales(){
        return apiService.listar_sesionvocales();
    }*/

    /*public Call<List<Praxia>> listarpraxias(){
        return apiService.listarpraxias();
    }*/

    public Call<List<SesionPraxia>> listar_sesionpraxiasxusuario(Integer id_usuario){
        return apiService.listar_sesionpraxiasxusuario(id_usuario);
    }


    /*public Call<List<Vocal>> listarvocales(){
        return apiService.listarvocales();
    }*/

    public Call<List<SesionFonema>> listar_sesionfonemasxusuario(Integer id_usuario){
        return apiService.listar_sesionfonemasxusuario(id_usuario);
    }

    /*public Call<List<SesionPraxia>> buscarxpraxiaxusuarioxfecha(Integer id_praxia, Integer id_paciente, String fecha){
        return apiService.buscarxpraxiaxusuarioxfecha(id_praxia,id_paciente,fecha);
    }*/

    public Call<List<ArchivoSesionPraxia>> buscararchivosxsesionpraxiaidxfecha(Integer id_sesion_praxia, String fecha){
        return apiService.buscararchivosxsesionpraxiaidxfecha(id_sesion_praxia, fecha);
    }

    /*
    public Call<List<SesionVocal>> buscarxvocalxusuarioxfecha(Integer id_vocal, Integer id_paciente, String fecha){
        return apiService.buscarxvocalxusuarioxfecha(id_vocal, id_paciente,fecha);
    }*/

    public Call<List<ArchivoSesionFonema>> buscararchivosxsesionfonemaidxfecha(Integer id_sesion_fonema, String fecha){
        return apiService.buscararchivosxsesionfonemaidxfecha(id_sesion_fonema, fecha);
    }


    public Call<List<Fonema>> getFonemas(){
        return apiService.listarfonemas();
    }

    public Call<List<Vocabulario>> buscarvocabularioxfonemaid(Integer fonema_id){
        return apiService.buscarvocabularioxfonemaid(fonema_id);
    }
}
