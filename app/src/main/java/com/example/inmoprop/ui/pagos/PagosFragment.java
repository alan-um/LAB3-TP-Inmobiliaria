package com.example.inmoprop.ui.pagos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inmoprop.R;
import com.example.inmoprop.databinding.FragmentInmueblesBinding;
import com.example.inmoprop.databinding.FragmentPagosBinding;
import com.example.inmoprop.ui.inmuebles.InmuebleAdapter;
import com.example.inmoprop.ui.inmuebles.InmueblesViewModel;

public class PagosFragment extends Fragment {

    private FragmentPagosBinding b;
    private PagosViewModel vm;

    public static PagosFragment newInstance() {
        return new PagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(PagosViewModel.class);
        b = FragmentPagosBinding.inflate(inflater, container, false);
        View root = b.getRoot();

        //Observe-------------------------------
        vm.getListaPagos().observe(getViewLifecycleOwner(), pagos -> {
            PagoAdapter pagoAdapter = new PagoAdapter(pagos, getContext(), getLayoutInflater());
            GridLayoutManager glm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
            b.rvListaPagos.setLayoutManager(glm);
            b.rvListaPagos.setAdapter(pagoAdapter);
        });
        vm.getToast().observe(getViewLifecycleOwner(), s -> {
            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
        });

        //Listener------------------------------
        /*b.btNuevoInmueble.setOnClickListener(v->{
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_nav).navigate(R.id.detalleInmuebleFragment);
        });*/

        vm.listarPagos(getArguments());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        b = null;
    }
}