package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.Promocao;
import br.ufrpe.readeasy.data.IRepositorioPromocao;
import br.ufrpe.readeasy.data.RepositorioPromocao;
import br.ufrpe.readeasy.exceptions.*;

import java.time.LocalDate;
import java.util.List;

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


    public void inserirPromocao(Promocao promocao) throws PromocaoExistenteException, PromocaoNulaException {
        if(promocao != null){
            if(!repPromocoes.existePromocao(promocao.getId())){
                if (!promocao.getTitulo().isEmpty() && promocao.getPorcentagemDeDesconto() >= 0 &&
                        promocao.getPorcentagemDeDesconto() <= 100 &&
                        promocao.getDataDeExpiracao().isAfter(LocalDate.now())){
                    do{
                        promocao.setId(repPromocoes.gerarId());
                    }while (repPromocoes.existePromocao(promocao.getId()));
                    repPromocoes.inserir(promocao);
                    repPromocoes.salvarArquivo();

                }

            }else{
                throw new PromocaoExistenteException(promocao.getId());
            }
        }else{
            throw new PromocaoNulaException(promocao.getTitulo());
        }

    }

    public void removerPromocao(Promocao promocao) throws PromocaoInexistenteException, PromocaoNulaException {
        if(promocao != null){
            if (repPromocoes.existePromocao(promocao.getId())) {
                repPromocoes.remover(promocao);
                repPromocoes.salvarArquivo();
            }else{
                throw new PromocaoInexistenteException(promocao.getTitulo());
            }
        }else{
            throw new PromocaoNulaException(promocao.getTitulo());
        }
    }

    public void atualizarPromocao(Promocao promocao, String titulo, int porcentagemDeDesconto,
                                  int qtdMinimaDeLivros, LocalDate dataDeCriacao, LocalDate dataDeExpiracao, boolean ativa)
            throws PromocaoNulaException, PromocaoInexistenteException {

        if (promocao != null) {
            if (repPromocoes.existePromocao(promocao.getId())) {

                if (titulo.isEmpty() || promocao.getTitulo().equals(titulo)) {
                    titulo = promocao.getTitulo();
                }

                if (porcentagemDeDesconto < 0 || porcentagemDeDesconto > 100 || promocao.getPorcentagemDeDesconto() ==
                        porcentagemDeDesconto) {
                    porcentagemDeDesconto = promocao.getPorcentagemDeDesconto();

                }

                if (dataDeExpiracao.isBefore(LocalDate.now())) {
                    dataDeExpiracao = promocao.getDataDeExpiracao();
                }

                repPromocoes.atualizar(promocao, titulo, porcentagemDeDesconto, qtdMinimaDeLivros, dataDeCriacao,
                        dataDeExpiracao, ativa);
                repPromocoes.salvarArquivo();

            }else{
                throw new PromocaoInexistenteException(promocao.getTitulo());
            }
        }else{
            throw new PromocaoNulaException(promocao.getTitulo());
        }
    }

    public List<Promocao> listarTodasPromocoes(){
        return repPromocoes.listarTodasPromocoes();
    }
    public List<Promocao> listarTodasPromocoesAtivas(){
        return repPromocoes.listarTodasPromocoesAtivas();
    }

    public boolean existePromocao(String id){
        return repPromocoes.existePromocao(id);
    }

    public Promocao buscarPromocao(String id){
        return repPromocoes.buscarPromocao(id);
    }

    public String gerarId(){
        return repPromocoes.gerarId();
    }
}
