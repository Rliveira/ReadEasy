package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.Endereco;
import br.ufrpe.readeasy.exceptions.ClienteNuloException;
import br.ufrpe.readeasy.exceptions.EnderecoNuloException;
import br.ufrpe.readeasy.exceptions.UsuarioInexistenteException;

import java.util.List;

public interface IControladorEndereco {

    public void adicionarEnderecoCliente(String cpf, Endereco endereco) throws UsuarioInexistenteException, EnderecoNuloException,
            ClienteNuloException;

    public void removerEnderecoCliente(String cpf, Endereco endereco) throws UsuarioInexistenteException, EnderecoNuloException,
            ClienteNuloException;

    public Endereco obterEnderecoPorCep(int cep);

    public List<Endereco> listarEnderecos();
}
