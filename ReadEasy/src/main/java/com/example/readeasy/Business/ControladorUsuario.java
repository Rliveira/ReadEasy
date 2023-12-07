package com.example.readeasy.Business;

import com.example.readeasy.Business.beans.*;
import com.example.readeasy.Data.IRepositorioUsuario;
import java.time.LocalDate;
import java.util.ArrayList;

// TODO import exceptions.*;
// TODO fazer exceptions

public class ControladorUsuario {
    private static ControladorUsuario instance;
    private IRepositorioUsuario repUsuario;
    private ControladorUsuario() {
    }
    public static ControladorUsuario getInstance() {
        if (instance == null) {
            instance = new ControladorUsuario();
        }
        return instance;
    }
    public void cadastrarUsuario(Usuario usuario) {
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
                                    // TODO throw new AdmResponsavelNuloException();
                                }
                            } else if (usuario instanceof Fornecedor) {
                                repUsuario.inserirUsuario(usuario);
                            } else if (usuario instanceof Cliente) {
                                repUsuario.inserirUsuario(usuario);
                            } else {
                                //TODO throw new TipoUsuarioInvalidoException(
                            }
                        } else {
                            // TODO throw new MenorDeIdadeException();
                        }
                    } else {
                        //TODO throw new DataInvalidaException();
                    }
                } else {
                    //TODO throw new CampoVazioException();
                }
            } else {
                //TODO throw new UsuarioExistenteException();
            }
        } else {
            //TODO throw new UsuarioNuloException();
        }
    }

    public void removerUsuario(Usuario usuario) {
        if (usuario != null) {
            if (repUsuario.existeUsuario(usuario.getCpf())) {
                repUsuario.removerUsuario(usuario);
            } else {
                //TODO throw new UsuarioInexistenteException();
            }
        } else {
            //TODO throw new UsuarioNuloException();
        }
    }

    public void atualizarFuncionario(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login, String senha, Endereco endereco, String telefone, boolean ehAdm, Funcionario admResponsavel) {
        if (usuario != null){
            if (repUsuario.existeUsuario(cpf)){
                if (usuario instanceof Funcionario) {
                    if (nome.isEmpty() || nome.equals(usuario.getNome())) {
                        nome = usuario.getNome();
                    } else {
                        usuario.setNome(nome);
                    }
                    if (cpf.isEmpty() || cpf.equals(usuario.getCpf())) {
                        cpf = usuario.getCpf();
                    } else {
                        usuario.setCpf(cpf);
                    }
                    if (dataNascimento == null || dataNascimento.equals(usuario.getDataNascimento())) {
                        dataNascimento = usuario.getDataNascimento();
                    } else {
                        usuario.setDataNascimento(dataNascimento);
                    }
                    if (login.isEmpty() || login.equals(usuario.getLogin())) {
                        login = usuario.getLogin();
                    } else {
                        usuario.setLogin(login);
                    }
                    if (senha.isEmpty() || senha.equals(usuario.getSenha())) {
                        senha = usuario.getSenha();
                    } else {
                        usuario.setSenha(senha);
                    }
                    if (endereco == null || endereco.equals(usuario.getEndereco())) {
                        endereco = usuario.getEndereco();
                    } else {
                        usuario.setEndereco(endereco);
                    }
                    if (telefone.isEmpty() || telefone.equals(usuario.getTelefone())) {
                        telefone = usuario.getTelefone();
                    } else {
                        usuario.setTelefone(telefone);
                    }
                    if (ehAdm == ((Funcionario) usuario).EhAdm()) {
                        ehAdm = ((Funcionario) usuario).EhAdm();
                    } else {
                        ((Funcionario) usuario).setEhAdm(ehAdm);
                    }
                    if (admResponsavel == null || admResponsavel.equals(((Funcionario) usuario).getAdmResponsavel())) {
                        admResponsavel = ((Funcionario) usuario).getAdmResponsavel();
                    } else {
                        ((Funcionario) usuario).setAdmResponsavel(admResponsavel);
                    }
                } else {
                    //TODO throw new TipoUsuarioInvalidoException();
                }
            } else {
                //TODO throw new UsuarioInexistenteException();
            }
        } else {
            //TODO throw new UsuarioNuloException();
        }
    }

    public void atualizarCliente(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login, String senha, Endereco endereco, String telefone) {
        if (usuario != null){
            if (repUsuario.existeUsuario(cpf)){
                if (usuario instanceof Cliente) {
                    if (nome.isEmpty() || nome.equals(usuario.getNome())) {
                        nome = usuario.getNome();
                    } else {
                        usuario.setNome(nome);
                    }
                    if (cpf.isEmpty() || cpf.equals(usuario.getCpf())) {
                        cpf = usuario.getCpf();
                    } else {
                        usuario.setCpf(cpf);
                    }
                    if (dataNascimento == null || dataNascimento.equals(usuario.getDataNascimento())) {
                        dataNascimento = usuario.getDataNascimento();
                    } else {
                        usuario.setDataNascimento(dataNascimento);
                    }
                    if (login.isEmpty() || login.equals(usuario.getLogin())) {
                        login = usuario.getLogin();
                    } else {
                        usuario.setLogin(login);
                    }
                    if (senha.isEmpty() || senha.equals(usuario.getSenha())) {
                        senha = usuario.getSenha();
                    } else {
                        usuario.setSenha(senha);
                    }
                    if (endereco == null || endereco.equals(usuario.getEndereco())) {
                        endereco = usuario.getEndereco();
                    } else {
                        usuario.setEndereco(endereco);
                    }
                    if (telefone.isEmpty() || telefone.equals(usuario.getTelefone())) {
                        telefone = usuario.getTelefone();
                    } else {
                        usuario.setTelefone(telefone);
                    }
                } else {
                    //TODO throw new TipoUsuarioInvalidoException();
                }
            } else {
                //TODO throw new UsuarioInexistenteException();
            }
        } else {
            //TODO throw new UsuarioNuloException();
        }
    }

    public void atualizarFornecedor(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login, String senha, Endereco endereco, String telefone, TipoFornecedor tipoFornecedor) {
        if (usuario != null){
            if (repUsuario.existeUsuario(cpf)){
                if (usuario instanceof Fornecedor) {
                    if (nome.isEmpty() || nome.equals(usuario.getNome())) {
                        nome = usuario.getNome();
                    } else {
                        usuario.setNome(nome);
                    }
                    if (cpf.isEmpty() || cpf.equals(usuario.getCpf())) {
                        cpf = usuario.getCpf();
                    } else {
                        usuario.setCpf(cpf);
                    }
                    if (dataNascimento == null || dataNascimento.equals(usuario.getDataNascimento())) {
                        dataNascimento = usuario.getDataNascimento();
                    } else {
                        usuario.setDataNascimento(dataNascimento);
                    }
                    if (login.isEmpty() || login.equals(usuario.getLogin())) {
                        login = usuario.getLogin();
                    } else {
                        usuario.setLogin(login);
                    }
                    if (senha.isEmpty() || senha.equals(usuario.getSenha())) {
                        senha = usuario.getSenha();
                    } else {
                        usuario.setSenha(senha);
                    }
                    if (endereco == null || endereco.equals(usuario.getEndereco())) {
                        endereco = usuario.getEndereco();
                    } else {
                        usuario.setEndereco(endereco);
                    }
                    if (telefone.isEmpty() || telefone.equals(usuario.getTelefone())) {
                        telefone = usuario.getTelefone();
                    } else {
                        usuario.setTelefone(telefone);
                    }
                    if (tipoFornecedor == null || tipoFornecedor.equals(((Fornecedor) usuario).getTipoFornecedor())){
                        tipoFornecedor = ((Fornecedor) usuario).getTipoFornecedor();
                    } else {
                        ((Fornecedor) usuario).setTipoFornecedor(tipoFornecedor);
                    }
                } else {
                    //TODO throw new TipoUsuarioInvalidoException();
                }
            } else {
                //TODO throw new UsuarioInexistenteException();
            }
        } else {
            //TODO throw new UsuarioNuloException();
        }
    }

    public void adicionarEnderecoDeEntrega(Usuario usuario, Endereco endereco) {
        if (usuario != null) {
            if (repUsuario.existeUsuario(usuario.getCpf())) {
                if (usuario instanceof Cliente) {
                    if (endereco != null) {
                        ((Cliente) usuario).adicionarEndereco(endereco);
                    } else {
                        //TODO throw new CampoVazioException();
                    }
                } else {
                    //TODO throw new TipoUsuarioInvalidoException();
                }
            } else {
                //TODO throw new UsuarioInexistenteException();
            }
        } else {
            //TODO throw new UsuarioNuloException();
        }
    }

    public void removerEnderecoDeEntrega(Usuario usuario, Endereco endereco) {
        if (usuario != null) {
            if (repUsuario.existeUsuario(usuario.getCpf())) {
                if (usuario instanceof Cliente) {
                    if (endereco != null) {
                        ((Cliente) usuario).removerEndereco(endereco);
                    } else {
                        //TODO throw new CampoVazioException();
                    }
                } else {
                    //TODO throw new TipoUsuarioInvalidoException();
                }
            } else {
                //TODO throw new UsuarioInexistenteException();
            }
        } else {
            //TODO throw new UsuarioNuloException();
        }
    }

    public Usuario procurarUsuario(String cpf) {
        if (!cpf.isEmpty()) {
            if (repUsuario.existeUsuario(cpf)) {
                return repUsuario.procurarUsuario(cpf);
            } else {
                //TODO throw new UsuarioInexistenteException();
            }
        } else {
            //TODO throw new CampoVazioException();
        }
        return null;
    }

    public void removerUsuario(String cpf) {
        if (!cpf.isEmpty()) {
            if (repUsuario.existeUsuario(cpf)) {
                repUsuario.removerUsuario(repUsuario.procurarUsuario(cpf));
            } else {
                //TODO throw new UsuarioInexistenteException();
            }
        } else {
            //TODO throw new CampoVazioException();
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
