package com.example.inmoprop.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.inmoprop.models.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

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
    }
}
