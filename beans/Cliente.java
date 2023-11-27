package beans;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente extends Usuario{

     private ArrayList<String> enderecosentrega;


    public Cliente(String nome, String cpf, LocalDate dataNascimento, String login, String senha, String endereco, String telefone) {
        super(nome, cpf, dataNascimento, login, senha, endereco, telefone);
        this.enderecosentrega = new ArrayList<>();
    }

    public ArrayList<String> getEnderecosentrega() {
        return enderecosentrega;
    }

    public void setEnderecosentrega(ArrayList<String> enderecosentrega) {
        this.enderecosentrega = enderecosentrega;
    }

    public void adicionarEndereco(String endereco) {
        this.enderecosentrega.add(endereco);
    }

    public void removerEndereco(String endereco) {
        this.enderecosentrega.remove(endereco);
    }
}
