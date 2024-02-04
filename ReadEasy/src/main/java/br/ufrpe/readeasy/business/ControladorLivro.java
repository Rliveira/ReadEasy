package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Genero;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.data.IRepositorioLivro;
import br.ufrpe.readeasy.data.RepositorioLivro;
import br.ufrpe.readeasy.exceptions.*;

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

    public void adicionarLivro(Livro livro) throws PrecoInvalidoException, LivroExistenteException {
        if(livro.getPreco() < 0){
            throw new PrecoInvalidoException();
        }

        repLivro.cadastrarLivro(livro);
    }

    @Override
    public void removerLivro(Livro livro) throws LivroNaoExistenteException {
        repLivro.removerLivro(livro);
    }

    @Override
    public void atualizarLivro(Livro livro, String titulo, String autor, double preco, Fornecedor fornecedor)
            throws PrecoInvalidoException, LivroExistenteException {
        if(preco >= 0){
            repLivro.atualizarLivro(livro, titulo, autor, preco, fornecedor);
        }
        else{
            throw new PrecoInvalidoException();
        }
    }

    @Override
    public void adicionarGenero(Livro livro, Genero genero) throws GeneroExistenteException {
        repLivro.adicionarGenero(livro, genero);
    }

    @Override
    public void removerGenero(Livro livro, Genero genero) throws GeneroNaoExistenteException, LivroSemGeneroException{
        repLivro.removerGenero(livro, genero);
    }

    @Override
    public void aumentarQuantidadeEmEstoque(Livro livro, int quantidade, LocalDate dataDaAtualizacao)
            throws QuantidadeInvalidaException {

        if(quantidade > 0){
            repLivro.aumentarQuantidadeEmEstoque(livro, quantidade, dataDaAtualizacao);
        }
        else{
            throw new QuantidadeInvalidaException();
        }


    }

    @Override
    public void diminuirQuantidadeEmEstoque(Livro livro, int quantidade) throws EstoqueInsuficienteException,
            QuantidadeInvalidaException {

        if(quantidade > 0){
            repLivro.diminuirQuantidadeEmEstoque(livro, quantidade);
        }
        else{
            throw new QuantidadeInvalidaException();
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
    public   List<Livro> listarTodosOslivrosEmOrdemAlfabetica(){
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
    public Map<Livro, Map<LocalDate, Integer>> ListarHistoricoDeVendasFornecedor(Fornecedor fornecedor
            , LocalDate dataInicio, LocalDate dataFim) throws FornecedorNaoEncontradoException, DataInvalidaException {

        if (dataInicio == null){
            dataInicio = LocalDate.MIN;
        }

        if (dataFim == null){
            dataFim = LocalDate.now();
        }

        LocalDate dataAtual = LocalDate.now();
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
    public List<Livro> historicoLivrosCompradosLivraria(LocalDate dataInicio, LocalDate dataFim) throws DataInvalidaException {
        return repLivro.historicoLivrosCompradosLivraria(dataInicio, dataFim);
    }

    @Override
    public List<Livro> listarTodosOsLivrosEmOrdemAlfabetica() {
        return repLivro.listarTodosOsLivrosEmOrdemAlfabetica();
    }

}