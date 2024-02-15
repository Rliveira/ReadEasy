package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.data.IRepositorioVenda;
import br.ufrpe.readeasy.data.RepositorioVenda;
import br.ufrpe.readeasy.exceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
                repoVenda.salvarArquivo();
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
            repoVenda.salvarArquivo();
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
                    repoVenda.salvarArquivo();

                } else throw new ListaDeLivrosVaziaException();
            } else
            {
                throw new UsuarioInexistenteException(cliente.getCpf());
            }
        } else throw new VendaInvalidaException();
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
    public List<Venda> historicoDeComprasDoCliente(Cliente cliente)
    {
        List<Venda> historicoInterno= null;

        if(cliente != null)
        {
            historicoInterno = repoVenda.historicoDeComprasDoUsuario(cliente);
        }

        return historicoInterno;
    }

    @Override
    public List<CompraDTO> listarComprasDTO(Cliente cliente) {
        List<CompraDTO> historicoCompras = new ArrayList<>();

        // obtém o histórico de compras do cliente
        List<Venda> historicoVendas = repoVenda.historicoDeVendas();

        // converte as vendas do histórico para objetos CompraDTO
        for (Venda venda : historicoVendas) {
            for (LivroVendido livroVendido : venda.getLivrosVendidos()) {
                Livro livro = livroVendido.getLivro();
                CompraDTO compraDTO = new CompraDTO(livro.getTitulo(), livro.getAutor(), livroVendido.getQuantidade(),
                        livroVendido.getQuantidade(), venda.getDataEHora());
                historicoCompras.add(compraDTO);
            }
        }

        return historicoCompras;
    }

    @Override
    public List<VendaDTO> listarVendasLivrariaDTO(LocalDate dataInicio, LocalDate dataFim) {
        List<VendaDTO> historicoCompras = new ArrayList<>();

        // obtém o histórico de compras do cliente
        List<Venda> historicoVendas = repoVenda.HistoricoDeVendasPorPeriodo(dataInicio, dataFim);

        // converte as vendas do histórico para objetos CompraDTO
        for (Venda venda : historicoVendas) {
            for (LivroVendido livroVendido : venda.getLivrosVendidos()) {
                Livro livro = livroVendido.getLivro();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataVenda = venda.getDataEHora().toLocalDate();
                VendaDTO vendaDTO = new VendaDTO(livro.getTitulo(), livro.getAutor(), livro.getFornecedor().getNome(),
                        venda.getCliente().getNome(), livroVendido.getQuantidade(), dataVenda, livro.getPreco());
                historicoCompras.add(vendaDTO);
            }
        }
        return historicoCompras;
    }

    @Override
    public Map<Cliente, Integer> listarMelhoresClientesPorCompra() throws HistoricoVazioException {
        return repoVenda.listarMelhoresClientesPorCompra();
    }

    @Override
    public Map<Cliente, Double> listarMelhoresClientesPorGasto() throws HistoricoVazioException {
        return repoVenda.listarMelhoresClientesPorGasto();
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
    public double calcularTotalLucroEntreDatas(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim) {
        return repoVenda.calcularTotalLucroEntreDatas(dataEHoraInicio, dataEHoraFim);
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
