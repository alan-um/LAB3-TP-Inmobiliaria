package com.example.inmoprop.ui.inmuebles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inmoprop.R;
import com.example.inmoprop.databinding.FragmentDetalleInmuebleBinding;
import com.example.inmoprop.databinding.FragmentInmueblesBinding;
import com.example.inmoprop.models.Inmueble;
import com.example.inmoprop.request.ApiClient;

public class DetalleInmuebleFragment extends Fragment {

    private FragmentDetalleInmuebleBinding b;
    private DetalleInmuebleViewModel vm;
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        b = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        View root = b.getRoot();


        //Observe--------------------------
        vm.getEditar().observe(getViewLifecycleOwner(), editar -> {
            b.etDireccionInmueble.setEnabled(editar);
            b.etTipoInmueble.setEnabled(editar);
            b.etUsoInmueble.setEnabled(editar);
            b.etSuperficieInmueble.setEnabled(editar);
            b.etAmbientesInmueble.setEnabled(editar);
            b.etPrecioInmueble.setEnabled(editar);
            b.etLatitudInmueble.setEnabled(editar);
            b.etLongitudInmueble.setEnabled(editar);
        });
        vm.getVerBtGuardar().observe(getViewLifecycleOwner(), visibilidad -> {
            b.btAgregarImagen.setVisibility(visibilidad);
            b.btGuardarInmueble.setVisibility(visibilidad);
            b.btCancelarInmueble.setVisibility(visibilidad);
        });
        vm.getVerIdInmueble().observe(getViewLifecycleOwner(), visibilidad -> {
            b.tvIdInmueble.setVisibility(visibilidad);
            b.etIdInmueble.setVisibility(visibilidad);
        });
        vm.getInmueble().observe(getViewLifecycleOwner(), i -> {
            b.etIdInmueble.setText(i.getIdInmueble() + "");
            Glide.with(this)
                    .load(ApiClient.URLBASE + i.getImagen())
                    .placeholder(R.drawable.logo_inmo_sierras)
                    .error("null")
                    .into(b.imgInmueble);
            b.etDireccionInmueble.setText(i.getDireccion());
            b.etTipoInmueble.setText(i.getTipo());
            b.etUsoInmueble.setText(i.getUso());
            b.etSuperficieInmueble.setText(i.getSuperficie() + "");
            b.etAmbientesInmueble.setText(i.getAmbientes() + "");
            b.etPrecioInmueble.setText(i.getValor() + "");
            b.etLatitudInmueble.setText(i.getLatitud() + "");
            b.etLongitudInmueble.setText(i.getLongitud() + "");
            b.cbDisponibleInmueble.setChecked(i.isDisponible());
        });
        vm.getUriImg().observe(getViewLifecycleOwner(),uri->{
            b.imgInmueble.setImageURI(uri);
        });
        vm.getToast().observe(getViewLifecycleOwner(), s -> {
            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
        });
        vm.getErrId().observe(getViewLifecycleOwner(), b.tvErrIdInmueble::setText);
        vm.getErrImg().observe(getViewLifecycleOwner(), b.tvErrImgInmueble::setText);
        vm.getErrDireccion().observe(getViewLifecycleOwner(), b.tvErrDireccionInmueble::setText);
        vm.getErrTipo().observe(getViewLifecycleOwner(), b.tvErrTipoInmueble::setText);
        vm.getErrUso().observe(getViewLifecycleOwner(), b.tvErrUsoInmueble::setText);
        vm.getErrSuperficie().observe(getViewLifecycleOwner(), b.tvErrSuperficieInmueble::setText);
        vm.getErrAmbientes().observe(getViewLifecycleOwner(), b.tvErrAmbientesInmueble::setText);
        vm.getErrPrecio().observe(getViewLifecycleOwner(), b.tvErrPrecioInmueble::setText);
        vm.getErrLatitud().observe(getViewLifecycleOwner(), b.tvErrLatitudInmueble::setText);
        vm.getErrLongitud().observe(getViewLifecycleOwner(), b.tvErrLongitudInmueble::setText);


        //Listener---------
        b.cbDisponibleInmueble.setOnClickListener(v -> {
            vm.cambiarDisponible(b.etIdInmueble.getText().toString(), b.cbDisponibleInmueble.isChecked());
        });
        b.btAgregarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch(intent);//Inicia la activity de galeria
            }
        });
        b.btGuardarInmueble.setOnClickListener(v -> {
            //b.etIdInmueble.setText(i.getIdInmueble()+"");
            //b.etDuenioInmueble.setText(i.getDuenio().getNombre()+" "+i.getDuenio().getApellido());
            vm.crearInmueble(b.etDireccionInmueble.getText().toString(),
                    b.etTipoInmueble.getText().toString(),
                    b.etUsoInmueble.getText().toString(),
                    b.etSuperficieInmueble.getText().toString(),
                    b.etAmbientesInmueble.getText().toString(),
                    b.etPrecioInmueble.getText().toString(),
                    b.etLatitudInmueble.getText().toString(),
                    b.etLongitudInmueble.getText().toString(),
                    b.cbDisponibleInmueble.isChecked());
        });
        b.btCancelarInmueble.setOnClickListener(v -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_nav).navigate(R.id.nav_inmuebles);
        });

        abrirGaleria();//Prepara la galeria para buscar las fotos!
        vm.inicio(getArguments());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        b = null;
    }
    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                vm.recibirFoto(result);
            }
        });
    }
}