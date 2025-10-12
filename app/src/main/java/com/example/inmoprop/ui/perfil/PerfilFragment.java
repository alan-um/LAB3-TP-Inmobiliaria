package com.example.inmoprop.ui.perfil;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmoprop.R;
import com.example.inmoprop.databinding.FragmentInicioBinding;
import com.example.inmoprop.databinding.FragmentPerfilBinding;
import com.example.inmoprop.ui.inicio.InicioViewModel;

public class PerfilFragment extends Fragment {
    private FragmentPerfilBinding b;
    private PerfilViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(PerfilViewModel.class);
        b = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = b.getRoot();

        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        b = null;
    }
}