package br.ufrpe.readeasy.business;


import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.exceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IControladorVenda
{
    void inserirVenda(Venda venda) throws VendaInvalidaException, UsuarioNuloException;
    void removerVenda(Venda venda)throws VendaNaoExisteException;
    void atualizarVenda(Venda venda, Cliente cliente,  ArrayList<LivroVendido>
                        livros) throws VendaInvalidaException, UsuarioNuloException, UsuarioInexistenteException,
                        ListaDeLivrosVaziaException;
    List<Venda> historicoDeVendas() throws HistoricoVazioException;
    List<Venda> HistoricoDeVendasPorPeriodo(LocalDate dataInicio, LocalDate dataFim);
    List<Venda> listarVendas();
    List<Venda> historicoDeComprasDoCliente(Cliente cliente) throws UsuarioNuloException;

    List<VendaDTO> listarVendasLivrariaDTO(LocalDate dataInicio, LocalDate dataFim);

    Map<Cliente, Integer> listarMelhoresClientesPorCompra() throws HistoricoVazioException;
    Map<Cliente, Double> listarMelhoresClientesPorGasto() throws HistoricoVazioException;
    Map<Livro, Integer> ranquearLivrosMaisVendidosEntreDatas(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim);
    int calcularTotalLivrosVendidosEntreDatas(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim);
    double calcularTotalLucroEntreDatas(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim);
    List<CompraDTO> listarComprasDTO(Cliente cliente);

    int calcularTotalDeVendasDiarias(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim);

    Map<LocalDate, Integer> listarLivrosVendidosPorData(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim);

    Map<LocalDate, Double> listarLucroPorData(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim);

    Map<LocalDate, Integer> listarVendasPorData(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim);


}

