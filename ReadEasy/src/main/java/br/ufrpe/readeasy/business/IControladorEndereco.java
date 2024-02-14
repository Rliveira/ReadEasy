
package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.Endereco;
import br.ufrpe.readeasy.exceptions.ClienteNuloException;
import br.ufrpe.readeasy.exceptions.EnderecoInexistenteException;
import br.ufrpe.readeasy.exceptions.EnderecoNuloException;
import br.ufrpe.readeasy.exceptions.UsuarioInexistenteException;

import java.util.List;

public interface IControladorEndereco {

    void adicionarEnderecoCliente(String cpf, Endereco endereco) throws UsuarioInexistenteException, EnderecoNuloException,
            ClienteNuloException;

    public void atualizarEndereco (Endereco endereco, int cep, String rua, String bairro, String cidade, String estado) throws
            EnderecoNuloException, EnderecoInexistenteException;
    void removerEnderecoCliente(String cpf, Endereco endereco) throws UsuarioInexistenteException, EnderecoNuloException,
            ClienteNuloException;

    Endereco obterEnderecoPorCep(int cep);

    List<Endereco> listarEnderecos();
}
