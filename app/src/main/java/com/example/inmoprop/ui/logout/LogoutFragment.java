package com.example.inmoprop.ui.logout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmoprop.databinding.FragmentLogoutBinding;

public class LogoutFragment extends Fragment {
    private FragmentLogoutBinding b;
    private LogoutViewModel vm;
    // Variable para mantener la referencia al modal
    private LogoutDialogFragment loadingDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(LogoutViewModel.class);
        b = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = b.getRoot();


        mostrarModal(root);
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        b = null;
    }

    public void mostrarModal(View view) {
        LogoutDialogFragment dialogo = new LogoutDialogFragment();
        dialogo.show(getChildFragmentManager(), LogoutDialogFragment.TAG);
    }
}