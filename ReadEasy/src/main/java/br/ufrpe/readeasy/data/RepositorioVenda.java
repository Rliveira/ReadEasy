package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.LivroVendido;
import br.ufrpe.readeasy.beans.Venda;

import java.time.LocalDateTime;
import java.util.*;


public class RepositorioVenda implements IRepositorioVenda{

    private static IRepositorioVenda instance;
    private ArrayList<Venda> vendas;

    public RepositorioVenda() {
        this.vendas = new ArrayList<>();
    }

    public static IRepositorioVenda getInstance(){
        if (instance == null) {
            instance = new RepositorioVenda();
        }
        return instance;
    }

    @Override
    public void inserirVenda(Venda venda){
        vendas.add(venda);
    }

    @Override
    public void removerVenda(Venda venda){
        vendas.remove(venda);
    }

    @Override
    public void atualizarVenda(Venda venda, Cliente cliente, LocalDateTime dataHora, ArrayList<LivroVendido> livros) {
        venda.setCliente(cliente);
        venda.setDataEHora(dataHora);
        venda.setLivrosVendidos(livros);
    }

    @Override
    public ArrayList<Venda> listarVendas(){
        return vendas;
    }

    @Override
    public ArrayList<Venda> historicoDeVendas(){

        ArrayList<Venda> historico = new ArrayList<>(vendas);
        historico.sort(Comparator.comparing(Venda::getDataEHora).reversed());

        return historico;
    }

    public List<Cliente> topClientes(Map<Cliente, Integer> map) {
        List<Map.Entry<Cliente, Integer>> entryList = new ArrayList<>(map.entrySet());

        entryList.sort(new Comparator<Map.Entry<Cliente, Integer>>() {
            @Override
            public int compare(Map.Entry<Cliente, Integer> entry1, Map.Entry<Cliente, Integer> entry2) {
                return entry1.getValue().compareTo(entry2.getValue());
            }
        });

        List<Cliente> listaTopClientes = new ArrayList<>();
        for (Map.Entry<Cliente, Integer> entry : entryList) {
            listaTopClientes.add(entry.getKey());
        }
        return listaTopClientes;
    }


    @Override
    public ArrayList<Venda> historicoDeComprasDoUsuario(Cliente cliente){
        ArrayList<Venda> historicoCliente = new ArrayList<>();

        for(Venda venda : vendas)
        {
            if(venda.getCliente().equals(cliente))
            {
                historicoCliente.add(venda);
            }
        }
        return historicoCliente;
    }
}
