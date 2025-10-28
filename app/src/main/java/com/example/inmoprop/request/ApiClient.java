package com.example.inmoprop.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.inmoprop.models.Contrato;
import com.example.inmoprop.models.Inmueble;
import com.example.inmoprop.models.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClient {
    public static final String URLBASE = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";
    public static InmobiliariaService getApi(){
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(InmobiliariaService.class);
    }

    //Declaración de Métodos para usar en RETROFIT
    public interface InmobiliariaService{
        @FormUrlEncoded
        @POST("api/Propietarios/login")
        Call<String> login(@Field("Usuario") String u, @Field("Clave") String c);

        @GET("api/Propietarios")
        Call<Propietario> getPropietario(@Header("Authorization") String token);

        @PUT("api/Propietarios/actualizar")
        Call<Propietario> actualizarPropietario(@Header("Authorization") String token, @Body Propietario p);

        @FormUrlEncoded
        @PUT("api/Propietarios/changePassword")
        Call<Void> cambiarPass(@Header("Authorization") String token, @Field("currentPassword") String a, @Field("newPassword") String n);

        @GET("api/Inmuebles")
        Call<List<Inmueble>> obtenerInmuebles(@Header("Authorization") String token);

        @GET("api/Inmuebles/GetContratoVigente")
        Call<List<Inmueble>> obtenerInmueblesConContratoVigente(@Header("Authorization") String token);

        @PUT("api/Inmuebles/actualizar")
        Call<Inmueble> actualizarInmueble(@Header("Authorization") String token, @Body Inmueble i);

        @Multipart
        @POST("api/Inmuebles/cargar")
        Call<Inmueble> cargarInmueble(
                @Header("Authorization") String token,
                @Part MultipartBody.Part imagen,
                @Part("inmueble") RequestBody inmueble);

        @GET("api/Contratos/inmueble/{id}")
        Call<Contrato> getContrato(@Header("Authorization") String token, @Path("id") int id);

        @GET("api/Pagos/contrato/{id}")
        Call<List<Contrato>> obtenerPagos(@Header("Authorization") String token, @Path("id") int id);
    }

    //Métodos para leer y guardar el TOKEN en SharedPreferences
    public static void guardarToken(Context context, String token){
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token",token);
        editor.apply();
    }
    public static String leerToken(Context context){
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token",null);
        //return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoibHVpc3Byb2Zlc3NvckBnbWFpbC5jb20iLCJGdWxsTmFtZSI6Ikx1aXNwcm9mZXNvciBNZXJjYWRvUHJvZmVzb3IiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJQcm9waWV0YXJpbyIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWVpZGVudGlmaWVyIjoiMyIsImV4cCI6MTc2MTYwMzc4OCwiaXNzIjoiaW5tb2JpbGlhcmlhVUxQIiwiYXVkIjoibW9iaWxlQVBQIn0.AFl2ZID1Xfq8vmCKkYb3b29h9kzhMYEbrYS4xJdTrcg";
    }
}
