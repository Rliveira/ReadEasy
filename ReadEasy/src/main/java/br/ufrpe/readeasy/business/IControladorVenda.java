package br.ufrpe.readeasy.business;


import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.beans.LivroVendido;
import br.ufrpe.readeasy.beans.Venda;
import br.ufrpe.readeasy.exceptions.*;

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
    List<Venda> HistoricoDeVendasPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim);
    List<Venda> listarVendas();
    List<Venda> historicoDeComprasDoCliente(Cliente cliente) throws UsuarioNuloException;
    Map<Cliente, Integer> listarMelhoresClientesPorCompra() throws HistoricoVazioException;
    Map<Cliente, Double> listarMelhoresClientesPorGasto() throws HistoricoVazioException;
    Map<Livro, Integer> ranquearLivrosMaisVendidosEntreDatas(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim);
    int calcularTotalLivrosVendidosEntreDatas(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim);
    double calcularTotalLucroEntreDatas(LocalDateTime dataEHoraInicio, LocalDateTime dataEHoraFim);
}

