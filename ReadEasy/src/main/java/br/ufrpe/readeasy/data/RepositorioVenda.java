package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.LivroVendido;
import br.ufrpe.readeasy.beans.Venda;

import java.time.LocalDateTime;
import java.util.*;


public class RepositorioVenda implements IRepositorioVenda
{
    private static IRepositorioVenda instance;
    private ArrayList<Venda> vendas;
    public RepositorioVenda()
    {
        this.vendas = new ArrayList<>();
    }

    public static IRepositorioVenda getInstance()
    {
        if (instance == null)
        {
            instance = new RepositorioVenda();
        }
        return instance;
    }

    @Override
    public void inserirVenda(Venda venda)
    {
        vendas.add(venda);
    }

    @Override
    public void removerVenda(Venda venda){
        vendas.remove(venda);
    }

    @Override
    public void atualizarVenda(Venda venda, Cliente cliente, LocalDateTime dataHora, ArrayList<LivroVendido> livros)
    {
        venda.setCliente(cliente);
        venda.setDataEHora(dataHora);
        venda.setLivrosVendidos(livros);
    }

    @Override
    public List<Venda> listarVendas()
    {
        return Collections.unmodifiableList(vendas);
    }

    @Override
    public List<Venda> historicoDeVendas()
    {
        List<Venda> historico = new ArrayList<>();

        for (Venda venda: vendas)
        {
         if(!venda.getCliente().getNome().isEmpty())
         {
             historico.add(venda);
         }
        }
        historico.sort(Comparator.comparing(Venda::getDataEHora).reversed());
        return historico;
    }

    @Override
    public List<Venda> HistoricoDeVendasPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim)
    {
        if (dataInicio == null)
        {
            dataInicio = LocalDateTime.MIN;
        }
        if (dataFim == null)
        {
            dataFim = LocalDateTime.now();
        }

        List<Venda> historico = new ArrayList<>();

        for (Venda venda: vendas)
        {
            if(venda.getDataEHora().isAfter(dataInicio) && venda.getDataEHora().isBefore(dataFim))
            {
                historico.add(venda);
            }
        }
        historico.sort(Comparator.comparing(Venda::getDataEHora).reversed());
        return historico;
    }

    public List<Cliente> listarMelhoresClientesPorCompra(Map<Cliente, Integer> map)
    {
        List<Map.Entry<Cliente, Integer>> entryList = new ArrayList<>(map.entrySet());

        entryList.sort(Map.Entry.comparingByValue());

        List<Cliente> listaTopClientes = new ArrayList<>();
        for (Map.Entry<Cliente, Integer> entry : entryList)
        {
            listaTopClientes.add(entry.getKey());
        }
        return listaTopClientes;
    }

    public List<Cliente> listarMelhoresClientesPorGasto(Map<Cliente, Double> map)
    {
        List<Map.Entry<Cliente, Double>> clienteGasto = new ArrayList<>(map.entrySet());

        clienteGasto.sort(Map.Entry.comparingByValue());

        List<Cliente> listaTopClientes = new ArrayList<>();
        for (Map.Entry<Cliente, Double> entry : clienteGasto)
        {
            listaTopClientes.add(entry.getKey());
        }
        return listaTopClientes;
    }

    @Override
    public List<Venda> listarVendasPorFornecedor(String nomeFornecedor, LocalDateTime dataInicio, LocalDateTime dataFim) {
        List<Venda> vendasFornecedor = new ArrayList<>();

        for (Venda venda : vendas) {
            if (venda.getLivrosVendidos().get(0).getLivro().getFornecedor().getNome().equals(nomeFornecedor) &&
                    venda.getDataEHora().isAfter(dataInicio) && venda.getDataEHora().isBefore(dataFim)) {
                vendasFornecedor.add(venda);
            }
        }
        return vendasFornecedor;
    }

    @Override
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
