package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.*;
import java.time.LocalDate;
import java.util.List;

import br.ufrpe.readeasy.exceptions.*;

public interface IRepositorioUsuario {
    void inserirUsuario(Usuario usuario);

    void removerUsuario(Usuario usuario);

    void atualizarCliente(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login, String senha,
                          Endereco endereco, String telefone);

    void atualizarFuncionario(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login, String senha,
                              Endereco endereco, String telefone, boolean ehAdm, Funcionario admResponsavel);

    void atualizarFornecedor(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login, String senha,
                             Endereco endereco, String telefone, TipoFornecedor tipoFornecedor);

    void adicionarEnderecoDeEntrega(Usuario usuario, Endereco endereco) throws EnderecoExistenteException;

    void removerEnderecoDeEntrega(Usuario usuario, Endereco endereco);

    void atualizarEnderecoDeEntrega(Usuario usuario, Endereco endereco, int cep, String novaRua,
                                    String novoBairo, String novaCidade, String novoEstado) throws EnderecoExistenteException;

    List<Endereco> listarEnderecosDeEntrega(Usuario usuario);

    List<Usuario> listarUsuarios();

    Usuario procurarUsuarioPorLogin(String login);

    List<Cliente> listarClientes();

    List<Funcionario> listarFuncionarios();

    List<Funcionario> listarAdms();

    List<Fornecedor> listarFornecedores();

    boolean existeUsuario(String cpf);

    Usuario procurarUsuario(String cpf);

    Cliente procurarCliente(String cpf);

    boolean checarLogin(String login, String senha);

    void salvarArquivo();
}
