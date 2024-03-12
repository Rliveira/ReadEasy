package br.ufrpe.readeasy.beans;

import java.time.LocalDate;

public class CompraLivrariaDTO {
    private String tituloLivro;
    private String autor;
    private String nomeFornecedor;
    private int quantidade;
    private Double valorTotalPago;
    private LocalDate dataDaCompra;

    //CONSTRUTOR:
    public CompraLivrariaDTO(String tituloLivro, String autor, String nomeFornecedor, int quantidade, Double valorTotalPago, LocalDate dataDaCompra) {
        this.tituloLivro = tituloLivro;
        this.autor = autor;
        this.nomeFornecedor = nomeFornecedor;
        this.quantidade = quantidade;
        this.valorTotalPago = valorTotalPago;
        this.dataDaCompra = dataDaCompra;
    }

    //GETs AND SETs:
    public String getTituloLivro() {
        return tituloLivro;
    }
    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
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
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public Double getValorTotalPago() {
        return valorTotalPago;
    }
    public void setValorTotalPago(Double valorTotalPago) {
        this.valorTotalPago = valorTotalPago;
    }
    public LocalDate getDataDaCompra() {
        return dataDaCompra;
    }
    public void setDataDaCompra(LocalDate dataDaCompra) {
        this.dataDaCompra = dataDaCompra;
    }
}
