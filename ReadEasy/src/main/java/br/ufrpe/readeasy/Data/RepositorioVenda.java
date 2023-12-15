package br.ufrpe.readeasy.Data;

import java.util.ArrayList;
import java.util.Comparator;


public class RepositorioVenda implements IRepositorioVenda
{
    private static RepositorioVenda instancia;
    private ArrayList<Venda> vendas;
    public RepositorioVenda(int capacidadeInicial)
    {
        this.vendas = new ArrayList<>(capacidadeInicial);
    }

    public static synchronized RepositorioVenda getInstance()
    {
        if(instancia == null)
        {
            instancia = new RepositorioVenda();
        }
        return instancia;
    }

    public boolean InserirVenda(Venda venda)
    {
        if(!vendas.contains(venda))
        {
            for(Venda v : vendas)
            {
                if(v.equals(venda))
                {
                    System.out.println("Esta venda já está no histórico");
                    return false;
                }
            }
            vendas.add(venda);
            return true;
        }
        else
        {
            System.out.println("Esta venda já está no histórico.");
            return false;
        }
    }

    public boolean removerVenda(Venda venda)
    {
        for(Venda v : vendas)
        {
            if(v.equals(venda))
            {
                vendas.remove(v);
                return true;
            }
        }
        System.out.println("Esta venda não consta no histórico.");
        return false;
    }

    public ArrayList<Venda> historicoDeVendas()
    {
        Arraylist<Venda> historico = new ArrayList<>(vendas);

        historico.sort(Comparator.comparing(Venda::getDataHora).reversed());

        return historico;
    }

    public Arraylist<Venda> listarMelhoresClientes(int tamanhoDaLista)
    {
        Map<Cliente, Integer> mapaClienteQtdLivros = new HashMap<>();

        for(Venda venda : vendas)
        {
            Cliente cliente = venda.getCliente();
            int qtdLivros = venda.getLivrosVendidos().size();

            mapaClienteQtdLivros.merge(cliente, quantidadeLivros, Integer ::sum);
        }

        ArrayList<Cliente> topClientes = mapaClienteQtdLivros.entrySet().stream()
                .sorted(Map.Entry.<Cliente, Integer>comparingByValue.reversed())
                .limit(tamanhoDaLista)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return topClientes;
    }

    public ArrayList<Venda> historicoDeComprasDoUsuario(Cliente cliente)
    {
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