package br.ufrpe.readeasy.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Promocao implements Serializable {
    private static final long serialVersionUID = 5L;
    private UUID id;
    private String titulo;
    private int porcentagemDeDesconto;
    private int qtdMinimaDeLivros;
    private LocalDate dataDeCriacao;
    private LocalDate dataDeExpiracao;
    private boolean ativa;

    //CONSTRUTOR:
    public Promocao(String titulo, int porcentagemDeDesconto, int qtdMinimaDeLivros, LocalDate dataDeCriacao, LocalDate dataDeExpiracao) {
        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.porcentagemDeDesconto = porcentagemDeDesconto;
        this.qtdMinimaDeLivros = qtdMinimaDeLivros;
        this.dataDeCriacao = dataDeCriacao;
        this.dataDeExpiracao = dataDeExpiracao;
        this.ativa = true;
    }

    //GETS AND SETS:
    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getPorcentagemDeDesconto() {
        return porcentagemDeDesconto;
    }

    public void setPorcentagemDeDesconto(int porcentagemDeDesconto) {
        this.porcentagemDeDesconto = porcentagemDeDesconto;
    }

    public int getQtdMinimaDeLivros() {
        return qtdMinimaDeLivros;
    }

    public void setQtdMinimaDeLivros(int qtdMinimaDeLivros) {
        this.qtdMinimaDeLivros = qtdMinimaDeLivros;
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

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public boolean isAtiva() {
        if (this.ativa && LocalDate.now().isAfter(getDataDeExpiracao())) {
            setAtiva(false);
        }
        return this.ativa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Promocao promocao)) return false;
        return porcentagemDeDesconto == promocao.porcentagemDeDesconto &&
                qtdMinimaDeLivros == promocao.qtdMinimaDeLivros &&
                ativa == promocao.ativa && Objects.equals(id, promocao.id) &&
                Objects.equals(titulo, promocao.titulo) &&
                Objects.equals(dataDeCriacao, promocao.dataDeCriacao) &&
                Objects.equals(dataDeExpiracao, promocao.dataDeExpiracao);
    }

    @Override
    public String toString() {
        return "Promocao{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", porcentagemDeDesconto=" + porcentagemDeDesconto +
                ", qtdMinimaDeLivros=" + qtdMinimaDeLivros +
                ", dataDeCriacao=" + dataDeCriacao +
                ", dataDeExpiracao=" + dataDeExpiracao +
                ", ativa=" + ativa +
                '}';
    }


}
