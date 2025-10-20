package com.example.inmoprop.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.example.inmoprop.R;
import com.example.inmoprop.models.Propietario;
import com.example.inmoprop.request.ApiClient;
import com.example.inmoprop.util.Validacion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Boolean> mEditar;
    private MutableLiveData<Integer> mVerBtGuardar;
    private MutableLiveData<Integer> mVerBtEditar;
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<String> toast;
    private MutableLiveData<String> mErrId;
    private MutableLiveData<String> mErrNombre;
    private MutableLiveData<String> mErrApellido;
    private MutableLiveData<String> mErrDni;
    private MutableLiveData<String> mErrTelefono;
    private MutableLiveData<String> mErrEmail;


    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();
    }

    public LiveData<Boolean> getEditar() {
        if (mEditar == null) {
            mEditar = new MutableLiveData<>(false);
        }
        return mEditar;
    }
    public LiveData<Integer> getVerBtEditar() {
        if (mVerBtEditar == null) {
            mVerBtEditar = new MutableLiveData<>(View.VISIBLE);
        }
        return mVerBtEditar;
    }
    public LiveData<Integer> getVerBtGuardar() {
        if (mVerBtGuardar == null) {
            mVerBtGuardar = new MutableLiveData<>(View.GONE);
        }
        return mVerBtGuardar;
    }
    public LiveData<Propietario> getPropietario() {
        if (mPropietario == null) {
            mPropietario = new MutableLiveData<>();
        }
        return mPropietario;
    }
    public LiveData<String> getToast() {
        if (toast == null) {
            toast = new MutableLiveData<>();
        }
        return toast;
    }
    public LiveData<String> getErrId() {
        if (mErrId == null) {
            mErrId = new MutableLiveData<>("");
        }
        return mErrId;
    }
    public LiveData<String> getErrNombre() {
        if (mErrNombre == null) {
            mErrNombre = new MutableLiveData<>("");
        }
        return mErrNombre;
    }
    public LiveData<String> getErrApellido() {
        if (mErrApellido == null) {
            mErrApellido = new MutableLiveData<>("");
        }
        return mErrApellido;
    }
    public LiveData<String> getErrDni() {
        if (mErrDni == null) {
            mErrDni = new MutableLiveData<>("");
        }
        return mErrDni;
    }
    public LiveData<String> getErrTelefono() {
        if (mErrTelefono == null) {
            mErrTelefono = new MutableLiveData<>("");
        }
        return mErrTelefono;
    }
    public LiveData<String> getErrEmail() {
        if (mErrEmail == null) {
            mErrEmail = new MutableLiveData<>("");
        }
        return mErrEmail;
    }

    public void mostrarPropietario(){
        //Propietario p = new Propietario(1, "Chico", "De Prueba","12345678","2664123456","probador@mail.com","CLAVE");
        //mPropietario.setValue(p);

        //Conecta con la DB para tomar el nombre del Propietario
        String token = ApiClient.leerToken(context);
        ApiClient.InmobiliariaService api = ApiClient.getApi();
        Call<Propietario> propietarioCall = api.getPropietario("Bearer "+token);
        propietarioCall.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    Propietario p = response.body();
                    mPropietario.setValue(p);
                }else{
                    toast.postValue("No se ha encontrado el usuario");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                toast.postValue("Error al intentar conectar con el servidor.");
            }
        });
    }

    public void activarEditar(){
            mEditar.setValue(true);
            mVerBtEditar.setValue(View.GONE);
            mVerBtGuardar.setValue(View.VISIBLE);
    }
    private void desactivarEditar(){
        mEditar.setValue(false);
        mVerBtEditar.setValue(View.VISIBLE);
        mVerBtGuardar.setValue(View.GONE);
    }

    public void guardarCambios(String id, String nombre, String apellido, String dni, String telefono, String email) {
        //Validaciones de campos
        String errId=Validacion.id(id);
        mErrId.setValue(errId);
        String errNombre=Validacion.nombre(nombre);
        mErrNombre.setValue(errNombre);
        String errApellido=Validacion.apellido(apellido);
        mErrApellido.setValue(errApellido);
        String errDni=Validacion.dni(dni);
        mErrDni.setValue(errDni);
        String errTelefono=Validacion.telefono(telefono);
        mErrTelefono.setValue(errTelefono);
        String errEmail = Validacion.usuario(email);
        mErrEmail.setValue(errEmail);

        //Actualiza el Propietario en la API
        if (errId.isBlank() && errNombre.isBlank() && errApellido.isBlank() && errDni.isBlank() && errTelefono.isBlank() && errEmail.isBlank()) {//Si no hay errores llama a la API
            int idPropietario = Integer.parseInt(id);
            Propietario p = new Propietario(idPropietario, nombre, apellido, dni, telefono, email, null);
            callActualizar(p);
        }
    }

    public void descartarCambios(){
        desactivarEditar();
        mPropietario.setValue(mPropietario.getValue());
    }

    private void callActualizar(Propietario p) {
        String token = ApiClient.leerToken(context);
        ApiClient.InmobiliariaService api = ApiClient.getApi();
        Call<Propietario> llamada = api.actualizarPropietario("Bearer "+token, p);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    Propietario p = response.body();
                    mPropietario.postValue(p);
                    toast.postValue("Los cambios se guardaron correctamente.");
                    desactivarEditar();
                } else {
                    toast.postValue("No se pudieron guardar los cambios.");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                toast.postValue("Error al intentar conectar con el servidor.");
            }
        });
    }
}