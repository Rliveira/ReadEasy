package br.ufrpe.readeasy.data;


import br.ufrpe.readeasy.beans.Promocao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RepositorioPromocao implements IRepositorioPromocao{

    private static IRepositorioPromocao instance;
    private ArrayList<Promocao> promocoes;

    public RepositorioPromocao() {
        this.promocoes = new ArrayList<>();
    }

    public static IRepositorioPromocao getInstance(){
        if (instance == null) {
            instance = new RepositorioPromocao();
        }
        return instance;
    }
    @Override
    public void inserir(Promocao promocao){
        promocoes.add(promocao);
    }

    @Override
    public void remover(Promocao promocao){
        promocoes.remove(promocao);
    }

    @Override
    public void atualizar(Promocao promocao, String titulo, int porcentagemDeDesconto, int qtdMinimaDeLivros,
                          LocalDate dataDeCriacao, LocalDate dataDeExpiracao, boolean ativa){
        promocao.setTitulo(titulo);
        promocao.setPorcentagemDeDesconto(porcentagemDeDesconto);
        promocao.setQtdMinimaDeLivros(qtdMinimaDeLivros);
        promocao.setDataDeCriacao(dataDeCriacao);
        promocao.setDataDeExpiracao(dataDeExpiracao);
        promocao.setAtiva(ativa);
    }

    @Override
    public List<Promocao> listarTodasPromocoes(){
        return promocoes;
    }

    @Override
    public List<Promocao> listarTodasPromocoesAtivas(){

        ArrayList<Promocao> listaDePromocoesAtivas = new ArrayList<>();
        for (Promocao promocao : promocoes){
            if (promocao.isAtiva()){
                listaDePromocoesAtivas.add(promocao);
            }
        }
        return listaDePromocoesAtivas;
    }

    @Override
    public boolean existePromocao(String id){
        boolean existe = false;
        for(Promocao promocao : promocoes){
            if(promocao.getId().equals(id)) {
                existe = true;
            }
        }
        return existe;
    }

    @Override
    public Promocao buscarPromocao(String id){
        Promocao promocao1 = null;
        for(Promocao promocao : promocoes){
            if(promocao.getId().equals(id)) {
                promocao1 = promocao;
            }
        }
        return promocao1;
    }

    @Override
    public String gerarId() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(caracteres.length());
            char caractere = caracteres.charAt(index);
            sb.append(caractere);
        }

        return sb.toString();
    }
}