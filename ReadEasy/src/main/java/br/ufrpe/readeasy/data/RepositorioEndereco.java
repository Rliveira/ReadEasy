package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.Endereco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositorioEndereco implements IRepositorioEndereco{
    private static IRepositorioEndereco instance;
    private Map<Integer, Endereco> enderecos;


    public static IRepositorioEndereco getInstance(){
        if (instance == null) {
            instance = new RepositorioEndereco();
        }
        return instance;
    }

    public RepositorioEndereco() {
        this.enderecos = new HashMap<>();
    }

    public void adicionarEndereco(Endereco endereco) {
        enderecos.put(endereco.getCep(), endereco);
    }

    public void removerEndereco(int cep) {
        enderecos.remove(cep);
    }

    public Endereco obterEnderecoPorCep(int cep) {
        return enderecos.get(cep);
    }

    public List<Endereco> listarEnderecos() {
        return new ArrayList<>(enderecos.values());
    }
}