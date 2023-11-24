package beans;

import java.util.ArrayList;

public class Livro {
    private String titulo;
    private String autor;
    private double preco;
    private ArrayList<String> generos;
    private Fornecedor fornecedor;

    public Livro(String titulo, String autor, double preco, Fornecedor fornecedor) {
        setTitulo(titulo);
        setAutor(autor);
        setPreco(preco);
        this.generos = new ArrayList<String>();
        setFornecedor(fornecedor);
    }

    public void adicionarGenero(String genero) {
        if (!genero.isEmpty()) {
            generos.add(genero);
        }
    }

    public void removerGenero(String genero) {
        if (!genero.isEmpty()) {
            generos.remove(genero);
        }
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

    public ArrayList<String> getGeneros() {
        return generos;
    }

    public void setGeneros(ArrayList<String> generos) {
        if (!generos.isEmpty()) {
            this.generos = generos;
        }
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        if (fornecedor != null) {
            this.fornecedor = fornecedor;
        }
    }
}
