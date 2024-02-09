package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.Livro;

import java.util.Comparator;
import java.util.List;

public class ComparadorDeLivro {

    // Método para listar livros em ordem alfabética por título
    public static List<Livro> ordenarPorTitulo(List<Livro> livros) {
        livros.sort(Comparator.comparing(Livro::getTitulo));
        return livros;
    }

    // Comparador para ordenar por preço (do mais caro pro mais barato)
    public static List<Livro> ordenarPorPreco(List<Livro> livros) {
        livros.sort(Comparator.comparingDouble(Livro::getPreco).reversed());
        return livros;
    }
}