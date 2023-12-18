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
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("+---------------------------------------------+\n");
        sb.append(String.format("| %-20s | %-20s |\n", "Nome", getNome()));
        sb.append(String.format("| %-20s | %-20s |\n", "CPF", getCpf()));
        sb.append(String.format("| %-20s | %-20s |\n", "Data de Nascimento", getDataNascimento()));
        sb.append(String.format("| %-20s | %-20s |\n", "Login", getLogin()));
        sb.append(String.format("| %-20s | %-20s |\n", "Senha", getSenha()));
        sb.append(String.format("| %-20s | %-20s |\n", "Endereço", getEndereco().getRua()));
        sb.append(String.format("| %-20s | %-20s |\n", "Telefone", getTelefone()));
        sb.append("+---------------------------------------------+\n");

        return sb.toString();
    }
}