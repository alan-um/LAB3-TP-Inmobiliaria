package com.example.inmoprop.ui.logout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import com.example.inmoprop.R;
import com.example.inmoprop.request.ApiClient;

public class LogoutDialogFragment extends DialogFragment {

    public static final String TAG = "LogoutDialogFragment";

    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Usa AlertDialog.Builder para construir la alerta/modal
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirma salir:")
                .setMessage("¿Estás seguro que desea salir?")

                // Botón ACEPTAR
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ApiClient.borrarToken(getContext());
                        getActivity().finish();
                    }
                })

                // Botón CANCELAR
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_nav).navigate(R.id.nav_inicio);
                    }
                });
        return builder.create();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false); //Para evitar que se cancela tocando fuera del dialogo.
    }

}