package com.example.meuprimeiroapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meuprimeiroapp.model.Usuario;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText loginEditText = findViewById(R.id.editTextEmail);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        Button loginButton = findViewById(R.id.buttonLogin);
        Button registrarButton = findViewById(R.id.buttonRegister);

        Bundle dados = getIntent().getExtras();
        Usuario usuario;

        if (dados != null) {
            usuario = (Usuario) dados.getSerializable("usuario");
        } else {
            usuario = null;
        }

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register_User.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String login = loginEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (login.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (usuario != null && login.equals(usuario.getNome()) && password.equals(usuario.getSenha())) {
                    Logar(usuario);
                } else {
                    Toast.makeText(MainActivity.this, "Login ou senha incorretos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void Logar(Usuario usuario){
        Toast.makeText(MainActivity.this, "Login realizado com sucesso", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, TelaPrincipal.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
    }
}