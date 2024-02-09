package br.ufrpe.readeasy.beans;

import br.ufrpe.readeasy.data.RepositorioUsuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente extends Usuario implements Serializable {
    private ArrayList<Endereco> enderecosentrega;

    //CONSTRUTOR:
    public Cliente(String nome, String cpf, LocalDate dataNascimento, String login, String senha, Endereco endereco,
                   String telefone) {
        super(nome, cpf, dataNascimento, login, senha, endereco, telefone);
        this.enderecosentrega = new ArrayList<>();
    }

    //MÉTODOS:
    public void adicionarEndereco(Endereco endereco) {
        this.enderecosentrega.add(endereco);
    }

    public void removerEndereco(Endereco endereco) {
        for (Endereco end : this.enderecosentrega) {
            if (end.equals(endereco)) {
                this.enderecosentrega.remove(end);
                break;
            }
        }
    }

    //GETS AND SETS:
    public ArrayList<Endereco> getEnderecosentrega() {
        return enderecosentrega;
    }

    @Override
    public String toString() { return super.toString();}
}