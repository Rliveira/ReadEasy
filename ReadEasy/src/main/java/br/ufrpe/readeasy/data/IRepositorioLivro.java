package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Genero;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.exceptions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IRepositorioLivro {
    void cadastrarLivro(Livro livro) throws LivroExistenteException;
    void removerLivro(Livro livro) throws LivroNaoExistenteException;
    void atualizarLivro(Livro livro, String titulo, String autor, double Preco, Fornecedor fornecedor) throws LivroExistenteException;
    void adicionarGenero (Livro livro, Genero genero) throws GeneroExistenteException;
    void removerGenero(Livro livro, Genero genero) throws GeneroNaoExistenteException, LivroSemGeneroException;
    void aumentarQuantidadeEmEstoque(Livro livro, int quantidade, LocalDate dataDaAtualizacao);
    void diminuirQuantidadeEmEstoque(Livro livro, int quantidade) throws EstoqueInsuficienteException
            , QuantidadeInvalidaException;
    Livro buscarLivro(UUID id);
    List<Livro> listarTodosOsLivrosEmOrdemAlfabetica();
    List<Livro> listarLivrosPorAutor(String autor);
    List<Livro> listarLivrosPorGenero(Genero genero) throws GeneroNaoExistenteException;
    List<Livro> listarLivrosPorFornecedor(Fornecedor fornecedor)  throws FornecedorNaoEncontradoException;
    Map<Livro, Map<LocalDate, Integer>> ListarHistoricoDeVendasFornecedor(Fornecedor fornecedo
            , LocalDate dataInicio, LocalDate dataFim) throws FornecedorNaoEncontradoException;

    List<Livro> historicoLivrosCompradosLivraria(LocalDate dataInicio, LocalDate dataFim) throws DataInvalidaException;
    List<Livro> listarEOrdenarLivrosPorPreco();
    Map<Livro, Integer> listarQuantidadeDeEstoque();

    Livro buscarLivroPorNome(String titulo);
}
