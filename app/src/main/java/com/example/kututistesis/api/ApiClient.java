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

    public Call<ResponseBody> registroArchivoSesionPraxias(Integer sesion_praxia_id, String fecha, String archivo){
        return apiService.registroArchivoSesionPraxias(sesion_praxia_id, fecha, archivo);
    }

    /*public Call<List<SesionPraxia>> listar_sesionpraxias(){
        return apiService.listar_sesionpraxias();
    }*/

    /*public Call<ResponseStatus> registroSesionVocales(SesionVocal request){
        Gson gson = new Gson();
        String json = gson.toJson(request);
        return apiService.registroSesionVocales(json);
    }*/

    public Call<ResponseStatus> registroArchivoSesionFonemas(Integer sesion_fonema_id, String fecha, String archivo){
        return apiService.registroArchivoSesionFonemas(sesion_fonema_id, fecha, archivo);
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

    public Call<List<SesionPraxia>> buscarxpraxiayusuario(Integer id_praxia, Integer id_paciente){
        return apiService.buscarxpraxiayusuario(id_praxia, id_paciente);
    }

    public Call<List<Fonema>> buscarfonemaxid(Integer id_fonema){
        return apiService.buscarfonemaxid(id_fonema);
    }

    public Call<List<SesionVocabulario>> listar_sesionvocabularioxusuario(@Path("id") Integer id){
        return apiService.listar_sesionvocabularioxusuario(id);
    }

    public Call<ResponseStatus> actualizarsesion_vocabulario(Integer id, Integer Intentos_Buenos,
                                                              Integer Intentos_Malos, Integer Intentos_x_Revisar,
                                                              String Fecha, Integer paciente_id){
        return apiService.actualizarsesion_vocabulario(id, Intentos_Buenos, Intentos_Malos, Intentos_x_Revisar, Fecha, paciente_id);
    }

    public Call<SesionVocabulario> buscarxvocabularioyusuario(Integer id_vocabulario, Integer id_usuario){
        return apiService.buscarxvocabularioyusuario(id_vocabulario, id_usuario);
    }

    public Call<List<PacienteLogro>> listarlogroxusuarioid(Integer paciente_id){
        return apiService.listarlogroxusuarioid(paciente_id);
    }

    public Call<ResponseStatus> agregar_logro(Integer paciente_id,Integer logro_id){
        return apiService.agregar_logro(paciente_id, logro_id);
    }

    public Call<List<SignUpForm>> buscar_pacientexid(Integer paciente_id){
        return apiService.buscar_pacientexid(paciente_id);
    }

    public Call<ResponseStatus> actualizar_paciente(Integer paciente_id, SignUpForm signUpForm){
        return apiService.actualizar_paciente(paciente_id, signUpForm.getNombre(), signUpForm.getApellido(), signUpForm.getCelular(),
                signUpForm.getCorreo(), signUpForm.getContrasenia(), signUpForm.getFecha_inscripcion(), signUpForm.getFecha_nacimiento(),
                signUpForm.getHabilitado(), signUpForm.getMedicoId(), signUpForm.getMascotaId());
    }

    public Call<List<Medico>> buscar_medicoxid(Integer medico_id){
        return apiService.buscar_medicoxid(medico_id);
    }
}
