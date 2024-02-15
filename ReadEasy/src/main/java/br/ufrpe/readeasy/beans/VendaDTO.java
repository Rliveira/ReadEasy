package br.ufrpe.readeasy.beans;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VendaDTO {

    private String titulo;
    private String autor;
    private String nomeFornecedor;
    private String nomeCliente;
    private int quantidade;
    private LocalDate dataVenda;
    private double preco;
    public VendaDTO(String titulo, String autor, String nomeFornecedor, String nomeCliente,
                    int quantidade, LocalDate dataVenda, double preco) {
        this.titulo = titulo;
        this.autor = autor;
        this.nomeFornecedor = nomeFornecedor;
        this.nomeCliente = nomeCliente;
        this.quantidade = quantidade;
        this.dataVenda = dataVenda;
        this.preco = preco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
