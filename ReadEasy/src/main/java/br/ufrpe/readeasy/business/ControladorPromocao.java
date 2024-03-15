package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.Promocao;
import br.ufrpe.readeasy.data.IRepositorioPromocao;
import br.ufrpe.readeasy.data.RepositorioPromocao;
import br.ufrpe.readeasy.exceptions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ControladorPromocao implements IControladorPromocao{
    private static ControladorPromocao instance;
    private IRepositorioPromocao repPromocoes;

    private ControladorPromocao() {
        this.repPromocoes = RepositorioPromocao.getInstance();
    }

    public static ControladorPromocao getInstance() {
        if (instance == null) {
            instance = new ControladorPromocao();
        }
        return instance;
    }


    public void inserirPromocao(Promocao promocao) throws PromocaoExistenteException, ValorInvalidoException, DataInvalidaException {

        if(promocao.getDataDeCriacao().isAfter(LocalDate.now()) ||
                promocao.getDataDeCriacao().isAfter(promocao.getDataDeExpiracao()) ||
                promocao.getDataDeCriacao().isBefore(LocalDate.now())){
            throw new DataInvalidaException("Data inválida");
        }
        else{
            if(!repPromocoes.verificarPromocoesComTitulosIguais(promocao, promocao.getTitulo())){

                if (promocao.getPorcentagemDeDesconto() >= 0 && promocao.getPorcentagemDeDesconto() <= 100){
                    repPromocoes.inserir(promocao);
                    repPromocoes.salvarArquivo();
                }
                else{
                    throw new ValorInvalidoException();
                }
            }else{
                throw new PromocaoExistenteException(promocao.getId());
            }
        }
    }

    public void removerPromocao(Promocao promocao){
        repPromocoes.remover(promocao);
        repPromocoes.salvarArquivo();
    }

    public void atualizarPromocao(Promocao promocao, String titulo, int porcentagemDeDesconto, int qtdMinimaDeLivros
            ,LocalDate dataDeCriacao, LocalDate dataDeExpiracao, boolean ativa) throws PromocaoExistenteException,
            ValorInvalidoException, DataInvalidaException{
        if(dataDeCriacao.isAfter(LocalDate.now()) || dataDeCriacao.isAfter(dataDeExpiracao) || dataDeExpiracao.isBefore(LocalDate.now())){
            throw new DataInvalidaException("Data inválida");
        }
        else{
            if(!repPromocoes.verificarPromocoesComTitulosIguais(promocao, promocao.getTitulo())){
                if(promocao.getPorcentagemDeDesconto() >= 0 && promocao.getPorcentagemDeDesconto() <= 100){
                    repPromocoes.atualizar(promocao, titulo, porcentagemDeDesconto, qtdMinimaDeLivros, dataDeCriacao,
                            dataDeExpiracao, ativa);
                    repPromocoes.salvarArquivo();
                }
                else{
                    throw new ValorInvalidoException();
                }
            }
            else{
                throw new PromocaoExistenteException(promocao.getId());
            }
        }
    }

    public List<Promocao> listarTodasPromocoes(){
        return repPromocoes.listarTodasPromocoes();
    }
    public List<Promocao> listarTodasPromocoesAtivas(){
        return repPromocoes.listarTodasPromocoesAtivas();
    }


    public Promocao buscarPromocao(UUID id){
        return repPromocoes.buscarPromocao(id);
    }
}
