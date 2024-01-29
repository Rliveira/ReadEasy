package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.exceptions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TesteLivros {
    public static void main(String[] args) throws RuntimeException {
        String data = "2001-08-23";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataNasc = LocalDate.parse(data, formatter);
        Endereco endereco1 = new Endereco(123456789, "Rua Fictícia, 123", "Jardim São Paulo" , "Recife", "Pernambuco");
        Endereco endereco2 = new Endereco(987654321, "Rua Fictícia, 456", "le blon" , "Rio de Janeiro", "Rio de Janeiro");
        Endereco endereco3 = new Endereco(147258369, "Rua Fictícia, 789", "copacabana" , "Rio de Janeiro", "Rio de Janeiro");
        TipoFornecedor tipoFornecedor1 = TipoFornecedor.DOADOR_ANONIMO;
        TipoFornecedor tipoFornecedor2 = TipoFornecedor.DISTRIBUIDORA_DE_LIVRO;
        TipoFornecedor tipoFornecedor3 = TipoFornecedor.ESCRITOR_INDEPENDENTE;
        Fornecedor fornecedor1 = new Fornecedor("Jão", "1234567", dataNasc,"Jvfrost", "1234" ,endereco1, "91234-5678", tipoFornecedor1);
        Fornecedor fornecedor2 = new Fornecedor("lulu", "123456", dataNasc,"luluKillerPig123", "1234" ,endereco2, "98765-4321", tipoFornecedor2);
        Fornecedor fornecedor3 = new Fornecedor("Ronyzito", "123456", dataNasc,"ronald wesley", "1234" ,endereco3, "98765-4321", tipoFornecedor3);

        Livro livro1 = new Livro("Memórias póstumas De Brás Cubas", "Machado De Assis", 30, fornecedor1);
        Livro livro2 = new Livro("Harry Potter e a pedra filosofal", "Jk roling", 20, fornecedor1);
        Livro livro3 = new Livro("As cronicas de narnia o leão", "C.S. Lewis", 70, fornecedor1);
        Livro livro4 = new Livro("O diário de um banana", "Jeff Kinney", 10, fornecedor2);
        Livro livro5 = new Livro("Dragon Ball", "Akira Toriyama", 79.50, fornecedor2);
        Livro livro6 = new Livro("Yuyu Hakusho", "Yoshihiro Togashi", 29.99, fornecedor2);
        Livro livro7 = new Livro("Dom Casmurro", "Machado de Assis", 50, fornecedor3);
        Livro livro8 = new Livro("O diário de um banana", "Jeff Kinney", 10, fornecedor3);

        ControladorLivro controladorLivro = ControladorLivro.getInstance();

        System.out.println("1- adição de livros no repositorio: ");
        try {
            controladorLivro.adicionarLivro(livro1);
            controladorLivro.adicionarLivro(livro2);
            controladorLivro.adicionarLivro(livro3);
            controladorLivro.adicionarLivro(livro4);
            controladorLivro.adicionarLivro(livro5);
            controladorLivro.adicionarLivro(livro6);
            controladorLivro.adicionarLivro(livro7);
            controladorLivro.adicionarLivro(livro8);
        } catch (LivroNuloException | CampoVazioException | PrecoInvalidoException | LivroExistenteException e) {
            System.out.println(e.getMessage());
        }

        List<Livro> livros = ControladorLivro.getInstance().listarTodosOslivrosEmOrdemAlfabetica();

        for(Livro livro : livros){
            System.out.println(livro.getTitulo());
        }

        System.out.println("\n-------------------------------------------\n");

        System.out.println("2 - remoção de livros no repositorio: ");

        try {
            controladorLivro.removerLivro(livro1);
        } catch (LivroNuloException | CampoVazioException | LivroNaoExistenteException e) {
            System.out.println(e.getMessage());
        }

        List<Livro> livros4 = ControladorLivro.getInstance().listarTodosOslivrosEmOrdemAlfabetica();

        for(Livro livro : livros4){
            System.out.println(livro.getTitulo());
        }

        try{
            controladorLivro.adicionarLivro(livro1);
        } catch (LivroNuloException | CampoVazioException | PrecoInvalidoException | LivroExistenteException e) {
            System.out.println(e.getMessage());;
        }

        System.out.println("\n-------------------------------------------\n");

        System.out.println("3- atualização de atributo livros no repositório: ");

        System.out.println("Versão antiga: " + livro8.getTitulo() + " | " +  livro8.getAutor() + " | " + livro8.getPreco() + " | " + livro8.getFornecedor().getNome());
        try {
            controladorLivro.atualizarLivro(livro8, "The hobbit", "J. R. R. Tolkien", 50, fornecedor1);
        } catch (LivroNaoExistenteException | CampoVazioException | LivroNuloException | PrecoInvalidoException |
                 UsuarioNuloException | DataInvalidaException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Após a edição: " + livro8.getTitulo() + " | " +  livro8.getAutor() + " | " + livro8.getPreco() + " | " + livro8.getFornecedor().getNome());

        System.out.println("\n-------------------------------------------\n");

        System.out.println("4- adição de genero livros: ");

        Genero genero1 = Genero.ACAO;
        Genero genero2 = Genero.ROMANCE;
        Genero genero3 = Genero.FICCAO_CIENTIFICA;
        Genero genero4 = Genero.FANTASIA;
        try {
            controladorLivro.adicionarGenero(livro1, genero1);
            controladorLivro.adicionarGenero(livro6, genero2);
            controladorLivro.adicionarGenero(livro2, genero2);
            controladorLivro.adicionarGenero(livro3, genero1);
            controladorLivro.adicionarGenero(livro3, genero2);
            controladorLivro.adicionarGenero(livro1, genero4);
            controladorLivro.adicionarGenero(livro1, genero3);
            controladorLivro.adicionarGenero(livro5, genero1);
            controladorLivro.adicionarGenero(livro4, genero2);
            controladorLivro.adicionarGenero(livro5, genero2);
            controladorLivro.adicionarGenero(livro2, genero4);
            controladorLivro.adicionarGenero(livro7, genero2);
            controladorLivro.adicionarGenero(livro8, genero4);
            controladorLivro.adicionarGenero(livro4, genero3);
        } catch (GeneroExistenteException | CampoVazioException | LivroNuloException | LivroNaoExistenteException e) {
            throw new RuntimeException(e);
        }

        List<Livro> livros0 = controladorLivro.listarTodosOslivrosEmOrdemAlfabetica();

        for (Livro livro : livros0){
            System.out.println('\n'+livro.getTitulo() + ": ");
            List<Genero> generos = livro.getGeneros();

            for (Genero genero : generos){
                System.out.println("   " + genero.getDescricaoEnum());
            }
        }

        System.out.println("\n-------------------------------------------\n");

        System.out.println("5- remocao de genero livros: ");

        try {
            controladorLivro.removerGenero(livro1, genero4);
        } catch (GeneroNaoExistenteException | CampoVazioException | LivroNuloException | LivroNaoExistenteException e) {
            throw new RuntimeException(e);
        }

        System.out.println("livro: "+ livro1.getTitulo() + "\ngenero:");

        List<Genero> generos = livro1.getGeneros();
        for (Genero genero : generos){
            System.out.println("   " + genero.getDescricaoEnum());
        }

        System.out.println("\n-------------------------------------------\n");

        System.out.println("6- adição da quantidade no Estoque: ");

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
        LocalDate data12 = LocalDate.of(2024, 1, 18);

        try {
            controladorLivro.aumentarQuantidadeEmEstoque(livro1, 10, data1);
            controladorLivro.aumentarQuantidadeEmEstoque(livro2, 30, data2);
            controladorLivro.aumentarQuantidadeEmEstoque(livro4, 5, data3);
            controladorLivro.aumentarQuantidadeEmEstoque(livro3, 70, data4);
            controladorLivro.aumentarQuantidadeEmEstoque(livro1, 15, data5);
            controladorLivro.aumentarQuantidadeEmEstoque(livro4, 37, data6);
            controladorLivro.aumentarQuantidadeEmEstoque(livro3, 39, data7);
            controladorLivro.aumentarQuantidadeEmEstoque(livro7, 60, data8);
            controladorLivro.aumentarQuantidadeEmEstoque(livro2, 30, data9);
            controladorLivro.aumentarQuantidadeEmEstoque(livro1, 350, data10);
            controladorLivro.aumentarQuantidadeEmEstoque(livro4, 50, data11);
            controladorLivro.aumentarQuantidadeEmEstoque(livro3, 100, data12);
            controladorLivro.aumentarQuantidadeEmEstoque(livro5, 10, data1);
            controladorLivro.aumentarQuantidadeEmEstoque(livro7, 30, data2);
            controladorLivro.aumentarQuantidadeEmEstoque(livro6, 5, data3);
            controladorLivro.aumentarQuantidadeEmEstoque(livro8, 70, data4);
            controladorLivro.aumentarQuantidadeEmEstoque(livro4, 15, data5);
            controladorLivro.aumentarQuantidadeEmEstoque(livro4, 37, data6);
            controladorLivro.aumentarQuantidadeEmEstoque(livro3, 39, data7);
            controladorLivro.aumentarQuantidadeEmEstoque(livro2, 60, data8);
            controladorLivro.aumentarQuantidadeEmEstoque(livro1, 30, data9);
            controladorLivro.aumentarQuantidadeEmEstoque(livro7, 350, data10);
            controladorLivro.aumentarQuantidadeEmEstoque(livro4, 50, data11);
            controladorLivro.aumentarQuantidadeEmEstoque(livro3, 100, data12);
        } catch (LivroNaoExistenteException | LivroNuloException | QuantidadeInvalidaException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Depois da adição:" );

        List<Livro> livros1 = controladorLivro.listarTodosOslivrosEmOrdemAlfabetica();

        for (Livro livro : livros1){
            System.out.println(livro.getTitulo() + " Quantidade em estoque: " + livro.getQuantidade());
        }

        System.out.println("\n-------------------------------------------\n");

        System.out.println("7- remoção da quantidade no Estoque: ");

        System.out.println("Vensão antiga: " + livro1.getTitulo() + " | " + "Quantidade: " + livro1.getQuantidade());

        try {
            controladorLivro.diminuirQuantidadeEmEstoque(livro1, 10);
        } catch (EstoqueInsuficienteException | QuantidadeInvalidaException | LivroNaoExistenteException |
                 LivroNuloException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Após a redução de quantidade:  " + livro1.getTitulo() + "estoque: " + livro1.getQuantidade());

        System.out.println("\n-------------------------------------------\n");

        System.out.println("8- quantidade de todos os livros: ");

        Map<Livro, Integer> estoqueLivros = controladorLivro.listarQuantidadeDeEstoque();

        for (Map.Entry<Livro, Integer> entry : estoqueLivros.entrySet()) {
            Livro livro = entry.getKey();
            int quantidadeEmEstoque = entry.getValue();

            System.out.println("Livro: " + livro.getTitulo() + " | " +" Quantidade em Estoque: " + quantidadeEmEstoque);
        }

        System.out.println("\n-------------------------------------------\n");

        System.out.println("9 - listar e ordenar livros por preço (do mais caro para o mais barato)");

        List<Livro> livros2 = controladorLivro.listarEOrdenarLivrosPorPreco();

        for (Livro livro : livros2){
            System.out.println("Titulo: " + livro.getTitulo() +  " | " + "Preço: " + livro.getPreco());
        }

        System.out.println("\n-------------------------------------------\n");

        System.out.println("10 - listar  livros por autor:");

        List<Livro> livros3 = controladorLivro.listarLivrosPorAutor("Machado De Assis");

        for (Livro livro : livros3){
            System.out.println("Titulo: " + livro.getTitulo() +  "\nautor: " + livro.getAutor());
        }

        System.out.println("\n-------------------------------------------\n");

        System.out.println("11 - listar  livros por genero:");

        List<Livro> livros6;

        try {
            livros6 = controladorLivro.listarLivrosPorGenero(genero1);
        } catch (GeneroNaoExistenteException e) {
            throw new RuntimeException(e);
        }

        System.out.println(genero1.getDescricaoEnum());
        for (Livro livro : livros6){
            System.out.println("titulo: "+ livro.getTitulo());
        }

        System.out.println("\n-------------------------------------------\n");

        System.out.println("12 - listar  livros por fornecedor:" + '\n');

        System.out.println("Livros do fornecedor " + fornecedor1.getNome() + " :" + '\n');

        List <Livro > livros10 = new ArrayList<>();

        try {
            livros10 = controladorLivro.listarLivrosPorFornecedor(fornecedor1);
        } catch (FornecedorNaoEncontradoException e){
            System.out.println(e.getMessage());
        }

        for (Livro livro : livros10){
            System.out.println(livro.getTitulo());
        }

        System.out.println("\n-------------------------------------------\n");

        System.out.println("13 - listar histórico de livros por fornecedor:");

        System.out.println("Fornecedor: " + fornecedor1.getNome());

        Map<Livro, Map<LocalDate, Integer>> historico = new HashMap<>();

        try{
            historico = controladorLivro.ListarHistoricoDeVendasFornecedor(fornecedor1,data1, data12);
        }catch (FornecedorNaoEncontradoException | DataInvalidaException e){
            System.out.println(e.getMessage());
        }

        for (Map.Entry<Livro, Map<LocalDate, Integer>> entry : historico.entrySet()) {
            Livro livro = entry.getKey();
            System.out.println("\nLivro: " + livro.getTitulo());

            Map<LocalDate, Integer> dadosNoIntervalo = entry.getValue();
            for (Map.Entry<LocalDate, Integer> dadosEntry : dadosNoIntervalo.entrySet()) {
                LocalDate dataAtualizacao = dadosEntry.getKey();
                int quantidade = dadosEntry.getValue();
                System.out.println("   Data da venda: " + dataAtualizacao + " - Quantidade comprada: " + quantidade);
            }
        }

        System.out.println("\n-------------------------------------------\n");

        System.out.println("14 - buscar livro do repositório");

        Livro livro = controladorLivro.buscarLivro(livro2.getId());
        System.out.println("Título: " + livro.getTitulo() + "  \nAutor: " +  livro.getAutor() + " \nPreço: " + livro.getPreco());
    }
}