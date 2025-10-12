package com.example.inmoprop.ui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmoprop.models.Propietario;
import com.example.inmoprop.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> error;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();
    }

    public LiveData<String> getError() {
        if (error == null) {
            error = new MutableLiveData<>();
        }
        return error;
    }

    public void ingresar(String u, String p) {
        ApiClient.InmobiliariaService api = ApiClient.getApi();
        Call<String> llamada = api.login(u, p);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    ApiClient.guardarToken(context, token);
                    Intent intent = new Intent(getApplication(), NavActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    error.postValue("");
                } else {
                    error.postValue("Usuario y/o contraseña incorrectos.");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                error.postValue("Error al intentar conectar con el servidor.");
            }
        });
    }

    public void ingresarHARDCODE(String u, String p) {
        Intent intent = new Intent(getApplication(), NavActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        error.postValue("");
    }
}
