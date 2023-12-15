package br.ufrpe.readeasy.beans;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente extends Usuario{
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
        this.enderecosentrega.remove(endereco);
    }

    //GETS AND SETS:
    public ArrayList<Endereco> getEnderecosentrega() {
        return enderecosentrega;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "enderecosentrega=" + enderecosentrega +
                '}' + super.toString();
    }
}