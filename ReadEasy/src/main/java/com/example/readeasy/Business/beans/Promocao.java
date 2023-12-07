package com.example.readeasy.Business.beans;

import java.time.LocalDate;

public class Promocao {
    private boolean estaAtiva;
    private Livro livro;
    private int porcentagemDeDesconto;
    private LocalDate dataDeCriacao;
    private LocalDate dataDeExpiracao;


    //CONSTRUTOR:
    public Promocao(Livro livro, int porcentagemDeDesconto, LocalDate dataDeCriacao, LocalDate dataDeExpiracao) {
        this.estaAtiva = true;
        this.livro = livro;
        this.porcentagemDeDesconto = porcentagemDeDesconto;
        this.dataDeCriacao = dataDeCriacao;
        this.dataDeExpiracao = dataDeExpiracao;
    }

    //GETS AND SETS:
    public boolean EstaAtiva() {
        if(LocalDate.now().isAfter(getDataDeExpiracao())){
            setEstaAtiva(false);
        }
        return estaAtiva;
    }

    private void setEstaAtiva(boolean estaAtiva) {
        this.estaAtiva = estaAtiva;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public int getPorcentagemDeDesconto() {
        return porcentagemDeDesconto;
    }

    public void setPorcentagemDeDesconto(int porcentagemDeDesconto) {
        this.porcentagemDeDesconto = porcentagemDeDesconto;
    }

    public LocalDate getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(LocalDate dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public LocalDate getDataDeExpiracao() {
        return dataDeExpiracao;
    }

    public void setDataDeExpiracao(LocalDate dataDeExpiracao) {
        this.dataDeExpiracao = dataDeExpiracao;
    }

    @Override
    public String toString() {
        return "Promocao{" +
                "estaAtiva=" + estaAtiva +
                ", livro=" + livro +
                ", porcentagemDeDesconto=" + porcentagemDeDesconto +
                ", dataDeCriacao=" + dataDeCriacao +
                ", dataDeExpiracao=" + dataDeExpiracao +
                '}';
    }
}
