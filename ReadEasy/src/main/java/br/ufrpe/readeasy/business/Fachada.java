package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.exceptions.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Fachada {

    private static Fachada instance;
    private IControladorUsuario controladorUsuario;
    private IControladorLivro controladorLivro;
    private IControladorVenda controladorVenda;
    private IControladorPromocao controladorPromocao;

    private Fachada() {
        this.controladorUsuario = ControladorUsuario.getInstance();
        this.controladorLivro = ControladorLivro.getInstance();
        this.controladorVenda = ControladorVenda.getInstance();
        this.controladorPromocao = ControladorPromocao.getInstance();
    }

    public static Fachada getInstance() {
        if (instance == null) {
            instance = new Fachada();
        }
        return instance;
    }

    public void cadastrarAdmInicial(){
        controladorUsuario.cadastrarAdmInicial();
    }

    public void cadastrarUsuario(Usuario usuario) throws UsuarioExistenteException, CampoVazioException,
            MenorDeIdadeException, DataInvalidaException {
        controladorUsuario.cadastrarUsuario(usuario);
    }

    public void removerUsuario(Usuario usuario) {
        controladorUsuario.removerUsuario(usuario);
    }

    public boolean checarLogin(String login, String senha) throws LoginInvalidoException, CampoVazioException {
        return controladorUsuario.checarLogin(login, senha);
    }

    public void atualizarFuncionario(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                                     String senha, Endereco endereco, String telefone, boolean ehAdm,
                                     Funcionario admResponsavel) throws UsuarioExistenteException, DataInvalidaException{
        controladorUsuario.atualizarFuncionario(usuario, nome, cpf, dataNascimento, login, senha, endereco, telefone,
                ehAdm, admResponsavel);
    }

    public void atualizarCliente(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                                 String senha, Endereco endereco, String telefone) throws UsuarioExistenteException,
            DataInvalidaException {
        controladorUsuario.atualizarCliente(usuario, nome, cpf, dataNascimento, login, senha, endereco, telefone);
    }

    public void atualizarFornecedor(Usuario usuario, String nome, String cpf, LocalDate dataNascimento, String login,
                                     String senha, Endereco endereco, String telefone, TipoFornecedor tipoFornecedor)
            throws DataInvalidaException, UsuarioExistenteException{
        controladorUsuario.atualizarFornecedor(usuario, nome, cpf, dataNascimento, login, senha, endereco, telefone,
                tipoFornecedor);
    }

    public void adicionarEnderecoDeEntrega(Usuario usuario, Endereco endereco) throws EnderecoExistenteException {
        controladorUsuario.adicionarEnderecoDeEntrega(usuario, endereco);
    }

    public void atualizarEnderecoDeEntrega(Usuario usuario, Endereco endereco, int cep, String rua, String bairro, String cidade, String estado) throws EnderecoExistenteException, CampoVazioException {
        controladorUsuario.atualizarEnderecoDeEntrega(usuario, endereco, cep, rua, bairro, cidade, estado);
    }

    public void removerEnderecoDeEntrega(Usuario usuario, Endereco endereco) {
        controladorUsuario.removerEnderecoDeEntrega(usuario, endereco);
    }

    public List<Endereco> listarEnderecosDeEntrega(Usuario usuario) {
        return controladorUsuario.listarEnderecosDeEntrega(usuario);
    }

    public Usuario procurarUsuario(String cpf){
        return controladorUsuario.procurarUsuario(cpf);
    }

    public List<Usuario> listarUsuarios() {
        return controladorUsuario.listarUsuarios();
    }

    public List<Cliente> listarClientes() {
        return controladorUsuario.listarClientes();
    }

    public List<Funcionario> listarFuncionarios() {
        return controladorUsuario.listarFuncionarios();
    }

    public List<Funcionario> listarAdms() {
        return controladorUsuario.listarAdms();}

    public List<Fornecedor> listarFornecedores() {
        return controladorUsuario.listarFornecedores();
    }

    public boolean existeUsuario(String cpf) {
        return controladorUsuario.existeUsuario(cpf);
    }

    public void adicionarLivro(Livro livro) throws ValorInvalidoException, LivroExistenteException {
        controladorLivro.adicionarLivro(livro);
    }

    public void removerLivro(Livro livro) {
        controladorLivro.removerLivro(livro);
    }

    public void atualizarLivro(Livro livro, String titulo, String autor, double preco, Fornecedor fornecedor,
                               byte[] capaDoLivro, URL urlLivro) throws ValorInvalidoException, LivroExistenteException {
        controladorLivro.atualizarLivro(livro, titulo, autor, preco, fornecedor, capaDoLivro, urlLivro);
    }

    public void adicionarGenero(Livro livro, Genero genero) throws GeneroExistenteException {
        controladorLivro.adicionarGenero(livro, genero);
    }

    public void removerGenero(Livro livro, Genero genero) throws GeneroNaoExistenteException, LivroSemGeneroException {
        controladorLivro.removerGenero(livro, genero);
    }

    public void aumentarQuantidadeEmEstoque(Livro livro, int quantidade, LocalDate dataVendaFornecedor
            , Double valorTotalPago) throws ValorInvalidoException {
        controladorLivro.aumentarQuantidadeEmEstoque(livro, quantidade, dataVendaFornecedor, valorTotalPago);
    }

    public void diminuirQuantidadeEmEstoque(Livro livro, int quantidade) throws EstoqueInsuficienteException,
            ValorInvalidoException {
        controladorLivro.diminuirQuantidadeEmEstoque(livro, quantidade);
    }

    public Livro buscarLivro(UUID id) {
        return controladorLivro.buscarLivro(id);
    }

    public List<Livro> listarTodosOslivrosEmOrdemAlfabetica() {
        return controladorLivro.listarTodosOslivrosEmOrdemAlfabetica();
    }

    public List<Livro> listarLivrosPorAutor(String nomeAutor) throws CampoVazioException {
        return controladorLivro.listarLivrosPorAutor(nomeAutor);
    }

    public List<Livro> listarLivrosPorGenero(Genero genero) throws GeneroNaoExistenteException {
        return controladorLivro.listarLivrosPorGenero(genero);
    }

    public List<Livro> listarLivrosPorFornecedor(Fornecedor fornecedor) throws FornecedorNaoEncontradoException {
        return controladorLivro.listarLivrosPorFornecedor(fornecedor);
    }

    public List<Livro> listarLivrosComEstoqueDisponivel(){
       return controladorLivro.listarLivrosComEstoqueDisponivel();
    }

    public List<Livro> listarEOrdenarLivrosPorPreco() {
        return controladorLivro.listarEOrdenarLivrosPorPreco();
    }

    public Map<Livro, Integer> listarQuantidadeDeEstoque() {
        return controladorLivro.listarQuantidadeDeEstoque();
    }

    public void inserirVenda(Venda venda) {
        controladorVenda.inserirVenda(venda);
    }

    public void removerVenda(Venda venda) {
        controladorVenda.removerVenda(venda);
    }

    public void atualizarVenda(Venda venda, Cliente cliente,  ArrayList<LivroVendido>
            livros) throws ListaDeLivrosVaziaException {
        controladorVenda.atualizarVenda(venda, cliente, livros);
    }

    public List<Venda> historicoDeVendas() throws HistoricoVazioException {
        return controladorVenda.historicoDeVendas();
    }

    public List<Venda> HistoricoDeVendasPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return controladorVenda.HistoricoDeVendasPorPeriodo(dataInicio,
                dataFim);
    }

    public List<Venda> listarVendas() {
        return controladorVenda.listarVendas();
    }

    public List<CompraClienteDTO> historicoDeComprasDoCliente(Cliente cliente, LocalDate dataInicio, LocalDate dataFim) throws DataInvalidaException {
         return controladorVenda.historicoDeComprasDoCliente(cliente, dataInicio, dataFim);
    }


    public void inserirPromocao(Promocao promocao) throws PromocaoExistenteException, ValorInvalidoException, DataInvalidaException {
        controladorPromocao.inserirPromocao(promocao);
    }

    public void removerPromocao(Promocao promocao){
        controladorPromocao.removerPromocao(promocao);
    }

    public void atualizarPromocao(Promocao promocao, String titulo, int porcentagemDeDesconto, int qtdMinimaDeLivros
            , LocalDate dataDeCriacao, LocalDate dataDeExpiracao, boolean ativa) throws PromocaoExistenteException
            , ValorInvalidoException, DataInvalidaException {

        controladorPromocao.atualizarPromocao(promocao, titulo, porcentagemDeDesconto, qtdMinimaDeLivros,
                dataDeCriacao, dataDeExpiracao, ativa);
    }


    public List<Promocao> listarTodasPromocoes() {
        return controladorPromocao.listarTodasPromocoes();
    }

    public List<Promocao> listarTodasPromocoesAtivas(){
        return controladorPromocao.listarTodasPromocoesAtivas();
    }

    public Promocao buscarPromocao(UUID id) {
        return controladorPromocao.buscarPromocao(id);
    }

    public List<CompraLivrariaDTO> historicoLivrosCompradosLivraria(LocalDate dataInicio, LocalDate dataFim) throws DataInvalidaException {
        return controladorLivro.historicoLivrosCompradosLivraria(dataInicio, dataFim);
    }

    public Map<String, Integer> ranquearClientesPorQuantidadeDeCompraEntreDatas(LocalDate dataInicio, LocalDate dataFim) throws HistoricoVazioException {
        return controladorVenda.ranquearClientesPorQuantidadeDeCompraEntreDatas(dataInicio, dataFim);
    }


    public Map<String, Double> raquearClientesPorGastoEntreDatas(LocalDate dataInicio, LocalDate dataFim) throws HistoricoVazioException {
        return controladorVenda.raquearClientesPorGastoEntreDatas(dataInicio, dataFim);
    }


    public Map<Livro, Integer> ranquearLivrosMaisVendidosEntreDatas(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim)
    {
        Map<Livro, Integer> listaInterna = controladorVenda.ranquearLivrosMaisVendidosEntreDatas(dataEHoraInicio,
                dataEHoraFim);
        return listaInterna;
    }

    public int calcularTotalLivrosVendidosEntreDatas (LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim)
    {
        return controladorVenda.calcularTotalLivrosVendidosEntreDatas(dataEHoraInicio, dataEHoraFim);
    }

    public List<CompraLivrariaDTO> ListarHistoricoDeVendasFornecedor(Fornecedor fornecedor, LocalDate dataInicio
            , LocalDate dataFim) throws FornecedorNaoEncontradoException, DataInvalidaException {
        return controladorLivro.ListarHistoricoDeVendasFornecedor(fornecedor, dataInicio, dataFim);
    }

    public Livro buscarLivroPorNome(String titulo) {
        return controladorLivro.buscarLivroPorNome(titulo);
    }

    public List<CompraClienteDTO> listarComprasDTO(Cliente cliente)
    {
        return controladorVenda.listarComprasDTO(cliente);
    }

    public List<VendaLivrariaDTO> listarVendasLivrariaDTO(LocalDate dataInicio, LocalDate dataFim) throws DataInvalidaException{
        return controladorVenda.listarVendasLivrariaDTO(dataInicio, dataFim);
    }

    public int calcularTotalDeVendasDiarias(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim){
        return controladorVenda.calcularTotalDeVendasDiarias(dataEHoraInicio, dataEHoraFim);
    }

    public Map<LocalDate, Integer> listarLivrosVendidosPorData(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim){
        return controladorVenda.listarLivrosVendidosPorData(dataEHoraInicio, dataEHoraFim);
    }

    public Map<LocalDate, Integer> listarVendasPorData(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim){
        return controladorVenda.listarVendasPorData(dataEHoraInicio, dataEHoraFim);
    }

    public Map<LocalDate, Double> listarLucroPorData(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim){
        return controladorVenda.listarLucroPorData(dataEHoraInicio, dataEHoraFim);
    }
    
    public List<CompraLivrariaDTO> ranquearFornecedoresMaisCompradosPorPeriodo(LocalDate dataInicio, LocalDate dataFim){
        return controladorLivro.ranquearFornecedoresMaisCompradosPorPeriodo(dataInicio, dataFim);
    }
    
    public Map<LocalDate, Integer> calcularQtdDeLivrosCompradosPorPeriodo(LocalDate dataInicio, LocalDate dataFim){
        return controladorLivro.calcularQtdDeLivrosCompradosPorPeriodo(dataInicio, dataFim);
    }
    
    public Map<LocalDate, Double> calcularValorTotalPagoDeLivrosCompradosPorPeriodo(LocalDate dataInicio, LocalDate dataFim){
        return controladorLivro.calcularValorTotalPagoDeLivrosCompradosPorPeriodo(dataInicio, dataFim);
    }

}

