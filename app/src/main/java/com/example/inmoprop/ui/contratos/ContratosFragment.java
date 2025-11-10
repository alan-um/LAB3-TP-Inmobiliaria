package com.example.inmoprop.ui.contratos;

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
import com.example.inmoprop.databinding.FragmentContratosBinding;
import com.example.inmoprop.databinding.FragmentInmueblesBinding;
import com.example.inmoprop.ui.inmuebles.InmuebleAdapter;

public class ContratosFragment extends Fragment {
    private FragmentContratosBinding b;
    private ContratosViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(ContratosViewModel.class);
        b = FragmentContratosBinding.inflate(inflater, container, false);
        View root = b.getRoot();

        //Observe-------------------------------
        vm.getListaInmuebles().observe(getViewLifecycleOwner(), inmuebles -> {
            ContratoAdapter contratoAdapter = new ContratoAdapter(inmuebles, getContext(), getLayoutInflater());
            GridLayoutManager glm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
            b.rvListaCotratos.setLayoutManager(glm);
            b.rvListaCotratos.setAdapter(contratoAdapter);
        });
        vm.getToast().observe(getViewLifecycleOwner(), s -> {
            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
        });

        //Listener------------------------------



        //Others-------------------------------
        vm.listarContratosVigentes();


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        b = null;
    }
}