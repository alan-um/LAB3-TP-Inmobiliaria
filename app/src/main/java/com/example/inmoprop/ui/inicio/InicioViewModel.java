package com.example.inmoprop.ui.inicio;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmoprop.models.Propietario;
import com.example.inmoprop.request.ApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioViewModel extends AndroidViewModel {
    private final String DIRECCION = "Av. Eva Duarte 331";
    private final LatLng LAT_LONG = new LatLng(-32.3478139449026, -65.00473800025674);
    private Context context;

    private MutableLiveData<String> mNombre;
    private MutableLiveData<String> mDireccion;
    private MutableLiveData<String> toast;
    private MutableLiveData<MapaActual> mMapaActual;

    public InicioViewModel(@NonNull Application application) {
        super(application);
        context=getApplication();
    }

    public LiveData<String> getNombre() {
        if(mNombre==null){
            mNombre = new MutableLiveData<>();
        }
        return mNombre;
    }
    public LiveData<String> getDireccion() {
        if(mDireccion==null){
            mDireccion = new MutableLiveData<>();
        }
        return mDireccion;
    }
    public LiveData<MapaActual> getMapaActual() {
        if(mMapaActual==null){
            mMapaActual=new MutableLiveData<>();
        }
        return mMapaActual;
    }
    public LiveData<String> getToast() {
        if(toast ==null){
            toast = new MutableLiveData<>();
        }
        return toast;
    }

    public void saludar(){
        //Conecta con la DB para tomar el nombre del Propietario
        String token = ApiClient.leerToken(context);
        ApiClient.InmobiliariaService api = ApiClient.getApi();
        Call<Propietario> propietarioCall = api.getPropietario("Bearer "+token);
        propietarioCall.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    Propietario p = response.body();
                    mNombre.postValue(p.getNombre());
                }else{
                    toast.postValue("No se ha encontrado el usuario");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                toast.postValue("Error al intentar conectar con el servidor.");
            }
        });

        //Indica la dirección de la Inmobiliaria
        mDireccion.setValue("Recuerda que puedas encontrarnos en: "+DIRECCION);
    }
    public void saludarHARDCODE(){
        mNombre.setValue("Luis HARD");
    }

    public void cargarMapa(){
        MapaActual mapaActual = new MapaActual();
        mMapaActual.setValue(mapaActual);
    }
    public class MapaActual implements OnMapReadyCallback {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {//Cuando el mapa está listo lo muestra

            //Para crear un marcador
            MarkerOptions marcadorULP = new MarkerOptions();
            marcadorULP.position(LAT_LONG);
            marcadorULP.title("Inm. de las Sierras");

            //Para agregar los marcadores al mapa
            googleMap.addMarker(marcadorULP);

            //Selecciona el tipo de mapa
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            CameraPosition cameraPosition = new CameraPosition.Builder().target(LAT_LONG).zoom(18.5f).bearing(0).tilt(10).build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.animateCamera(cameraUpdate);
        }
    }
}