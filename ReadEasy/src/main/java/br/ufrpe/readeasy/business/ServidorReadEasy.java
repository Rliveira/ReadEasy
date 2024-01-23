package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.exceptions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServidorReadEasy {

    private static ServidorReadEasy instance;
    private IControladorUsuario controladorUsuario;
    private IControladorLivro controladorLivro;
    private IControladorVenda controladorVenda;
    private IControladorPromocao controladorPromocao;
    private IControladorEndereco controladorEndereco;

    private ServidorReadEasy() {
        this.controladorUsuario = ControladorUsuario.getInstance();
        this.controladorLivro = ControladorLivro.getInstance();
        this.controladorVenda = ControladorVenda.getInstance();
        this.controladorPromocao = ControladorPromocao.getInstance();
        this.controladorEndereco = ControladorEndereco.getInstance();
    }

    public static ServidorReadEasy getInstance() {
        if (instance == null) {
            instance = new ServidorReadEasy();
        }
        return instance;
    }

    public void cadastrarAdmInicial(){
        if (!controladorUsuario.existeUsuario("12384274165")) {
            try {
                controladorUsuario.cadastrarUsuario(new Funcionario("Lucas Albuquerque", "12384274165",
                        LocalDate.of(2000, 1, 1), "admin", "admin1234",
                        new Endereco(59624712, "Rua Fictícia", "Bairro",
                        "Cidade", "Estado"), "(81)99196-9420", true, null));
            } catch (UsuarioNuloException | UsuarioExistenteException | TipoUsuarioInvalidoException |
                    CampoVazioException | MenorDeIdadeException | DataInvalidaException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public void cadastrarUsuario(Usuario usuario) throws UsuarioNuloException, UsuarioExistenteException,
            TipoUsuarioInvalidoException, CampoVazioException, MenorDeIdadeException, DataInvalidaException {
        controladorUsuario.cadastrarUsuario(usuario);
    }

    public void removerUsuario(Usuario usuario) throws UsuarioNuloException, UsuarioInexistenteException {
        controladorUsuario.removerUsuario(usuario);
    }

    public void checarLogin(String login, String senha) throws LoginInvalidoException, CampoVazioException {
        controladorUsuario.checarLogin(login, senha);
    }

    public void atualizarFuncionario(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                                     String senha, Endereco endereco, String telefone, boolean ehAdm,
                                     Funcionario admResponsavel) throws TipoUsuarioInvalidoException,
            UsuarioExistenteException, DataInvalidaException, UsuarioInexistenteException, UsuarioNuloException {
        controladorUsuario.atualizarFuncionario(usuario, nome, cpf, dataNascimento, login, senha, endereco, telefone,
                ehAdm, admResponsavel);
    }

    public void atualizarCliente(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                                 String senha, Endereco endereco, String telefone) throws UsuarioExistenteException,
            DataInvalidaException, TipoUsuarioInvalidoException, UsuarioInexistenteException, UsuarioNuloException {
        controladorUsuario.atualizarCliente(usuario, nome, cpf, dataNascimento, login, senha, endereco, telefone);
    }

    public void atualizarFornecedor(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                                     String senha, Endereco endereco, String telefone, TipoFornecedor tipoFornecedor)
            throws DataInvalidaException, UsuarioExistenteException, TipoUsuarioInvalidoException,
            UsuarioInexistenteException, UsuarioNuloException {
        controladorUsuario.atualizarFornecedor(usuario, nome, cpf, dataNascimento, login, senha, endereco, telefone,
                tipoFornecedor);
    }

    public void adicionarEnderecoDeEntrega(Usuario usuario, Endereco endereco) throws CampoVazioException,
            TipoUsuarioInvalidoException, UsuarioInexistenteException, UsuarioNuloException, EnderecoExistenteException {
        controladorUsuario.adicionarEnderecoDeEntrega(usuario, endereco);
    }

    public void removerEnderecoDeEntrega(Usuario usuario, Endereco endereco) throws CampoVazioException,
            TipoUsuarioInvalidoException, UsuarioInexistenteException, UsuarioNuloException, EnderecoInexistenteException {
        controladorUsuario.removerEnderecoDeEntrega(usuario, endereco);
    }

    public void listarEnderecosDeEntrega(Usuario usuario) throws TipoUsuarioInvalidoException, UsuarioInexistenteException,
            UsuarioNuloException {
        controladorUsuario.listarEnderecosDeEntrega(usuario);
    }

    public Usuario procurarUsuario(String cpf) throws UsuarioInexistenteException, CampoVazioException {
        return controladorUsuario.procurarUsuario(cpf);
    }

    public void removerUsuario(String cpf) throws UsuarioInexistenteException, CampoVazioException {
        controladorUsuario.removerUsuario(cpf);
    }

    public void listarUsuarios() {
        controladorUsuario.listarUsuarios();
    }

    public void listarClientes() {
        controladorUsuario.listarClientes();
    }

    public void listarFuncionarios() {
        controladorUsuario.listarFuncionarios();
    }

    public void listarAdms() {
        controladorUsuario.listarAdms();
    }

    public void listarFornecedores() {
        controladorUsuario.listarFornecedores();
    }

    public boolean existeUsuario(String cpf) {
        return controladorUsuario.existeUsuario(cpf);
    }

    public void adicionarLivro(Livro livro) throws LivroNuloException, CampoVazioException,
            PrecoInvalidoException, LivroExistenteException {
        controladorLivro.adicionarLivro(livro);
    }

    public void removerLivro(Livro livro) throws LivroNuloException, CampoVazioException, LivroNaoExistenteException {
        controladorLivro.removerLivro(livro);
    }

    public void atualizarLivro(Livro livro, String titulo, String autor, double preco, Fornecedor fornecedor)
            throws LivroNaoExistenteException, CampoVazioException, LivroNuloException,
            PrecoInvalidoException, UsuarioNuloException, DataInvalidaException, MenorDeIdadeException {
        controladorLivro.atualizarLivro(livro, titulo, autor, preco, fornecedor);
    }

    public void adicionarGenero(Livro livro, Genero genero) throws GeneroExistenteException, CampoVazioException,
            LivroNuloException, LivroNaoExistenteException {
        controladorLivro.adicionarGenero(livro, genero);
    }

    public void removerGenero(Livro livro, Genero genero) throws GeneroNaoExistenteException, CampoVazioException,
            LivroNuloException, LivroNaoExistenteException {
        controladorLivro.removerGenero(livro, genero);
    }

    public void aumentarQuantidadeEmEstoque(Livro livro, int quantidade) throws LivroNaoExistenteException
            , LivroNuloException, QuantidadeInvalidaException {
        controladorLivro.aumentarQuantidadeEmEstoque(livro, quantidade);
    }

    public void diminuirQuantidadeEmEstoque(Livro livro, int quantidade) throws EstoqueInsuficienteException,
            QuantidadeInvalidaException, LivroNaoExistenteException, LivroNuloException {
        controladorLivro.diminuirQuantidadeEmEstoque(livro, quantidade);
    }

    public Livro buscarLivro(UUID id) throws LivroNaoExistenteException, CampoVazioException {
        return controladorLivro.buscarLivro(id);
    }

    public void listarTodosOslivrosEmOrdemAlfabetica() {
        controladorLivro.listarTodosOslivrosEmOrdemAlfabetica();
    }

    public void listarLivrosPorAutor(String nomeAutor) throws CampoVazioException {
        controladorLivro.listarLivrosPorAutor(nomeAutor);
    }

    public void listarLivrosPorGenero(Genero genero) throws GeneroNaoExistenteException {
        controladorLivro.listarLivrosPorGenero(genero);
    }

    public void listarLivrosPorFornecedor(Fornecedor fornecedor) {
        controladorLivro.listarLivrosPorFornecedor(fornecedor);
    }

    public void listarEOrdenarLivrosPorPreco() {
        controladorLivro.listarEOrdenarLivrosPorPreco();
    }

    public void listarQuantidadeDeEstoque() {
        controladorLivro.listarQuantidadeDeEstoque();
    }

    public void inserirVenda(Venda venda) throws VendaInvalidaException, UsuarioNuloException {
        controladorVenda.inserirVenda(venda);
    }

    public void removerVenda(Venda venda) throws VendaNaoExisteException {
        controladorVenda.removerVenda(venda);
    }

    public void atualizarVenda(Venda venda, Cliente cliente,  ArrayList<LivroVendido>
            livros) throws VendaInvalidaException, UsuarioNuloException, UsuarioInexistenteException,
            ListaDeLivrosVaziaException {
        controladorVenda.atualizarVenda(venda, cliente, livros);
    }

    public void historicoDeVendas() throws HistoricoVazioException {
        controladorVenda.historicoDeVendas();
    }

    public void HistoricoDeVendasPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        controladorVenda.HistoricoDeVendasPorPeriodo(dataInicio.atStartOfDay(),
                dataFim.atTime(23, 59, 59));
    }

    public void listarVendas() {
        controladorVenda.listarVendas();
    }

    public void historicoDeComprasDoCliente(Cliente cliente) throws UsuarioNuloException {
        controladorVenda.historicoDeComprasDoCliente(cliente);
    }

    public void listarMelhoresClientesPorCompra() throws HistoricoVazioException {
        controladorVenda.listarMelhoresClientesPorCompra();
    }

    public void listarMelhoresClientesPorGasto() throws HistoricoVazioException {
        controladorVenda.listarMelhoresClientesPorGasto();
    }

    public void inserirPromocao(Promocao promocao) throws PromocaoExistenteException,
            PromocaoNulaException, PromocaoAtualizadaException, PromocaoInseridaComSucessoException {
        controladorPromocao.inserirPromocao(promocao);
    }

    public void removerPromocao(Promocao promocao) throws PromocaoInexistenteException, PromocaoNulaException {
        controladorPromocao.removerPromocao(promocao);
    }

    public void atualizarPromocao(Promocao promocao, String titulo, int porcentagemDeDesconto,
                                  int qtdMinimaDeDiarias, int qtdMinimaDeLocacoes,
                                  LocalDate dataDeExpiracao, boolean ativa)
            throws PromocaoNulaException, PromocaoInexistenteException, PromocaoAtualizadaException {
        controladorPromocao.atualizarPromocao(promocao, titulo, porcentagemDeDesconto, qtdMinimaDeDiarias,
                qtdMinimaDeLocacoes, dataDeExpiracao, ativa);
    }


    public List<Promocao> listarTodasPromocoes() {
        return controladorPromocao.listarTodasPromocoes();
    }

    public boolean existePromocao(String id) {
        return controladorPromocao.existePromocao(id);
    }

    public Promocao buscarPromocao(String id) {
        return controladorPromocao.buscarPromocao(id);
    }

    public String gerarId() {
        return controladorPromocao.gerarId();
    }

    public void adicionarEnderecoCliente(String cpf, Endereco endereco) throws UsuarioInexistenteException, EnderecoNuloException,
            ClienteNuloException {
        controladorEndereco.adicionarEnderecoCliente(cpf, endereco);
    }

    public void removerEnderecoCliente(String cpf, Endereco endereco) throws UsuarioInexistenteException, EnderecoNuloException,
            ClienteNuloException {
        controladorEndereco.removerEnderecoCliente(cpf, endereco);
    }

    public Endereco obterEnderecoPorCep(int cep) {
        return controladorEndereco.obterEnderecoPorCep(cep);
    }

    public List<Endereco> listarEnderecos() {
        return controladorEndereco.listarEnderecos();
    }


}
