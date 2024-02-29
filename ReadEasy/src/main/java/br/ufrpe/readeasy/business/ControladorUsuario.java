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
    public void cadastrarAdmInicial(){
        if (!repUsuario.existeUsuario("12384274165")) {
            try {
                Funcionario admInicial = new Funcionario("Lucas Albuquerque", "12384274165",
                        LocalDate.of(2000, 1, 1), "admin", "admin1234",
                        new Endereco(59624712, "Rua Fictícia", "Bairro",
                                "Cidade", "Estado"), "81991969420", true, null);
                this.cadastrarUsuario(new Funcionario("Lucas Albuquerque", "12384274165",
                        LocalDate.of(2000, 1, 1), "admin", "admin1234",
                        new Endereco(59624712, "Rua Fictícia", "Bairro",
                                "Cidade", "Estado"), "81991969420", true, admInicial));
                repUsuario.salvarArquivo();
            } catch ( UsuarioExistenteException |
                     CampoVazioException | MenorDeIdadeException | DataInvalidaException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Override
    public void cadastrarUsuario(Usuario usuario) throws MenorDeIdadeException, DataInvalidaException,
            CampoVazioException, UsuarioExistenteException {
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
                                    repUsuario.salvarArquivo();
                                } else {
                                    throw new CampoVazioException();
                                }
                            } else if (usuario instanceof Fornecedor) {
                                repUsuario.inserirUsuario(usuario);
                                repUsuario.salvarArquivo();
                            } else if (usuario instanceof Cliente) {
                                repUsuario.inserirUsuario(usuario);
                                repUsuario.salvarArquivo();
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
        }
    }

    @Override
    public void removerUsuario(Usuario usuario){
        repUsuario.removerUsuario(usuario);
        repUsuario.salvarArquivo();
    }

    @Override
    public Boolean checarLogin(String login, String senha) throws LoginInvalidoException, CampoVazioException {
        Boolean loginCorreto = false;
        if (!login.isEmpty() && !senha.isEmpty()) {
            if (repUsuario.checarLogin(login, senha)) {
                loginCorreto = true;
                return loginCorreto;
            } else {
                throw new LoginInvalidoException();
            }
        } else {
            throw new CampoVazioException();
        }
    }

    @Override
    public void atualizarFuncionario(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login
            , String senha, Endereco endereco, String telefone, boolean ehAdm, Funcionario admResponsavel)
            throws UsuarioExistenteException, DataInvalidaException {

        if (nome.isEmpty() || nome.equals(usuario.getNome())) {
            nome = usuario.getNome();
        }
        usuario.setNome(nome);
        repUsuario.salvarArquivo();
        if (cpf.isEmpty() || cpf.equals(usuario.getCpf())) {
            cpf = usuario.getCpf();
        } else {
            if (!repUsuario.existeUsuario(cpf)){
                usuario.setCpf(cpf);
                repUsuario.salvarArquivo();
            } else {
                throw new UsuarioExistenteException(cpf);
            }

        }
        if (dataNascimento == null || dataNascimento.equals(usuario.getDataNascimento())) {
            dataNascimento = usuario.getDataNascimento();
        } else {
            if (!dataNascimento.isAfter(LocalDate.now())){
                usuario.setDataNascimento(dataNascimento);
                repUsuario.salvarArquivo();
            } else{
                throw new DataInvalidaException("A data Inválida, selecione uma data de nascimento" +
                        " anterior a data atual.");
            }

        }
        if (login.isEmpty() || login.equals(usuario.getLogin())) {
            login = usuario.getLogin();
        }
        usuario.setLogin(login);
        repUsuario.salvarArquivo();
        if (senha.isEmpty() || senha.equals(usuario.getSenha())) {
            senha = usuario.getSenha();
        }
        usuario.setSenha(senha);
        repUsuario.salvarArquivo();
        if (endereco == null || endereco.equals(usuario.getEndereco())) {
            endereco = usuario.getEndereco();
        }
        usuario.setEndereco(endereco);
        repUsuario.salvarArquivo();
        if (telefone.isEmpty() || telefone.equals(usuario.getTelefone())) {
            telefone = usuario.getTelefone();
        }
        usuario.setTelefone(telefone);
        repUsuario.salvarArquivo();
        ((Funcionario) usuario).setAdm(ehAdm);
        repUsuario.salvarArquivo();
        if (admResponsavel == null || admResponsavel.equals(((Funcionario) usuario).getAdmResponsavel())) {
            admResponsavel = ((Funcionario) usuario).getAdmResponsavel();
        }
        ((Funcionario) usuario).setAdmResponsavel(admResponsavel);
        repUsuario.salvarArquivo();
    }

    @Override
    public void atualizarCliente(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                                 String senha, Endereco endereco, String telefone) throws UsuarioExistenteException,
            DataInvalidaException {

        if (nome.isEmpty() || nome.equals(usuario.getNome())) {
            nome = usuario.getNome();
        }
        usuario.setNome(nome);
        repUsuario.salvarArquivo();
        if (cpf.isEmpty() || cpf.equals(usuario.getCpf())) {
            cpf = usuario.getCpf();
        } else {
            if (!repUsuario.existeUsuario(cpf)){
                usuario.setCpf(cpf);
                repUsuario.salvarArquivo();
            } else {
                throw new UsuarioExistenteException(cpf);
            }

        }
        if (dataNascimento == null || dataNascimento.equals(usuario.getDataNascimento())) {
            dataNascimento = usuario.getDataNascimento();
        } else {
            if (!dataNascimento.isAfter(LocalDate.now())){
                usuario.setDataNascimento(dataNascimento);
                repUsuario.salvarArquivo();
            } else{
                throw new DataInvalidaException("A data Inválida, selecione uma data de nascimento" +
                        " anterior a data atual.");
            }
        }
        if (login.isEmpty() || login.equals(usuario.getLogin())) {
            login = usuario.getLogin();
        }
        usuario.setLogin(login);
        repUsuario.salvarArquivo();
        if (senha.isEmpty() || senha.equals(usuario.getSenha())) {
            senha = usuario.getSenha();
        }
        usuario.setSenha(senha);
        repUsuario.salvarArquivo();
        if (endereco == null || endereco.equals(usuario.getEndereco())) {
            endereco = usuario.getEndereco();
        }
        usuario.setEndereco(endereco);
        repUsuario.salvarArquivo();

        if (telefone.isEmpty() || telefone.equals(usuario.getTelefone())) {
            telefone = usuario.getTelefone();
        }
        usuario.setTelefone(telefone);
        repUsuario.salvarArquivo();
    }

    @Override
    public void atualizarFornecedor(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login
            , String senha, Endereco endereco, String telefone, TipoFornecedor tipoFornecedor)
            throws DataInvalidaException, UsuarioExistenteException{

        if (nome.isEmpty() || nome.equals(usuario.getNome())) {
            nome = usuario.getNome();
        }
        usuario.setNome(nome);
        repUsuario.salvarArquivo();

        if (cpf.isEmpty() || cpf.equals(usuario.getCpf())) {
            cpf = usuario.getCpf();
        } else {
            if (!repUsuario.existeUsuario(cpf) || (cpf.equals(usuario.getCpf()))){
                usuario.setCpf(cpf);
                repUsuario.salvarArquivo();
            } else {
                throw new UsuarioExistenteException(cpf);
            }

        }
        if (dataNascimento == null || dataNascimento.equals(usuario.getDataNascimento())) {
            dataNascimento = usuario.getDataNascimento();
        } else {
            if (!dataNascimento.isAfter(LocalDate.now())){
                usuario.setDataNascimento(dataNascimento);
                repUsuario.salvarArquivo();
            } else{
                throw new DataInvalidaException("A data Inválida, selecione uma data de nascimento" +
                        " anterior a data atual.");
            }

        }
        if (login.isEmpty() || login.equals(usuario.getLogin())) {
            login = usuario.getLogin();
        }
        usuario.setLogin(login);
        repUsuario.salvarArquivo();

        if (senha.isEmpty() || senha.equals(usuario.getSenha())) {
            senha = usuario.getSenha();
        }
        usuario.setSenha(senha);
        repUsuario.salvarArquivo();
        if (endereco == null || endereco.equals(usuario.getEndereco())) {
            endereco = usuario.getEndereco();
        }
        usuario.setEndereco(endereco);
        repUsuario.salvarArquivo();

        if (telefone.isEmpty() || telefone.equals(usuario.getTelefone())) {
            telefone = usuario.getTelefone();
        }
        usuario.setTelefone(telefone);
        repUsuario.salvarArquivo();

        if (tipoFornecedor == null || tipoFornecedor.equals(((Fornecedor) usuario).getTipoFornecedor())){
            tipoFornecedor = ((Fornecedor) usuario).getTipoFornecedor();
        }
        ((Fornecedor) usuario).setTipoFornecedor(tipoFornecedor);
        repUsuario.salvarArquivo();

    }

    @Override
    public void adicionarEnderecoDeEntrega(Usuario usuario, Endereco endereco) throws EnderecoExistenteException {
        if (((Cliente) usuario).getEnderecosentrega().contains(endereco)) {
            throw new EnderecoExistenteException(endereco.getCep());
        } else {
            ((Cliente) usuario).adicionarEndereco(endereco);
            repUsuario.salvarArquivo();
        }
    }

    @Override
    public void removerEnderecoDeEntrega(Usuario usuario, Endereco endereco) {
        if (endereco != null) {
            if (((Cliente) usuario).getEnderecosentrega().contains(endereco)) {
                ((Cliente) usuario).removerEndereco(endereco);
                repUsuario.salvarArquivo();
            }
        }
    }

    @Override
    public void atualizarEnderecoDeEntrega(Usuario usuario, Endereco endereco, int cep, String novaRua
            , String novoBairo, String novaCidade, String novoEstado) throws CampoVazioException, EnderecoExistenteException {

        if(!novaRua.isEmpty() && !novoBairo.isEmpty() && !novaCidade.isEmpty() && novoEstado != null){
            repUsuario.atualizarEnderecoDeEntrega(usuario, endereco, cep, novaRua, novoBairo, novaCidade, novoEstado);
        }
        else{
            throw new CampoVazioException();
        }
    }

    @Override
    public List<Endereco> listarEnderecosDeEntrega(Usuario usuario) {
        return repUsuario.listarEnderecosDeEntrega(usuario);
    }

    @Override
    public Usuario procurarUsuario(String cpf) {
        return repUsuario.procurarUsuario(cpf);
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
