package br.ufrpe.readeasy.data;

public class IRepositorioVenda
{
    boolean inserirVenda(Venda venda);
    boolean removerVenda(Venda venda);
    void atualizarVenda(Cliente cliente, LocalDateTime dataHora, Arraylist<LivroVendido> livros);
    ArrayList<Venda> historicoDeVendas;

    Arraylist<Venda> listarMelhoresClientes;
    Arraylist<Venda> historicoDeComprasDoUsuario(Cliente cliente);
}