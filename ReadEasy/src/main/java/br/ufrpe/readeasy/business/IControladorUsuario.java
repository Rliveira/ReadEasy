package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.exceptions.*;

import java.time.LocalDate;
import java.util.List;

public interface IControladorUsuario {
    void cadastrarAdmInicial();

    void cadastrarUsuario(Usuario usuario) throws MenorDeIdadeException,
            DataInvalidaException, CampoVazioException, UsuarioExistenteException;
    void removerUsuario(Usuario usuario);

    Boolean checarLogin(String login, String senha) throws LoginInvalidoException, CampoVazioException;

    void atualizarFuncionario(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                              String senha, Endereco endereco, String telefone, boolean ehAdm,
                              Funcionario admResponsavel) throws UsuarioExistenteException, DataInvalidaException;
            ;

    void atualizarCliente(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                          String senha, Endereco endereco, String telefone) throws UsuarioExistenteException,
            DataInvalidaException;

    void atualizarFornecedor(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                                    String senha, Endereco endereco, String telefone, TipoFornecedor tipoFornecedor)
            throws DataInvalidaException, UsuarioExistenteException;

    void adicionarEnderecoDeEntrega(Usuario usuario, Endereco endereco) throws EnderecoExistenteException;

    void removerEnderecoDeEntrega(Usuario usuario, Endereco endereco);

    void atualizarEnderecoDeEntrega( Usuario usuario, Endereco endereco, int cep, String novaRua, String novoBairo,
                                     String novaCidade, String novoEstado) throws CampoVazioException, EnderecoExistenteException;

    List<Endereco> listarEnderecosDeEntrega(Usuario usuario);

    Usuario procurarUsuario(String cpf);

    List<Usuario> listarUsuarios();

    List<Cliente> listarClientes();

    List<Funcionario> listarFuncionarios();

    List<Funcionario> listarAdms();

    List<Fornecedor> listarFornecedores();

    boolean existeUsuario(String cpf);
}
