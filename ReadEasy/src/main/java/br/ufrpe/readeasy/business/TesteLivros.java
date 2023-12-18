package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.exceptions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TesteLivros {
    public static void main(String[] args) throws RuntimeException {
        String data = "2001-08-23";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataNasc = LocalDate.parse(data, formatter);
        Endereco endereco = new Endereco(123456, "praça exp brasileiro", "Jardim Brasil" , "Recife", "Penambuco");
        TipoFornecedor tipoFornecedor = TipoFornecedor.ESCRITOR_INDEPENDENTE;
        Fornecedor fornecedor = new Fornecedor("Jão", "123456", dataNasc,"Jvfrost", "1234" ,endereco, "98728-3294", tipoFornecedor);

        Livro livro1 = new Livro("Memórias póstumas De Brás Cubas", "Machado De Assis", 30, fornecedor);
        Livro livro2 = new Livro("Harry Potter e a pedra filosofal", "Jk roling", 20, fornecedor);
        Livro livro3 = new Livro("As cronicas de narnia o leão", "Machado de Assis", 70, fornecedor);
        Livro livro4 = new Livro("O diário de um banana", "Machado De Assis", 10, fornecedor);

        ControladorLivro controladorLivro = ControladorLivro.getInstance();

        System.out.println("1- adição de livros no repositorio: ");
        try {
            controladorLivro.adicionarLivro(livro1);
            controladorLivro.adicionarLivro(livro2);
            controladorLivro.adicionarLivro(livro3);
            controladorLivro.adicionarLivro(livro4);
        } catch (LivroNuloException | CampoVazioException | PrecoInvalidoException | LivroExistenteException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }

        List<Livro> livros4 = ControladorLivro.getInstance().listarTodosOslivrosEmOrdemAlfabetica();

        for(Livro livro : livros4){
            System.out.println(livro.getTitulo());
        }

        System.out.println("\n-------------------------------------------\n");

        System.out.println("3- atualização de atributo livros no repositório: ");

        System.out.println("Versão antiga: " + livro1.getTitulo() + " | " +  livro1.getAutor() + " | " + livro1.getPreco() );
        try {
            controladorLivro.atualizarLivro(livro1, "aguando o dia em que lu vai comprar o meu pastel", "jão", 900, fornecedor);
        } catch (LivroNaoExistenteException | CampoVazioException | LivroNuloException | PrecoInvalidoException |
                 UsuarioNuloException | DataInvalidaException | MenorDeIdadeException e) {
            throw new RuntimeException(e);
        }

        System.out.println(livro1.getTitulo() + " | " +  livro1.getAutor() + " | " + livro1.getPreco());

        System.out.println("\n-------------------------------------------\n");

        System.out.println("4- adição de genero livros: ");

        Genero genero1 = Genero.ACAO;
        Genero genero2 = Genero.ROMANCE;
        Genero genero3 = Genero.FICCAO_CIENTIFICA;
        Genero genero4 = Genero.FANTASIA;
        try {
            controladorLivro.adicionarGenero(livro1, genero1);
            controladorLivro.adicionarGenero(livro1, genero2);
            controladorLivro.adicionarGenero(livro2, genero2);
            controladorLivro.adicionarGenero(livro3, genero1);
            controladorLivro.adicionarGenero(livro3, genero2);
            controladorLivro.adicionarGenero(livro1, genero4);
        } catch (GeneroExistenteException | CampoVazioException | LivroNuloException | LivroNaoExistenteException e) {
            throw new RuntimeException(e);
        }

        System.out.println("livro: "+ livro1.getTitulo() + "\ngenero:");
        List<Genero> generos = livro1.getGeneros();

        for (Genero genero : generos){
            System.out.println("   " + genero.getDescricaoEnum());
        }

        System.out.println("\n-------------------------------------------\n");

        System.out.println("5- remocao de genero livros: ");

        try {
            controladorLivro.removerGenero(livro1, genero4);
        } catch (GeneroNaoExistenteException | CampoVazioException | LivroNuloException | LivroNaoExistenteException e) {
            throw new RuntimeException(e);
        }

        System.out.println("livro: "+ livro1.getTitulo() + "\ngenero:");

        generos = livro1.getGeneros();
        for (Genero genero : generos){
            System.out.println("   " + genero.getDescricaoEnum());
        }

        System.out.println("\n-------------------------------------------\n");

        System.out.println("6- adição da quantidade: ");

        System.out.println("antes da adição-> livro: "+ livro1.getTitulo() + "\nquantidade de livros: " + livro1.getQuantidade());

        try {
            controladorLivro.aumentarQuantidadeEmEstoque(livro1, 10);
        } catch (LivroNaoExistenteException | LivroNuloException | QuantidadeInvalidaException e) {
            throw new RuntimeException(e);
        }
        System.out.println("depois da adição-> livro: "+ livro1.getTitulo() + "\nquantidade de livros" + livro1.getQuantidade());

        System.out.println("\n-------------------------------------------\n");

        System.out.println("7- remoção da quantidade: ");

        try {
            controladorLivro.diminuirQuantidadeEmEstoque(livro1, 2);
        } catch (EstoqueInsuficienteException | QuantidadeInvalidaException | LivroNaoExistenteException |
                 LivroNuloException e) {
            throw new RuntimeException(e);
        }

        System.out.println("livro: "+ livro1.getTitulo() + "\nestoque: " + livro1.getQuantidade());

        System.out.println("\n-------------------------------------------\n");

        System.out.println("7- quantidade de todos os livros: ");


        Map<Livro, Integer> estoqueLivros = controladorLivro.listarQuantidadeDeEstoque();


        for (Map.Entry<Livro, Integer> entry : estoqueLivros.entrySet()) {
            Livro livro = entry.getKey();
            int quantidadeEmEstoque = entry.getValue();

            System.out.println("Livro: " + livro.getTitulo() + ", Quantidade em Estoque: " + quantidadeEmEstoque);
        }

        System.out.println("\n-------------------------------------------\n");


        System.out.println("8 - listar e ordenar livros por preço (do mais caro para o mais barato)");

        List<Livro> livros2 = controladorLivro.listarEOrdenarLivrosPorPreco();

        for (Livro livro : livros2){
            System.out.println("Titulo: " + livro.getTitulo() +  "\nPreço: " + livro.getPreco());
        }

        System.out.println("\n-------------------------------------------\n");

        System.out.println("9 - listar  livros por autor:");

        List<Livro> livros3 = controladorLivro.listarLivrosPorAutor("Machado De Assis");

        for (Livro livro : livros3){
            System.out.println("Titulo: " + livro.getTitulo() +  "\nautor: " + livro.getAutor());
        }

        System.out.println("\n-------------------------------------------\n");

        System.out.println("10 - listar  livros por genero:");

        List<Livro> livros6 = new ArrayList<>();

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

        System.out.println("11 - listar  livros por fornecedor:");

        System.out.println("fornecedor: " + fornecedor.getNome());
        List <Livro > livros10 = controladorLivro.listarLivrosPorFornecedor(fornecedor);

        for (Livro livro : livros6){
            System.out.println("titulo: "+ livro.getTitulo());
        }


        System.out.println("\n-------------------------------------------\n");

        System.out.println("12 - buscar livro do repositório");

        Livro livro = controladorLivro.buscarLivro(livro2.getId());
        System.out.println(livro.getTitulo() + "  " +  livro.getAutor() + " " + livro.getPreco());
    }

}