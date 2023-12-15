package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.exceptions.*;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IControladorUsuario {
    void cadastrarUsuario(Usuario usuario) throws TipoUsuarioInvalidoException, MenorDeIdadeException,
            DataInvalidaException, CampoVazioException, UsuarioExistenteException, UsuarioNuloException;
    void removerUsuario(Usuario usuario) throws UsuarioInexistenteException, UsuarioNuloException;

    void atualizarFuncionario(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                              String senha, Endereco endereco, String telefone, boolean ehAdm,
                              Funcionario admResponsavel) throws TipoUsuarioInvalidoException,
            UsuarioExistenteException, DataInvalidaException, UsuarioInexistenteException, UsuarioNuloException;

    void atualizarCliente(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                          String senha, Endereco endereco, String telefone) throws UsuarioExistenteException,
            DataInvalidaException, TipoUsuarioInvalidoException, UsuarioInexistenteException, UsuarioNuloException;

    void atualizarFornecedor(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                                    String senha, Endereco endereco, String telefone, TipoFornecedor tipoFornecedor)
            throws DataInvalidaException, UsuarioExistenteException, TipoUsuarioInvalidoException,
            UsuarioInexistenteException, UsuarioNuloException;

    void adicionarEnderecoDeEntrega(Usuario usuario, Endereco endereco) throws CampoVazioException,
            TipoUsuarioInvalidoException, UsuarioInexistenteException, UsuarioNuloException;

    void removerEnderecoDeEntrega(Usuario usuario, Endereco endereco) throws CampoVazioException,
            TipoUsuarioInvalidoException, UsuarioInexistenteException, UsuarioNuloException;


    Usuario procurarUsuario(String cpf) throws UsuarioInexistenteException, CampoVazioException;

    void removerUsuario(String cpf) throws UsuarioInexistenteException, CampoVazioException;

    ArrayList<Usuario> listarUsuarios();

    ArrayList<Cliente> listarClientes();

    ArrayList<Funcionario> listarFuncionarios();

    ArrayList<Funcionario> listarAdms();

    ArrayList<Fornecedor> listarFornecedores();

    boolean existeUsuario(String cpf);
}
