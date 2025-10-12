package com.example.inmoprop.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmoprop.R;
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


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        b = null;
    }
}