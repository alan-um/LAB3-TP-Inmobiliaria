package com.example.inmoprop.ui.inquilinos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmoprop.models.Contrato;
import com.example.inmoprop.models.Inquilino;
import com.example.inmoprop.models.Propietario;
import com.example.inmoprop.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinosViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Inquilino> mInquilino= new MutableLiveData<>();
    private MutableLiveData<String> toast= new MutableLiveData<>();
    private MutableLiveData<String> mErrId= new MutableLiveData<>("");
    private MutableLiveData<String> mErrNombre= new MutableLiveData<>("");
    private MutableLiveData<String> mErrApellido= new MutableLiveData<>("");
    private MutableLiveData<String> mErrDni= new MutableLiveData<>("");
    private MutableLiveData<String> mErrTelefono= new MutableLiveData<>("");
    private MutableLiveData<String> mErrEmail= new MutableLiveData<>("");
    public InquilinosViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();
    }

    public LiveData<Inquilino> getInquilino() {
        return mInquilino;
    }
    public LiveData<String> getToast() {
        return toast;
    }
    public LiveData<String> getErrId() {
        return mErrId;
    }
    public LiveData<String> getErrNombre() {
        return mErrNombre;
    }
    public LiveData<String> getErrApellido() {
        return mErrApellido;
    }
    public LiveData<String> getErrDni() {
        return mErrDni;
    }
    public LiveData<String> getErrTelefono() {
        return mErrTelefono;
    }
    public LiveData<String> getErrEmail() {
        return mErrEmail;
    }

    public void mostrarInquilino(Bundle args){
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
                        mInquilino.postValue(c.getInquilino());
                    }else{
                        toast.postValue("No se ha encontrado el inquilino");
                    }
                }

                @Override
                public void onFailure(Call<Contrato> call, Throwable t) {
                    toast.postValue("Error al intentar conectar con el servidor.");
                }
            });
        }else{
            toast.setValue("No es posible identificar el inquilino.");
        }
    }

}