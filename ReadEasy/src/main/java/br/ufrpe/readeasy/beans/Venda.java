package br.ufrpe.readeasy.beans;
import br.ufrpe.readeasy.business.ControladorVenda;
import br.ufrpe.readeasy.business.IControladorVenda;
import br.ufrpe.readeasy.data.IRepositorioUsuario;
import br.ufrpe.readeasy.data.IRepositorioVenda;
import br.ufrpe.readeasy.data.RepositorioUsuario;
import br.ufrpe.readeasy.data.RepositorioVenda;
import br.ufrpe.readeasy.exceptions.*;
import javafx.beans.value.ObservableValue;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Venda implements Serializable {
    private static final long serialVersionUID = 3L;
    private ArrayList<LivroVendido> livrosVendidos;
    private Cliente cliente;
    private LocalDateTime dataEHora;
    private Endereco enderecoEntrega;
    private Promocao promocaoAplicada;

    //CONSTRUTOR:
    public Venda(Cliente cliente, LocalDateTime dataEHora, Endereco enderecoEntrega, Promocao promocaoAplicada) {
        this.livrosVendidos = new ArrayList<>();
        this.cliente = cliente;
        this.dataEHora = dataEHora;
        this.enderecoEntrega = enderecoEntrega;
        this.promocaoAplicada = promocaoAplicada;
    }

    //MÉTODOS:
    public double calcularTotal() {
        double total = 0;
        int porcentagemDeDesconto = 0;

        for (LivroVendido livroVendido : livrosVendidos) {
            total += livroVendido.calcularTotal();
        }
        //se tiver alguma promocao aplicada a venda:
        if(this.promocaoAplicada != null){
            porcentagemDeDesconto = this.getPromocaoAplicada().getPorcentagemDeDesconto();
        }
        double desconto = total * ((double) porcentagemDeDesconto /100);
        total = total - desconto;

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

    public void alterarQuantidade(Livro livro, int quantidade) {
        boolean achou = false;
        for (int i = 0; i < livrosVendidos.size() && !achou; i++) {
            if (livrosVendidos.get(i).getLivro().equals(livro) &&
                    livrosVendidos.get(i).getQuantidade() != quantidade && quantidade != 0) {
                livrosVendidos.get(i).setQuantidade(quantidade);
            }
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Venda venda = (Venda) obj;
        return Objects.equals(livrosVendidos, venda.livrosVendidos) &&
                Objects.equals(cliente, venda.cliente) &&
                Objects.equals(dataEHora, venda.dataEHora);
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

    public int hashCode() {
        return Objects.hash(livrosVendidos, cliente, dataEHora);
    }

    public ArrayList<LivroVendido> getLivrosVendidos() {
        return livrosVendidos;
    }

    public void setLivrosVendidos(ArrayList<LivroVendido> livrosVendidos) {
        this.livrosVendidos = livrosVendidos;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public Promocao getPromocaoAplicada() {
        return promocaoAplicada;
    }

    public void setPromocaoAplicada(Promocao promocaoAplicada) {
        this.promocaoAplicada = promocaoAplicada;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n+----------------------+--------------------------------+----------------------" +
                "+----------------------+----------------------+----------------------+\n");
        sb.append(String.format("| %-20s | %-30s | %-20s | %-20s | %-20s | %-20s |\n", "Livro", "Autor", "Fornecedor"
                , "Preço por Unidade", "Quantidade", "Preço Total"));
        sb.append("|----------------------|--------------------------------|----------------------" +
                "|----------------------|----------------------|----------------------|\n");

        double precoTotalVenda = 0.0;
        for (LivroVendido livro : livrosVendidos) {
            double precoTotalLivro = livro.calcularTotal();
            precoTotalVenda += precoTotalLivro;

            sb.append(String.format("| %-20s | %-30s | %-20s | %-20.2f | %-20d | %-20.2f |\n",
                    livro.getLivro().getTitulo(), livro.getLivro().getAutor(), livro.getLivro().getFornecedor()
                            .getNome(), livro.getLivro().getPreco(), livro.getQuantidade(), precoTotalLivro));
        }

        sb.append("|----------------------|--------------------------------|----------------------" +
                "|----------------------|----------------------|----------------------|\n");
        sb.append(String.format("| %-20s | %-30s | %-20s | %-20s | %-20s | %-20s |\n", "Cliente"
                , cliente.getNome(), "CPF: " + cliente.getCpf(), "", "", ""));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String dataHoraFormatada = dataEHora.format(formatter);

        sb.append(String.format("| %-20s | %-30s | %-20s | %-20s | %-20s | %-20s |\n", "Data e Hora", dataEHora, "", ""
                , "", ""));
        sb.append(String.format("| %-20s | %-30s | %-20s | %-20s | %-20s | %-20.2f |\n", "Total Venda", "", "", "", ""
                , precoTotalVenda));
        sb.append("+----------------------+--------------------------------+----------------------" +
                "+----------------------+----------------------+----------------------+\n");

        return sb.toString();
    }
}
