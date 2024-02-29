package br.ufrpe.readeasy.data;


import br.ufrpe.readeasy.beans.Promocao;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RepositorioPromocao implements IRepositorioPromocao, Serializable{
    private static final long serialVersionUID = 11L;

    private static IRepositorioPromocao instance;
    private ArrayList<Promocao> promocoes;

    public RepositorioPromocao() {
        this.promocoes = new ArrayList<>();
    }

    public static IRepositorioPromocao getInstance(){
        if (instance == null) {
            instance = lerDoArquivo();
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
    public boolean existePromocao(UUID id){
        boolean existe = false;
        for(Promocao promocao : promocoes){
            if(promocao.getId().equals(id)) {
                existe = true;
            }
        }
        return existe;
    }

    @Override
    public Promocao buscarPromocao(UUID id){
        Promocao promocao1 = null;
        for(Promocao promocao : promocoes){
            if(promocao.getId().equals(id)) {
                promocao1 = promocao;
            }
        }
        return promocao1;
    }

    private static RepositorioPromocao lerDoArquivo() {
        RepositorioPromocao instanciaLocal = null;

        File in = new File("RepoPromocao.dat");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(in);
            ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            instanciaLocal = (RepositorioPromocao) o;
        } catch (Exception e) {
            instanciaLocal = new RepositorioPromocao();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {/* Silent exception */
                }
            }
        }

        return instanciaLocal;
    }

    @Override
    public void salvarArquivo() {
        if (instance == null) {
            return;
        }
        File out = new File("RepoPromocao.dat");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(out);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(instance);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    /* Silent */}
            }
        }
    }
}