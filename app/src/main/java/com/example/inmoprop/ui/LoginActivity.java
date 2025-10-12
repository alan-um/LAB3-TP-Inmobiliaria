package com.example.inmoprop.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmoprop.R;
import com.example.inmoprop.databinding.ActivityLoginBinding;
import com.example.inmoprop.request.ApiClient;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding b;
    private LoginViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityLoginBinding.inflate(getLayoutInflater());
        vm = new ViewModelProvider(this).get(LoginViewModel.class);

        EdgeToEdge.enable(this);
        setContentView(b.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        b.etUsuario.setText("luisprofessor@gmail.com");
        b.etPass.setText("DEEKQW");

        //Observe
        /*vm.getError().observe(this, e->{
            b.tvError.setText(e);
        });*/
        vm.getError().observe(this, b.tvError::setText);//Forma más corta!! Usando ::

        //Listener
        b.btLogin.setOnClickListener(v -> {
            vm.ingresar(b.etUsuario.getText().toString(), b.etPass.getText().toString());
            //vm.ingresarHARDCODE(b.etUsuario.getText().toString(), b.etPass.getText().toString());
        });
    }
}