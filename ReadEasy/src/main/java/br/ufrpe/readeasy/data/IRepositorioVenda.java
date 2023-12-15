package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.LivroVendido;
import br.ufrpe.readeasy.beans.Venda;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

    public interface IRepositorioVenda {

        public void inserirVenda(Venda venda);

        public void removerVenda(Venda venda);

        public void atualizarVenda(Venda venda, Cliente cliente, LocalDateTime dataHora, ArrayList<LivroVendido> livros);

        public ArrayList<Venda> listarVendas();

        public ArrayList<Venda> historicoDeVendas();

        public List<Cliente> topClientes(Map<Cliente, Integer> map);

        public ArrayList<Venda> historicoDeComprasDoUsuario(Cliente cliente);

    }
