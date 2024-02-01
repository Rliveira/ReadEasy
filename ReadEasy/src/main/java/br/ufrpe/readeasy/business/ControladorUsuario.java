package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.data.IRepositorioUsuario;
import br.ufrpe.readeasy.data.RepositorioUsuario;
import br.ufrpe.readeasy.exceptions.*;

import java.time.LocalDate;
import java.util.List;


public class ControladorUsuario implements IControladorUsuario{
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
    @Override
    public void cadastrarUsuario(Usuario usuario) throws TipoUsuarioInvalidoException, MenorDeIdadeException,
            DataInvalidaException, CampoVazioException, UsuarioExistenteException, UsuarioNuloException {
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
                        throw new DataInvalidaException("A data Inválida, selecione uma data de nascimento " +
                                "anterior a data atual.");
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

    @Override
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

    @Override
    public void checarLogin(String login, String senha) throws LoginInvalidoException, CampoVazioException {
        if (!login.isEmpty() && !senha.isEmpty()) {
            if (repUsuario.checarLogin(login, senha)) {
                System.out.println("Login realizado com sucesso!");
            } else {
                throw new LoginInvalidoException();
            }
        } else {
            throw new CampoVazioException();
        }
    }

    @Override
    public void atualizarFuncionario(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                                     String senha, Endereco endereco, String telefone, boolean ehAdm,
                                     Funcionario admResponsavel) throws TipoUsuarioInvalidoException,
            UsuarioExistenteException, DataInvalidaException, UsuarioInexistenteException, UsuarioNuloException {
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
                            throw new DataInvalidaException("A data Inválida, selecione uma data de nascimento" +
                                    " anterior a data atual.");
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
                    if (ehAdm == ((Funcionario) usuario).isAdm()) {
                        ehAdm = ((Funcionario) usuario).isAdm();
                    }
                    ((Funcionario) usuario).setAdm(ehAdm);
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

    @Override
    public void atualizarCliente(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                                 String senha, Endereco endereco, String telefone) throws UsuarioExistenteException,
            DataInvalidaException, TipoUsuarioInvalidoException, UsuarioInexistenteException, UsuarioNuloException {
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
                            throw new DataInvalidaException("A data Inválida, selecione uma data de nascimento" +
                                    " anterior a data atual.");
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

    @Override
    public void atualizarFornecedor(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                                    String senha, Endereco endereco, String telefone, TipoFornecedor tipoFornecedor)
            throws DataInvalidaException, UsuarioExistenteException, TipoUsuarioInvalidoException,
            UsuarioInexistenteException, UsuarioNuloException {
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
                            throw new DataInvalidaException("A data Inválida, selecione uma data de nascimento" +
                                    " anterior a data atual.");
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

    @Override
    public void adicionarEnderecoDeEntrega(Usuario usuario, Endereco endereco) throws CampoVazioException,
            TipoUsuarioInvalidoException, UsuarioInexistenteException, UsuarioNuloException, EnderecoExistenteException {
        if (usuario != null) {
            if (repUsuario.existeUsuario(usuario.getCpf())) {
                if (usuario instanceof Cliente) {
                    if (endereco != null) {
                        if (((Cliente) usuario).getEnderecosentrega().contains(endereco)) {
                            throw new EnderecoExistenteException(endereco.getCep());
                        } else {
                            ((Cliente) usuario).adicionarEndereco(endereco);
                        }
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

    @Override
    public void removerEnderecoDeEntrega(Usuario usuario, Endereco endereco) throws CampoVazioException,
            TipoUsuarioInvalidoException, UsuarioInexistenteException, UsuarioNuloException, EnderecoInexistenteException {
        if (usuario != null) {
            if (repUsuario.existeUsuario(usuario.getCpf())) {
                if (usuario instanceof Cliente) {
                    if (endereco != null) {
                        if (((Cliente) usuario).getEnderecosentrega().contains(endereco)) {
                            ((Cliente) usuario).removerEndereco(endereco);
                        } else {
                            throw new EnderecoInexistenteException(endereco.getCep());
                        }
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

    @Override
    public List<Endereco> listarEnderecosDeEntrega(Usuario usuario) throws TipoUsuarioInvalidoException, UsuarioInexistenteException,
            UsuarioNuloException {
        return repUsuario.listarEnderecosDeEntrega(usuario);
    }

    @Override
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

    @Override
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

    @Override
    public List<Usuario> listarUsuarios() {
        return repUsuario.listarUsuarios();
    }

    @Override
    public List<Cliente> listarClientes() {
        return repUsuario.listarClientes();
    }

    @Override
    public List<Funcionario> listarFuncionarios() {
        return repUsuario.listarFuncionarios();
    }

    @Override
    public List<Funcionario> listarAdms() {
        return repUsuario.listarAdms();
    }

    @Override
    public List<Fornecedor> listarFornecedores() {
        return repUsuario.listarFornecedores();
    }

    @Override
    public boolean existeUsuario(String cpf) {
        return repUsuario.existeUsuario(cpf);
    }

}
