package com.example.readeasy.Business.beans;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Venda {
    private ArrayList<LivroVendido> livrosVendidos;
    private Cliente cliente;
    private LocalDateTime dataEHora;

    //CONSTRUTOR:
    public Venda(Cliente cliente) {
        this.livrosVendidos = new ArrayList<LivroVendido>();
        this.cliente = cliente;
        this.dataEHora = LocalDateTime.now();
    }

    //MÉTODOS:
    public double calcularTotal() {
        double total = 0;
        for (LivroVendido livroVendido : livrosVendidos) {
            total += livroVendido.calcularTotal();
        }
        return total;
    }

    public void adicionarLivro(Livro livro, int quantidade) {
        LivroVendido livroVendido = new LivroVendido(livro, quantidade);
        livrosVendidos.add(livroVendido);
    }

    public void removerLivro(Livro livro) {
        boolean achou = false;
        for (int i = 0; i < livrosVendidos.size() && !achou; i++) {
            if (livrosVendidos.get(i).getLivro().equals(livro)) {
                livrosVendidos.remove(livrosVendidos.get(i));
                achou = true;
            }
        }
    }

    public void alterarQuantidade(Livro livro, int quantidade){
        boolean achou = false;
        for (int i = 0; i < livrosVendidos.size() && !achou; i++) {
            if (livrosVendidos.get(i).getLivro().equals(livro) &&
                    livrosVendidos.get(i).getQuantidade() != quantidade && quantidade != 0 ) {
                livrosVendidos.get(i).setQuantidade(quantidade);
            }
        }
    }

    public boolean equals(Venda o)
    {
        if(this == o)
        {
            return true;
        }
        if(o == null || this.getClass() != o.getClass())
        {
            return false;
        }
        Venda venda = (Venda) o;
        return o.equals(livrosVendidos, this.livrosVendidos) && o.equals(cliente, this.cliente) &&
                o.equals(dataEHora, this.dataEHora);
    }

    //GETS AND SETS:
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataEHora() {
        return dataEHora;
    }

    public void setDataEHora(LocalDateTime dataEHora) {
        this.dataEHora = dataEHora;
    }

    public int hashCode()
    {
        return Objects.hash(livrosVendidos, cliente, dataEHora);
    }
}
