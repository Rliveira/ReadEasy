package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.CompraLivrariaDTO;
import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Genero;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.data.IRepositorioLivro;
import br.ufrpe.readeasy.data.RepositorioLivro;
import br.ufrpe.readeasy.exceptions.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ControladorLivro implements IControladorLivro {
    private IRepositorioLivro repLivro;
    private static ControladorLivro instance;

    //CONSTRUTOR:
    private ControladorLivro(){
        repLivro = RepositorioLivro.getInstance();
    }

    //SINGLETON:
    public static ControladorLivro getInstance(){
        if (instance == null) {
            instance = new ControladorLivro();
        }
        return instance;
    }


    //MÉTODOS:

    public void adicionarLivro(Livro livro) throws ValorInvalidoException, LivroExistenteException {
        if(livro.getPreco() < 0){
            throw new ValorInvalidoException();
        }

        repLivro.cadastrarLivro(livro);
        repLivro.salvarArquivo();
    }

    @Override
    public void removerLivro(Livro livro) {
        repLivro.removerLivro(livro);
        repLivro.salvarArquivo();
    }

    @Override
    public void atualizarLivro(Livro livro, String titulo, String autor, double preco, Fornecedor fornecedor, byte[] capaDoLivro, URL urlLivro)
            throws ValorInvalidoException, LivroExistenteException {
        if(preco >= 0){
            repLivro.atualizarLivro(livro, titulo, autor, preco, fornecedor, capaDoLivro, urlLivro);
            repLivro.salvarArquivo();
        }
        else{
            throw new ValorInvalidoException();
        }
    }

    @Override
    public void adicionarGenero(Livro livro, Genero genero) throws GeneroExistenteException {
        repLivro.adicionarGenero(livro, genero);
        repLivro.salvarArquivo();
    }

    @Override
    public void removerGenero(Livro livro, Genero genero) throws GeneroNaoExistenteException, LivroSemGeneroException{
        repLivro.removerGenero(livro, genero);
        repLivro.salvarArquivo();
    }

    @Override
    public void aumentarQuantidadeEmEstoque(Livro livro, int quantidade, LocalDate dataDaAtualizacao, double valorTotalPago)
            throws ValorInvalidoException {

        if(quantidade > 0 && valorTotalPago > 0){
            repLivro.aumentarQuantidadeEmEstoque(livro, quantidade, dataDaAtualizacao, valorTotalPago);
            repLivro.salvarArquivo();
        }
        else{
            throw new ValorInvalidoException();
        }

    }

    @Override
    public void diminuirQuantidadeEmEstoque(Livro livro, int quantidade) throws EstoqueInsuficienteException,
            ValorInvalidoException {

        if(quantidade > 0){
            repLivro.diminuirQuantidadeEmEstoque(livro, quantidade);
            repLivro.salvarArquivo();
        }
        else{
            throw new ValorInvalidoException();
        }
    }

    @Override
    public Livro buscarLivro(UUID id) {
        return repLivro.buscarLivro(id);
    }

    @Override
    public Livro buscarLivroPorNome(String titulo) {
        return repLivro.buscarLivroPorNome(titulo);
    }

    @Override
    public  List<Livro> listarTodosOslivrosEmOrdemAlfabetica(){
        return repLivro.listarTodosOsLivrosEmOrdemAlfabetica();
    }

    @Override
    public List<Livro> listarLivrosPorAutor(String nomeAutor) {
        List<Livro> lista = new ArrayList<>();

        if(nomeAutor != null && !nomeAutor.isBlank()){
            lista = repLivro.listarLivrosPorAutor(nomeAutor);
        }
        return lista;
    }

    @Override
    public List<Livro> listarLivrosPorGenero(Genero genero) throws GeneroNaoExistenteException{
        return  repLivro.listarLivrosPorGenero(genero);
    }

    @Override
    public List<Livro> listarLivrosPorFornecedor(Fornecedor fornecedor) throws FornecedorNaoEncontradoException {
        return repLivro.listarLivrosPorFornecedor(fornecedor);
    }

    @Override
    public List<CompraLivrariaDTO> ListarHistoricoDeVendasFornecedor(Fornecedor fornecedor
            , LocalDate dataInicio, LocalDate dataFim) throws FornecedorNaoEncontradoException, DataInvalidaException {

        if (dataInicio == null){
            dataInicio = LocalDate.MIN;
        }

        if (dataFim == null){
            dataFim = LocalDate.now();
        }

        if (dataInicio.isAfter(dataFim)){
            throw new DataInvalidaException("Datas inválidas. Certifique-se de que a data de início não seja posterior"
                    +  " à data de fim e que ambas não sejam datas posteriores ou iguais à data atual.");
        }

        return repLivro.ListarHistoricoDeVendasFornecedor(fornecedor, dataInicio, dataFim);
    }

    @Override
    public List<Livro> listarEOrdenarLivrosPorPreco(){
        return repLivro.listarEOrdenarLivrosPorPreco();
    }

    @Override
    public Map<Livro, Integer> listarQuantidadeDeEstoque(){
        return repLivro.listarQuantidadeDeEstoque();
    }

    @Override
    public List<CompraLivrariaDTO> historicoLivrosCompradosLivraria(LocalDate dataInicio, LocalDate dataFim) throws DataInvalidaException {
        return repLivro.historicoLivrosCompradosLivraria(dataInicio, dataFim);
    }

    @Override
    public List<Livro> listarLivrosComEstoqueDisponivel() {
        return repLivro.listarLivrosComEstoqueDisponivel();
    }

    @Override
    public List<CompraLivrariaDTO> ranquearFornecedoresMaisCompradosPorPeriodo(LocalDate dataInicio, LocalDate dataFim){
        return repLivro.ranquearFornecedoresMaisCompradosPorPeriodo(dataInicio, dataFim);
    }

    @Override
    public Map<LocalDate, Integer> calcularQtdDeLivrosCompradosPorPeriodo(LocalDate dataInicio, LocalDate dataFim){
        return repLivro.calcularQtdDeLivrosCompradosPorPeriodo(dataInicio, dataFim);
    }

    @Override
    public Map<LocalDate, Double> calcularValorTotalPagoDeLivrosCompradosPorPeriodo(LocalDate dataInicio, LocalDate dataFim){
        return repLivro.calcularValorTotalPagoDeLivrosCompradosPorPeriodo(dataInicio, dataFim);
    }
}