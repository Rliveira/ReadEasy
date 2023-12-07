package com.example.readeasy.Business.beans;

import java.util.ArrayList;

public class Livro {
    private String titulo;
    private String autor;
    private double preco;
    private ArrayList<Genero> generos;
    private Fornecedor fornecedor;
    private String imagem;

    //CONSTRUTOR:
    public Livro(String titulo, String autor, double preco, Fornecedor fornecedor) {
        setTitulo(titulo);
        setAutor(autor);
        setPreco(preco);
        this.generos = new ArrayList<Genero>();
        setFornecedor(fornecedor);
    }

    //MÉTODOS:
    public void adicionarGenero(Genero genero) {
        generos.add(genero);
    }

    public void removerGenero(Genero genero) {
        generos.remove(genero);
    }

    //GETS AND SETS:
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public double getPreco() {
        return preco;
    }

    public void setTitulo(String titulo) {
        if (!titulo.isEmpty()) {
            this.titulo = titulo;
        }
    }

    public void setAutor(String autor) {
        if (!autor.isEmpty()) {
            this.autor = autor;
        }
    }

    public void setPreco(double preco) {
        if (preco > 0) {
            this.preco = preco;
        }
    }

    public ArrayList<Genero> getGeneros() {
        return generos;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        if (fornecedor != null) {
            this.fornecedor = fornecedor;
        }
    }

    public String getImagem() {
        return imagem;
    }
    public void setImagem(String imagem) {
        if (!imagem.isEmpty()) {
            this.imagem = imagem;
        }
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", preco=" + preco +
                ", generos=" + generos +
                ", fornecedor=" + fornecedor +
                ", imagem='" + imagem + '\'' +
                '}';
    }
}
