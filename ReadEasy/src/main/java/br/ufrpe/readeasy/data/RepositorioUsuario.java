package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.exceptions.TipoUsuarioInvalidoException;
import br.ufrpe.readeasy.exceptions.UsuarioInexistenteException;
import br.ufrpe.readeasy.exceptions.UsuarioNuloException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositorioUsuario implements IRepositorioUsuario, Serializable{

    private static IRepositorioUsuario instance;
    private ArrayList<Usuario> usuarios;

    public RepositorioUsuario() {
        this.usuarios = new ArrayList<>();
    }

    public static IRepositorioUsuario getInstance(){
        if (instance == null) {
            instance = lerDoArquivo();
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

    public void adicionarEnderecoDeEntrega(Usuario usuario, Endereco endereco){
        ((Cliente) usuario).adicionarEndereco(endereco);
    }


    public void removerEnderecoDeEntrega(Usuario usuario, Endereco endereco){
        ((Cliente) usuario).removerEndereco(endereco);
    }

    public List<Endereco> listarEnderecosDeEntrega(Usuario usuario) throws TipoUsuarioInvalidoException, UsuarioInexistenteException, UsuarioNuloException {
        if (usuario != null) {
            if (this.existeUsuario(usuario.getCpf())) {
                if (usuario instanceof Cliente) {
                    List<Endereco> enderecos = ((Cliente) usuario).getEnderecosentrega();
                    return Collections.unmodifiableList(enderecos);
                } else {
                    throw new TipoUsuarioInvalidoException();
                }
            } else {
                throw new UsuarioInexistenteException(usuario.getCpf());
            }
        } else {
            throw new UsuarioNuloException();
        }
    }

    @Override
    public List<Usuario> listarUsuarios(){
        return Collections.unmodifiableList(usuarios);
    }
    @Override
    public List<Cliente> listarClientes(){
        ArrayList<Cliente> listaDeClientes = new ArrayList<>();
        for(Usuario usuario : usuarios){
            if(usuario instanceof Cliente){
                listaDeClientes.add((Cliente) usuario);
            }
        }
        return Collections.unmodifiableList(listaDeClientes);
    }
    @Override
    public List<Funcionario> listarFuncionarios(){
        ArrayList<Funcionario> listaDeColaboradores = new ArrayList<>();
        for(Usuario usuario : usuarios){
            if(usuario instanceof Funcionario && !((Funcionario) usuario).isAdm()){
                listaDeColaboradores.add((Funcionario) usuario);
            }
        }
        return Collections.unmodifiableList(listaDeColaboradores);
    }
    @Override
    public List<Funcionario> listarAdms(){
        ArrayList<Funcionario> listaDeAdministradores = new ArrayList<>();
        for(Usuario usuario : usuarios){
            if(usuario instanceof Funcionario && ((Funcionario) usuario).isAdm()){
                listaDeAdministradores.add((Funcionario) usuario);
            }
        }
        return Collections.unmodifiableList(listaDeAdministradores);
    }
    @Override
    public List<Fornecedor> listarFornecedores(){
        ArrayList<Fornecedor> listaDeFornecedores = new ArrayList<>();
        for(Usuario usuario : usuarios){
            if(usuario instanceof Fornecedor){
                listaDeFornecedores.add((Fornecedor) usuario);
            }
        }
        return Collections.unmodifiableList(listaDeFornecedores);
    }
    @Override
    public boolean existeUsuario(String cpf){
        boolean existe = false;
        for(Usuario user : usuarios){
            if(user.getCpf().equals(cpf)){
                existe = true;
                break;
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
                break;
            }
        }
        return usuarioAux;
    }

    @Override
    public Usuario procurarUsuarioPorLogin(String login){
        Usuario usuarioAux = null;
        for(Usuario user : usuarios){
            if(user.getLogin().equals(login)){
                usuarioAux = user;
                break;
            }
        }
        return usuarioAux;
    }

    @Override
    public Cliente procurarCliente(String cpf) {
        Cliente clienteAux = null;
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Cliente && usuario.getCpf().equals(cpf)) {
                return (Cliente) usuario;
            }
        }
        return clienteAux;
    }

    @Override
    public boolean checarLogin(String login, String senha) {
        Usuario usuario = this.procurarUsuarioPorLogin(login);
        if (usuario != null) {
            if (usuario.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }

    private static RepositorioUsuario lerDoArquivo() {
        RepositorioUsuario instanciaLocal = null;

        File in = new File("RepoUsuarios.dat");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(in);
            ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            instanciaLocal = (RepositorioUsuario) o;
        } catch (Exception e) {
            instanciaLocal = new RepositorioUsuario();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {/* Silent exception */
                }
            }
        }

        return instanciaLocal;
    }

    @Override
    public void salvarArquivo() {
        if (instance == null) {
            return;
        }
        File out = new File("RepoUsuarios.dat");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(out);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(instance);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    /* Silent */}
            }
        }
    }
}


