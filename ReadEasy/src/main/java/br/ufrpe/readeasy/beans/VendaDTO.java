package br.ufrpe.readeasy.beans;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VendaDTO {

    private Livro livro;
    private int quantidade;
    private Cliente cliente;
    private LocalDate dataVenda;
    public VendaDTO(Livro livro, int quantidade,Cliente cliente, LocalDate dataVenda) {
        this.livro = livro;
        this.quantidade = quantidade;
        this.cliente = cliente;
        this.dataVenda = dataVenda;
    }

    public Livro getLivroDTO() {
        return livro;
    }

    public void setLivroDTO(Livro livro) {
        this.livro = livro;
    }

    public int getQuantidadeDTO() {
        return quantidade;
    }

    public void setQuantidadeDTO(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataVendaDTO() {
        return dataVenda;
    }

    public void setDataVendaDTO(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Cliente getClienteDTO() {
        return cliente;
    }

    public void setClienteDTO(Cliente cliente) {
        this.cliente = cliente;
    }
}
