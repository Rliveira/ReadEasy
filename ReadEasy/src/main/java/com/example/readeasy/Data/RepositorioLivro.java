package com.example.readeasy.Data;

import com.example.readeasy.Business.ComparadorDeLivro;
import com.example.readeasy.Business.beans.Fornecedor;
import com.example.readeasy.Business.beans.Genero;
import com.example.readeasy.Business.beans.Livro;
import com.example.readeasy.Exceptions.GeneroExistenteException;
import com.example.readeasy.Exceptions.GeneroNaoExistenteException;
import com.example.readeasy.Exceptions.LivroExistenteException;
import com.example.readeasy.Exceptions.LivroNaoExistenteException;

import java.util.ArrayList;
import java.util.List;


public class RepositorioLivro implements IRepositorioLivro {
    private static RepositorioLivro instance;
    private List<Livro> livros;

    //CONSTRUTOR:
    private RepositorioLivro() {
        this.livros = new ArrayList<>();
    }

    //SINGLETON:
    public static RepositorioLivro getInstance() {
        if (instance == null) {
            instance = new RepositorioLivro();
        }
        return instance;
    }

    //MÉTODOS:
    @Override
    public void adicionarLivros(List<Livro> livros) throws LivroExistenteException {
        for (Livro livro : livros) {
            if (this.livros.contains(livro)) {
                throw new LivroExistenteException(livro.toString());
            }
        }

        this.livros.addAll(livros);
    }

    @Override
    public void removerLivros(List<Livro> livros) throws LivroNaoExistenteException {
        for (Livro livro : livros) {
            if (!this.livros.contains(livro)) {
                throw new LivroNaoExistenteException(livro.getTitulo());
            }
        }

        this.livros.removeAll(livros);
    }

    @Override
    public void atualizarLivros(Livro livro) throws LivroNaoExistenteException {
        boolean achou = false;

        for (int i = 0; i < livros.size() && !achou; i++) {
            if (livros.get(i).getId().equals(livro.getId())) {
                livros.remove(i);
                livros.add(livro);
                achou = true;
            }
        }
        if (!achou) {
            throw new LivroNaoExistenteException(livro.getTitulo());
        }
    }

    @Override
    public void adicionarGenero(String tituloLivro, Genero genero) throws GeneroExistenteException, LivroNaoExistenteException {
        for (Livro livro : livros) {
            if (livro.getTitulo().equals(tituloLivro)) {
                if (!livro.getGeneros().contains(genero)) {
                    livro.adicionarGenero(genero);
                } else {
                    throw new GeneroExistenteException(tituloLivro, genero.getDescricaoEnum());
                }
            } else {
                throw new LivroNaoExistenteException(tituloLivro);
            }
        }
    }

    @Override
    public void removerGenero(Livro livro, Genero genero) throws GeneroNaoExistenteException, LivroNaoExistenteException {
        if (livro.getGeneros().contains(genero)) {
            for (Livro livroRepositorio : livros) {
                if (livroRepositorio.getTitulo().equals(livro.getTitulo())) {
                    livro.removerGenero(genero);
                } else {
                    throw new LivroNaoExistenteException(livro.getTitulo());
                }
            }
        } else {
            throw new GeneroNaoExistenteException(livro.getTitulo(), genero.getDescricaoEnum());
        }
    }

    @Override
    public List<Livro> listarTodosOsLivrosEmOrdemAlfabetica() {
        List<Livro> lista = new ArrayList<>();
        lista.addAll(livros);
        lista = ComparadorDeLivro.ordenarPorTitulo(lista);
        return lista;
    }

    @Override
    public List<Livro> listarLivrosPorTitulo(String titulo) throws LivroNaoExistenteException {
        List<Livro> lista = new ArrayList<>();

        for (Livro livro : livros) {
            if (livro.getTitulo().equals(titulo)) {
                lista.add(livro);
            }
        }
        return lista;
    }

    @Override
    public List<Livro> listarLivrosPorAutor(String autor) {

        List<Livro> lista = new ArrayList<>();

        for (Livro livro : livros) {
            if (livro.getAutor().contains(autor)) {
                lista.add(livro);
            }
        }
        return lista;
    }

    @Override
    public List<Livro> listarLivrosPorGenero(Genero genero) throws GeneroNaoExistenteException {
        List<Livro> lista = new ArrayList<>();

        for (Livro livro : livros) {
            if (livro.getGeneros().contains(genero)) {
                lista.add(livro);
            }
        }
        return lista;
    }

    @Override
    public List<Livro> listarLivrosPorFornecedor(Fornecedor fornecedor) {
        List<Livro> lista = new ArrayList<>();

        for (Livro livro : livros) {
            if (livro.getFornecedor().equals(fornecedor)) {
                lista.add(livro);
            }
        }
        return lista;
    }

    @Override
    public List<Livro> listarEOrdenarLivrosPorPreco() {
        List<Livro> lista = new ArrayList<>();
        lista.addAll(livros);
        lista = ComparadorDeLivro.ordenarPorPreco(lista);
        return lista;
    }
}
