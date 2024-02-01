package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public void adicionarEnderecoDeEntrega(Usuario usuario, Endereco endereco);

    public void removerEnderecoDeEntrega(Usuario usuario, Endereco endereco);

    public List<Endereco> listarEnderecosDeEntrega(Usuario usuario) throws TipoUsuarioInvalidoException, UsuarioInexistenteException,
            UsuarioNuloException;

    List<Usuario> listarUsuarios();

    Usuario procurarUsuarioPorLogin(String login);

    List<Cliente> listarClientes();

    List<Funcionario> listarFuncionarios();

    List<Funcionario> listarAdms();

    List<Fornecedor> listarFornecedores();

    boolean existeUsuario(String cpf);
    public Usuario procurarUsuario(String cpf);

    public Cliente procurarCliente(String cpf);

    boolean checarLogin(String login, String senha);

}
