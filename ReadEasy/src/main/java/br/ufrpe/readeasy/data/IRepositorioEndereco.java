package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.Endereco;

import java.util.List;

public interface IRepositorioEndereco {

    public void adicionarEndereco(Endereco endereco);

    public void atualizarEndereco (Endereco endereco, int cep, String rua, String bairro, String cidade, String estado);

    public void removerEndereco(int cep);

    public Endereco obterEnderecoPorCep(int cep);

    public List<Endereco> listarEnderecos();

    public void adicionarEnderecoCliente(String cpf, Endereco endereco);

    public void removerEnderecoCliente(String cpf, Endereco endereco);

    public boolean existeEndereco(int cep);

    void salvarArquivo();
}