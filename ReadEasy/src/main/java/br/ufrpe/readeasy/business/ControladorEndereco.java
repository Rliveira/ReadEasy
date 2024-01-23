package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Endereco;
import br.ufrpe.readeasy.data.*;
import br.ufrpe.readeasy.exceptions.ClienteNuloException;
import br.ufrpe.readeasy.exceptions.EnderecoNuloException;
import br.ufrpe.readeasy.exceptions.UsuarioInexistenteException;


import java.util.List;

public class ControladorEndereco implements IControladorEndereco{

    private static ControladorEndereco instance;
    private IRepositorioEndereco repEnderecos;

    private ControladorEndereco() {
        this.repEnderecos = RepositorioEndereco.getInstance();
    }

    public static ControladorEndereco getInstance() {
        if (instance == null) {
            instance = new ControladorEndereco();
        }
        return instance;
    }

    public void adicionarEnderecoCliente(String cpf, Endereco endereco) throws UsuarioInexistenteException, EnderecoNuloException,
            ClienteNuloException {
        if (cpf != null) {
            if(endereco != null) {
                Cliente cliente = RepositorioUsuario.getInstance().procurarCliente(cpf);

                if (cliente != null) {
                    cliente.adicionarEndereco(endereco);
                    repEnderecos.adicionarEndereco(endereco);
                } else {
                    throw new UsuarioInexistenteException(cpf);
                }
            }else{
                throw new EnderecoNuloException(endereco);
            }
        } else {
            throw new ClienteNuloException(cpf);
        }
    }

    public void removerEnderecoCliente(String cpf, Endereco endereco) throws UsuarioInexistenteException, EnderecoNuloException,
            ClienteNuloException {
        if (cpf != null) {
            if (endereco != null) {
                Cliente cliente = RepositorioUsuario.getInstance().procurarCliente(cpf);
                if (cliente != null) {
                    cliente.removerEndereco(endereco);
                    repEnderecos.removerEndereco(endereco.getCep());
                } else {
                     throw new UsuarioInexistenteException(cpf);
                }
            }else{
                throw new EnderecoNuloException(endereco);
            }
        } else {
            throw new ClienteNuloException(cpf);
        }
    }
    public Endereco obterEnderecoPorCep(int cep){
        return repEnderecos.obterEnderecoPorCep(cep);
    }

    public List<Endereco> listarEnderecos(){
        return repEnderecos.listarEnderecos();
    }

}
