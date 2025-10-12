package com.example.inmoprop.ui.inicio;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmoprop.models.Propietario;
import com.example.inmoprop.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioViewModel extends AndroidViewModel {
    private Context context;

    private MutableLiveData<String> mText;

    public InicioViewModel(@NonNull Application application) {
        super(application);
        context=getApplication();
    }

    public LiveData<String> getText() {
        if(mText==null){
            mText = new MutableLiveData<>();
        }
        return mText;
    }

    public void buscarPropietario(){
        String token = ApiClient.leerToken(context);
        ApiClient.InmobiliariaService api = ApiClient.getApi();
        Call<Propietario> propietarioCall = api.getPropietario("Bearer "+token);
        propietarioCall.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    Propietario p = response.body();
                    mText.postValue("Bienvenido\n"+p.getNombre());
                }else{
                    mText.postValue("Pa mi que no che...");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {

            }
        });
    }

    public void buscarPropietarioHARDCODE(){
        mText.setValue("Luis");
    }
}