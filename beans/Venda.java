package beans;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Venda {
    private ArrayList<LivroVendido> livrosVendidos;
    private Cliente cliente;
    private LocalDateTime data;

    public Venda(Cliente cliente) {
        this.livrosVendidos = new ArrayList<LivroVendido>();
        this.cliente = cliente;
        this.data = LocalDateTime.now();
    }

    public double calcularTotal() {
        double total = 0;
        for (LivroVendido livroVendido : livrosVendidos) {
            total += livroVendido.getValorTotal();
        }
        return total;
    }

    public void adicionarLivro(Livro livro, int quantidade) {
        LivroVendido livroVendido = new LivroVendido(livro, quantidade);
        livrosVendidos.add(livroVendido);
    }

    public void removerLivro(Livro livro, int quantidade) {
        for (LivroVendido livroVendido : livrosVendidos) {
            if (livroVendido.getLivro().equals(livro)) {
                if (livroVendido.getQuantidade() > quantidade) {
                    livroVendido.setQuantidade(livroVendido.getQuantidade() - quantidade);
                } else {
                    livrosVendidos.remove(livroVendido);
                }
                break;
            }
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
