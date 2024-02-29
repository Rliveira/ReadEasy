package br.ufrpe.readeasy;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.GeneroExistenteException;
import br.ufrpe.readeasy.gui.SessaoUsuario;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TesteHistoricoCompraEVenda {
    public static void InicializarTesteHistoricoCompraEVenda() {

        ServidorReadEasy servidor = ServidorReadEasy.getInstance();
        Fornecedor fornecedor = new Fornecedor("Fornecedor 1", "098876865",
                LocalDate.of(1909, 1, 1), "forn", "1234",
                new Endereco(12345678, "Rua 1", "Bairro 1", "Cidade 1", "PE"),
                "12312314", TipoFornecedor.EDITORA);
        Fornecedor  fornecedor2 = new Fornecedor("Fornecedor 2", "1234567890",
                LocalDate.of(1990, 1, 1), "forn", "1234",
                new Endereco(12345678, "Rua 1", "Bairro 1", "Cidade 1", "PE"),
                "12312314", TipoFornecedor.DISTRIBUIDORA_DE_LIVRO);

        Promocao promocao1 = new Promocao("promoção 1", 10, 5, LocalDate.now(), LocalDate.now().plusDays(10));
        Promocao promocao2 = new Promocao("promoção 2", 20, 7, LocalDate.now(), LocalDate.now().plusDays(30));

        String urlS1 = "https://m.media-amazon.com/images/I/71LJ4k-k9hL._SL1500_.jpg";
        String urlS2 = "https://m.media-amazon.com/images/I/71FxgtFKcQL._SL1500_.jpg";
        String urlS3 = "https://m.media-amazon.com/images/I/61t0bwt1s3L._SL1000_.jpg";
        String urlS4 = "https://m.media-amazon.com/images/I/81QuEGw8VPL._SL1500_.jpg";
        String urlS5 = "https://m.media-amazon.com/images/I/617ZJMlC86L._SL1294_.jpg";
        String urlS6 = "https://upload.wikimedia.org/wikipedia/pt/7/72/The_Hobbit_Cover.JPG";

        byte[] bytes1 = getUrlBytes(urlS1);
        byte[] bytes2 = getUrlBytes(urlS2);
        byte[] bytes3 = getUrlBytes(urlS3);
        byte[] bytes4 = getUrlBytes(urlS4);
        byte[] bytes5 = getUrlBytes(urlS5);
        byte[] bytes6 = getUrlBytes(urlS6);

        URL url1 = null;
        URL url2 = null;
        URL url3 = null;
        URL url4 = null;
        URL url5 = null;
        URL url6 = null;

        try {
            url1 = new URL(urlS1);
            url2 = new URL(urlS2);
            url3 = new URL(urlS3);
            url4 = new URL(urlS4);
            url5 = new URL(urlS5);
            url6 = new URL(urlS6);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }


        Livro livro1 = new Livro("O Pequeno Príncipe", "Antoine de Saint-Exupéry", 20.00, fornecedor, bytes1, url1);
        Livro livro2 = new Livro("To Kill a Mockingbird", "Harper Lee", 24.99, fornecedor2, bytes2, url2);
        Livro livro3 = new Livro("1984", "George Orwell", 19.99, fornecedor, bytes3, url3);
        Livro livro4 = new Livro("The Great Gatsby", "F. Scott Fitzgerald", 29.99, fornecedor, bytes4, url4);
        Livro livro5 = new Livro("Harry Potter e a pedra filosfal", "J.K. Rowling", 34.99, fornecedor2, bytes5, url5);
        Livro livro6 = new Livro("The Hobbit", "J.R.R. Tolkien", 22.99, fornecedor2, bytes6, url6);

        try{
            servidor.cadastrarUsuario(fornecedor);
            servidor.cadastrarUsuario(fornecedor2);
            servidor.adicionarLivro(livro1);
            servidor.adicionarLivro(livro2);
            servidor.adicionarLivro(livro3);
            servidor.adicionarLivro(livro4);
            servidor.adicionarLivro(livro5);
            servidor.adicionarLivro(livro6);

            servidor.aumentarQuantidadeEmEstoque(livro1, 10, LocalDate.of(2020, 1, 1));
            servidor.aumentarQuantidadeEmEstoque(livro2, 19, LocalDate.of(2020, 2, 13));
            servidor.aumentarQuantidadeEmEstoque(livro3, 20, LocalDate.of(2020, 3, 15));
            servidor.aumentarQuantidadeEmEstoque(livro4, 5, LocalDate.of(2020, 4, 1));
            servidor.aumentarQuantidadeEmEstoque(livro5, 15, LocalDate.of(2020, 5, 1));
            servidor.aumentarQuantidadeEmEstoque(livro6, 3, LocalDate.of(2020, 6, 1));

            Endereco endereco = new Endereco(12345678, "Rua 1", "Bairro 1", "Cidade 1", "PE");
            Endereco endereco1 = new Endereco(12345678, "Rua 1", "Bairro 1", "Cidade 1", "PE");

            servidor.cadastrarUsuario(new Cliente("Cliente 1", "1234567890",
                    LocalDate.of(1990, 1, 1), "cli", "1234",endereco, "12312314"));
            servidor.cadastrarUsuario(new Cliente("Cliente 2", "123451239043",
                    LocalDate.of(1990, 1, 1), "cli", "1234", endereco1, "12312314"));

            Venda venda1 = new Venda((Cliente)servidor.procurarUsuario("1234567890"), LocalDateTime.of(2020, 7, 1, 12, 0, 0), endereco, null);
            venda1.adicionarLivro(livro1, 2);
            venda1.adicionarLivro(livro6, 3);
            venda1.adicionarLivro(livro3, 1);

            Venda venda2 = new Venda((Cliente)servidor.procurarUsuario("123451239043"), LocalDateTime.of(2020, 8, 1, 12, 0, 0), endereco1, null);
            venda2.adicionarLivro(livro2, 1);
            venda2.adicionarLivro(livro4, 1);
            venda2.adicionarLivro(livro5, 2);

            servidor.inserirVenda(venda1);
            servidor.inserirVenda(venda2);

            SessaoUsuario.setUsuarioLogado(fornecedor);

        }  catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Genero genero1 = Genero.ACAO;
        Genero genero2 = Genero.ROMANCE;
        Genero genero3 = Genero.FICCAO_CIENTIFICA;
        Genero genero4 = Genero.FANTASIA;
        Genero genero5 = Genero.COMEDIA;
        Genero genero6 = Genero.CULINARIA;
        Genero genero7 = Genero.TERROR;
        Genero genero8 = Genero.ECONOMIA;
        try {
            servidor.adicionarGenero(livro1, genero1);
            servidor.adicionarGenero(livro6, genero2);
            servidor.adicionarGenero(livro3, genero1);
            servidor.adicionarGenero(livro3, genero2);
            servidor.adicionarGenero(livro1, genero4);
            servidor.adicionarGenero(livro1, genero3);
            servidor.adicionarGenero(livro6, genero1);
            servidor.adicionarGenero(livro4, genero5);
            servidor.adicionarGenero(livro5, genero7);
            servidor.adicionarGenero(livro2, genero4);
            servidor.adicionarGenero(livro4, genero6);
            servidor.adicionarGenero(livro6, genero5);
            servidor.adicionarGenero(livro6, genero6);
            servidor.adicionarGenero(livro2, genero3);
            servidor.adicionarGenero(livro3, genero4);
            servidor.adicionarGenero(livro3, genero6);
            servidor.adicionarGenero(livro1, genero7);
            servidor.adicionarGenero(livro5, genero3);
            servidor.adicionarGenero(livro4, genero2);
            servidor.adicionarGenero(livro5, genero8);
            servidor.adicionarGenero(livro1, genero6);
            servidor.adicionarGenero(livro1, genero8);
            servidor.adicionarGenero(livro4, genero3);
        } catch (GeneroExistenteException e) {
            throw new RuntimeException(e);
        }

        try{
            servidor.inserirPromocao(promocao1);
            servidor.inserirPromocao(promocao2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static byte[] getUrlBytes(String urlString) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            URL url = new URL(urlString);
            InputStream inputStream = url.openStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

}
