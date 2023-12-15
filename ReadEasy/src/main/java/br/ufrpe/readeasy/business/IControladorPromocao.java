package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.Promocao;
import br.ufrpe.readeasy.exceptions.PromocaoAtualizadaException;
import br.ufrpe.readeasy.exceptions.PromocaoExistenteException;
import br.ufrpe.readeasy.exceptions.PromocaoInexistenteException;
import br.ufrpe.readeasy.exceptions.PromocaoNulaException;

import java.time.LocalDate;
import java.util.List;

public interface IControladorPromocao {

    public void inserirPromocao (Promocao promocao) throws PromocaoExistenteException,
            PromocaoNulaException, PromocaoAtualizadaException;

    public void removerPromocao(Promocao promocao) throws PromocaoInexistenteException, PromocaoNulaException;

    public void atualizarPromocao(Promocao promocao, String titulo, int porcentagemDeDesconto,
                                  int qtdMinimaDeDiarias, int qtdMinimaDeLocacoes,
                                  LocalDate dataDeExpiracao, boolean ativa)
            throws PromocaoNulaException, PromocaoInexistenteException, PromocaoAtualizadaException;


    public List<Promocao> listarTodasPromocoes();

    public boolean existePromocao(String id);

    public Promocao buscarPromocao(String id);

    public String gerarId();

}