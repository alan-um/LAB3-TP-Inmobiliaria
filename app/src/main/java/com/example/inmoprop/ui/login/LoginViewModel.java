package com.example.inmoprop.ui.login;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmoprop.request.ApiClient;
import com.example.inmoprop.ui.NavActivity;
import com.example.inmoprop.util.Validacion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> mErrLogin;
    private MutableLiveData<String> mErrUsuario;
    private MutableLiveData<String> mErrPass;
    private MutableLiveData<Boolean> enableBoton = new MutableLiveData<>(true);
    private MutableLiveData<Integer> verProgress = new MutableLiveData<>(View.INVISIBLE);

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();
        finLlamado();
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

    public LiveData<Boolean> getEnableBoton() {
        return enableBoton;
    }

    public LiveData<Integer> getVerProgress() {
        return verProgress;
    }

    public void ingresar(String u, String p) {
        //Validación de campos
        String errUsuario = Validacion.usuario(u);
        mErrUsuario.setValue(errUsuario);
        String errPass = Validacion.pass(p);
        mErrPass.setValue(errPass);

        //LLamada a la API
        if (errUsuario.isBlank() && errPass.isBlank()) {//Si no hay errores llama a la API
            callLogin(u, p);
        }
    }

    private void callLogin(String u, String p) {
        inicioLlamado();
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
                    finLlamado();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mErrLogin.postValue("Error al intentar conectar con el servidor.");
                finLlamado();
            }
        });
    }

    public void ingresarHARDCODE(String u, String p) {
        Intent intent = new Intent(getApplication(), NavActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        mErrLogin.postValue("");
    }

    private void inicioLlamado() {
        enableBoton.setValue(false);
        verProgress.setValue(View.VISIBLE);
        mErrLogin.setValue("");
    }

    public void finLlamado() {
        enableBoton.setValue(true);
        verProgress.setValue(View.INVISIBLE);
    }


    //------------------------Sensores-----------------------------------------
    private SensorManager manager;
    private List<Sensor> listaSensores;
    private DetectarAgitacion detectarAgitacion;

    public void activarDetector() {
        manager = (SensorManager) getApplication().getSystemService(Context.SENSOR_SERVICE);
        listaSensores = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        detectarAgitacion = new DetectarAgitacion();
        if (listaSensores.size() > 0) {
            manager.registerListener(detectarAgitacion, listaSensores.get(0), SensorManager.SENSOR_DELAY_GAME);
        }
    }

    public void desactivarDetector() {
        if (listaSensores.size() > 0) {
            manager.unregisterListener(detectarAgitacion);
        }
    }

    private class DetectarAgitacion implements SensorEventListener {
        private final float LIMITE_FUERZA_G = 3.7f; //Mínima aceleración para tener en cuenta.
        private final int TIEMPO_ENTRE_EVENTOS = 500; //Tiempo mínimo que debe transcurrir entre eventos.
        private final int TIEMPO_PARA_RESET_CONT = 3000; //Tiempo máximo sin eventos, resetea el contador.
        private final int CANT_MAX_AGITACIONES = 3; //Cantidad de agitaciones que se deben hacer para iniciar la llamada.
        private int cont = 0;
        private long tUltimoAgite = 0;


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float EG = SensorManager.GRAVITY_EARTH;
            float fuerzaG = (x * x + y * y + z * z) / (EG * EG);


            if (fuerzaG > LIMITE_FUERZA_G) {
                long tActual = System.currentTimeMillis();

                //Corrobora si paso el tiempo mínimo entre eventos
                if (tActual < tUltimoAgite + TIEMPO_ENTRE_EVENTOS) {
                    return;
                }

                //Resetear el contador, si el tiempo entre eventos supera el máximo.
                if (tActual > tUltimoAgite + TIEMPO_PARA_RESET_CONT) {
                    cont = 0;
                }

                cont++;
                tUltimoAgite = tActual;

                //Si el contador excede el límite hace la llamada
                if (cont >= CANT_MAX_AGITACIONES) {
                    //mDatos.setValue("Llamando a la Inmobiliaria");
                    Toast.makeText(context, "Llamando a la Inmobiliaria", Toast.LENGTH_SHORT).show();
                    Intent intentLlamada = new Intent(Intent.ACTION_CALL);
                    intentLlamada.setData(Uri.parse("tel:2664349020"));
                    intentLlamada.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentLlamada);
                }
            }
        }
    }
}
