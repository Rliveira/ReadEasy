package com.example.readeasy.Business;

import com.example.readeasy.Business.beans.*;
import com.example.readeasy.Data.IRepositorioUsuario;
import java.time.LocalDate;
import java.util.ArrayList;

import com.example.readeasy.Exceptions.*;

public class ControladorUsuario {
    private static ControladorUsuario instance;
    private IRepositorioUsuario repUsuario;
    private ControladorUsuario() {
        this.repUsuario = RepositorioUsuario.getInstance();
    }
    public static ControladorUsuario getInstance() {
        if (instance == null) {
            instance = new ControladorUsuario();
        }
        return instance;
    }
    public void cadastrarUsuario(Usuario usuario) throws TipoUsuarioInvalidoException, MenorDeIdadeException, DataInvalidaException, CampoVazioException, UsuarioExistenteException, UsuarioNuloException {
        if (usuario != null) {
            if (!repUsuario.existeUsuario(usuario.getCpf())) {
                if (!usuario.getNome().isEmpty() && !usuario.getCpf().isEmpty() && usuario.getDataNascimento() != null
                        && !usuario.getLogin().isEmpty() && !usuario.getSenha().isEmpty() && usuario.getEndereco() != null
                        && !usuario.getTelefone().isEmpty()) {
                    if (usuario.getDataNascimento().isBefore(LocalDate.now())) {
                        if (usuario.getIdade() >= 18) {
                            if (usuario instanceof Funcionario) {
                                if (((Funcionario) usuario).getAdmResponsavel() != null) {
                                    repUsuario.inserirUsuario(usuario);
                                } else {
                                    throw new CampoVazioException();
                                }
                            } else if (usuario instanceof Fornecedor) {
                                repUsuario.inserirUsuario(usuario);
                            } else if (usuario instanceof Cliente) {
                                repUsuario.inserirUsuario(usuario);
                            } else {
                                throw new TipoUsuarioInvalidoException();
                            }
                        } else {
                            throw new MenorDeIdadeException(usuario.getIdade());
                        }
                    } else {
                        throw new DataInvalidaException(usuario.getDataNascimento());
                    }
                } else {
                    throw new CampoVazioException();
                }
            } else {
                throw new UsuarioExistenteException(usuario.getCpf());
            }
        } else {
            throw new UsuarioNuloException();
        }
    }

    public void removerUsuario(Usuario usuario) throws UsuarioInexistenteException, UsuarioNuloException {
        if (usuario != null) {
            if (repUsuario.existeUsuario(usuario.getCpf())) {
                repUsuario.removerUsuario(usuario);
            } else {
                throw new UsuarioInexistenteException(usuario.getCpf());
            }
        } else {
            throw new UsuarioNuloException();
        }
    }

