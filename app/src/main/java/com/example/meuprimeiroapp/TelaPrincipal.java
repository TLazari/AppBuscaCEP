package com.example.meuprimeiroapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.meuprimeiroapp.model.Usuario;

public class TelaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_principal);

        TextView txtMensagem = findViewById(R.id.tvNomeUsuario);

        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            Usuario usuario = (Usuario) dados.getSerializable("usuario");
            txtMensagem.setText(usuario.getNome());
        }

    }
}
