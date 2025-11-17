package com.example.meuprimeiroapp.model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String nome;
    private String cpf;
    private String Cep;
    private String email;
    private String senha;

    public Usuario(String nome, String cpf, String cep, String email, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.Cep = cep;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String login, String password) {
        this.nome = login;
        this.senha = password;
    }

    public boolean ehValido() {
        return nome != null && !nome.isEmpty() &&
                email != null && email.contains("@") &&
                senha != null && !senha.isEmpty() &&
                cpf != null && !cpf.isEmpty();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return Cep;
    }

    public void setCep(String cep) {
        Cep = cep;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
