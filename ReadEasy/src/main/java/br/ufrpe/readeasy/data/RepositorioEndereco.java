package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Endereco;
import br.ufrpe.readeasy.beans.Usuario;

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

    @Override
    public void adicionarEndereco(Endereco endereco) {
        enderecos.put(endereco.getCep(), endereco);
    }

    @Override
    public void removerEndereco(int cep) {
        enderecos.remove(cep);
    }

    @Override
    public Endereco obterEnderecoPorCep(int cep) {
        return enderecos.get(cep);
    }

    @Override
    public List<Endereco> listarEnderecos() {
        return new ArrayList<>(enderecos.values());
    }

    @Override
    public void adicionarEnderecoCliente(String cpf, Endereco endereco) {
        Cliente cliente = RepositorioUsuario.getInstance().procurarCliente(cpf);
        cliente.adicionarEndereco(endereco);
        enderecos.put(endereco.getCep(), endereco);
    }

    @Override
    public void removerEnderecoCliente(String cpf, Endereco endereco) {
        Cliente cliente = RepositorioUsuario.getInstance().procurarCliente(cpf);
        cliente.removerEndereco(endereco);
        enderecos.remove(endereco.getCep(), endereco);
    }
}