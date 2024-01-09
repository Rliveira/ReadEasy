package br.ufrpe.readeasy.business;


import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.LivroVendido;
import br.ufrpe.readeasy.beans.Venda;
import br.ufrpe.readeasy.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public interface IControladorVenda
{
    void inserirVenda(Venda venda) throws VendaInvalidaException, UsuarioNuloException;
    void removerVenda(Venda venda)throws VendaNaoExisteException;
    void atualizarVenda(Venda venda, Cliente cliente,  ArrayList<LivroVendido>
                        livros) throws VendaInvalidaException, UsuarioNuloException, UsuarioInexistenteException,
                        ListaDeLivrosVaziaException;
    List<Venda> historicoDeVendas() throws HistoricoVazioException;
    List<Venda> listarVendas();
    List<Venda> historicoDeComprasDoCliente(Cliente cliente) throws UsuarioNuloException;
    List<Cliente> listarMelhoresClientesPorCompra() throws HistoricoVazioException;
    List<Cliente> listarMelhoresClientesPorGasto() throws HistoricoVazioException;

}

