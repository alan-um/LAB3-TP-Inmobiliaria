package com.example.inmoprop.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.inmoprop.R;
import com.example.inmoprop.databinding.FragmentDetalleInmuebleBinding;
import com.example.inmoprop.databinding.FragmentInmueblesBinding;
import com.example.inmoprop.databinding.FragmentPerfilBinding;

public class InmueblesFragment extends Fragment {
    private FragmentInmueblesBinding b;
    private InmueblesViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(InmueblesViewModel.class);
        b = FragmentInmueblesBinding.inflate(inflater, container, false);
        View root = b.getRoot();

        //Observe-------------------------------
        vm.getListaInmuebles().observe(getViewLifecycleOwner(), inmuebles -> {
            InmuebleAdapter inmuebleAdapter = new InmuebleAdapter(inmuebles, getContext(), getLayoutInflater());
            GridLayoutManager glm = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
            b.rvListaInmueble.setLayoutManager(glm);
            b.rvListaInmueble.setAdapter(inmuebleAdapter);
        });
        vm.getToast().observe(getViewLifecycleOwner(), s -> {
            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
        });

        //Listener------------------------------
        b.btNuevoInmueble.setOnClickListener(v->{
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_nav).navigate(R.id.detalleInmuebleFragment);
        });

        vm.listarInmuebles();
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        b = null;
    }
}