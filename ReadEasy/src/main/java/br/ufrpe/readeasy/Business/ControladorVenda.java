package br.ufrpe.readeasy.Business;
import java.util.ArrayList;

public class ControladorVenda
{
    private RepositorioVenda repoVenda;

    public ControladorVenda()
    {
        this.repoVenda = RepositorioVenda.getInstance();
    }

    public void adicionarVenda(Venda venda)
    {
        if(repoVenda.inserirVenda(Venda))
        {
            System.out.println("Venda adicionada com sucesso.");
        }
        else
        {
            System.out.println("Falha ao adicionar a venda.");
        }
    }

    public void removerVenda(Venda venda)
    {
        if(repoVenda.removerVenda(venda))
        {
            System.out.println("Venda removida com sucesso.");
        }
        else
        {
            System.out.println("Falha ao remover a venda.");
        }
    }

    public void historicoDeVendas
    {
        ArrayList<Venda> historico = repoVenda.historicoDeVendas;

        System.out.println("Histórico de vendas:");
        for(Venda venda : historico)
        {
            System.out.println(venda);
        }
    }

    public void historicoDeComprasDoUsuario(Cliente cliente)
    {
        ArrayList<Venda> historicoCliente = repoVenda.historicoDeComprasDoUsuario;

        System.out.println("Histórico de compras do usuário %c", cliente.getNome);
        for(Venda venda : historicoCliente)
        {
            System.out.println(venda);
        }
    }
}
