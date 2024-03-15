package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.Promocao;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IRepositorioPromocao {
    void inserir(Promocao promocao);
    void remover(Promocao promocao);
    void atualizar(Promocao promocao, String titulo, int porcentagemDeDesconto, int qtdMinimaDeLivros,
                          LocalDate dataDeCriacao, LocalDate dataDeExpiracao, boolean ativa);
    List<Promocao> listarTodasPromocoes();
    List<Promocao> listarTodasPromocoesAtivas();
    Promocao buscarPromocao(UUID id);
    void salvarArquivo();
    boolean verificarPromocoesComTitulosIguais(Promocao promocao, String novoNome);
}