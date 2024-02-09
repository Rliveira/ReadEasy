package br.ufrpe.readeasy.beans;

import java.io.Serializable;
import java.time.LocalDate;

public class Fornecedor extends Usuario implements Serializable {
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
        StringBuilder sb = new StringBuilder();
        sb.append("+---------------------------------------------+\n");
        sb.append(String.format("| %-20s | %-20s |\n", "Tipo de Fornecedor", getTipoFornecedor()));
        return sb + super.toString();
    }
}
