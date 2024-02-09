package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Endereco;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositorioEndereco implements IRepositorioEndereco, Serializable{
    private static IRepositorioEndereco instance;
    private Map<Integer, Endereco> enderecos;


    public static IRepositorioEndereco getInstance(){
        if (instance == null) {
            instance = lerDoArquivo();
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

    private static RepositorioEndereco lerDoArquivo() {
        RepositorioEndereco instanciaLocal = null;

        File in = new File("RepoEnderecos.dat");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(in);
            ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            instanciaLocal = (RepositorioEndereco) o;
        } catch (Exception e) {
            instanciaLocal = new RepositorioEndereco();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {/* Silent exception */
                }
            }
        }

        return instanciaLocal;
    }

    @Override
    public void salvarArquivo() {
        if (instance == null) {
            return;
        }
        File out = new File("RepoEnderecos.dat");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(out);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(instance);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    /* Silent */}
            }
        }
    }
}