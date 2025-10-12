package com.example.inmoprop.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmoprop.databinding.FragmentInicioBinding;
import com.example.inmoprop.request.ApiClient;

public class InicioFragment extends Fragment {
    private FragmentInicioBinding b;
    private InicioViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(InicioViewModel.class);
        b = FragmentInicioBinding.inflate(inflater, container, false);
        View root = b.getRoot();

        //final TextView textView = b.textHome;
        vm.getText().observe(getViewLifecycleOwner(), b.textInicio::setText);

        vm.buscarPropietario();
        //vm.buscarPropietarioHARDCODE();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        b = null;
    }
}