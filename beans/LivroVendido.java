package beans;

public class LivroVendido {
    private Livro livro;
    private int quantidade;

    public LivroVendido(Livro livro, int quantidade) {
        this.livro = livro;
        this.quantidade = quantidade;
    }

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

    public double getValorTotal() {
        return livro.getPreco() * quantidade;
    }
}
