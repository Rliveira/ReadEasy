package br.ufrpe.readeasy.beans;

public class LivroVendido {
    private Livro livro;
    private int quantidade;

    //CONSTRUTOR:
    public LivroVendido(Livro livro, int quantidade) {
        this.livro = livro;
        this.quantidade = quantidade;
    }

    //MÉTODOS:
    public double calcularTotal() {
        return livro.getPreco() * quantidade;
    }

    //GETS AND SETS:
    public Livro getLivro() {
        return livro;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setLivro(Livro livro) {
       if (livro != null) {
           this.livro = livro;
       }
    }

    public void setQuantidade(int quantidade) {
        if (quantidade > 0) {
            this.quantidade = quantidade;
        }
    }
}
