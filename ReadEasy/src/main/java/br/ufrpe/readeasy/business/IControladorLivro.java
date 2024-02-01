package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Genero;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.exceptions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IControladorLivro {
    void adicionarLivro(Livro livro) throws LivroNuloException, CampoVazioException,
            PrecoInvalidoException, LivroExistenteException;
    void removerLivro(Livro livro) throws LivroNuloException, CampoVazioException, LivroNaoExistenteException;
    void atualizarLivro(Livro livro, String titulo, String autor, double preco, Fornecedor fornecedor)
            throws LivroNaoExistenteException, CampoVazioException, LivroNuloException,
            PrecoInvalidoException, UsuarioNuloException, DataInvalidaException;
    void adicionarGenero(Livro livro, Genero genero) throws GeneroExistenteException, CampoVazioException,
            LivroNuloException, LivroNaoExistenteException;
    void removerGenero(Livro livro, Genero genero) throws GeneroNaoExistenteException, CampoVazioException,
            LivroNuloException, LivroNaoExistenteException;
    void aumentarQuantidadeEmEstoque(Livro livro, int quantidade, LocalDate dataDaAtualizacao)
            throws LivroNaoExistenteException, LivroNuloException, QuantidadeInvalidaException;
    void diminuirQuantidadeEmEstoque(Livro livro, int quantidade) throws EstoqueInsuficienteException,
            QuantidadeInvalidaException, LivroNaoExistenteException, LivroNuloException;
    Livro buscarLivro(UUID id);
    Livro buscarLivroPorNome(String titulo);
    List<Livro> listarTodosOslivrosEmOrdemAlfabetica();
    List<Livro> listarLivrosPorAutor(String nomeAutor);
    List<Livro> listarLivrosPorGenero(Genero genero) throws GeneroNaoExistenteException;
    List<Livro> listarLivrosPorFornecedor(Fornecedor fornecedor)  throws FornecedorNaoEncontradoException;
    Map<Livro, Map<LocalDate, Integer>> ListarHistoricoDeVendasFornecedor(Fornecedor fornecedor
            , LocalDate dataInicio, LocalDate dataFim) throws FornecedorNaoEncontradoException, DataInvalidaException;
    List<Livro> listarEOrdenarLivrosPorPreco();
    Map<Livro, Integer> listarQuantidadeDeEstoque();

    List<Livro> historicoLivrosCompradosLivraria(LocalDate dataInicio, LocalDate dataFim) throws DataInvalidaException;

    List<Livro> listarTodosOsLivrosEmOrdemAlfabetica();
}