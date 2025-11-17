package com.example.meuprimeiroapp.model;

public class Endereco {

    private String cep, logradouro, bairro, localidade,uf;
    public Endereco(String cep, String logradouro, String bairro, String localidade, String uf) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }
}
