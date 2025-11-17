package com.example.meuprimeiroapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.meuprimeiroapp.model.Endereco;
import com.example.meuprimeiroapp.model.Usuario;

import java.util.ArrayList;

public class TelaPrincipal extends AppCompatActivity {

    private EditText editTextCEP;
    private Button btnPesquisar;
    private ListView listViewHistorico;
    private ArrayList<Endereco> historicoBusca = new ArrayList<>();
    private ArrayAdapter<Endereco> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_principal);

        inicializarComponentes();
        configurarListeners();
        carregarDadosUsuario();

    }
    private void inicializarComponentes() {
        editTextCEP = findViewById(R.id.etCEP);
        btnPesquisar = findViewById(R.id.btnBuscar);
        listViewHistorico = findViewById(R.id.lvCep);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historicoBusca);
        listViewHistorico.setAdapter(adapter);
    }

    private void configurarListeners() {
        configurarBotaoPesquisar();
        configurarClickItemHistorico();
    }
    private void carregarDadosUsuario() {
        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            Usuario usuario = (Usuario) dados.getSerializable("usuario");
            if (usuario == null) {
                TextView txtMensagem = findViewById(R.id.tvNomeUsuario);
                txtMensagem.setText(usuario.getNome());
            }
        }
    }
    private void configurarClickItemHistorico() {

        listViewHistorico.setOnItemClickListener((parent, view, position, id) -> {
            Endereco enderecoSelecionado = historicoBusca.get(position);
            new AlertDialog.Builder(this)
                    .setTitle("Deseja editar ou atualizar este endereço?\n \n" + enderecoSelecionado.toString())
                    .setPositiveButton("Editar", (dialog, which) -> {
                        // Função para preencher campos)
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

        listViewHistorico.setOnItemLongClickListener((parent, view, position, id) -> {
            Endereco enderecoSelecionado = historicoBusca.get(position);
            new AlertDialog.Builder(this)
                    .setTitle("Excluir Item")
                    .setMessage("Confirma a exclusão deste endereço?\n \n" + enderecoSelecionado.toString())
                    .setPositiveButton("Excluir",(dialog, which) -> {
                        historicoBusca.remove(enderecoSelecionado);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
            return true;
        });

    }
    private void configurarBotaoPesquisar() {
        btnPesquisar.setOnClickListener(v -> {
            String cep = editTextCEP.getText().toString().trim();
            if (cep.length() == 8){
                // new BuscaCepTask().execute(cep);
            } else {
                Toast.makeText(this, "CEP inválido. Insira 8 dígitos", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
