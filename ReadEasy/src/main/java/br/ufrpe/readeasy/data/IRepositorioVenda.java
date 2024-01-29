package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.LivroVendido;
import br.ufrpe.readeasy.beans.Venda;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

    public interface IRepositorioVenda {

        void inserirVenda(Venda venda);

        void removerVenda(Venda venda);

        void atualizarVenda(Venda venda, Cliente cliente, LocalDateTime dataHora, ArrayList<LivroVendido> livros);

        List<Venda> listarVendas();

        List<Venda> historicoDeVendas();

        List<Venda> HistoricoDeVendasPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim);

        List<Cliente> listarMelhoresClientesPorCompra(Map<Cliente, Integer> map);

        ArrayList<Venda> historicoDeComprasDoUsuario(Cliente cliente);

        List<Cliente> listarMelhoresClientesPorGasto(Map<Cliente, Double> clienteGasto);

        List<Venda> listarVendasPorFornecedor(String nomeFornecedor, LocalDateTime dataInicio, LocalDateTime dataFim);
    }
