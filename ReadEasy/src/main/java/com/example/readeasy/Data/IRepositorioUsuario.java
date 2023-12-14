package com.example.readeasy.Data;
import com.example.readeasy.Business.beans.*;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IRepositorioUsuario {
    void inserirUsuario(Usuario usuario);
    void removerUsuario(Usuario usuario);
    void atualizarCliente(Usuario usuario,String nome, String cpf, LocalDate dataNascimento, String login, String senha, Endereco endereco, String telefone);
    void adicionarEnderecoDeEntrega(Usuario usuario, String endereco);
    void removerEnderecoDeEntrega(Usuario usuario, String endereco);
    void atualizarFuncionario(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login, String senha, Endereco endereco, String telefone, boolean ehAdm, Funcionario admResponsavel);
    void atualizarFornecedor(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login, String senha, Endereco endereco, String telefone, TipoFornecedor tipoFornecedor);
    Usuario procurarUsuario(String cpf);
    ArrayList<Usuario> listarUsuarios();
    ArrayList<Cliente> listarClientes();
    ArrayList<Funcionario> listarFuncionarios();
    ArrayList<Funcionario> listarAdms();
    ArrayList<Fornecedor> listarFornecedores();
    boolean existeUsuario(String cpf);
}
