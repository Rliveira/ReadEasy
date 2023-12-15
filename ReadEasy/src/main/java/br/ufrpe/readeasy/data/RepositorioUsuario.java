package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class RepositorioUsuario implements IRepositorioUsuario{

    private static IRepositorioUsuario instance;
    private ArrayList<Usuario> usuarios;

    public RepositorioUsuario() {
        this.usuarios = new ArrayList<>();
    }

    public static IRepositorioUsuario getInstance(){
        if (instance == null) {
            instance = new RepositorioUsuario();
        }
        return instance;
    }

    @Override
    public void inserirUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public void removerUsuario(Usuario usuario){
        usuarios.remove(usuario);
    }

    @Override
    public void atualizarCliente(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                                 String senha, Endereco endereco, String telefone){
        usuario.setSenha(login);
        usuario.setLogin(senha);
        ((Cliente) usuario).setNome(nome);
        ((Cliente) usuario).setCpf(cpf);
        ((Cliente) usuario).setDataNascimento(dataNascimento);
        ((Cliente) usuario).setEndereco(endereco);
        ((Cliente) usuario).setTelefone(telefone);
    }
    @Override
    public void atualizarFuncionario(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                                     String senha, Endereco endereco, String telefone, boolean ehAdm,
                                     Funcionario admResponsavel){
        usuario.setSenha(login);
        usuario.setLogin(senha);
        ((Funcionario) usuario).setNome(nome);
        ((Funcionario) usuario).setCpf(cpf);
        ((Funcionario) usuario).setDataNascimento(dataNascimento);
        ((Funcionario) usuario).setEndereco(endereco);
        ((Funcionario) usuario).setTelefone(telefone);
        ((Funcionario) usuario).setAdm(ehAdm);
        ((Funcionario) usuario).setAdmResponsavel(admResponsavel);
    }
    @Override
    public void atualizarFornecedor(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                                    String senha, Endereco endereco, String telefone, TipoFornecedor tipoFornecedor){
        usuario.setSenha(login);
        usuario.setLogin(senha);
        ((Fornecedor) usuario).setNome(nome);
        ((Fornecedor) usuario).setCpf(cpf);
        ((Fornecedor) usuario).setDataNascimento(dataNascimento);
        ((Fornecedor) usuario).setEndereco(endereco);
        ((Fornecedor) usuario).setTelefone(telefone);
        ((Fornecedor) usuario).setTipoFornecedor(tipoFornecedor);
    }

    @Override
    public ArrayList<Usuario> listarUsuarios(){
        return usuarios;
    }
    @Override
    public ArrayList<Cliente> listarClientes(){
        ArrayList<Cliente> listaDeClientes = new ArrayList<>();
        for(Usuario usuario : usuarios){
            if(usuario instanceof Cliente){
                listaDeClientes.add((Cliente) usuario);
            }
        }
        return listaDeClientes;
    }
    @Override
    public ArrayList<Funcionario> listarFuncionarios(){
        ArrayList<Funcionario> listaDeColaboradores = new ArrayList<>();
        for(Usuario usuario : usuarios){
            if(usuario instanceof Funcionario && !((Funcionario) usuario).isAdm()){
                listaDeColaboradores.add((Funcionario) usuario);
            }
        }
        return listaDeColaboradores;
    }
    @Override
    public ArrayList<Funcionario> listarAdms(){
        ArrayList<Funcionario> listaDeAdministradores = new ArrayList<>();
        for(Usuario usuario : usuarios){
            if(usuario instanceof Funcionario && ((Funcionario) usuario).isAdm()){
                listaDeAdministradores.add((Funcionario) usuario);
            }
        }
        return listaDeAdministradores;
    }
    @Override
    public ArrayList<Fornecedor> listarFornecedores(){
        ArrayList<Fornecedor> listaDeFornecedores = new ArrayList<>();
        for(Usuario usuario : usuarios){
            if(usuario instanceof Fornecedor){
                listaDeFornecedores.add((Fornecedor) usuario);
            }
        }
        return listaDeFornecedores;
    }
    @Override
    public boolean existeUsuario(String cpf){
        boolean existe = false;
        for(Usuario user : usuarios){
            if(user.getCpf().equals(cpf)){
                existe = true;
            }
        }
        return existe;
    }

    @Override
    public Usuario procurarUsuario(String cpf){
        Usuario usuarioAux = null;
        for(Usuario user : usuarios){
            if(user.getCpf().equals(cpf)){
                usuarioAux = user;
            }
        }
        return usuarioAux;
    }
}


