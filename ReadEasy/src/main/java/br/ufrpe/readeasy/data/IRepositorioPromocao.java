package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.Promocao;

import java.time.LocalDate;
import java.util.List;

public interface IRepositorioPromocao {

    public void inserir(Promocao promocao);

    public void remover(Promocao promocao);

    public void atualizar(Promocao promocao, String titulo, int porcentagemDeDesconto, int qtdMinimaDeLivros,
                          LocalDate dataDeCriacao, LocalDate dataDeExpiracao, boolean ativa);

    public List<Promocao> listarTodasPromocoes();

    public List<Promocao> listarTodasPromocoesAtivas();

    boolean existePromocao(String id);

    Promocao buscarPromocao(String id);

    String gerarId();

    void salvarArquivo();
}

