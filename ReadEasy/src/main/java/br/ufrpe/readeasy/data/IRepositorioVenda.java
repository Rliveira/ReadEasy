package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.beans.LivroVendido;
import br.ufrpe.readeasy.beans.Venda;
import br.ufrpe.readeasy.exceptions.HistoricoVazioException;

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

        ArrayList<Venda> historicoDeComprasDoUsuario(Cliente cliente);

        Map<Cliente, Integer> listarMelhoresClientesPorCompra() throws HistoricoVazioException;

        Map<Cliente, Double> listarMelhoresClientesPorGasto() throws HistoricoVazioException;

        Map<Livro, Integer> ranquearLivrosMaisVendidosEntreDatas(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim);

        int calcularTotalLivrosVendidosEntreDatas(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim);

        double calcularTotalLucroEntreDatas(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim);
    }
