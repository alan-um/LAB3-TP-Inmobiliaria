package com.example.inmoprop.ui.perfil;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inmoprop.R;
import com.example.inmoprop.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {
    private FragmentPerfilBinding b;
    private PerfilViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(PerfilViewModel.class);
        b = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = b.getRoot();

        //--Observe-------------------------------------
        vm.getEditar().observe(getViewLifecycleOwner(), editar ->{
            b.etNombre.setEnabled(editar);
            b.etApellido.setEnabled(editar);
            b.etDni.setEnabled(editar);
            b.etTelefono.setEnabled(editar);
            b.etEmail.setEnabled(editar);
        });
        vm.getVerBtEditar().observe(getViewLifecycleOwner(), visibilidad ->{
            b.btEditar.setVisibility(visibilidad);
        });
        vm.getVerBtGuardar().observe(getViewLifecycleOwner(), visibilidad ->{
            b.btGuardar.setVisibility(visibilidad);
            b.btCancelar.setVisibility(visibilidad);
        });
        vm.getPropietario().observe(getViewLifecycleOwner(), p ->{
            b.etIdPropietario.setText(p.getIdPropietario()+"");
            b.etNombre.setText(p.getNombre());
            b.etApellido.setText(p.getApellido());
            b.etDni.setText(p.getDni());
            b.etTelefono.setText(p.getTelefono());
            b.etEmail.setText(p.getEmail());
        });
        vm.getToast().observe(getViewLifecycleOwner(), s ->{
            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
        });
        vm.getErrId().observe(getViewLifecycleOwner(), b.tvErrIdPropietario::setText);
        vm.getErrNombre().observe(getViewLifecycleOwner(), b.tvErrNombre::setText);
        vm.getErrApellido().observe(getViewLifecycleOwner(), b.tvErrApellido::setText);
        vm.getErrDni().observe(getViewLifecycleOwner(), b.tvErrDni::setText);
        vm.getErrTelefono().observe(getViewLifecycleOwner(), b.tvErrTelefono::setText);
        vm.getErrEmail().observe(getViewLifecycleOwner(), b.tvErrEmail::setText);


        //--Listener------------------------------------
        b.btEditar.setOnClickListener(v->{
            vm.activarEditar();
        });
        b.btGuardar.setOnClickListener(v->{
            String id = b.etIdPropietario.getText().toString();
            String n = b.etNombre.getText().toString().trim();
            String a = b.etApellido.getText().toString().trim();
            String d = b.etDni.getText().toString().trim();
            String t = b.etTelefono.getText().toString().trim();
            String e = b.etEmail.getText().toString().trim();
            vm.guardarCambios(id,n,a,d,t,e);
        });
        b.btCancelar.setOnClickListener(v->{
            vm.descartarCambios();
        });
        b.btCambiarPass.setOnClickListener(v->{
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_nav).navigate(R.id.cambiarPassFragment);
        });

        //Cargar los datos del usuario logeado.
        vm.mostrarPropietario();
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        b = null;
    }
}