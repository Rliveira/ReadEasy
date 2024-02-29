package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.Promocao;
import br.ufrpe.readeasy.exceptions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IControladorPromocao {

    void inserirPromocao (Promocao promocao) throws PromocaoExistenteException;
    void removerPromocao(Promocao promocao);
    void atualizarPromocao(Promocao promocao, String titulo, int porcentagemDeDesconto, int qtdMinimaDeLivros
            , LocalDate dataDeCriacao, LocalDate dataDeExpiracao, boolean ativa);
    List<Promocao> listarTodasPromocoes();
    List<Promocao> listarTodasPromocoesAtivas();
    boolean existePromocao(UUID id);
    Promocao buscarPromocao(UUID id);
}