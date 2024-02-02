package br.ufrpe.readeasy.business;

import java.text.DecimalFormat;
import java.time.LocalDate;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.data.IRepositorioVenda;
import br.ufrpe.readeasy.data.RepositorioVenda;
import br.ufrpe.readeasy.exceptions.HistoricoVazioException;
import br.ufrpe.readeasy.exceptions.UsuarioNuloException;
import br.ufrpe.readeasy.exceptions.VendaInvalidaException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        LocalDate data1 = LocalDate.of(2023, 3, 15);
        LocalDate data2 = LocalDate.of(2023, 4, 20);
        LocalDate data3 = LocalDate.of(2023, 5, 10);
        LocalDate data4 = LocalDate.of(2023, 6, 5);
        LocalDate data5 = LocalDate.of(2023, 7, 12);
        LocalDate data6 = LocalDate.of(2023, 8, 28);
        LocalDate data7 = LocalDate.of(2023, 9, 9);
        LocalDate data8 = LocalDate.of(2023, 10, 16);
        LocalDate data9 = LocalDate.of(2023, 11, 22);
        LocalDate data10 = LocalDate.of(2023, 12, 8);
        LocalDate data11 = LocalDate.of(2024, 1, 5);
        LocalDate data12 = LocalDate.of(2024, 2, 18);

        LocalDateTime dataEhora1 = LocalDateTime.of(data1, LocalDateTime.now().toLocalTime());
        LocalDateTime dataEhora2 = LocalDateTime.of(data2, LocalDateTime.now().toLocalTime());
        LocalDateTime dataEhora3 = LocalDateTime.of(data3, LocalDateTime.now().toLocalTime());
        LocalDateTime dataEhora4 = LocalDateTime.of(data4, LocalDateTime.now().toLocalTime());
        LocalDateTime dataEhora5 = LocalDateTime.of(data5, LocalDateTime.now().toLocalTime());
        LocalDateTime dataEhora6 = LocalDateTime.of(data6, LocalDateTime.now().toLocalTime());
        LocalDateTime dataEhora7 = LocalDateTime.of(data7, LocalDateTime.now().toLocalTime());
        LocalDateTime dataEhora8 = LocalDateTime.of(data8, LocalDateTime.now().toLocalTime());
        LocalDateTime dataEhora9 = LocalDateTime.of(data9, LocalDateTime.now().toLocalTime());
        LocalDateTime dataEhora10 = LocalDateTime.of(data10, LocalDateTime.now().toLocalTime());
        LocalDateTime dataEhora11 = LocalDateTime.of(data11, LocalDateTime.now().toLocalTime());
        LocalDateTime dataEhora12 = LocalDateTime.of(data12, LocalDateTime.now().toLocalTime());

        Venda venda1 = new Venda(cliente2, dataEhora1);
        venda1.adicionarLivro(livro1, 5);
        venda1.adicionarLivro(livro2, 2);
        venda1.adicionarLivro(livro4, 3);

        Venda venda2 = new Venda(cliente2, dataEhora2);
        venda2.adicionarLivro(livro1, 3);

        Venda venda3 = new Venda(cliente2, dataEhora3);
        venda3.adicionarLivro(livro4, 5);
        venda3.adicionarLivro(livro3, 2);

        Venda venda4 = new Venda(cliente4, dataEhora4);
        venda4.adicionarLivro(livro1, 5);
        venda4.adicionarLivro(livro3, 1);
        venda4.adicionarLivro(livro4, 3);

        Venda venda5 = new Venda(cliente4, dataEhora5);
        venda5.adicionarLivro(livro1, 2);
        venda5.adicionarLivro(livro2, 9);
        venda5.adicionarLivro(livro4, 3);

        Venda venda6 = new Venda(cliente4, dataEhora6);
        venda6.adicionarLivro(livro1, 5);
        venda6.adicionarLivro(livro2, 7);
        venda6.adicionarLivro(livro4, 2);

        Venda venda7 = new Venda(cliente4, dataEhora7);
        venda7.adicionarLivro(livro3, 11);
        venda7.adicionarLivro(livro2, 1);
        venda7.adicionarLivro(livro4, 6);

        Venda venda8 = new Venda(cliente4, dataEhora8);
        venda8.adicionarLivro(livro1, 21);
        venda8.adicionarLivro(livro3, 1);
        venda8.adicionarLivro(livro4, 7);

        Venda venda9 = new Venda(cliente1,dataEhora9);
        venda9.adicionarLivro(livro1, 2);
        venda9.adicionarLivro(livro2, 12);
        venda9.adicionarLivro(livro4, 3);

        Venda venda10 = new Venda(cliente1, dataEhora10);
        venda10.adicionarLivro(livro1, 1);
        venda10.adicionarLivro(livro2, 4);
        venda10.adicionarLivro(livro4, 1);

        Venda venda11 = new Venda(cliente3, dataEhora11);
        venda11.adicionarLivro(livro2, 10);
        venda11.adicionarLivro(livro3, 1);
        venda11.adicionarLivro(livro4, 3);

        Venda venda12 = new Venda(cliente3, dataEhora12);
        venda12.adicionarLivro(livro1, 4);
        venda12.adicionarLivro(livro2, 9);
        venda12.adicionarLivro(livro4, 3);

        controladorVenda.inserirVenda(venda1);
        controladorVenda.inserirVenda(venda2);
        controladorVenda.inserirVenda(venda3);
        controladorVenda.inserirVenda(venda4);
        controladorVenda.inserirVenda(venda5);
        controladorVenda.inserirVenda(venda6);
        controladorVenda.inserirVenda(venda7);
        controladorVenda.inserirVenda(venda8);
        controladorVenda.inserirVenda(venda9);
        controladorVenda.inserirVenda(venda10);
        controladorVenda.inserirVenda(venda11);
        controladorVenda.inserirVenda(venda12);

        controladorVenda.historicoDeVendas();

        Map<Cliente, Integer> rankingClientes = new HashMap<>();

        try {
            rankingClientes = controladorVenda.listarMelhoresClientesPorCompra();
        } catch (HistoricoVazioException e) {
            System.out.println("Não há histórico de vendas.");
        }

        System.out.println("\n\nRanking de Clientes por Quantidade de Compras:");
        for (Map.Entry<Cliente, Integer> entry : rankingClientes.entrySet()) {
            System.out.println("Cliente: " + entry.getKey().getNome() +
                    " - Quantidade de Compras: " + entry.getValue());
        }

        Map<Cliente, Double> rankingClientesMaisGastam = new HashMap<>();

        try {
            rankingClientesMaisGastam = controladorVenda.listarMelhoresClientesPorGasto();
        } catch (HistoricoVazioException e) {
            System.out.println("Não há histórico de vendas.");
        }

        System.out.println("\n\nRanking de Clientes por gasto:");
        DecimalFormat df = new DecimalFormat("#.##"); // Formato para dois dígitos após a vírgula
        for (Map.Entry<Cliente, Double> entry : rankingClientesMaisGastam.entrySet()) {
            String totalGastoFormatado = df.format(entry.getValue());
            System.out.println("Cliente: " + entry.getKey().getNome() +
                    " - total gasto: " + totalGastoFormatado);
        }

        int quantidadeLivros = controladorVenda.calcularTotalLivrosVendidosEntreDatas(dataEhora1,dataEhora12);
        System.out.println("\n\ntotal de livros vendidos: " + quantidadeLivros);

        double lucro = controladorVenda.calcularTotalLucroEntreDatas(dataEhora1,dataEhora12);
        String lucroFormatado = df.format(lucro);
        System.out.println("\n\ntotal de livro entre datas: " + lucroFormatado + "\n");

        Map<Livro, Integer> livrosranqueados = controladorVenda.ranquearLivrosMaisVendidosEntreDatas(dataEhora1, dataEhora12);
        for (Map.Entry<Livro, Integer> entry : livrosranqueados.entrySet()) {
            System.out.println("Livro: " + entry.getKey().getTitulo() +
                    " - Quantidade vendas: " + entry.getValue());
        }
    }


}

