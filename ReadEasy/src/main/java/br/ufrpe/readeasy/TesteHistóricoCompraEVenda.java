package br.ufrpe.readeasy;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.business.ServidorReadEasy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TesteHistóricoCompraEVenda {
    public static void InicializarTesteHistóricoCompraEVenda() {
        System.out.println("TesteHistóricoCompraEVenda");
        ServidorReadEasy servidor = ServidorReadEasy.getInstance();
        Fornecedor fornecedor = new Fornecedor("Fornecedor 1", "1234567890",
                LocalDate.of(2020, 1, 1), "forn", "1234",
                new Endereco(12345678, "Rua 1", "Bairro 1", "Cidade 1", "PE"),
                "12312314", TipoFornecedor.EDITORA);
        Fornecedor fornecedor2 = new Fornecedor("Fornecedor 2", "1234567890",
                LocalDate.of(2020, 1, 1), "forn", "1234",
                new Endereco(12345678, "Rua 1", "Bairro 1", "Cidade 1", "PE"),
                "12312314", TipoFornecedor.DISTRIBUIDORA_DE_LIVRO);
        try{
            servidor.adicionarLivro(new Livro("O Pequeno Príncipe", "Antoine de Saint-Exupéry", 20.00, fornecedor));
            servidor.adicionarLivro(new Livro("To Kill a Mockingbird", "Harper Lee", 24.99, fornecedor2));
            servidor.adicionarLivro(new Livro("1984", "George Orwell", 19.99, fornecedor));
            servidor.adicionarLivro(new Livro("The Great Gatsby", "F. Scott Fitzgerald", 29.99, fornecedor));
            servidor.adicionarLivro(new Livro("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 34.99, fornecedor2));
            servidor.adicionarLivro(new Livro("The Hobbit", "J.R.R. Tolkien", 22.99, fornecedor2));

            servidor.aumentarQuantidadeEmEstoque(servidor.buscarLivroPorNome("O Pequeno Príncipe"),
                    10, LocalDate.of(2020, 1, 1));
            servidor.aumentarQuantidadeEmEstoque(servidor.buscarLivroPorNome("To Kill a Mockingbird"),
                    19, LocalDate.of(2020, 2, 13));
            servidor.aumentarQuantidadeEmEstoque(servidor.buscarLivroPorNome("1984"),
                    20, LocalDate.of(2020, 3, 15));

            servidor.aumentarQuantidadeEmEstoque(servidor.buscarLivroPorNome("The Great Gatsby"), 5,
                    LocalDate.of(2020, 4, 1));
            servidor.aumentarQuantidadeEmEstoque(servidor.buscarLivroPorNome("Harry Potter and the Sorcerer's Stone"),
                    15, LocalDate.of(2020, 5, 1));

            servidor.aumentarQuantidadeEmEstoque(servidor.buscarLivroPorNome("The Hobbit"), 3,
                    LocalDate.of(2020, 6, 1));

            servidor.cadastrarUsuario(new Cliente("Cliente 1", "1234567890",
                    LocalDate.of(1990, 1, 1), "cli", "1234",
                    new Endereco(12345678, "Rua 1", "Bairro 1", "Cidade 1", "PE"),
                    "12312314"));
            servidor.cadastrarUsuario(new Cliente("Cliente 2", "1234512390", LocalDate.of(1990, 1, 1),
                    "cli", "1234", new Endereco(12345678, "Rua 1", "Bairro 1", "Cidade 1", "PE"),
                    "12312314"));

            Venda venda1 = new Venda((Cliente)servidor.procurarUsuario("1234567890"));
            LivroVendido livroVendido1 = new LivroVendido(servidor.buscarLivroPorNome("O Pequeno Príncipe"), 2);
            LivroVendido livroVendido2 = new LivroVendido(servidor.buscarLivroPorNome("To Kill a Mockingbird"), 3);
            LivroVendido livroVendido3 = new LivroVendido(servidor.buscarLivroPorNome("1984"), 1);

            servidor.inserirVenda(venda1);
            servidor.atualizarVenda(venda1, (Cliente)servidor.procurarUsuario("1234567890"), new ArrayList<LivroVendido>(List.of(livroVendido1, livroVendido3)));

            Venda venda2 = new Venda((Cliente)servidor.procurarUsuario("1234512390"));
            LivroVendido livroVendido4 = new LivroVendido(servidor.buscarLivroPorNome("The Great Gatsby"), 1);
            LivroVendido livroVendido5 = new LivroVendido(servidor.buscarLivroPorNome("Harry Potter and the Sorcerer's Stone"), 2);
            servidor.inserirVenda(venda2);
            servidor.atualizarVenda(venda2, (Cliente)servidor.procurarUsuario("1234512390"), new ArrayList<LivroVendido>(List.of(livroVendido2,livroVendido4, livroVendido5)));

            System.out.println("Histórico de vendas:");

        }  catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
