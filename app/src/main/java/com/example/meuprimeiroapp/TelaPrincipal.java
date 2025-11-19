package com.example.meuprimeiroapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meuprimeiroapp.model.Endereco;
import com.example.meuprimeiroapp.model.Usuario;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TelaPrincipal extends AppCompatActivity {

    private EditText editTextCEP;
    private Button btnPesquisar;
    private ListView listViewHistorico;
    private final ArrayList<Endereco> historicoBusca = new ArrayList<>();
    private ArrayAdapter<Endereco> adapter;
    private boolean isEditMode = false;
    private int editPosition = -1;

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
                TextView txtMensagem = findViewById(R.id.tvNomeUsuario);
                txtMensagem.setText(usuario.getNome());
        }
    }
    private void configurarClickItemHistorico() {

        listViewHistorico.setOnItemClickListener((parent, view, position, id) -> {
            Endereco enderecoSelecionado = historicoBusca.get(position);
            new AlertDialog.Builder(this)
                    .setTitle("Deseja editar ou atualizar este endereço?\n \n" + enderecoSelecionado.toString())
                    .setPositiveButton("Editar", (dialog, which) -> {
                        fillEditFieldsForEdit(enderecoSelecionado, position);
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
    public void configurarBotaoPesquisar() {
        btnPesquisar.setOnClickListener(v -> {
            if (validarCEP()) {
                String cep = editTextCEP.getText().toString().trim();
                BuscaCEP(cep);
            }
        });
    }

    private boolean validarCEP(){
        String cep = editTextCEP.getText().toString().trim();
        if (cep.length() == 7){
            return true;
        } else {
            Toast.makeText(this, "CEP inválido. Insira 7 dígitos", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private void fillEditFieldsForEdit(Endereco selecionado, int position){
        editTextCEP.setText(selecionado.getCep());
        isEditMode = true;
        editPosition = position;
        btnPesquisar.setText("Atualizar");
    }
    private void BuscaCEP(String cep) {
        new Thread(() -> {
            try {
                URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);

                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder resposta = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        resposta.append(line);
                    }
                    reader.close();

                    JSONObject json = new JSONObject(resposta.toString());
                    Endereco endereco = new Endereco(
                        json.optString("cep"),
                        json.optString("logradouro"),
                        json.optString("bairro"),
                        json.optString("localidade"),
                        json.optString("uf")
                    );

                    runOnUiThread(() -> {
                        adicionarEndereco(endereco);
                    });
                } else {
                    runOnUiThread(() ->
                        Toast.makeText(TelaPrincipal.this, "Erro na requisição: " + responseCode, Toast.LENGTH_SHORT).show()
                    );
                }
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(TelaPrincipal.this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    private void adicionarEndereco(Endereco endereco) {
        if (!isEditMode){
            historicoBusca.add(endereco);
        } else {
            historicoBusca.set(editPosition, endereco);
            btnPesquisar.setText("Pesquisar");
            isEditMode = false;
            editPosition = -1;
        }
        adapter.notifyDataSetChanged();
        editTextCEP.setText("");
    }
}
