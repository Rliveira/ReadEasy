package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.exceptions.*;
import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Genero;
import br.ufrpe.readeasy.beans.Livro;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IRepositorioLivro {
    void cadastrarLivro(Livro livro) throws LivroExistenteException;
    void removerLivro(Livro livro) throws LivroNaoExistenteException;
    void atualizarLivro(Livro livro, String titulo, String autor, double Preco, Fornecedor fornecedor) throws LivroNaoExistenteException;
    void adicionarGenero (Livro livro, Genero genero) throws GeneroExistenteException
            , LivroNaoExistenteException;
    void removerGenero(Livro livro, Genero genero) throws GeneroNaoExistenteException, LivroNaoExistenteException;
    void aumentarQuantidadeEmEstoque(Livro livro, int quantidade) throws LivroNaoExistenteException;
    void diminuirQuantidadeEmEstoque(Livro livro, int quantidade) throws EstoqueInsuficienteException
            , QuantidadeInvalidaException, LivroNaoExistenteException;
    Livro buscarLivro(UUID id);
    List<Livro> listarTodosOsLivrosEmOrdemAlfabetica();
    List<Livro> listarLivrosPorAutor(String autor);
    List<Livro> listarLivrosPorGenero(Genero genero) throws GeneroNaoExistenteException;
    List<Livro> listarLivrosPorFornecedor(Fornecedor fornecedor);
    List<Livro> listarEOrdenarLivrosPorPreco();
    Map<Livro, Integer> listarQuantidadeDeEstoque();
}
