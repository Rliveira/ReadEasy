package br.ufrpe.readeasy.beans;

import java.io.Serializable;
import java.time.LocalDate;

public class RegistroComprasLivraria implements Serializable {
    private static final long serialVersionUID = 100L;
    private int quantidade;
    private Double valorTotalPago;
    private LocalDate dataDaCompra;

    //CONSTRUTOR:
    public RegistroComprasLivraria(int quantidade, Double valorTotalPago, LocalDate dataDaCompra) {
        this.quantidade = quantidade;
        this.valorTotalPago = valorTotalPago;
        this.dataDaCompra = dataDaCompra;
    }

    //GETs AND SETs:
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
