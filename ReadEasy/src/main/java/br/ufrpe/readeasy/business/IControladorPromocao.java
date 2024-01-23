package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.Promocao;
import br.ufrpe.readeasy.exceptions.*;

import java.time.LocalDate;
import java.util.List;

public interface IControladorPromocao {

    void inserirPromocao (Promocao promocao) throws PromocaoExistenteException,
            PromocaoNulaException, PromocaoAtualizadaException, PromocaoInseridaComSucessoException;

    void removerPromocao(Promocao promocao) throws PromocaoInexistenteException, PromocaoNulaException;

    void atualizarPromocao(Promocao promocao, String titulo, int porcentagemDeDesconto,
                                  int qtdMinimaDeDiarias, int qtdMinimaDeLocacoes,
                                  LocalDate dataDeExpiracao, boolean ativa)
            throws PromocaoNulaException, PromocaoInexistenteException, PromocaoAtualizadaException;


    List<Promocao> listarTodasPromocoes();

    boolean existePromocao(String id);

    Promocao buscarPromocao(String id);

    String gerarId();

}