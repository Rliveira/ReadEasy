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
        controladorUsuario.cadastrarAdmInicial();
    }

    public void cadastrarUsuario(Usuario usuario) throws UsuarioNuloException, UsuarioExistenteException,
            TipoUsuarioInvalidoException, CampoVazioException, MenorDeIdadeException, DataInvalidaException {
        controladorUsuario.cadastrarUsuario(usuario);
    }

    public void removerUsuario(Usuario usuario) throws UsuarioNuloException, UsuarioInexistenteException {
        controladorUsuario.removerUsuario(usuario);
    }

    public boolean checarLogin(String login, String senha) throws LoginInvalidoException, CampoVazioException {
        return controladorUsuario.checarLogin(login, senha);
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

    public List<Endereco> listarEnderecosDeEntrega(Usuario usuario) throws TipoUsuarioInvalidoException, UsuarioInexistenteException,
            UsuarioNuloException {
        return controladorUsuario.listarEnderecosDeEntrega(usuario);
    }

    public Usuario procurarUsuario(String cpf) throws UsuarioInexistenteException, CampoVazioException {
        return controladorUsuario.procurarUsuario(cpf);
    }

    public void removerUsuario(String cpf) throws UsuarioInexistenteException, CampoVazioException {
        controladorUsuario.removerUsuario(cpf);
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

    public void adicionarLivro(Livro livro) throws PrecoInvalidoException, LivroExistenteException {
        controladorLivro.adicionarLivro(livro);
    }

    public void removerLivro(Livro livro) throws LivroNaoExistenteException {
        controladorLivro.removerLivro(livro);
    }

    public void atualizarLivro(Livro livro, String titulo, String autor, double preco, Fornecedor fornecedor, URL urlLivro)
            throws PrecoInvalidoException, LivroExistenteException {
        controladorLivro.atualizarLivro(livro, titulo, autor, preco, fornecedor, urlLivro);
    }

    public void adicionarGenero(Livro livro, Genero genero) throws GeneroExistenteException {
        controladorLivro.adicionarGenero(livro, genero);
    }

    public void removerGenero(Livro livro, Genero genero) throws GeneroNaoExistenteException, LivroSemGeneroException {
        controladorLivro.removerGenero(livro, genero);
    }

    public void aumentarQuantidadeEmEstoque(Livro livro, int quantidade, LocalDate dataVendaFornecedor ) throws QuantidadeInvalidaException {
        controladorLivro.aumentarQuantidadeEmEstoque(livro, quantidade, dataVendaFornecedor);
    }

    public void diminuirQuantidadeEmEstoque(Livro livro, int quantidade) throws EstoqueInsuficienteException,
            QuantidadeInvalidaException {
        controladorLivro.diminuirQuantidadeEmEstoque(livro, quantidade);
    }

    public Livro buscarLivro(UUID id) throws LivroNaoExistenteException, CampoVazioException {
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

    public List<Livro> listarEOrdenarLivrosPorPreco() {
        return controladorLivro.listarEOrdenarLivrosPorPreco();
    }

    public Map<Livro, Integer> listarQuantidadeDeEstoque() {
        return controladorLivro.listarQuantidadeDeEstoque();
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

    public List<Venda> historicoDeComprasDoCliente(Cliente cliente)
    {
         return controladorVenda.historicoDeComprasDoCliente(cliente);
    }


    public void inserirPromocao(Promocao promocao) throws PromocaoNulaException, PromocaoExistenteException {
        controladorPromocao.inserirPromocao(promocao);
    }

    public void removerPromocao(Promocao promocao) throws PromocaoInexistenteException, PromocaoNulaException {
        controladorPromocao.removerPromocao(promocao);
    }

    public void atualizarPromocao(Promocao promocao, String titulo, int porcentagemDeDesconto, int qtdMinimaDeLivros,
                          LocalDate dataDeCriacao, LocalDate dataDeExpiracao, boolean ativa)
            throws PromocaoNulaException, PromocaoInexistenteException {
        controladorPromocao.atualizarPromocao(promocao, titulo, porcentagemDeDesconto, qtdMinimaDeLivros,
                dataDeCriacao, dataDeExpiracao, ativa);
    }


    public List<Promocao> listarTodasPromocoes() {
        return controladorPromocao.listarTodasPromocoes();
    }
    public List<Promocao> listarTodasPromocoesAtivas(){
        return controladorPromocao.listarTodasPromocoesAtivas();
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

    public List<Livro> historicoLivrosCompradosLivraria(LocalDate dataInicio, LocalDate dataFim) throws DataInvalidaException {
        return controladorLivro.historicoLivrosCompradosLivraria(dataInicio, dataFim);
    }
    public Map<Cliente, Integer> listarMelhoresClientesPorCompra() throws HistoricoVazioException
    {
        Map<Cliente, Integer> listaInterna = controladorVenda.listarMelhoresClientesPorCompra();
        return listaInterna;
    }

    public Map<Cliente, Double> listarMelhoresClientesPorGasto() throws HistoricoVazioException
    {
        Map<Cliente, Double> listaInterna = controladorVenda.listarMelhoresClientesPorGasto();
        return listaInterna;
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

    public double calcularTotalLucroEntreDatas (LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim)
    {
        return controladorVenda.calcularTotalLucroEntreDatas(dataEHoraInicio, dataEHoraFim);
    }


    public Map<Livro, Map<LocalDate, Integer>> ListarHistoricoDeVendasFornecedor(Fornecedor fornecedor
            , LocalDate dataInicio, LocalDate dataFim) throws FornecedorNaoEncontradoException, DataInvalidaException
    {
        return controladorLivro.ListarHistoricoDeVendasFornecedor(fornecedor,
                dataInicio, dataFim);

    }

    public Livro buscarLivroPorNome(String titulo) {
        return controladorLivro.buscarLivroPorNome(titulo);
    }

    public List<Livro> listarTodosOsLivrosEmOrdemAlfabetica() {
        return controladorLivro.listarTodosOsLivrosEmOrdemAlfabetica();
    }

    public List<CompraDTO> listarComprasDTO(Cliente cliente)
    {
        return controladorVenda.listarComprasDTO(cliente);
    }

    public List<VendaDTO> listarVendasLivrariaDTO(LocalDate dataInicio, LocalDate dataFim){return controladorVenda.listarVendasLivrariaDTO(dataInicio, dataFim);}

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

}

