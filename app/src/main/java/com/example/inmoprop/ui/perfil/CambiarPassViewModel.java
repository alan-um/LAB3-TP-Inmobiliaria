package com.example.inmoprop.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmoprop.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarPassViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> toast;
    private MutableLiveData<Boolean> apiRespuesta;
    private MutableLiveData<String> mErrActualPass;
    private MutableLiveData<String> mErrNuevaPass;
    private MutableLiveData<String> mErrRepitePass;

    private MutableLiveData<Boolean> enableBoton = new MutableLiveData<>(true);
    private MutableLiveData<Integer> verProgress = new MutableLiveData<>(View.INVISIBLE);


    public CambiarPassViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();
    }

    public LiveData<String> getToast() {
        if (toast == null) {
            toast = new MutableLiveData<>();
        }
        return toast;
    }

    public LiveData<Boolean> getApiRespuesta() {
        if (apiRespuesta == null) {
            apiRespuesta = new MutableLiveData<>();
        }
        return apiRespuesta;
    }

    public LiveData<String> getErrActualPass() {
        if (mErrActualPass == null) {
            mErrActualPass = new MutableLiveData<>("");
        }
        return mErrActualPass;
    }

    public LiveData<String> getErrNuevaPass() {
        if (mErrNuevaPass == null) {
            mErrNuevaPass = new MutableLiveData<>("");
        }
        return mErrNuevaPass;
    }

    public LiveData<String> getErrRepitePass() {
        if (mErrRepitePass == null) {
            mErrRepitePass = new MutableLiveData<>("");
        }
        return mErrRepitePass;
    }

    public LiveData<Boolean> getEnableBoton() {
        return enableBoton;
    }
    public LiveData<Integer> getVerProgress() {
        return verProgress;
    }

        public void cambiarPass(String actualPass, String nuevaPass, String repitePass) {
        if (validaPass(actualPass,nuevaPass,repitePass)) {//Si no hay errores llama a la API
            inicioLlamado();
            String token = ApiClient.leerToken(context);
            ApiClient.InmobiliariaService api = ApiClient.getApi();
            Call<Void> llamada = api.cambiarPass("Bearer " + token, actualPass, nuevaPass);
            llamada.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        toast.postValue("La contraseña se actualizó correctamente.");
                        apiRespuesta.postValue(true);
                    } else {
                        mErrActualPass.postValue("La contraseña ingresada es incorrecta.");
                    }
                    finLlamado();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    toast.postValue("Error al intentar conectar con el servidor.");
                    finLlamado();
                }
            });
        }
    }

    private void inicioLlamado(){
        enableBoton.setValue(false);
        verProgress.setValue(View.VISIBLE);
    }

    private void finLlamado(){
        enableBoton.setValue(true);
        verProgress.setValue(View.INVISIBLE);
    }

    private boolean validaPass(String actualPass, String nuevaPass, String repitePass){
        Boolean error = true;
        mErrActualPass.setValue("");
        mErrNuevaPass.setValue("");
        mErrRepitePass.setValue("");
        if (actualPass.isBlank()) {
            mErrActualPass.setValue("Debe ingresar la contraseña actual.");
            error = false;
        }
        if (nuevaPass.isBlank()) {
            mErrNuevaPass.setValue("Debe ingresar la nueva contraseña.");
            error = false;
        }
        if (repitePass.isBlank()) {
            mErrRepitePass.setValue("Debe ingresar nuevamente la nueva contraseña.");
            error = false;
        } else {
            if (!repitePass.equals(nuevaPass)) {
                mErrRepitePass.setValue("La contraseña ingresada no es igual a la nueva contraseña.");
                error = false;
            }
        }
        return error;
    }
}