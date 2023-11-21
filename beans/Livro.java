package beans;

import java.util.ArrayList;

public class Livro {
    private String titulo;
    private String autor;
    private String editora;
    private int ano;
    private double preco;
    private ArrayList<String> generos;
    private Fornecedor fornecedor;

    public Livro(String titulo, String autor, String editora,
                 int ano, double preco, ArrayList<String> generos, Fornecedor fornecedor) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.ano = ano;
        this.preco = preco;
        this.generos = generos;
        this.fornecedor = fornecedor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditora() {
        return editora;
    }

    public int getAno() {
        return ano;
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

    public void setEditora(String editora) {
        if (!editora.isEmpty()) {
            this.editora = editora;
        }
    }

    public void setAno(int ano) {
        if (ano > 0) {
            this.ano = ano;
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
