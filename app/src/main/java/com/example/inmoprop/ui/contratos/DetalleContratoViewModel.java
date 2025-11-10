package com.example.inmoprop.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmoprop.models.Contrato;
import com.example.inmoprop.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Contrato> mContrato = new MutableLiveData<>();

    private MutableLiveData<String> toast = new MutableLiveData<>();
    private MutableLiveData<String> mErrId = new MutableLiveData<>("");
    private MutableLiveData<String> mErrDireccion = new MutableLiveData<>("");
    private MutableLiveData<String> mErrPropietario = new MutableLiveData<>("");
    private MutableLiveData<String> mErrInquilino = new MutableLiveData<>("");
    private MutableLiveData<String> mErrFechaInicio = new MutableLiveData<>("");
    private MutableLiveData<String> mErrFechaFin = new MutableLiveData<>("");
    private MutableLiveData<String> mErrPrecio = new MutableLiveData<>("");
    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();
    }

    public LiveData<String> getToast() {
        return toast;
    }
    public LiveData<String> getErrId() {
        return mErrId;
    }
    public LiveData<String> getErrDireccion() {
        return mErrDireccion;
    }
    public LiveData<String> getErrPropietario() {
        return mErrPropietario;
    }
    public LiveData<String> getErrInquilino() {
        return mErrInquilino;
    }
    public LiveData<String> getErrFechaInicio() {
        return mErrFechaInicio;
    }
    public LiveData<String> getErrFechaFin() {
        return mErrFechaFin;
    }
    public LiveData<String> getErrPrecio() {
        return mErrPrecio;
    }

    public LiveData<Contrato> getContrato() {
        return mContrato;
    }

    public void cargarContrato(Bundle args) {
        if (args != null && args.getInt("idInmueble",-1)>0){
            int id =args.getInt("idInmueble",-1);

            //Conecta con la DB para tomar el nombre del Propietario
            String token = ApiClient.leerToken(context);
            ApiClient.InmobiliariaService api = ApiClient.getApi();
            Call<Contrato> propietarioCall = api.getContrato("Bearer "+token,id);
            propietarioCall.enqueue(new Callback<Contrato>() {
                @Override
                public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                    if(response.isSuccessful()){
                        Contrato c = response.body();
                        mContrato.postValue(c);
                    }else{
                        toast.postValue("No se ha encontrado el contrato");
                    }
                }

                @Override
                public void onFailure(Call<Contrato> call, Throwable t) {
                    toast.postValue("Error al intentar conectar con el servidor.");
                }
            });
        }else{
            toast.setValue("No es posible identificar el contrato.");
        }
    }
}