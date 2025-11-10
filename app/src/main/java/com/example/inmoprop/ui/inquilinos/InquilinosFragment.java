package com.example.inmoprop.ui.inquilinos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmoprop.databinding.FragmentInmueblesBinding;
import com.example.inmoprop.databinding.FragmentInquilinosBinding;

public class InquilinosFragment extends Fragment {
    private FragmentInquilinosBinding b;
    private InquilinosViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(InquilinosViewModel.class);
        b = FragmentInquilinosBinding.inflate(inflater, container, false);
        View root = b.getRoot();

        //--Observe-------------------------------------
        vm.getInquilino().observe(getViewLifecycleOwner(), i ->{
            b.etIdInquilino.setText(i.getIdInquilino()+"");
            b.etNombreInquilino.setText(i.getNombre());
            b.etApellidoInquilino.setText(i.getApellido());
            b.etDniInquilino.setText(i.getDni());
            b.etTelefonoInquilino.setText(i.getTelefono());
            b.etEmailInquilino.setText(i.getEmail());
        });
        vm.getToast().observe(getViewLifecycleOwner(), s ->{
            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
        });
        vm.getErrId().observe(getViewLifecycleOwner(), b.tvErrIdInquilino::setText);
        vm.getErrNombre().observe(getViewLifecycleOwner(), b.tvErrNombreInquilino::setText);
        vm.getErrApellido().observe(getViewLifecycleOwner(), b.tvErrApellidoInquilino::setText);
        vm.getErrDni().observe(getViewLifecycleOwner(), b.tvErrDniInquilino::setText);
        vm.getErrTelefono().observe(getViewLifecycleOwner(), b.tvErrTelefonoInquilino::setText);
        vm.getErrEmail().observe(getViewLifecycleOwner(), b.tvErrEmailInquilino::setText);

        vm.mostrarInquilino(getArguments());
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        b = null;
    }
}