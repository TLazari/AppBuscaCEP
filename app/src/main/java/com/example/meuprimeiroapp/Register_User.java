package com.example.meuprimeiroapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.example.meuprimeiroapp.model.Usuario;


public class Register_User extends AppCompatActivity {
    private  EditText editNome,editCPF,editCEP,editEmail,editSenha,editConfirmarSenha;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_user);

        editNome = findViewById(R.id.editNome);
        editCPF = findViewById(R.id.editCPF);
        editCEP = findViewById(R.id.editCEP);
        editEmail = findViewById(R.id.editEmailLogin);
        editSenha = findViewById(R.id.editSenha);
        editConfirmarSenha = findViewById(R.id.editConfirmarSenha);

        Button btnRegister = findViewById(R.id.btnRegistrar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarUsuario();
                }
            });
        }

    private void salvarUsuario() {
        Usuario usuario = new Usuario(
                editNome.getText().toString(),
                editCPF.getText().toString(),
                editCEP.getText().toString(),
                editEmail.getText().toString(),
                editSenha.getText().toString()
        );
        if (usuario.ehValido()){
            Toast.makeText(Register_User.this, "Salvando Usuario", Toast.LENGTH_LONG).show();

            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", usuario);

            Intent intent = new Intent(Register_User.this, MainActivity.class);
            intent.putExtras(bundle);

            startActivity(intent);
        }else{
            Toast.makeText(Register_User.this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
        }
    }
}