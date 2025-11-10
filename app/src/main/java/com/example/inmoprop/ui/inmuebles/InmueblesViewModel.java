package com.example.inmoprop.ui.inmuebles;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmoprop.models.Inmueble;
import com.example.inmoprop.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Inmueble>> mListaInmuebles;
    private MutableLiveData<String> toast;

    public InmueblesViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();
    }

    public LiveData<List<Inmueble>> getListaInmuebles() {
        if (mListaInmuebles == null) {
            mListaInmuebles = new MutableLiveData<>();
        }
        return mListaInmuebles;
    }

    public LiveData<String> getToast() {
        if (toast == null) {
            toast = new MutableLiveData<>();
        }
        return toast;
    }

    public void listarInmuebles() {
        //Conecta con la DB para tomar la lista de inmuebles
        String token = ApiClient.leerToken(context);
        ApiClient.InmobiliariaService api = ApiClient.getApi();
        Call<List<Inmueble>> inmueblesCall = api.obtenerInmuebles("Bearer " + token);
        inmueblesCall.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    mListaInmuebles.postValue(response.body());
                } else {
                    toast.postValue("No se han encontrado inmuebles");
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                toast.postValue("Error al intentar conectar con el servidor.");
            }
        });
    }
}