package com.example.inmoprop.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmoprop.models.Inmueble;
import com.example.inmoprop.models.Propietario;
import com.example.inmoprop.request.ApiClient;
import com.example.inmoprop.util.Validacion;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Inmueble> mInmueble = new MutableLiveData<>();
    private MutableLiveData<Uri> mUriImg = new MutableLiveData<>();

    private MutableLiveData<Boolean> mEditar = new MutableLiveData<>(false);
    private MutableLiveData<Integer> mVerBtGuardar = new MutableLiveData<>(View.GONE);
    private MutableLiveData<Integer> mVerIdInmueble = new MutableLiveData<>(View.VISIBLE);

    private MutableLiveData<String> toast = new MutableLiveData<>();
    private MutableLiveData<String> mErrId = new MutableLiveData<>("");
    private MutableLiveData<String> mErrImg = new MutableLiveData<>("");
    private MutableLiveData<String> mErrDireccion = new MutableLiveData<>("");
    private MutableLiveData<String> mErrTipo = new MutableLiveData<>("");
    private MutableLiveData<String> mErrUso = new MutableLiveData<>("");
    private MutableLiveData<String> mErrSuperficie = new MutableLiveData<>("");
    private MutableLiveData<String> mErrAmbientes = new MutableLiveData<>("");
    private MutableLiveData<String> mErrPrecio = new MutableLiveData<>("");
    private MutableLiveData<String> mErrLatitud = new MutableLiveData<>("");
    private MutableLiveData<String> mErrLongitud = new MutableLiveData<>("");


    public DetalleInmuebleViewModel(@NonNull Application application) {
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

    public LiveData<String> getErrImg() {
        return mErrImg;
    }

    public LiveData<String> getErrTipo() {
        return mErrTipo;
    }

    public LiveData<String> getErrUso() {
        return mErrUso;
    }

    public LiveData<String> getErrSuperficie() {
        return mErrSuperficie;
    }

    public LiveData<String> getErrAmbientes() {
        return mErrAmbientes;
    }

    public LiveData<String> getErrPrecio() {
        return mErrPrecio;
    }

    public LiveData<String> getErrLatitud() {
        return mErrLatitud;
    }

    public LiveData<String> getErrLongitud() {
        return mErrLongitud;
    }

    public LiveData<Boolean> getEditar() {
        return mEditar;
    }

    public LiveData<Integer> getVerBtGuardar() {
        return mVerBtGuardar;
    }
    public LiveData<Integer> getVerIdInmueble() {
        return mVerIdInmueble;
    }

    public LiveData<Inmueble> getInmueble() {
        return mInmueble;
    }

    public LiveData<Uri> getUriImg() {
        return mUriImg;
    }

    public void inicio(Bundle args) {
        if (args == null) {
            activarEditar();
        } else {
            mostrarInmueble(args.getSerializable("inmueble", Inmueble.class));
        }
    }

    private void activarEditar() {
        mEditar.setValue(true);
        mVerBtGuardar.setValue(View.VISIBLE);
        mVerIdInmueble.setValue(View.GONE);
    }

    private void desactivarEditar() {
        mEditar.setValue(false);
        mVerBtGuardar.setValue(View.GONE);
        mVerIdInmueble.setValue(View.VISIBLE);
    }

    private void mostrarInmueble(Inmueble i) {
        if (i != null) {
            mInmueble.setValue(i);
        }
    }

    public void cambiarDisponible(String id, Boolean disponible) {
        if (!mEditar.getValue()) {//Si no es un inmueble nuevo, entonces actualiza DISPONIBLE
            //Validaciones de campos
            String errId = Validacion.id(id);
            mErrId.setValue(errId);
            if (errId.isBlank()) {//Si el id es un valor numérico crea el Inmueble y hace la llamada a la API
                Inmueble i = new Inmueble();
                i.setIdInmueble(Integer.parseInt(id));
                i.setDisponible(disponible);

                String token = ApiClient.leerToken(context);
                ApiClient.InmobiliariaService api = ApiClient.getApi();
                Call<Inmueble> llamada = api.actualizarInmueble("Bearer " + token, i);
                llamada.enqueue(new Callback<Inmueble>() {
                    @Override
                    public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                        if (response.isSuccessful()) {
                            toast.postValue("Se actualizó la disponibilidad del inmueble.");
                        } else {
                            toast.postValue("No se pudieron guardar los cambios.");
                        }
                    }

                    @Override
                    public void onFailure(Call<Inmueble> call, Throwable t) {
                        toast.postValue("Error al intentar conectar con el servidor.");
                    }
                });
            }
        }
    }

    public void crearInmueble(String direccion, String tipo, String uso, String superficie, String ambientes, String valor, String latitud, String longitud, Boolean disponible) {
        //Validaciones de campos
        String errImg;
        if (mUriImg.getValue() == null) {
            errImg = "Debe cargar la imagen del Inmueble.";

        } else {
            errImg = "";
        }
        mErrImg.setValue(errImg);

        String errDireccion = Validacion.direccion(direccion);
        mErrDireccion.setValue(errDireccion);

        String errTipo = Validacion.tipo(tipo);
        mErrTipo.setValue(errTipo);

        String errUso = Validacion.uso(uso);
        mErrUso.setValue(errUso);

        String errSuperficie = Validacion.superficie(superficie);
        mErrSuperficie.setValue(errSuperficie);

        String errAmbientes = Validacion.ambientes(ambientes);
        mErrAmbientes.setValue(errAmbientes);

        String errPrecio = Validacion.precio(valor);
        mErrPrecio.setValue(errPrecio);

        String errLatitud = Validacion.latitud(latitud);
        mErrLatitud.setValue(errLatitud);

        String errLongitud = Validacion.longitud(longitud);
        mErrLongitud.setValue(errLongitud);


        //Si no hay errores crea el inmueble y llama a la API
        if (errImg.isBlank() && errDireccion.isBlank() && errTipo.isBlank() && errUso.isBlank() && errSuperficie.isBlank() && errAmbientes.isBlank() && errPrecio.isBlank() && errLatitud.isBlank() && errLongitud.isBlank()) {
            int ambientesInt = Integer.parseInt(ambientes);
            int superficieInt = Integer.parseInt(superficie);
            Double valorDbl = Double.parseDouble(valor);
            Double latitudDbl = Double.parseDouble(latitud);
            Double longitudDbl = Double.parseDouble(longitud);
            Inmueble i = new Inmueble(direccion, uso, tipo, ambientesInt, superficieInt, latitudDbl, longitudDbl, valorDbl, disponible);
            callCrear(i);
        }
    }

    private byte[] transformarImagen() {
        try {
            Uri uri = mUriImg.getValue();
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);//Abre el canal que lee la URI de la Img.
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);//Crea el Bitmap con los datos de la IMG
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//Abre el canal que escribe un ByteArray
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException er) {
            toast.postValue("Error al transoformar la imagen en un ArrayByte.");
            return new byte[]{};
        }
    }

    private void callCrear(Inmueble i) {
        String token = ApiClient.leerToken(context);
        ApiClient.InmobiliariaService api = ApiClient.getApi();

        byte[] imagen = transformarImagen();
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
        MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);

        String inmuebleJson = new Gson().toJson(i);
        RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);

        Call<Inmueble> llamada = api.cargarInmueble("Bearer "+token, imagenPart, inmuebleBody);
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()) {
                    Inmueble i = response.body();
                    mInmueble.postValue(i);
                    toast.postValue("Se añadió el inmueble correctamente.");
                    desactivarEditar();
                } else {
                    toast.postValue("No se pudieron guardar los cambios.");
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                toast.postValue("Error al intentar conectar con el servidor.");
            }
        });
    }

    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            mUriImg.postValue(uri);
        }
    }
}