package com.example.inmoprop.ui.pagos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmoprop.models.Inmueble;
import com.example.inmoprop.models.Pago;
import com.example.inmoprop.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Pago>> mListaPagos = new MutableLiveData<>();
    private MutableLiveData<String> toast = new MutableLiveData<>();
    ;

    public PagosViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();
    }

    public LiveData<List<Pago>> getListaPagos() {
        return mListaPagos;
    }

    public LiveData<String> getToast() {
        return toast;
    }

    public void listarPagos(Bundle args) {
        if (args != null && args.getInt("idContrato", -1) > 0) {
            int id = args.getInt("idContrato", -1);
            //Conecta con la DB para tomar la lista de pagos
            String token = ApiClient.leerToken(context);
            ApiClient.InmobiliariaService api = ApiClient.getApi();
            Call<List<Pago>> pagosCall = api.obtenerPagos("Bearer " + token, id);
            pagosCall.enqueue(new Callback<List<Pago>>() {
                @Override
                public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                    if (response.isSuccessful()) {
                        mListaPagos.postValue(response.body());
                    } else {
                        toast.postValue("No se han encontrado pagos");
                    }
                }

                @Override
                public void onFailure(Call<List<Pago>> call, Throwable t) {
                    toast.postValue("Error al intentar conectar con el servidor.");
                }
            });
        }else{
            toast.setValue("No es posible identificar el contrato.");
        }
    }
}