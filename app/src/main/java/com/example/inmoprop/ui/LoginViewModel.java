package com.example.inmoprop.ui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmoprop.request.ApiClient;
import com.example.inmoprop.util.Validacion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> mErrLogin;
    private MutableLiveData<String> mErrUsuario;
    private MutableLiveData<String> mErrPass;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();
    }

    public LiveData<String> getErrLogin() {
        if (mErrLogin == null) {
            mErrLogin = new MutableLiveData<>();
        }
        return mErrLogin;
    }

    public LiveData<String> getErrUsuario() {
        if (mErrUsuario == null) {
            mErrUsuario = new MutableLiveData<>();
        }
        return mErrUsuario;
    }

    public LiveData<String> getErrPass() {
        if (mErrPass == null) {
            mErrPass = new MutableLiveData<>();
        }
        return mErrPass;
    }

    public void ingresar(String u, String p) {
        //Validación de campos
        String errUsuario = Validacion.usuario(u);
        mErrUsuario.setValue(errUsuario);
        String errPass = Validacion.pass(p);
        mErrPass.setValue(errPass);

        //LLamada a la API
        if (errUsuario.isBlank() && errPass.isBlank()) {//Si no hay errores llama a la API
            callLogin(u,p);
        }
    }

    private void callLogin(String u, String p) {
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
                    mErrLogin.postValue("");
                } else {
                    mErrLogin.postValue("Usuario y/o contraseña incorrectos.");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mErrLogin.postValue("Error al intentar conectar con el servidor.");
            }
        });
    }

    public void ingresarHARDCODE(String u, String p) {
        Intent intent = new Intent(getApplication(), NavActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        mErrLogin.postValue("");
    }
}
