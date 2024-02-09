package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.beans.LivroVendido;
import br.ufrpe.readeasy.beans.Venda;
import br.ufrpe.readeasy.exceptions.HistoricoVazioException;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class RepositorioVenda implements IRepositorioVenda, Serializable
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
            instance = lerDoArquivo();
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
    public List<Venda> HistoricoDeVendasPorPeriodo(LocalDate dataDeInicio, LocalDate dataDeFim)
    {
        if (dataDeInicio == null)
        {
            dataDeInicio = LocalDate.MIN;
        }
        if (dataDeFim == null)
        {
            dataDeFim = LocalDate.now();
        }

        LocalDateTime dataInicio = dataDeInicio.atStartOfDay();
        LocalDateTime dataFim = dataDeFim.atTime(23,59,59,59);

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

            if (dataEHoraDaVenda.isEqual(dataEHoraInicio) || dataEHoraDaVenda.isEqual(dataEHoraFim) ||
                    (dataEHoraDaVenda.isAfter(dataEHoraInicio) && dataEHoraDaVenda.isBefore(dataEHoraFim))) {

                for (LivroVendido livroVendido : venda.getLivrosVendidos()) {
                    livrosVendidosNoIntervalo.add(livroVendido);
                }
            }
        }
        return livrosVendidosNoIntervalo;
    }

    @Override
    public int calcularTotalDeVendasDiarias(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim){
        int totalVendas = 0;

        for (Venda venda : vendas) {
            LocalDateTime dataEHoraDaVenda = venda.getDataEHora();

            if (dataEHoraDaVenda.isEqual(dataEHoraInicio) || dataEHoraDaVenda.isEqual(dataEHoraFim) ||
                    (dataEHoraDaVenda.isAfter(dataEHoraInicio) && dataEHoraDaVenda.isBefore(dataEHoraFim))) {
                totalVendas++;
            }
        }

        return  totalVendas;
    }


    @Override
    public Map<LocalDate, Integer> listarNumeroDeLivrosVendidosPorData(LocalDateTime dataEHoraInicio,
                                                                       LocalDateTime dataEHoraFim){
        Map<LocalDate, Integer> totalLivrosPorData = new HashMap<>();
        LocalDate datainicio = dataEHoraInicio.toLocalDate();

        // Inicializa o mapa com todas as datas do intervalo e valores 0
        while (!datainicio.isAfter(dataEHoraFim.toLocalDate())) {
            totalLivrosPorData.put(datainicio, 0);
            datainicio = datainicio.plusDays(1);
        }

        for (Venda venda : vendas){
            LocalDateTime dataEHoraDaVenda = venda.getDataEHora();

            if (dataEHoraDaVenda.isEqual(dataEHoraInicio) || dataEHoraDaVenda.isEqual(dataEHoraFim) ||
                    (dataEHoraDaVenda.isAfter(dataEHoraInicio) && dataEHoraDaVenda.isBefore(dataEHoraFim))) {

                for (LivroVendido livroVendido : venda.getLivrosVendidos()) {
                    LocalDate dataDaVenda = dataEHoraDaVenda.toLocalDate();
                    totalLivrosPorData.put(dataDaVenda, totalLivrosPorData.getOrDefault(dataDaVenda, 0)
                            + livroVendido.getQuantidade());
                }
            }
        }
        return totalLivrosPorData;
    }

    @Override
    public Map<LocalDate, Integer> listarNumeroDeVendasPorData(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim) {
        Map<LocalDate, Integer> totalVendasPorData = new HashMap<>();
        LocalDate datainicio = dataEHoraInicio.toLocalDate();

        // Inicializa o mapa com todas as datas do intervalo e valores 0
        while (!datainicio.isAfter(dataEHoraFim.toLocalDate())) {
            totalVendasPorData.put(datainicio, 0);
            datainicio = datainicio.plusDays(1);
        }

        for (Venda venda : vendas) {
            LocalDateTime dataEHoraDaVenda = venda.getDataEHora().toLocalDate().atStartOfDay();

            if ((dataEHoraDaVenda.isEqual(dataEHoraInicio) || dataEHoraDaVenda.isEqual(dataEHoraFim) ||
                    (dataEHoraDaVenda.isAfter(dataEHoraInicio) && dataEHoraDaVenda.isBefore(dataEHoraFim)))) {
                totalVendasPorData.put(dataEHoraDaVenda.toLocalDate(), totalVendasPorData.getOrDefault(dataEHoraDaVenda.toLocalDate(), 0) + 1);
            }
        }
        return totalVendasPorData;
    }

    @Override
    public Map<LocalDate, Double> listarLucroPorData(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim) {
        Map<LocalDate, Double> totalLucroPorData = new HashMap<>();
        LocalDate datainicio = dataEHoraInicio.toLocalDate();

        // Inicializa o mapa com todas as datas do intervalo e valores 0
        while (!datainicio.isAfter(dataEHoraFim.toLocalDate())) {
            totalLucroPorData.put(datainicio, 0.0);
            datainicio = datainicio.plusDays(1);
        }

        for (Venda venda : vendas) {
            LocalDateTime dataEHoraDaVenda = venda.getDataEHora();

            if (dataEHoraDaVenda.isEqual(dataEHoraInicio) || dataEHoraDaVenda.isEqual(dataEHoraFim) ||
                    (dataEHoraDaVenda.isAfter(dataEHoraInicio) && dataEHoraDaVenda.isBefore(dataEHoraFim))) {
                double lucroDaVenda = venda.calcularTotal();
                totalLucroPorData.put(dataEHoraDaVenda.toLocalDate(), totalLucroPorData.getOrDefault(dataEHoraDaVenda.toLocalDate(), 0.0) + lucroDaVenda);
            }
        }
        return totalLucroPorData;
    }

    private static RepositorioVenda lerDoArquivo() {
        RepositorioVenda instanciaLocal = null;

        File in = new File("RepoVenda.dat");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(in);
            ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            instanciaLocal = (RepositorioVenda) o;
        } catch (Exception e) {
            instanciaLocal = new RepositorioVenda();
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
        File out = new File("RepoVenda.dat");
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
