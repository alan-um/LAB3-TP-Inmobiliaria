package com.example.inmoprop.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmoprop.databinding.FragmentContratosBinding;
import com.example.inmoprop.databinding.FragmentInmueblesBinding;

public class ContratosFragment extends Fragment {
    private FragmentContratosBinding b;
    private ContratosViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(ContratosViewModel.class);
        b = FragmentContratosBinding.inflate(inflater, container, false);
        View root = b.getRoot();


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        b = null;
    }
}