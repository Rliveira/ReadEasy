package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.beans.LivroVendido;
import br.ufrpe.readeasy.beans.Venda;
import br.ufrpe.readeasy.exceptions.HistoricoVazioException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


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

    @Override
    public Map<Cliente, Integer> listarMelhoresClientesPorCompra() throws HistoricoVazioException {
        Map<Cliente, Integer> clienteCompra = new HashMap<>();

        if (!vendas.isEmpty()) {
            for (Venda venda : vendas) {
                clienteCompra.put(venda.getCliente(), clienteCompra.getOrDefault(venda.getCliente(), 0) + 1);
            }

            return clienteCompra.entrySet()
                    .stream()
                    .sorted(Map.Entry.<Cliente, Integer>comparingByValue().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        } else {
            throw new HistoricoVazioException();
        }
    }

    @Override
    public Map<Cliente, Double> listarMelhoresClientesPorGasto() throws HistoricoVazioException {
        Map<Cliente, Double> clienteGasto = new HashMap<>();

        if (!vendas.isEmpty()) {
            for (Venda venda : vendas) {
                Cliente cliente = venda.getCliente();
                double totalGasto = clienteGasto.getOrDefault(cliente, 0.0) + venda.calcularTotal();
                clienteGasto.put(cliente, totalGasto);
            }

            return clienteGasto.entrySet()
                    .stream()
                    .sorted(Map.Entry.<Cliente, Double>comparingByValue().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        } else {
            throw new HistoricoVazioException();
        }
    }
    @Override
    public Map<Livro, Integer> ranquearLivrosMaisVendidosEntreDatas(LocalDateTime dataEHoraInicio,
                                                                    LocalDateTime dataEHoraFim){
        List<LivroVendido> livrosVendidosNoIntervalo = listarLivrosVendidosEntreDatas(dataEHoraInicio, dataEHoraFim);
        Map<Livro, Integer> ranking = new HashMap<>();

        for (LivroVendido livroVendido : livrosVendidosNoIntervalo) {
            Livro livro = livroVendido.getLivro();
            ranking.put(livro, ranking.getOrDefault(livro, 0) + livroVendido.getQuantidade());
        }

        Map<Livro, Integer> rankingOrdenado = ranking.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return rankingOrdenado;
    }

    @Override
    public int calcularTotalLivrosVendidosEntreDatas(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim) {
        List<LivroVendido> livrosVendidosNoIntervalo = listarLivrosVendidosEntreDatas(dataEHoraInicio, dataEHoraFim);
        int totalLivrosVendidos = 0;

        for (LivroVendido livroVendido : livrosVendidosNoIntervalo) {
            totalLivrosVendidos += livroVendido.getQuantidade();
        }

        return totalLivrosVendidos;
    }
    @Override
    public double calcularTotalLucroEntreDatas(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim) {
        List<LivroVendido> livrosVendidosNoIntervalo = listarLivrosVendidosEntreDatas(dataEHoraInicio, dataEHoraFim);
        double totalLucro = 0.0;

        for (LivroVendido livroVendido : livrosVendidosNoIntervalo) {
            int quantidade = livroVendido.getQuantidade();
            double precoUnitario = livroVendido.getLivro().getPreco();
            double lucroDaVenda = quantidade * precoUnitario;
            totalLucro += lucroDaVenda;
        }

        return totalLucro;
    }

    private List<LivroVendido> listarLivrosVendidosEntreDatas(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim){
        List<LivroVendido> livrosVendidosNoIntervalo = new ArrayList<>();

        for (Venda venda : vendas) {
            LocalDateTime dataEHoraDaVenda = venda.getDataEHora();

            if (dataEHoraDaVenda.isEqual(dataEHoraInicio) && dataEHoraDaVenda.isEqual(dataEHoraFim) &&
            (dataEHoraDaVenda.isAfter(dataEHoraInicio) && dataEHoraDaVenda.isBefore(dataEHoraFim))) {

                for (LivroVendido livroVendido : venda.getLivrosVendidos()) {
                    livrosVendidosNoIntervalo.add(livroVendido);
                }
            }
        }
        return livrosVendidosNoIntervalo;
    }
}
