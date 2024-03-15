package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.data.IRepositorioVenda;
import br.ufrpe.readeasy.data.RepositorioVenda;
import br.ufrpe.readeasy.exceptions.DataInvalidaException;
import br.ufrpe.readeasy.exceptions.HistoricoVazioException;
import br.ufrpe.readeasy.exceptions.ListaDeLivrosVaziaException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public void inserirVenda(Venda venda)
    {
        repoVenda.inserirVenda(venda);
        repoVenda.salvarArquivo();
    }

    @Override
    public void removerVenda(Venda venda)
    {
        repoVenda.removerVenda(venda);
        repoVenda.salvarArquivo();
    }

    @Override
    public void atualizarVenda(Venda venda, Cliente cliente, ArrayList<LivroVendido> livros)
            throws ListaDeLivrosVaziaException
    {
        if (!livros.isEmpty())
        {
            repoVenda.atualizarVenda(venda, cliente, venda.getDataEHora(), livros);
            repoVenda.salvarArquivo();

        } else throw new ListaDeLivrosVaziaException();
    }

    @Override
    public List<Venda> historicoDeVendas() throws HistoricoVazioException
    {
        List<Venda> historicoInterno;

        if (!repoVenda.historicoDeVendas().isEmpty())
        {
            historicoInterno = repoVenda.historicoDeVendas();
        }
        else
        {
            throw new HistoricoVazioException();
        }

        return historicoInterno;
    }

    @Override
    public List<Venda> HistoricoDeVendasPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return repoVenda.HistoricoDeVendasPorPeriodo(dataInicio, dataFim);
    }

    @Override
    public List<CompraClienteDTO> historicoDeComprasDoCliente(Cliente cliente, LocalDate dataInicio, LocalDate dataFim) throws DataInvalidaException {
        if (dataInicio.isAfter(dataFim) || dataInicio.isAfter(LocalDate.now()) || dataFim.isAfter(LocalDate.now()) ) {
            throw new DataInvalidaException("Data de início' ou 'Data de fim' inválida(s)");
        }
        return repoVenda.historicoDeComprasDoCliente(cliente, dataInicio, dataFim);
    }

    @Override
    public List<CompraClienteDTO> listarComprasDTO(Cliente cliente) {
        List<CompraClienteDTO> historicoCompras = new ArrayList<>();

        // obtém o histórico de compras do cliente
        List<Venda> historicoVendas = repoVenda.historicoDeVendas();

        // converte as vendas do histórico para objetos CompraDTO
        for (Venda venda : historicoVendas) {
            for (LivroVendido livroVendido : venda.getLivrosVendidos()) {
                Livro livro = livroVendido.getLivro();
                CompraClienteDTO compraClienteDTO = new CompraClienteDTO(livro.getTitulo(), livro.getAutor(), livroVendido.getQuantidade(),
                        livroVendido.getQuantidade(), venda.getDataEHora());
                historicoCompras.add(compraClienteDTO);
            }
        }

        return historicoCompras;
    }

    @Override
    public List<VendaLivrariaDTO> listarVendasLivrariaDTO(LocalDate dataInicio, LocalDate dataFim) throws DataInvalidaException{
        List<VendaLivrariaDTO> historicoCompras = new ArrayList<>();
        if (dataInicio == null){
            dataInicio = LocalDate.MIN;
        }
        if (dataFim == null) {
            dataFim = LocalDate.now();
        }

        if (dataInicio.isAfter(dataFim) || dataInicio.isAfter(LocalDate.now()) || dataFim.isAfter(LocalDate.now()) ) {
            throw new DataInvalidaException("Data de início' ou 'Data de fim' inválida(s)");
        }

        // obtém o histórico de compras do cliente
        List<Venda> historicoVendas = repoVenda.HistoricoDeVendasPorPeriodo(dataInicio, dataFim);

        // converte as vendas do histórico para objetos CompraDTO
        for (Venda venda : historicoVendas) {
            for (LivroVendido livroVendido : venda.getLivrosVendidos()) {
                Livro livro = livroVendido.getLivro();
                LocalDate dataVenda = venda.getDataEHora().toLocalDate();
                VendaLivrariaDTO vendaLivrariaDTO = new VendaLivrariaDTO(livro.getTitulo(), livro.getAutor(), livro.getFornecedor().getNome(),
                        venda.getCliente().getNome(), livroVendido.getQuantidade(), dataVenda, livro.getPreco());
                historicoCompras.add(vendaLivrariaDTO);
            }
        }
        return historicoCompras;
    }

    @Override
    public Map<String, Integer> ranquearClientesPorQuantidadeDeCompraEntreDatas(LocalDate dataInicio, LocalDate dataFim) throws HistoricoVazioException {
        return repoVenda.ranquearClientesPorQuantidadeDeCompraEntreDatas(dataInicio, dataFim);
    }

    @Override
    public Map<String, Double> raquearClientesPorGastoEntreDatas(LocalDate dataInicio, LocalDate dataFim) throws HistoricoVazioException {
        return repoVenda.raquearClientesPorGastoEntreDatas(dataInicio, dataFim);
    }

    @Override
    public Map<Livro, Integer> ranquearLivrosMaisVendidosEntreDatas(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim) {
        return repoVenda.ranquearLivrosMaisVendidosEntreDatas(dataEHoraInicio, dataEHoraFim);
    }

    @Override
    public int calcularTotalLivrosVendidosEntreDatas(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim) {
        return repoVenda.calcularTotalLivrosVendidosEntreDatas(dataEHoraInicio, dataEHoraFim);
    }

    @Override
    public int calcularTotalDeVendasDiarias(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim){
        return repoVenda.calcularTotalDeVendasDiarias(dataEHoraInicio, dataEHoraFim);
    }

    @Override
    public Map<LocalDate, Integer> listarLivrosVendidosPorData(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim){
        return repoVenda.listarNumeroDeLivrosVendidosPorData(dataEHoraInicio, dataEHoraFim);
    }

    @Override
    public Map<LocalDate, Integer> listarVendasPorData(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim){
        return repoVenda.listarNumeroDeVendasPorData(dataEHoraInicio, dataEHoraFim);
    }

    @Override
    public Map<LocalDate, Double> listarLucroPorData(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim){
        return repoVenda.listarLucroPorData(dataEHoraInicio, dataEHoraFim);
    }
}
