package com.example.readeasy.Data;

import com.example.readeasy.Business.beans.Fornecedor;
import com.example.readeasy.Business.beans.Genero;
import com.example.readeasy.Business.beans.Livro;
import com.example.readeasy.Exceptions.GeneroExistenteException;
import com.example.readeasy.Exceptions.GeneroNaoExistenteException;
import com.example.readeasy.Exceptions.LivroExistenteException;
import com.example.readeasy.Exceptions.LivroNaoExistenteException;

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
