package br.ufrpe.readeasy.beans;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Livro implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String titulo;
    private String autor;
    private double preco;
    private ArrayList<Genero> generos;
    private Fornecedor fornecedor;
    private URL UrlLivro;
    private byte[] capaDoLivro;
    private int quantidade;
    private List<RegistroComprasLivraria> registroLivrariaRegistroCompras;

    //CONSTRUTOR:
    public Livro(String titulo, String autor, double preco, Fornecedor fornecedor, byte[] capaDoLivro, URL UrlLivro) {
        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.autor = autor;
        this.preco = preco;
        this.generos = new ArrayList<>();
        this.fornecedor = fornecedor;
        this.quantidade = 0;
        this.registroLivrariaRegistroCompras = new ArrayList<>();
        this.capaDoLivro = capaDoLivro;
        this.UrlLivro = UrlLivro;
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

    public void atualizarRegistroDeEstoque(RegistroComprasLivraria registroComprasLivraria) {
        this.registroLivrariaRegistroCompras.add(registroComprasLivraria);
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
        this.titulo = titulo;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public void setPreco(double preco) {
            this.preco = preco;
    }
    public ArrayList<Genero> getGeneros() {
        return generos;
    }
    public Fornecedor getFornecedor() {
        return fornecedor;
    }
    public void setFornecedor(Fornecedor fornecedor) {
            this.fornecedor = fornecedor;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public byte[] getCapaDoLivro() {
        return capaDoLivro;
    }
    public void setCapaDoLivro(byte[] capaDoLivro) {
        this.capaDoLivro = capaDoLivro;
    }
    public URL getUrlLivro() {
        return UrlLivro;
    }
    public void setUrlLivro(URL urlLivro) {
        UrlLivro = urlLivro;
    }
    public List<RegistroComprasLivraria> getRegistroCompraFornecedor() {
        return registroLivrariaRegistroCompras;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", preco=" + preco +
                ", generos=" + generos +
                ", fornecedor=" + fornecedor +
                ", imagem='" + capaDoLivro + '\'' +
                '}';
    }
}
