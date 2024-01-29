package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.LivroVendido;
import br.ufrpe.readeasy.beans.Venda;
import br.ufrpe.readeasy.data.IRepositorioVenda;
import br.ufrpe.readeasy.data.RepositorioVenda;
import br.ufrpe.readeasy.exceptions.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ControladorVenda implements IControladorVenda
{
    private static ControladorVenda instance;
    private IRepositorioVenda repoVenda;
    public ControladorVenda()
    {
        this.repoVenda = RepositorioVenda.getInstance();
    }

    public static ControladorVenda getInstance()
    {
        if(instance == null)
        {
            instance = new ControladorVenda();
        }
        return instance;
    }
    @Override
    public List<Venda> listarVendas()
    {
        return repoVenda.listarVendas();
    }
    @Override
    public void inserirVenda(Venda venda) throws UsuarioNuloException, VendaInvalidaException
    {
        if(venda!=null){
            if (venda.getCliente()!=null)
            {
                repoVenda.inserirVenda(venda);
            }
            else
            {
                throw new UsuarioNuloException();
            }
        }
        else
        {
            throw new VendaInvalidaException();
        }
    }
    @Override
    public void removerVenda(Venda venda) throws VendaNaoExisteException
    {
        if(repoVenda.historicoDeVendas().contains(venda))
        {
            repoVenda.removerVenda(venda);
        }
        else
        {
            throw new VendaNaoExisteException();
        }
    }
    @Override
    public void atualizarVenda(Venda venda, Cliente cliente, ArrayList<LivroVendido> livros)
                                throws VendaInvalidaException, UsuarioInexistenteException, ListaDeLivrosVaziaException
    {
        if(!(venda == null))
        {
            if(venda.getCliente() != null)
            {
                if (!livros.isEmpty())
                {
                    repoVenda.atualizarVenda(venda, cliente, venda.getDataEHora(), livros);

                } else throw new ListaDeLivrosVaziaException();
            } else
            {
                throw new UsuarioInexistenteException(cliente.getCpf());
            }
            /*{
                throw new UsuarioNuloException();
            } */
        } else throw new VendaInvalidaException();
    }
    @Override
    public List<Venda> historicoDeVendas() throws HistoricoVazioException
    {
        List<Venda> historicoInterno;

        if (!repoVenda.historicoDeVendas().isEmpty())
        {
            historicoInterno = repoVenda.historicoDeVendas();
            System.out.println(historicoInterno.toString());
        }
        else
        {
            throw new HistoricoVazioException();
        }

        return historicoInterno;
    }

    @Override
    public List<Venda> HistoricoDeVendasPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return repoVenda.HistoricoDeVendasPorPeriodo(dataInicio, dataFim);
    }


    @Override
    public List<Venda> historicoDeComprasDoCliente(Cliente cliente) throws UsuarioNuloException {
        List<Venda> historicoInterno;

        if(cliente != null)
        {
            historicoInterno = repoVenda.historicoDeComprasDoUsuario(cliente);
        }
        else
        {
            throw new UsuarioNuloException();
        }
        return historicoInterno;
    }
    @Override
    public List<Cliente> listarMelhoresClientesPorCompra() throws HistoricoVazioException
    {
        Map<Cliente, Integer> clienteCompra = new HashMap<>();

        if(!repoVenda.listarVendas().isEmpty())
        {
            for (int i = listarVendas().size() -1; i >= 0; i--)
            {
                clienteCompra.put(repoVenda.listarVendas().get(i).getCliente()
                        , repoVenda.listarVendas().get(i).getLivrosVendidos().size());
            }
            List<Cliente> listaInterna = clienteCompra.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            return listaInterna;
        }
        else
        {
            throw new HistoricoVazioException();
        }
    }
    @Override
    public List<Cliente> listarMelhoresClientesPorGasto() throws HistoricoVazioException
    {
        if(!repoVenda.listarVendas().isEmpty())
        {
            Map<Cliente, Double> clienteGasto = new HashMap<>();

            for (int i = 0; i < repoVenda.listarVendas().size(); i++)
            {
                clienteGasto.put(repoVenda.listarVendas().get(i).getCliente()
                        , repoVenda.listarVendas().get(i).calcularTotal());
            }

            List<Cliente> listaInterna = clienteGasto.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            return listaInterna;
        }
        else
        {
            throw new HistoricoVazioException();
        }
    }

    @Override
    public List<Venda> listarVendasPorFornecedor(String nomeFornecedor, LocalDateTime dataInicio,
                                                 LocalDateTime dataFim) throws HistoricoVazioException {
        List<Venda> lista = repoVenda.listarVendasPorFornecedor(nomeFornecedor, dataInicio, dataFim);
        if(lista.isEmpty()){
            throw new HistoricoVazioException();
        }
        return lista;
    }
}
