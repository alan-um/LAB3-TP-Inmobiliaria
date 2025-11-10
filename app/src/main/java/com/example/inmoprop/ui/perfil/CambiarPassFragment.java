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
import com.example.inmoprop.databinding.FragmentCambiarPassBinding;
import com.example.inmoprop.databinding.FragmentPerfilBinding;

public class CambiarPassFragment extends Fragment {
    private FragmentCambiarPassBinding b;
    private CambiarPassViewModel vm;

    public static CambiarPassFragment newInstance() {
        return new CambiarPassFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(CambiarPassViewModel.class);
        b = FragmentCambiarPassBinding.inflate(inflater, container, false);
        View root = b.getRoot();

        vm.getToast().observe(getViewLifecycleOwner(), s ->{
            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
        });
        vm.getApiRespuesta().observe(getViewLifecycleOwner(),b->{
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_nav).navigate(R.id.nav_perfil);
        });
        vm.getErrActualPass().observe(getViewLifecycleOwner(), b.tvErrActualPass::setText);
        vm.getErrNuevaPass().observe(getViewLifecycleOwner(), b.tvErrNuevaPass::setText);
        vm.getErrRepitePass().observe(getViewLifecycleOwner(), b.tvErrRepitePass::setText);

        vm.getEnableBoton().observe(getViewLifecycleOwner(),b.btGuardar::setEnabled);
        vm.getVerProgress().observe(getViewLifecycleOwner(),b.pbGuardar::setVisibility);

        b.btGuardar.setOnClickListener(v-> {
            String a = b.etActualPass.getText().toString().trim();
            String n = b.etNuevaPass.getText().toString().trim();
            String r = b.etRepitePass.getText().toString().trim();
            vm.cambiarPass(a,n,r);
        });
        b.btCancelar.setOnClickListener(v->{
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_nav).navigate(R.id.nav_perfil);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        b = null;
    }
}