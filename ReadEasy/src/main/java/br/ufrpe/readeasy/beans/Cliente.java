package br.ufrpe.readeasy.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente extends Usuario implements Serializable {
    private static final long serialVersionUID = 6L;
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

    public void editarEnderecoDeEntrega(Endereco endereco, int novoCep, String novaRua, String novoBairro,
                                        String novaCidade ,String novoEstado){
        endereco.setCep(novoCep);
        endereco.setRua(novaRua);
        endereco.setBairro(novoBairro);
        endereco.setCidade(novaCidade);
        endereco.setEstado(novoEstado);
    }

    //GETS AND SETS:
    public ArrayList<Endereco> getEnderecosentrega() {
        return enderecosentrega;
    }

    @Override
    public String toString() { return super.toString();}
}