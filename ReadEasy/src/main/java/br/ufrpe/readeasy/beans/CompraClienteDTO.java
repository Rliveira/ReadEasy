package br.ufrpe.readeasy.beans;

import java.time.LocalDateTime;

public class CompraClienteDTO
{
    private String tituloLivro;
    private String autorLivro;
    private int quantidade;
    private double preco;
    private LocalDateTime dataCompra;

    // Construtor
    public CompraClienteDTO(String tituloLivro, String autorLivro, int quantidade, double preco, LocalDateTime dataCompra) {
        this.tituloLivro = tituloLivro;
        this.autorLivro = autorLivro;
        this.quantidade = quantidade;
        this.preco = preco;
        this.dataCompra = dataCompra;
    }

    // Getters e Setters
    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public String getAutorLivro() {
        return autorLivro;
    }

    public void setAutorLivro(String autorLivro) {
        this.autorLivro = autorLivro;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }
}
