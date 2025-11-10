package com.example.inmoprop.ui.contratos;

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

import com.bumptech.glide.Glide;
import com.example.inmoprop.R;
import com.example.inmoprop.databinding.FragmentDetalleContratoBinding;
import com.example.inmoprop.databinding.FragmentDetalleInmuebleBinding;
import com.example.inmoprop.request.ApiClient;
import com.example.inmoprop.ui.inmuebles.DetalleInmuebleViewModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

public class DetalleContratoFragment extends Fragment {

    private FragmentDetalleContratoBinding b;
    private DetalleContratoViewModel vm;

    public static DetalleContratoFragment newInstance() {
        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        b = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        View root = b.getRoot();

        //Observe--------------------------
        vm.getContrato().observe(getViewLifecycleOwner(), c -> {
            b.etIdContrato.setText(c.getIdContrato() + "");
            b.etDireccionContrato.setText(c.getInmueble().getDireccion());
            b.etPropietarioContrato.setText(c.getInmueble().getDuenio().getNombre()+" "+c.getInmueble().getDuenio().getApellido());
            b.etInquilinoContrato.setText(c.getInquilino().getNombre()+" "+c.getInquilino().getApellido());
            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate ldFechaInicio = LocalDate.ofInstant(c.getFechaInicio().toInstant(),ZoneId.of("GMT"));
            b.etFechaInicioContrato.setText(ldFechaInicio.format(formatoFecha));
            LocalDate ldFechaFinalizacion = LocalDate.ofInstant(c.getFechaFinalizacion().toInstant(),ZoneId.of("GMT"));
            b.etFechaFinContrato.setText(ldFechaFinalizacion.format(formatoFecha));
            b.etPrecioContrato.setText(c.getMontoAlquiler()+"");
        });
        vm.getToast().observe(getViewLifecycleOwner(), s -> {
            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
        });
        vm.getErrId().observe(getViewLifecycleOwner(), b.tvErrIdContrato::setText);
        vm.getErrDireccion().observe(getViewLifecycleOwner(), b.tvErrDireccionContrato::setText);
        vm.getErrPropietario().observe(getViewLifecycleOwner(), b.tvErrPropietarioContrato::setText);
        vm.getErrInquilino().observe(getViewLifecycleOwner(), b.tvErrInquilinoContrato::setText);
        vm.getErrFechaInicio().observe(getViewLifecycleOwner(), b.tvErrFechaInicioContrato::setText);
        vm.getErrFechaFin().observe(getViewLifecycleOwner(), b.tvErrFechaFinContrato::setText);
        vm.getErrPrecio().observe(getViewLifecycleOwner(), b.tvErrPrecioContrato::setText);


        //Listener---------
        b.btVerPagos.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("idContrato",vm.getContrato().getValue().getIdContrato());
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_nav).navigate(R.id.pagosFragment,bundle);
        });

        vm.cargarContrato(getArguments());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        b = null;
    }
}