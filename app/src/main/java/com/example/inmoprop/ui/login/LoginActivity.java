package com.example.inmoprop.ui.login;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.Intent.ACTION_AIRPLANE_MODE_CHANGED;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

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

        solicitarPermisos();

        b.etUsuario.setText("propietario@mail.com");
        b.etPass.setText("123");

        //Observe
        vm.getErrUsuario().observe(this, b.tvErrUsuario::setText);//Forma más corta!! Usando ::
        vm.getErrPass().observe(this, b.tvErrPass::setText);
        vm.getErrLogin().observe(this, b.tvErrLogin::setText);

        vm.getEnableBoton().observe(this,b.btLogin::setEnabled);
        vm.getVerProgress().observe(this,b.pbLogin::setVisibility);

        //Listener
        b.btLogin.setOnClickListener(v -> {
            vm.ingresar(b.etUsuario.getText().toString().trim(), b.etPass.getText().toString());
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        vm.activarDetector();
    }

    @Override
    protected void onStop() {
        super.onStop();
        vm.desactivarDetector();
        vm.finLlamado();
    }

    public void solicitarPermisos(){
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                && (checkSelfPermission(CALL_PHONE) != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{CALL_PHONE}, 1000);
        }
    }
}