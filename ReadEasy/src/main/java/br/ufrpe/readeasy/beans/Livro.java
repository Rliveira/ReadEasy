package br.ufrpe.readeasy.beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Livro {
    private UUID id;
    private String titulo;
    private String autor;
    private double preco;
    private ArrayList<Genero> generos;
    private Fornecedor fornecedor;
    private String imagem;
    private int quantidade;
    private Map<LocalDate, Integer> registroAtualizacaoEstoque;

    //CONSTRUTOR:
    public Livro(String titulo, String autor, double preco, Fornecedor fornecedor) {
        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.autor = autor;
        this.preco = preco;
        this.generos = new ArrayList<>();
        this.fornecedor = fornecedor;
        this.quantidade = 0;
        this.registroAtualizacaoEstoque = new HashMap<>();
    }

    //MÉTODOS:
    public void adicionarGenero(Genero genero) {
        generos.add(genero);
    }

    public void removerGenero(Genero genero) {
        generos.remove(genero);
    }

    public void aumentarQuantidade(int quantidade) {
        setQuantidade(this.getQuantidade() + quantidade);
    }

    public void diminuirQuantidade(int quantidade){
        setQuantidade(this.getQuantidade() - quantidade);
    }

    public void atualizarRegistroDeEstoque(LocalDate dataAtualizacao, int qtd) {
        Integer quantidade = qtd;

        if (this.registroAtualizacaoEstoque.containsKey(dataAtualizacao)) {
            Integer valorAtual = registroAtualizacaoEstoque.get(dataAtualizacao);
            registroAtualizacaoEstoque.put(dataAtualizacao, valorAtual + quantidade);
        } else {
            registroAtualizacaoEstoque.put(dataAtualizacao, quantidade);
        }
    }

    //GETS AND SETS:
    public UUID getId() {
        return id;
    }

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

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getImagem() {
        return imagem;
    }
    public void setImagem(String imagem) {
        if (!imagem.isEmpty()) {
            this.imagem = imagem;
        }
    }

    public Map<LocalDate, Integer> getRegistroAtualizacaoEstoque() {
        return registroAtualizacaoEstoque;
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
