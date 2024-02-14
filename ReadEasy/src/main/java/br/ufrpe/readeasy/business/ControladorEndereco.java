package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Endereco;
import br.ufrpe.readeasy.data.*;
import br.ufrpe.readeasy.exceptions.ClienteNuloException;
import br.ufrpe.readeasy.exceptions.EnderecoInexistenteException;
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
                    repEnderecos.salvarArquivo();
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

    public void atualizarEndereco(Endereco endereco, int cep, String rua, String bairro, String cidade, String estado)
            throws EnderecoNuloException {
        if (endereco != null) {

            if (cep > 0 && endereco.getCep() != cep) {
                endereco.setCep(cep);
            }
            if (rua != null && !rua.isEmpty() && !endereco.getRua().equals(rua)) {
                endereco.setRua(rua);
            }
            if (bairro != null && !bairro.isEmpty() && !endereco.getBairro().equals(bairro)) {
                endereco.setBairro(bairro);
            }
            if (cidade != null && !cidade.isEmpty() && !endereco.getCidade().equals(cidade)) {
                endereco.setCidade(cidade);
            }
            if (estado != null && !estado.isEmpty() && !endereco.getEstado().equals(estado)) {
                endereco.setEstado(estado);
            }


            repEnderecos.atualizarEndereco(endereco, cep, rua, bairro, cidade, estado);

            repEnderecos.salvarArquivo();

        } else {
            throw new EnderecoNuloException(endereco);
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
                    repEnderecos.salvarArquivo();
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
