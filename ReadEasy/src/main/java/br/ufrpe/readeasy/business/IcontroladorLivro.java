package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Genero;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.exceptions.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IcontroladorLivro {
    void adicionarLivro(Livro livro) throws LivroNuloException, CampoVazioException,
            PrecoInvalidoException, LivroExistenteException;
    void removerLivro(Livro livro) throws LivroNuloException, CampoVazioException, LivroNaoExistenteException;
    void atualizarLivro(Livro livro, String titulo, String autor, double preco, Fornecedor fornecedor)
            throws LivroNaoExistenteException, CampoVazioException, LivroNuloException,
            PrecoInvalidoException, UsuarioNuloException, DataInvalidaException, MenorDeIdadeException;
    void adicionarGenero(Livro livro, Genero genero) throws GeneroExistenteException, CampoVazioException,
            LivroNuloException, LivroNaoExistenteException;
    void removerGenero(Livro livro, Genero genero) throws GeneroNaoExistenteException, CampoVazioException,
            LivroNuloException, LivroNaoExistenteException;
    void aumentarQuantidadeEmEstoque(Livro livro, int quantidade) throws LivroNaoExistenteException
            , LivroNuloException, QuantidadeInvalidaException;    void diminuirQuantidadeEmEstoque(Livro livro, int quantidade) throws EstoqueInsuficienteException,
            QuantidadeInvalidaException, LivroNaoExistenteException, LivroNuloException;
    Livro buscarLivro(UUID id);
    List<Livro> listarTodosOslivrosEmOrdemAlfabetica();
    List<Livro> listarLivrosPorAutor(String nomeAutor);
    List<Livro> listarLivrosPorGenero(Genero genero) throws GeneroNaoExistenteException;
    List<Livro> listarLivrosPorFornecedor(Fornecedor fornecedor);
    List<Livro> listarEOrdenarLivrosPorPreco();
    Map<Livro, Integer> listarQuantidadeDeEstoque();
}