package br.ufrpe.readeasy.Data;

import br.ufrpe.readeasy.Exceptions.GeneroNaoExistenteException;
import br.ufrpe.readeasy.Exceptions.LivroExistenteException;
import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Genero;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.Exceptions.GeneroExistenteException;
import br.ufrpe.readeasy.Exceptions.LivroNaoExistenteException;

import java.util.List;

public interface IRepositorioLivro {
    void adicionarLivros(List<Livro> livros) throws LivroExistenteException;
    void removerLivros(List<Livro> livros) throws LivroNaoExistenteException;
    void atualizarLivros(Livro livro) throws LivroNaoExistenteException;
    void adicionarGenero (String tituloLivro, Genero genero) throws GeneroExistenteException, LivroNaoExistenteException;
    void removerGenero(Livro livro, Genero genero) throws GeneroNaoExistenteException, LivroNaoExistenteException;
    List<Livro> listarTodosOsLivrosEmOrdemAlfabetica();
    List<Livro> listarLivrosPorTitulo(String titulo) throws LivroNaoExistenteException;
    List<Livro> listarLivrosPorAutor(String autor);
    List<Livro> listarLivrosPorGenero(Genero genero) throws GeneroNaoExistenteException;
    List<Livro> listarLivrosPorFornecedor(Fornecedor fornecedor);
    List<Livro> listarEOrdenarLivrosPorPreco();
}
