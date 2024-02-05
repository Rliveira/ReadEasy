package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.Promocao;
import br.ufrpe.readeasy.exceptions.*;

import java.time.LocalDate;
import java.util.List;

public interface IControladorPromocao {

    void inserirPromocao (Promocao promocao) throws PromocaoNulaException, PromocaoExistenteException;

    void removerPromocao(Promocao promocao) throws PromocaoInexistenteException, PromocaoNulaException;

    public void atualizarPromocao(Promocao promocao, String titulo, int porcentagemDeDesconto,
                                  int qtdMinimaDeLivros, LocalDate dataDeCriacao, LocalDate dataDeExpiracao, boolean ativa)
            throws PromocaoNulaException, PromocaoInexistenteException;


    List<Promocao> listarTodasPromocoes();

    public List<Promocao> listarTodasPromocoesAtivas();

    boolean existePromocao(String id);

    Promocao buscarPromocao(String id);

    String gerarId();

}