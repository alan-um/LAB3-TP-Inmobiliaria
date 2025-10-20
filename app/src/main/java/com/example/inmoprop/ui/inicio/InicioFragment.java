package com.example.inmoprop.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmoprop.R;
import com.example.inmoprop.databinding.FragmentInicioBinding;
import com.google.android.gms.maps.SupportMapFragment;

public class InicioFragment extends Fragment {
    private FragmentInicioBinding b;
    private InicioViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(InicioViewModel.class);
        b = FragmentInicioBinding.inflate(inflater, container, false);
        View root = b.getRoot();

        //Observe
        vm.getNombre().observe(getViewLifecycleOwner(), b.tvNombre::setText);
        vm.getDireccion().observe(getViewLifecycleOwner(), b.tvDireccion::setText);
        vm.getToast().observe(getViewLifecycleOwner(), s ->{
            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
        });

        vm.getMapaActual().observe(getViewLifecycleOwner(),mapaActual -> {
            SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapa);
            supportMapFragment.getMapAsync(mapaActual);
        });

        //Other
        vm.saludar();
        vm.cargarMapa();
        //vm.saludarHARDCODE();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        b = null;
    }
}