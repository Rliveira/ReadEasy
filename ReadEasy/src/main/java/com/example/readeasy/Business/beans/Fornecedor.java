package com.example.readeasy.Business.beans;

import java.time.LocalDate;

public class Fornecedor extends Usuario{
    private TipoFornecedor tipoFornecedor;

    //CONSTRUTOR:
    public Fornecedor(String nome, String cpf, LocalDate dataNascimento, String login, String senha, Endereco endereco,
                      String telefone, TipoFornecedor tipoFornecedor) {
        super(nome, cpf, dataNascimento, login, senha, endereco, telefone);
        this.tipoFornecedor = tipoFornecedor;
    }

    //GETS AND SETS:
    public TipoFornecedor getTipoFornecedor() {
        return tipoFornecedor;
    }

    public void setTipoFornecedor(TipoFornecedor tipoFornecedor) {
        this.tipoFornecedor = tipoFornecedor;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "tipoFornecedor=" + tipoFornecedor +
                '}' + super.toString();
    }
}
