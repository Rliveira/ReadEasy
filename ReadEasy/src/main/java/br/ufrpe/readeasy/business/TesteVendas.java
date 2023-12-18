package br.ufrpe.readeasy.business;

import java.time.LocalDate;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.data.IRepositorioVenda;
import br.ufrpe.readeasy.data.RepositorioVenda;
import br.ufrpe.readeasy.exceptions.HistoricoVazioException;
import br.ufrpe.readeasy.exceptions.UsuarioNuloException;
import br.ufrpe.readeasy.exceptions.VendaInvalidaException;

import java.util.List;

public class TesteVendas
{
    public static void main(String[] args) throws VendaInvalidaException, UsuarioNuloException, HistoricoVazioException {
        IControladorVenda controladorVenda = new ControladorVenda();
        IRepositorioVenda repoVenda = new RepositorioVenda();

        Endereco end1 = new Endereco(18460154, "Rua dos Bobos", "Jardel", "Londres", "CE");
        Endereco end2 = new Endereco(18460182, "Rua Bom Jesus", "Recife Antigo", "Recife", "RJ");
        Endereco end3 = new Endereco(17520154, "Rua da Aurora", "Graças", "Maraial", "RN");
        Endereco end4 = new Endereco(18469874, "Rua Solidão", "Pau Amarelo", "Paulista", "SP");

        Cliente cliente1 = new Cliente("Rony", "123456789", LocalDate.of(2001, 7,17),
                "rony.oliveira", "123batata", end1, "40028922");
        Cliente cliente2 = new Cliente("Louise", "78942135" , LocalDate.of(2000, 8,16),
                "luluzinha123", "churros7878", end2, "54965135");
        Cliente cliente3 = new Cliente("Mariane", "5498751324", LocalDate.of(2003, 12,17),
                "marimari", "1717829", end3, "60608677");
        Cliente cliente4 = new Cliente("João", "979798453", LocalDate.of(1998, 2,1),
                "jv.moraes", "tlou69", end4, "40028922");

        Fornecedor fornecedor1 = new Fornecedor("Sarava", "1235498", LocalDate.now(), "sarava1",
                "livroslivros", end1, "12354657", TipoFornecedor.ATACADISTAS);
        Fornecedor fornecedor2 = new Fornecedor("Kabum", "1235498", LocalDate.now(), "ska12311",
                "livros123", end3, "12354657", TipoFornecedor.DISTRIBUIDORA_DE_LIVRO);
        Fornecedor fornecedor3 = new Fornecedor("Americanas", "12122398", LocalDate.now(), "americabbb1",
                "livros321", end4, "12354657", TipoFornecedor.EDITORA);

        Livro livro1 = new Livro("Yuyu Hakusho", "Yoshihiro Togashi", 29.99, fornecedor1);
        Livro livro2 = new Livro("Dragon Ball", "Akira Toriyama", 79.50, fornecedor2);
        Livro livro3 = new Livro("Dom Casmurro", "Machado de Assis", 50, fornecedor3);
        Livro livro4 = new Livro("Racismo Estrutural", "Silvio Almeida", 95.50, fornecedor2);

        Venda venda1 = new Venda(cliente2);
        venda1.adicionarLivro(livro1, 5);
        venda1.adicionarLivro(livro2, 2);
        venda1.adicionarLivro(livro4, 3);

        Venda venda2 = new Venda(cliente2);
        venda2.adicionarLivro(livro1, 3);


        Venda venda3 = new Venda(cliente2);
        venda3.adicionarLivro(livro4, 5);
        venda3.adicionarLivro(livro3, 2);


        Venda venda4 = new Venda(cliente4);
        venda4.adicionarLivro(livro1, 5);
        venda4.adicionarLivro(livro2, 1);
        venda4.adicionarLivro(livro4, 3);

        controladorVenda.inserirVenda(venda1);
        controladorVenda.inserirVenda(venda2);
        controladorVenda.inserirVenda(venda3);
        controladorVenda.inserirVenda(venda4);

        controladorVenda.historicoDeVendas();

        List<Cliente> topClientes = controladorVenda.listarMelhoresClientesPorCompra();

        for(int i=0; i< topClientes.size(); i++)
        {
            System.out.println(topClientes.get(i).toString());
        }
    }


}
