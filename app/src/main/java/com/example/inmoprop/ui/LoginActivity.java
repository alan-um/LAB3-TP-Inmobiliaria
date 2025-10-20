package com.example.inmoprop.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmoprop.R;
import com.example.inmoprop.databinding.ActivityLoginBinding;

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
        vm.getErrUsuario().observe(this, b.tvErrUsuario::setText);//Forma más corta!! Usando ::
        vm.getErrPass().observe(this, b.tvErrPass::setText);
        vm.getErrLogin().observe(this, b.tvErrLogin::setText);

        //Listener
        b.btLogin.setOnClickListener(v -> {
            vm.ingresar(b.etUsuario.getText().toString().trim(), b.etPass.getText().toString());
            //vm.ingresarHARDCODE(b.etUsuario.getText().toString().trim(), b.etPass.getText().toString());
        });
    }
}