    public void atualizarFuncionario(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login, String senha, Endereco endereco, String telefone, boolean ehAdm, Funcionario admResponsavel) throws TipoUsuarioInvalidoException, UsuarioExistenteException, DataInvalidaException, UsuarioInexistenteException, UsuarioNuloException {
        if (usuario != null){
            if (repUsuario.existeUsuario(usuario.getCpf())){
                if (usuario instanceof Funcionario) {
                    if (nome.isEmpty() || nome.equals(usuario.getNome())) {
                        nome = usuario.getNome();
                    }
                        usuario.setNome(nome);
                    if (cpf.isEmpty() || cpf.equals(usuario.getCpf())) {
                        cpf = usuario.getCpf();
                    } else {
                        if (!repUsuario.existeUsuario(cpf)){
                            usuario.setCpf(cpf);
                        } else {
                            throw new UsuarioExistenteException(cpf);
                        }

                    }
                    if (dataNascimento == null || dataNascimento.equals(usuario.getDataNascimento())) {
                        dataNascimento = usuario.getDataNascimento();
                    } else {
                        if (!dataNascimento.isBefore(LocalDate.now())){
                            usuario.setDataNascimento(dataNascimento);
                        } else{
                            throw new DataInvalidaException(dataNascimento);
                        }

                    }
                    if (login.isEmpty() || login.equals(usuario.getLogin())) {
                        login = usuario.getLogin();
                    }
                        usuario.setLogin(login);
                    if (senha.isEmpty() || senha.equals(usuario.getSenha())) {
                        senha = usuario.getSenha();
                    }
                        usuario.setSenha(senha);
                    if (endereco == null || endereco.equals(usuario.getEndereco())) {
                        endereco = usuario.getEndereco();
                    }
                        usuario.setEndereco(endereco);
                    if (telefone.isEmpty() || telefone.equals(usuario.getTelefone())) {
                        telefone = usuario.getTelefone();
                    }
                        usuario.setTelefone(telefone);
                    if (ehAdm == ((Funcionario) usuario).EhAdm()) {
                        ehAdm = ((Funcionario) usuario).EhAdm();
                    }
                        ((Funcionario) usuario).setEhAdm(ehAdm);
                    if (admResponsavel == null || admResponsavel.equals(((Funcionario) usuario).getAdmResponsavel())) {
                        admResponsavel = ((Funcionario) usuario).getAdmResponsavel();
                    }
                        ((Funcionario) usuario).setAdmResponsavel(admResponsavel);
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

    public void atualizarCliente(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login, String senha, Endereco endereco, String telefone) throws UsuarioExistenteException, DataInvalidaException, TipoUsuarioInvalidoException, UsuarioInexistenteException, UsuarioNuloException {
        if (usuario != null){
            if (repUsuario.existeUsuario(usuario.getCpf())){
                if (usuario instanceof Cliente) {
                    if (nome.isEmpty() || nome.equals(usuario.getNome())) {
                        nome = usuario.getNome();
                    }
                        usuario.setNome(nome);
                    if (cpf.isEmpty() || cpf.equals(usuario.getCpf())) {
                            cpf = usuario.getCpf();
                    } else {
                        if (!repUsuario.existeUsuario(cpf)){
                            usuario.setCpf(cpf);
                        } else {
                            throw new UsuarioExistenteException(cpf);
                        }

                    }
                    if (dataNascimento == null || dataNascimento.equals(usuario.getDataNascimento())) {
                        dataNascimento = usuario.getDataNascimento();
                    } else {
                        if (!dataNascimento.isBefore(LocalDate.now())){
                            usuario.setDataNascimento(dataNascimento);
                        } else{
                            throw new DataInvalidaException(dataNascimento);
                        }
                    }
                    if (login.isEmpty() || login.equals(usuario.getLogin())) {
                        login = usuario.getLogin();
                    }
                        usuario.setLogin(login);
                    if (senha.isEmpty() || senha.equals(usuario.getSenha())) {
                        senha = usuario.getSenha();
                    }
                        usuario.setSenha(senha);
                    if (endereco == null || endereco.equals(usuario.getEndereco())) {
                        endereco = usuario.getEndereco();
                    }
                        usuario.setEndereco(endereco);

                    if (telefone.isEmpty() || telefone.equals(usuario.getTelefone())) {
                        telefone = usuario.getTelefone();
                    }
                        usuario.setTelefone(telefone);

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

    public void atualizarFornecedor(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login, String senha, Endereco endereco, String telefone, TipoFornecedor tipoFornecedor) throws DataInvalidaException, UsuarioExistenteException, TipoUsuarioInvalidoException, UsuarioInexistenteException, UsuarioNuloException {
        if (usuario != null){
            if (repUsuario.existeUsuario(usuario.getCpf())){
                if (usuario instanceof Fornecedor) {
                    if (nome.isEmpty() || nome.equals(usuario.getNome())) {
                        nome = usuario.getNome();
                    }
                        usuario.setNome(nome);

                    if (cpf.isEmpty() || cpf.equals(usuario.getCpf())) {
                        cpf = usuario.getCpf();
                    } else {
                        if (!repUsuario.existeUsuario(cpf)){
                            usuario.setCpf(cpf);
                        } else {
                            throw new UsuarioExistenteException(cpf);
                        }

                    }
                    if (dataNascimento == null || dataNascimento.equals(usuario.getDataNascimento())) {
                        dataNascimento = usuario.getDataNascimento();
                    } else {
                        if (!dataNascimento.isBefore(LocalDate.now())){
                            usuario.setDataNascimento(dataNascimento);
                        } else{
                            throw new DataInvalidaException(dataNascimento);
                        }

                    }
                    if (login.isEmpty() || login.equals(usuario.getLogin())) {
                        login = usuario.getLogin();
                    }
                        usuario.setLogin(login);

                    if (senha.isEmpty() || senha.equals(usuario.getSenha())) {
                        senha = usuario.getSenha();
                    }
                        usuario.setSenha(senha);
                    if (endereco == null || endereco.equals(usuario.getEndereco())) {
                        endereco = usuario.getEndereco();
                    }
                        usuario.setEndereco(endereco);

                    if (telefone.isEmpty() || telefone.equals(usuario.getTelefone())) {
                        telefone = usuario.getTelefone();
                    }
                        usuario.setTelefone(telefone);

                    if (tipoFornecedor == null || tipoFornecedor.equals(((Fornecedor) usuario).getTipoFornecedor())){
                        tipoFornecedor = ((Fornecedor) usuario).getTipoFornecedor();
                    }
                        ((Fornecedor) usuario).setTipoFornecedor(tipoFornecedor);

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

    public void adicionarEnderecoDeEntrega(Usuario usuario, Endereco endereco) throws CampoVazioException, TipoUsuarioInvalidoException, UsuarioInexistenteException, UsuarioNuloException {
        if (usuario != null) {
            if (repUsuario.existeUsuario(usuario.getCpf())) {
                if (usuario instanceof Cliente) {
                    if (endereco != null) {
                        ((Cliente) usuario).adicionarEndereco(endereco);
                    } else {
                        throw new CampoVazioException();
                    }
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

    public void removerEnderecoDeEntrega(Usuario usuario, Endereco endereco) throws CampoVazioException, TipoUsuarioInvalidoException, UsuarioInexistenteException, UsuarioNuloException {
        if (usuario != null) {
            if (repUsuario.existeUsuario(usuario.getCpf())) {
                if (usuario instanceof Cliente) {
                    if (endereco != null) {
                        ((Cliente) usuario).removerEndereco(endereco);
                    } else {
                        throw new CampoVazioException();
                    }
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

    public Usuario procurarUsuario(String cpf) throws UsuarioInexistenteException, CampoVazioException {
        if (!cpf.isEmpty()) {
            if (repUsuario.existeUsuario(cpf)) {
                return repUsuario.procurarUsuario(cpf);
            } else {
                throw new UsuarioInexistenteException(cpf);
            }
        } else {
            throw new CampoVazioException();
        }
    }

    public void removerUsuario(String cpf) throws UsuarioInexistenteException, CampoVazioException {
        if (!cpf.isEmpty()) {
            if (repUsuario.existeUsuario(cpf)) {
                repUsuario.removerUsuario(repUsuario.procurarUsuario(cpf));
            } else {
                throw new UsuarioInexistenteException(cpf);
            }
        } else {
            throw new CampoVazioException();
        }
    }

    public ArrayList<Usuario> listarUsuarios() {
        return repUsuario.listarUsuarios();
    }

    public ArrayList<Cliente> listarClientes() {
        return repUsuario.listarClientes();
    }

    public ArrayList<Funcionario> listarFuncionarios() {
        return repUsuario.listarFuncionarios();
    }

    public ArrayList<Funcionario> listarAdms() {
        return repUsuario.listarAdms();
    }

    public ArrayList<Fornecedor> listarFornecedores() {
        return repUsuario.listarFornecedores();
    }

    public boolean existeUsuario(String cpf) {
        return repUsuario.existeUsuario(cpf);
    }

}