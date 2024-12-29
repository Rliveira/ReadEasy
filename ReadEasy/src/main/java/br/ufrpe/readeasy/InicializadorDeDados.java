package br.ufrpe.readeasy;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.business.Fachada;
import br.ufrpe.readeasy.exceptions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InicializadorDeDados {
    public static void main() {
        Fachada fachada = Fachada.getInstance();
        String data = "2001-08-23";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataNasc = LocalDate.parse(data, formatter);
        Endereco endereco1 = new Endereco(123456789, "Rua Fictícia, 123", "Jardim São Paulo", "Recife", "Pernambuco");
        Endereco endereco2 = new Endereco(987654321, "Rua Fictícia, 456", "le blon", "Rio de Janeiro", "Rio de Janeiro");
        Endereco endereco3 = new Endereco(147258369, "Rua Fictícia, 789", "copacabana", "Rio de Janeiro", "Rio de Janeiro");
        TipoFornecedor tipoFornecedor1 = TipoFornecedor.DOADOR_ANONIMO;
        TipoFornecedor tipoFornecedor2 = TipoFornecedor.DISTRIBUIDORA_DE_LIVRO;
        TipoFornecedor tipoFornecedor3 = TipoFornecedor.ESCRITOR_INDEPENDENTE;
        Fornecedor jaoF = new Fornecedor("Jão", "1234567", dataNasc, "Jvfrost", "1234", endereco1, "912345678", tipoFornecedor1);
        Fornecedor luluF = new Fornecedor("lulu", "123456", dataNasc, "luluKillerPig123", "1234", endereco2, "987654321", tipoFornecedor2);
        Fornecedor ronyF = new Fornecedor("Ronyzito", "1234568", dataNasc, "ronald wesley", "1234", endereco3, "987654321", tipoFornecedor3);
        Fornecedor lucasf = new Fornecedor("Lucas", "098876865", LocalDate.of(1909, 1, 1), "forn", "1234", new Endereco(12345678, "Rua 1", "Bairro 1", "Cidade 1", "PE"), "12312314", TipoFornecedor.EDITORA);
        Fornecedor marif = new Fornecedor("Mari", "1234567890", LocalDate.of(1990, 1, 1), "forn", "1234", new Endereco(12345678, "Rua 1", "Bairro 1", "Cidade 1", "PE"), "12312314", TipoFornecedor.DISTRIBUIDORA_DE_LIVRO);
        Funcionario funcionario1 = new Funcionario("Rony ADM", "1234568515", dataNasc, "adm2", "1234", endereco3, "987654321", true, null);
        Funcionario funcionario2 = new Funcionario("Ronyzito Func.", "1234568515", dataNasc, "ronyzito", "1234", endereco3, "987654321", false, funcionario1);

        String urlS1 = "https://lojasemear.com.br/product_images/u/691/p7733__91582_std.jpg";
        String urlS2 = "https://m.media-amazon.com/images/I/81PbdwXqKkL._SL1500_.jpg";
        String urlS3 = "https://m.media-amazon.com/images/I/7158aW38zxL._AC_UF1000,1000_QL80_.jpg";
        String urlS4 = "https://m.media-amazon.com/images/I/81TmHlRleJL._SL1500_.jpg";
        String urlS5 = "https://m.media-amazon.com/images/I/71IIA44vw1L._SL1000_.jpg";
        String urlS6 = "https://m.media-amazon.com/images/I/81uRHF8TGfL._SL1500_.jpg";
        String urlS7 = "https://m.media-amazon.com/images/I/81ObqL6bR5S._SL1500_.jpg";
        String urlS8 = "https://m.media-amazon.com/images/I/814J+usGFjL._SL1500_.jpg";
        String urlS9 = "https://m.media-amazon.com/images/I/81jbivNEVML._AC_UF1000,1000_QL80_.jpg";
        String urlS10 = "https://m.media-amazon.com/images/I/515pFJ2KfVL.jpg";
        String urlS11 = "https://m.media-amazon.com/images/I/515TcMeZStL.jpg";
        String urlS12 = "https://m.media-amazon.com/images/I/519namgWJTL._SY445_SX342_.jpg";
        String urlS13 = "https://m.media-amazon.com/images/I/81yFIh1yoZL._AC_UF1000,1000_QL80_.jpg";
        String urlS14 = "https://m.media-amazon.com/images/I/71LJ4k-k9hL._SL1500_.jpg";
        String urlS15 = "https://m.media-amazon.com/images/I/71FxgtFKcQL._SL1500_.jpg";
        String urlS16 = "https://m.media-amazon.com/images/I/61t0bwt1s3L._SL1000_.jpg";
        String urlS17 = "https://m.media-amazon.com/images/I/81QuEGw8VPL._SL1500_.jpg";
        String urlS18 = "https://m.media-amazon.com/images/I/617ZJMlC86L._SL1294_.jpg";
        String urlS19 = "https://upload.wikimedia.org/wikipedia/pt/7/72/The_Hobbit_Cover.JPG";
        String urlS20 = "https://m.media-amazon.com/images/I/51SDFG0BD8L.jpg";
        String urlS21 = "https://m.media-amazon.com/images/I/81SQPrWU7SL._AC_UF1000,1000_QL80_.jpg";
        String urlS22 = "https://m.media-amazon.com/images/I/719esIW3D7L._SL1297_.jpg";
        String urlS23 = "https://m.media-amazon.com/images/I/51T-82qFZyL.jpg";
        String urlS24 = "https://m.media-amazon.com/images/I/91xUwI2UEVL.jpg";
        String urlS25 = "https://m.media-amazon.com/images/I/71y+XnBXm4L._AC_UF1000,1000_QL80_.jpg";
        String urlS26 = "https://m.media-amazon.com/images/I/71t11qhOQVL._AC_UF1000,1000_QL80_.jpg";
        String urlS27 = "https://m.media-amazon.com/images/I/61GWN9NPJvL._AC_UF1000,1000_QL80_.jpg";
        String urlS28 = "https://m.media-amazon.com/images/I/81CHe2lqzDL._SY466_.jpg";
        String urlS29 = "https://m.media-amazon.com/images/I/71lnvXSiITL._SY466_.jpg";
        String urlS30 = "https://m.media-amazon.com/images/I/71xaXeBqYTL._AC_UF1000,1000_QL80_.jpg";

        byte[] bytes1 = getUrlBytes(urlS1);
        byte[] bytes2 = getUrlBytes(urlS2);
        byte[] bytes3 = getUrlBytes(urlS3);
        byte[] bytes4 = getUrlBytes(urlS4);
        byte[] bytes5 = getUrlBytes(urlS5);
        byte[] bytes6 = getUrlBytes(urlS6);
        byte[] bytes7 = getUrlBytes(urlS7);
        byte[] bytes8 = getUrlBytes(urlS8);
        byte[] bytes9 = getUrlBytes(urlS9);
        byte[] bytes10 = getUrlBytes(urlS10);
        byte[] bytes11 = getUrlBytes(urlS11);
        byte[] bytes12 = getUrlBytes(urlS12);
        byte[] bytes13 = getUrlBytes(urlS13);
        byte[] bytes14 = getUrlBytes(urlS14);
        byte[] bytes15 = getUrlBytes(urlS15);
        byte[] bytes16 = getUrlBytes(urlS16);
        byte[] bytes17 = getUrlBytes(urlS17);
        byte[] bytes18 = getUrlBytes(urlS18);
        byte[] bytes19 = getUrlBytes(urlS19);
        byte[] bytes20 = getUrlBytes(urlS20);
        byte[] bytes21 = getUrlBytes(urlS21);
        byte[] bytes22 = getUrlBytes(urlS22);
        byte[] bytes23 = getUrlBytes(urlS23);
        byte[] bytes24 = getUrlBytes(urlS24);
        byte[] bytes25 = getUrlBytes(urlS25);
        byte[] bytes26 = getUrlBytes(urlS26);
        byte[] bytes27 = getUrlBytes(urlS27);
        byte[] bytes28 = getUrlBytes(urlS28);
        byte[] bytes29 = getUrlBytes(urlS29);
        byte[] bytes30 = getUrlBytes(urlS30);

        URL url1;
        URL url2;
        URL url3;
        URL url4;
        URL url5;
        URL url6;
        URL url7;
        URL url8;
        URL url9;
        URL url10;
        URL url11;
        URL url12;
        URL url13;
        URL url14;
        URL url15;
        URL url16;
        URL url17;
        URL url18;
        URL url19;
        URL url20;
        URL url21;
        URL url22;
        URL url23;
        URL url24;
        URL url25;
        URL url26;
        URL url27;
        URL url28;
        URL url29;
        URL url30;

        try {
            url1 = new URL(urlS1);
            url2 = new URL(urlS2);
            url3 = new URL(urlS3);
            url4 = new URL(urlS4);
            url5 = new URL(urlS5);
            url6 = new URL(urlS6);
            url7 = new URL(urlS7);
            url8 = new URL(urlS8);
            url9 = new URL(urlS9);
            url10 = new URL(urlS10);
            url11 = new URL(urlS11);
            url12 = new URL(urlS12);
            url13 = new URL(urlS13);
            url14 = new URL(urlS14);
            url15 = new URL(urlS15);
            url16 = new URL(urlS16);
            url17 = new URL(urlS17);
            url18 = new URL(urlS18);
            url19 = new URL(urlS19);
            url20 = new URL(urlS20);
            url21 = new URL(urlS21);
            url22 = new URL(urlS22);
            url23 = new URL(urlS23);
            url24 = new URL(urlS24);
            url25 = new URL(urlS25);
            url26 = new URL(urlS26);
            url27 = new URL(urlS27);
            url28 = new URL(urlS28);
            url29 = new URL(urlS29);
            url30 = new URL(urlS30);

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        Livro livro1 = new Livro("Memórias póstumas De Brás Cubas", "Machado De Assis", 30, ronyF, bytes1, url1);
        Livro livro2 = new Livro("Harry Potter e as relíquias da morte", "J.K. Rowling", 20, jaoF, bytes2, url2);
        Livro livro3 = new Livro("As crônicas de narnia o leão, a Feiticeira e o Guarda roupa", "C.S. Lewis", 70, ronyF, bytes3, url3);
        Livro livro4 = new Livro("Jujutsu Kaisen vol. 1", "Gege Akutami", 40, marif, bytes4, url4);
        Livro livro5 = new Livro("Dragon Ball ,vol. 1", "Akira Toriyama", 35.50, marif, bytes5, url5);
        Livro livro6 = new Livro("Yuyu Hakusho ,vol. 1", "Yoshihiro Togashi", 30.75, marif, bytes6, url6);
        Livro livro7 = new Livro("Dom Casmurro", "Machado de Assis", 50, ronyF, bytes7, url7);
        Livro livro8 = new Livro("Guerra e paz", "Liev Tolstói", 65, ronyF, bytes8, url8);
        Livro livro9 = new Livro("Harry Potter e a Câmara secreta", "J.K. Rowling", 25.30, jaoF, bytes9, url9);
        Livro livro10 = new Livro("Harry Potter e o Cálice de fogo", "J.K. Rowling", 40, jaoF, bytes10, url10);
        Livro livro11 = new Livro("Harry Potter e o Prisioneiro de Azkaban", "J.K. Rowling", 35, jaoF, bytes11, url11);
        Livro livro12 = new Livro("Harry Potter e a Ordem da Fenix", "J.K. Rowling", 50, jaoF, bytes12, url12);
        Livro livro13 = new Livro("Harry Potter e o Enigma do Príncipe", "J.K. Rowling", 45, jaoF, bytes13, url13);
        Livro livro14 = new Livro("O Pequeno Príncipe", "Antoine de Saint-Exupéry", 20.00, lucasf, bytes14, url14);
        Livro livro15 = new Livro("To Kill a Mockingbird", "Harper Lee", 25, luluF, bytes15, url15);
        Livro livro16 = new Livro("1984", "George Orwell", 30, lucasf, bytes16, url16);
        Livro livro17 = new Livro("The Great Gatsby", "F. Scott Fitzgerald", 30, lucasf, bytes17, url17);
        Livro livro18 = new Livro("Harry Potter e a pedra filosfal", "J.K. Rowling", 35.50, jaoF, bytes18, url18);
        Livro livro19 = new Livro("The Hobbit", "J.R.R. Tolkien", 70, luluF, bytes19, url19);
        Livro livro20 = new Livro("O sol é para todos", "Harper Lee", 35, luluF, bytes20, url20);
        Livro livro21 = new Livro("Cem anos de solidão", "Gabriel García Márquez", 40, luluF, bytes21, url21);
        Livro livro22 = new Livro("Orgulho e Preconceito", "Jane Austen", 50, luluF, bytes22, url22);
        Livro livro23 = new Livro("A divina comédia", "Dante Alighieri", 40, luluF, bytes23, url23);
        Livro livro24 = new Livro("Naruto ,Vol. 1", "Masashi Kishimoto", 40, luluF, bytes24, url24);
        Livro livro25 = new Livro("One Piece Vol. 1", "Eichiiro Oda", 35, marif, bytes25, url25);
        Livro livro26 = new Livro("Frieren e a Jornada para o Além, Vol. 1", "Kanehito Yamada", 35, marif, bytes26, url26);
        Livro livro27 = new Livro("Fullmetal Alchemist ,Vol. 1", "Hiromu Arakawa", 35, jaoF, bytes27, url27);
        Livro livro28 = new Livro("Blue Period ,Vol. 1", "Tsubasa Yamaguchi", 40, marif, bytes28, url28);
        Livro livro29 = new Livro("Berserk, Vol 1", "Kentaro Miura", 35, lucasf, bytes29, url29);
        Livro livro30 = new Livro("Vinland Saga, Vol 1", "Makoto Yukimuraa", 30, lucasf, bytes30, url30);

        try {
            fachada.adicionarLivro(livro1);
            fachada.adicionarLivro(livro2);
            fachada.adicionarLivro(livro3);
            fachada.adicionarLivro(livro4);
            fachada.adicionarLivro(livro5);
            fachada.adicionarLivro(livro6);
            fachada.adicionarLivro(livro7);
            fachada.adicionarLivro(livro8);
            fachada.adicionarLivro(livro9);
            fachada.adicionarLivro(livro10);
            fachada.adicionarLivro(livro11);
            fachada.adicionarLivro(livro12);
            fachada.adicionarLivro(livro13);
            fachada.adicionarLivro(livro14);
            fachada.adicionarLivro(livro15);
            fachada.adicionarLivro(livro16);
            fachada.adicionarLivro(livro17);
            fachada.adicionarLivro(livro18);
            fachada.adicionarLivro(livro19);
            fachada.adicionarLivro(livro20);
            fachada.adicionarLivro(livro21);
            fachada.adicionarLivro(livro22);
            fachada.adicionarLivro(livro23);
            fachada.adicionarLivro(livro24);
            fachada.adicionarLivro(livro25);
            fachada.adicionarLivro(livro26);
            fachada.adicionarLivro(livro27);
            fachada.adicionarLivro(livro28);
            fachada.adicionarLivro(livro29);
            fachada.adicionarLivro(livro30);
        } catch (ValorInvalidoException | LivroExistenteException e) {
            System.out.println(e.getMessage());
        }

        try {
            fachada.cadastrarUsuario(jaoF);
            fachada.cadastrarUsuario(luluF);
            fachada.cadastrarUsuario(ronyF);
            fachada.cadastrarUsuario(marif);
            fachada.cadastrarUsuario(lucasf);
            fachada.cadastrarUsuario(funcionario2);

        } catch (MenorDeIdadeException | DataInvalidaException | CampoVazioException | UsuarioExistenteException e) {
            System.out.println(e.getMessage());
        }

        Genero acao = Genero.ACAO;
        Genero romance = Genero.ROMANCE;
        Genero ficcao = Genero.FICCAO;
        Genero naoFiccao = Genero.NAO_FICCAO;
        Genero fantasia = Genero.FANTASIA;
        Genero comedia = Genero.COMEDIA;
        Genero sobrenatural = Genero.SOBRENATURAL;
        Genero terror = Genero.TERROR;
        Genero historico = Genero.HISTORICO;
        Genero criticaSocial = Genero.CRITICA_SOCIAL;
        Genero aventura = Genero.AVENTURA;
        Genero drama = Genero.DRAMA;
        Genero suspense = Genero.SUSPENSE;
        Genero literaturaClassica = Genero.LITERATURA_CLASSICA;
        Genero academico = Genero.ACADEMICO;
        Genero autobiografia = Genero.AUTOBIOGRAFIA;
        Genero epico = Genero.EPICO;
        Genero poesia = Genero.POESIA;
        Genero historiaEmQuadrinhos = Genero.HISTORIA_EM_QUADRINHOS;
        Genero infantoJuvenil = Genero.INFANTO_JUVENIL;
        Genero religioso = Genero.RELIGIOSO;

        try {
            fachada.adicionarGenero(livro1, romance);
            fachada.adicionarGenero(livro1, literaturaClassica);
            fachada.adicionarGenero(livro1, comedia);
            fachada.adicionarGenero(livro1, criticaSocial);

            fachada.adicionarGenero(livro2, fantasia);
            fachada.adicionarGenero(livro2, aventura);
            fachada.adicionarGenero(livro2, infantoJuvenil);
            fachada.adicionarGenero(livro2, ficcao);

            fachada.adicionarGenero(livro3, infantoJuvenil);
            fachada.adicionarGenero(livro3, fantasia);
            fachada.adicionarGenero(livro3, aventura);

            fachada.adicionarGenero(livro4, acao);
            fachada.adicionarGenero(livro4, fantasia);
            fachada.adicionarGenero(livro4, sobrenatural);
            fachada.adicionarGenero(livro4, aventura);
            fachada.adicionarGenero(livro4, historiaEmQuadrinhos);

            fachada.adicionarGenero(livro5, fantasia);
            fachada.adicionarGenero(livro5, acao);
            fachada.adicionarGenero(livro5, aventura);
            fachada.adicionarGenero(livro5, historiaEmQuadrinhos);

            fachada.adicionarGenero(livro6, fantasia);
            fachada.adicionarGenero(livro6, acao);
            fachada.adicionarGenero(livro6, aventura);
            fachada.adicionarGenero(livro6, sobrenatural);
            fachada.adicionarGenero(livro6, historiaEmQuadrinhos);

            fachada.adicionarGenero(livro7, romance);
            fachada.adicionarGenero(livro7, literaturaClassica);
            fachada.adicionarGenero(livro7, drama);

            fachada.adicionarGenero(livro8, romance);
            fachada.adicionarGenero(livro8, epico);
            fachada.adicionarGenero(livro8, historico);
            fachada.adicionarGenero(livro8, literaturaClassica);
            fachada.adicionarGenero(livro8, drama);

            fachada.adicionarGenero(livro9, aventura);
            fachada.adicionarGenero(livro9, suspense);
            fachada.adicionarGenero(livro9, infantoJuvenil);
            fachada.adicionarGenero(livro9, fantasia);

            fachada.adicionarGenero(livro10, aventura);
            fachada.adicionarGenero(livro10, infantoJuvenil);
            fachada.adicionarGenero(livro10, fantasia);

            fachada.adicionarGenero(livro11, aventura);
            fachada.adicionarGenero(livro11, suspense);
            fachada.adicionarGenero(livro11, infantoJuvenil);
            fachada.adicionarGenero(livro11, fantasia);

            fachada.adicionarGenero(livro12, aventura);
            fachada.adicionarGenero(livro12, infantoJuvenil);
            fachada.adicionarGenero(livro12, fantasia);

            fachada.adicionarGenero(livro13, aventura);
            fachada.adicionarGenero(livro13, infantoJuvenil);
            fachada.adicionarGenero(livro13, fantasia);

            fachada.adicionarGenero(livro14, poesia);
            fachada.adicionarGenero(livro14, infantoJuvenil);
            fachada.adicionarGenero(livro14, fantasia);

            fachada.adicionarGenero(livro15, drama);
            fachada.adicionarGenero(livro15, romance);
            fachada.adicionarGenero(livro15, historico);
            fachada.adicionarGenero(livro15, literaturaClassica);
            fachada.adicionarGenero(livro15, criticaSocial);

            fachada.adicionarGenero(livro16, criticaSocial);
            fachada.adicionarGenero(livro16, ficcao);

            fachada.adicionarGenero(livro17, drama);
            fachada.adicionarGenero(livro17, romance);
            fachada.adicionarGenero(livro17, criticaSocial);

            fachada.adicionarGenero(livro18, aventura);
            fachada.adicionarGenero(livro18, infantoJuvenil);
            fachada.adicionarGenero(livro18, fantasia);

            fachada.adicionarGenero(livro19, aventura);
            fachada.adicionarGenero(livro19, infantoJuvenil);
            fachada.adicionarGenero(livro19, fantasia);
            fachada.adicionarGenero(livro19, ficcao);
            fachada.adicionarGenero(livro19, epico);

            fachada.adicionarGenero(livro20, romance);
            fachada.adicionarGenero(livro20, drama);
            fachada.adicionarGenero(livro20, literaturaClassica);
            fachada.adicionarGenero(livro20, criticaSocial);

            fachada.adicionarGenero(livro21, romance);
            fachada.adicionarGenero(livro21, literaturaClassica);

            fachada.adicionarGenero(livro22, romance);
            fachada.adicionarGenero(livro22, drama);
            fachada.adicionarGenero(livro22, literaturaClassica);

            fachada.adicionarGenero(livro23, literaturaClassica);
            fachada.adicionarGenero(livro23, poesia);
            fachada.adicionarGenero(livro23, epico);
            fachada.adicionarGenero(livro23, religioso);
            fachada.adicionarGenero(livro23, drama);

            fachada.adicionarGenero(livro24, aventura);
            fachada.adicionarGenero(livro24, infantoJuvenil);
            fachada.adicionarGenero(livro24, fantasia);
            fachada.adicionarGenero(livro24, acao);
            fachada.adicionarGenero(livro24, historiaEmQuadrinhos);
            fachada.adicionarGenero(livro24, drama);
            fachada.adicionarGenero(livro24, comedia);

            fachada.adicionarGenero(livro25, aventura);
            fachada.adicionarGenero(livro25, infantoJuvenil);
            fachada.adicionarGenero(livro25, fantasia);
            fachada.adicionarGenero(livro25, acao);
            fachada.adicionarGenero(livro25, historiaEmQuadrinhos);
            fachada.adicionarGenero(livro25, comedia);

            fachada.adicionarGenero(livro26, aventura);
            fachada.adicionarGenero(livro26, fantasia);
            fachada.adicionarGenero(livro26, drama);
            fachada.adicionarGenero(livro26, historiaEmQuadrinhos);
            fachada.adicionarGenero(livro26, comedia);

            fachada.adicionarGenero(livro27, aventura);
            fachada.adicionarGenero(livro27, acao);
            fachada.adicionarGenero(livro27, ficcao);
            fachada.adicionarGenero(livro27, drama);
            fachada.adicionarGenero(livro27, historiaEmQuadrinhos);
            fachada.adicionarGenero(livro27, comedia);

            fachada.adicionarGenero(livro28, drama);
            fachada.adicionarGenero(livro28, historiaEmQuadrinhos);

            fachada.adicionarGenero(livro29, aventura);
            fachada.adicionarGenero(livro29, acao);
            fachada.adicionarGenero(livro29, fantasia);
            fachada.adicionarGenero(livro29, drama);
            fachada.adicionarGenero(livro29, historiaEmQuadrinhos);
            fachada.adicionarGenero(livro29, sobrenatural);
            fachada.adicionarGenero(livro29, terror);

            fachada.adicionarGenero(livro30, aventura);
            fachada.adicionarGenero(livro30, acao);
            fachada.adicionarGenero(livro30, historico);
            fachada.adicionarGenero(livro30, drama);
            fachada.adicionarGenero(livro30, historiaEmQuadrinhos);

        } catch (GeneroExistenteException e) {
            throw new RuntimeException(e);
        }

        Endereco end1 = new Endereco(18460154, "Rua dos Bobos", "Jardel", "Londres", "CE");
        Endereco end2 = new Endereco(18460182, "Rua Bom Jesus", "Recife Antigo", "Recife", "RJ");
        Endereco end3 = new Endereco(17520154, "Rua da Aurora", "Graças", "Maraial", "RN");
        Endereco end4 = new Endereco(18469874, "Rua Solidão", "Pau Amarelo", "Paulista", "SP");
        Endereco end5 = new Endereco(18469874, "Honório correia", "cordeiro", "fds", "PE");

        Cliente cliente1 = new Cliente("Rony", "123456789", LocalDate.of(2001, 7, 17),
                "rony.oliveira", "123batata", end1, "40028922");
        Cliente cliente2 = new Cliente("Louise", "78942135", LocalDate.of(2000, 8, 16),
                "luluzinha123", "churros7878", end2, "54965135");
        Cliente cliente3 = new Cliente("Mariane", "5498751324", LocalDate.of(2003, 12, 17),
                "marimari", "1717829", end3, "60608677");
        Cliente cliente4 = new Cliente("Leandro", "979798453555", LocalDate.of(1998, 2, 1),
                "prof leandro123", "tlou69", end4, "40028922");
        Cliente cliente5 = new Cliente("jão", "979798453", LocalDate.of(1998, 2, 1),
                "jv.moraes", "tlou69", end4, "40028922");

        cliente1.adicionarEndereco(end1);
        cliente2.adicionarEndereco(end2);
        cliente3.adicionarEndereco(end3);
        cliente4.adicionarEndereco(end4);
        cliente5.adicionarEndereco(end1);

        try {
            fachada.cadastrarUsuario(cliente1);
            fachada.cadastrarUsuario(cliente2);
            fachada.cadastrarUsuario(cliente3);
            fachada.cadastrarUsuario(cliente4);
            fachada.cadastrarUsuario(cliente5);

        } catch (UsuarioExistenteException | CampoVazioException | MenorDeIdadeException | DataInvalidaException e) {
            throw new RuntimeException(e);
        }


        LocalDateTime dataVenda1 = LocalDateTime.of(2020, 2, 18, 16, 15);
        LocalDateTime dataVenda2 = LocalDateTime.of(2020, 3, 10, 14, 0);
        LocalDateTime dataVenda3 = LocalDateTime.of(2020, 3, 15, 16, 15);
        LocalDateTime dataVenda4 = LocalDateTime.of(2020, 4, 5, 15, 15);
        LocalDateTime dataVenda5 = LocalDateTime.of(2020, 7, 9, 11, 0);

        LocalDateTime dataVenda6 = LocalDateTime.of(2021, 1, 5, 12, 30);
        LocalDateTime dataVenda7 = LocalDateTime.of(2021, 2, 2, 13, 45);
        LocalDateTime dataVenda8 = LocalDateTime.of(2021, 3, 10, 14, 0);
        LocalDateTime dataVenda9 = LocalDateTime.of(2021, 4, 5, 15, 15);
        LocalDateTime dataVenda10 = LocalDateTime.of(2021, 10, 8, 14, 45);
        LocalDateTime dataVenda11 = LocalDateTime.of(2021, 11, 5, 15, 0);
        LocalDateTime dataVenda12 = LocalDateTime.of(2021, 11, 5, 15, 30);
        LocalDateTime dataVenda13 = LocalDateTime.of(2021, 12, 18, 12, 15);
        LocalDateTime dataVenda14 = LocalDateTime.of(2021, 12, 18, 16, 15);

        LocalDateTime dataVenda15 = LocalDateTime.of(2022, 1, 5, 12, 30);
        LocalDateTime dataVenda16 = LocalDateTime.of(2022, 2, 18, 13, 45);
        LocalDateTime dataVenda17 = LocalDateTime.of(2022, 2, 18, 13, 45);
        LocalDateTime dataVenda18 = LocalDateTime.of(2022, 3, 10, 14, 0);
        LocalDateTime dataVenda19 = LocalDateTime.of(2022, 5, 12, 16, 30);
        LocalDateTime dataVenda20 = LocalDateTime.of(2022, 6, 28, 10, 45);
        LocalDateTime dataVenda21 = LocalDateTime.of(2022, 6, 28, 15, 45);
        LocalDateTime dataVenda22 = LocalDateTime.of(2022, 7, 9, 11, 0);
        LocalDateTime dataVenda23 = LocalDateTime.of(2022, 7, 9, 19, 0);
        LocalDateTime dataVenda24 = LocalDateTime.of(2022, 8, 16, 12, 15);
        LocalDateTime dataVenda25 = LocalDateTime.of(2022, 9, 22, 13, 30);
        LocalDateTime dataVenda26 = LocalDateTime.of(2022, 10, 8, 14, 45);

        LocalDateTime dataVenda27 = LocalDateTime.of(2023, 1, 1, 14, 45);
        LocalDateTime dataVenda28 = LocalDateTime.of(2023, 1, 4, 16, 15);
        LocalDateTime dataVenda29 = LocalDateTime.of(2023, 2, 1, 12, 30);
        LocalDateTime dataVenda30 = LocalDateTime.of(2023, 2, 1, 16, 30);
        LocalDateTime dataVenda31 = LocalDateTime.of(2023, 2, 1, 10, 45);
        LocalDateTime dataVenda32 = LocalDateTime.of(2023, 3, 12, 17, 30);
        LocalDateTime dataVenda33 = LocalDateTime.of(2023, 3, 12, 16, 30);
        LocalDateTime dataVenda34 = LocalDateTime.of(2023, 4, 16, 12, 15);
        LocalDateTime dataVenda35 = LocalDateTime.of(2023, 5, 22, 13, 30);
        LocalDateTime dataVenda36 = LocalDateTime.of(2023, 5, 28, 10, 45);
        LocalDateTime dataVenda37 = LocalDateTime.of(2023, 6, 2, 15, 15);
        LocalDateTime dataVenda38 = LocalDateTime.of(2023, 7, 3, 16, 15);
        LocalDateTime dataVenda39 = LocalDateTime.of(2023, 8, 16, 12, 15);
        LocalDateTime dataVenda40 = LocalDateTime.of(2023, 8, 16, 16, 15);
        LocalDateTime dataVenda41 = LocalDateTime.of(2023, 9, 22, 13, 30);
        LocalDateTime dataVenda42 = LocalDateTime.of(2023, 9, 22, 14, 30);
        LocalDateTime dataVenda43 = LocalDateTime.of(2023, 11, 5, 15, 0);

        LocalDateTime dataVenda44 = LocalDateTime.of(2024, 1, 1, 11, 0);
        LocalDateTime dataVenda45 = LocalDateTime.of(2024, 1, 5, 12, 30);
        LocalDateTime dataVenda46 = LocalDateTime.of(2024, 1, 6, 13, 45);
        LocalDateTime dataVenda47 = LocalDateTime.of(2024, 1, 12, 14, 0);
        LocalDateTime dataVenda48 = LocalDateTime.of(2024, 1, 20, 15, 0);
        LocalDateTime dataVenda49 = LocalDateTime.of(2024, 1, 25, 15, 15);
        LocalDateTime dataVenda50 = LocalDateTime.of(2024, 2, 2, 14, 45);
        LocalDateTime dataVenda51 = LocalDateTime.of(2024, 2, 5, 14, 45);
        LocalDateTime dataVenda52 = LocalDateTime.of(2024, 2, 11, 14, 45);
        LocalDateTime dataVenda53 = LocalDateTime.of(2024, 2, 13, 14, 45);
        LocalDateTime dataVenda54 = LocalDateTime.of(2024, 2, 18, 14, 45);
        LocalDateTime dataVenda55 = LocalDateTime.of(2024, 2, 25, 14, 45);
        LocalDateTime dataVenda56 = LocalDateTime.of(2024, 2, 27, 14, 45);
        LocalDateTime dataVenda57 = LocalDateTime.of(2024, 2, 26, 14, 45);
        LocalDateTime dataVenda58 = LocalDateTime.of(2024, 2, 24, 14, 45);
        LocalDateTime dataVenda59 = LocalDateTime.of(2024, 1, 31, 14, 45);
        LocalDateTime dataVenda60 = LocalDateTime.of(2024, 3, 1, 19, 45);
        LocalDateTime dataVenda61 = LocalDateTime.of(2024, 3, 1, 14, 45);
        LocalDateTime dataVenda62 = LocalDateTime.of(2024, 3, 3, 14, 45);
        LocalDateTime dataVenda63 = LocalDateTime.of(2024, 3, 4, 14, 45);
        LocalDateTime dataVenda64 = LocalDateTime.of(2024, 3, 5, 14, 45);
        LocalDateTime dataVenda65 = LocalDateTime.of(2024, 3, 5, 17, 45);
        LocalDateTime dataVenda66 = LocalDateTime.of(2024, 3, 8, 14, 45);
        LocalDateTime dataVenda67 = LocalDateTime.of(2024, 3, 9, 14, 45);
        LocalDateTime dataVenda68 = LocalDateTime.of(2024, 3, 9, 14, 45);
        LocalDateTime dataVenda69 = LocalDateTime.of(2024, 3, 12, 14, 45);
        LocalDateTime dataVenda70 = LocalDateTime.of(2024, 3, 12, 14, 45);

        //Compras
        LocalDate dataCompra1 = LocalDate.of(2020, 5, 29);
        LocalDate dataCompra2 = LocalDate.of(2020, 7, 12);
        LocalDate dataCompra3 = LocalDate.of(2020, 7, 15);
        LocalDate dataCompra4 = LocalDate.of(2020, 8, 9);
        LocalDate dataCompra5 = LocalDate.of(2020, 8, 20);
        LocalDate dataCompra6 = LocalDate.of(2020, 9, 25);
        LocalDate dataCompra7 = LocalDate.of(2020, 9, 3);
        LocalDate dataCompra8 = LocalDate.of(2020, 10, 5);
        LocalDate dataCompra9 = LocalDate.of(2020, 11, 10);
        LocalDate dataCompra10 = LocalDate.of(2020, 11, 6);
        LocalDate dataCompra11 = LocalDate.of(2020, 12, 15);
        LocalDate dataCompra12 = LocalDate.of(2020, 12, 31);
        LocalDate dataCompra13 = LocalDate.of(2021, 1, 12);
        LocalDate dataCompra14 = LocalDate.of(2021, 1, 20);
        LocalDate dataCompra15 = LocalDate.of(2021, 1, 25);
        LocalDate dataCompra16 = LocalDate.of(2021, 2, 25);
        LocalDate dataCompra17 = LocalDate.of(2021, 3, 8);
        LocalDate dataCompra19 = LocalDate.of(2021, 4, 5);
        LocalDate dataCompra20 = LocalDate.of(2021, 4, 17);
        LocalDate dataCompra21 = LocalDate.of(2021, 5, 10);
        LocalDate dataCompra22 = LocalDate.of(2021, 6, 15);
        LocalDate dataCompra23 = LocalDate.of(2021, 6, 7);
        LocalDate dataCompra24 = LocalDate.of(2021, 7, 20);
        LocalDate dataCompra25 = LocalDate.of(2021, 10, 26);
        LocalDate dataCompra26 = LocalDate.of(2021, 8, 25);
        LocalDate dataCompra27 = LocalDate.of(2021, 9, 30);
        LocalDate dataCompra28 = LocalDate.of(2021, 10, 5);
        LocalDate dataCompra29 = LocalDate.of(2021, 11, 10);
        LocalDate dataCompra30 = LocalDate.of(2021, 12, 15);
        LocalDate dataCompra31 = LocalDate.of(2021, 8, 5);
        LocalDate dataCompra32 = LocalDate.of(2022, 3, 3);
        LocalDate dataCompra33 = LocalDate.of(2022, 11, 1);
        LocalDate dataCompra34 = LocalDate.of(2022, 10, 22);
        LocalDate dataCompra35 = LocalDate.of(2022, 1, 20);
        LocalDate dataCompra36 = LocalDate.of(2023, 7, 23);
        LocalDate dataCompra37 = LocalDate.of(2023, 3, 30);
        LocalDate dataCompra38 = LocalDate.of(2022, 9, 15);
        LocalDate dataCompra39 = LocalDate.of(2023, 10, 30);
        LocalDate dataCompra40 = LocalDate.of(2022, 2, 25);
        LocalDate dataCompra41 = LocalDate.of(2021, 8, 28);
        LocalDate dataCompra42 = LocalDate.of(2022, 4, 5);
        LocalDate dataCompra43 = LocalDate.of(2023, 11, 19);
        LocalDate dataCompra44 = LocalDate.of(2024, 3, 12);
        LocalDate dataCompra45 = LocalDate.of(2024, 3, 10);
        LocalDate dataCompra46 = LocalDate.of(2024, 2, 14);
        LocalDate dataCompra47 = LocalDate.of(2024, 2, 20);
        LocalDate dataCompra48 = LocalDate.of(2024, 2, 18);
        LocalDate dataCompra49 = LocalDate.of(2024, 3, 5);
        LocalDate dataCompra50 = LocalDate.of(2024, 1, 4);
        LocalDate dataCompra18 = LocalDate.of(2024, 3, 12);


        try {
            fachada.aumentarQuantidadeEmEstoque(livro1, 30, dataCompra1, 420.0);
            fachada.aumentarQuantidadeEmEstoque(livro1, 10, dataCompra16, 140.0);
            fachada.aumentarQuantidadeEmEstoque(livro1, 15, dataCompra10, 210.0);
            fachada.aumentarQuantidadeEmEstoque(livro1, 15, dataCompra46, 210.0);
            fachada.aumentarQuantidadeEmEstoque(livro1, 50, dataCompra39, 700.0);

            fachada.aumentarQuantidadeEmEstoque(livro2, 15, dataCompra2, 210.0);
            fachada.aumentarQuantidadeEmEstoque(livro2, 20, dataCompra35, 280.0);
            fachada.aumentarQuantidadeEmEstoque(livro2, 30, dataCompra32, 420.0);
            fachada.aumentarQuantidadeEmEstoque(livro2, 20, dataCompra49, 280.0);
            fachada.aumentarQuantidadeEmEstoque(livro2, 10, dataCompra50, 140.0);

            fachada.aumentarQuantidadeEmEstoque(livro3, 25, dataCompra3, 1475.0);
            fachada.aumentarQuantidadeEmEstoque(livro3, 20, dataCompra31, 1180.0);
            fachada.aumentarQuantidadeEmEstoque(livro3, 25, dataCompra34, 1475.0);

            fachada.aumentarQuantidadeEmEstoque(livro4, 171, dataCompra4, 4800.0);
            fachada.aumentarQuantidadeEmEstoque(livro4, 20, dataCompra18, 560.0);

            fachada.aumentarQuantidadeEmEstoque(livro5, 20, dataCompra5, 490.0);
            fachada.aumentarQuantidadeEmEstoque(livro5, 20, dataCompra31, 490.0);
            fachada.aumentarQuantidadeEmEstoque(livro5, 30, dataCompra43, 735.0);

            fachada.aumentarQuantidadeEmEstoque(livro6, 80, dataCompra6, 1725.0);

            fachada.aumentarQuantidadeEmEstoque(livro7, 60, dataCompra7, 2100.0);
            fachada.aumentarQuantidadeEmEstoque(livro7, 15, dataCompra48, 525.0);

            fachada.aumentarQuantidadeEmEstoque(livro8, 26, dataCompra47, 1185.0);
            fachada.aumentarQuantidadeEmEstoque(livro8, 40, dataCompra47, 1820.0);

            fachada.aumentarQuantidadeEmEstoque(livro9, 40, dataCompra9, 710.0);
            fachada.aumentarQuantidadeEmEstoque(livro9, 40, dataCompra49, 710.0);

            fachada.aumentarQuantidadeEmEstoque(livro10, 60, dataCompra10, 1680.0);
            fachada.aumentarQuantidadeEmEstoque(livro10, 15, dataCompra20, 420.0);

            fachada.aumentarQuantidadeEmEstoque(livro11, 62, dataCompra11, 1520.0);

            fachada.aumentarQuantidadeEmEstoque(livro12, 20, dataCompra12, 700.0);
            fachada.aumentarQuantidadeEmEstoque(livro12, 10, dataCompra12, 350.0);
            fachada.aumentarQuantidadeEmEstoque(livro12, 5, dataCompra12, 175.0);

            fachada.aumentarQuantidadeEmEstoque(livro13, 50, dataCompra13, 1575.0);

            fachada.aumentarQuantidadeEmEstoque(livro14, 10, dataCompra14, 140.0);
            fachada.aumentarQuantidadeEmEstoque(livro14, 30, dataCompra11, 420.0);

            fachada.aumentarQuantidadeEmEstoque(livro15, 19, dataCompra15, 335.0);
            fachada.aumentarQuantidadeEmEstoque(livro15, 11, dataCompra15, 195.0);

            fachada.aumentarQuantidadeEmEstoque(livro16, 20, dataCompra16, 420.0);
            fachada.aumentarQuantidadeEmEstoque(livro16, 17, dataCompra49, 360.0);

            fachada.aumentarQuantidadeEmEstoque(livro17, 30, dataCompra17, 630.0);

            fachada.aumentarQuantidadeEmEstoque(livro18, 15, dataCompra18, 385.0);
            fachada.aumentarQuantidadeEmEstoque(livro18, 15, dataCompra8, 385.0);
            fachada.aumentarQuantidadeEmEstoque(livro18, 15, dataCompra46, 385.0);

            fachada.aumentarQuantidadeEmEstoque(livro19, 50, dataCompra19, 2950.0);
            fachada.aumentarQuantidadeEmEstoque(livro19, 20, dataCompra47, 1180.0);

            fachada.aumentarQuantidadeEmEstoque(livro20, 33, dataCompra20, 810.0);
            fachada.aumentarQuantidadeEmEstoque(livro20, 10, dataCompra40, 245.0);
            fachada.aumentarQuantidadeEmEstoque(livro20, 15, dataCompra44, 370.0);

            fachada.aumentarQuantidadeEmEstoque(livro21, 26, dataCompra21, 730.0);
            fachada.aumentarQuantidadeEmEstoque(livro21, 20, dataCompra21, 560.0);

            fachada.aumentarQuantidadeEmEstoque(livro22, 30, dataCompra22, 1050.0);

            fachada.aumentarQuantidadeEmEstoque(livro23, 30, dataCompra23, 840.0);

            fachada.aumentarQuantidadeEmEstoque(livro24, 10, dataCompra24, 280.0);
            fachada.aumentarQuantidadeEmEstoque(livro24, 10, dataCompra33, 280.0);
            fachada.aumentarQuantidadeEmEstoque(livro24, 10, dataCompra41, 280.0);

            fachada.aumentarQuantidadeEmEstoque(livro25, 50, dataCompra25, 1225.0);
            fachada.aumentarQuantidadeEmEstoque(livro25, 10, dataCompra38, 1225.0);

            fachada.aumentarQuantidadeEmEstoque(livro26, 10, dataCompra26, 240.0);
            fachada.aumentarQuantidadeEmEstoque(livro26, 5, dataCompra36, 120.0);
            fachada.aumentarQuantidadeEmEstoque(livro26, 15, dataCompra37, 360.0);

            fachada.aumentarQuantidadeEmEstoque(livro27, 20, dataCompra27, 490.0);
            fachada.aumentarQuantidadeEmEstoque(livro27, 10, dataCompra42, 245.0);
            fachada.aumentarQuantidadeEmEstoque(livro27, 5, dataCompra1, 125.0);

            fachada.aumentarQuantidadeEmEstoque(livro28, 30, dataCompra28, 840.0);
            fachada.aumentarQuantidadeEmEstoque(livro28, 8, dataCompra46, 240.0);

            fachada.aumentarQuantidadeEmEstoque(livro29, 30, dataCompra29, 840.0);
            fachada.aumentarQuantidadeEmEstoque(livro29, 10, dataCompra45, 245.0);

            fachada.aumentarQuantidadeEmEstoque(livro30, 35, dataCompra30, 965.0);
            fachada.aumentarQuantidadeEmEstoque(livro30, 20, dataCompra18, 490.0);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Venda venda1 = new Venda(cliente2, dataVenda45, end2, null);
        venda1.adicionarLivro(livro1, 5);
        venda1.adicionarLivro(livro2, 2);
        venda1.adicionarLivro(livro4, 3);

        Venda venda2 = new Venda(cliente2, dataVenda46, end2, null);
        venda2.adicionarLivro(livro5, 3);
        venda2.adicionarLivro(livro9, 3);
        venda2.adicionarLivro(livro10, 3);
        venda2.adicionarLivro(livro17, 2);
        venda2.adicionarLivro(livro2, 1);

        Venda venda3 = new Venda(cliente2, dataVenda47, end2, null);
        venda3.adicionarLivro(livro6, 2);
        venda3.adicionarLivro(livro3, 2);

        Venda venda4 = new Venda(cliente5, dataVenda49, end5, null);
        venda4.adicionarLivro(livro1, 4);
        venda4.adicionarLivro(livro8, 1);
        venda4.adicionarLivro(livro4, 3);
        venda4.adicionarLivro(livro5, 3);
        venda4.adicionarLivro(livro16, 2);
        venda4.adicionarLivro(livro15, 1);

        Venda venda5 = new Venda(cliente4, dataVenda33, end4, null);
        venda5.adicionarLivro(livro1, 2);
        venda5.adicionarLivro(livro8, 3);
        venda5.adicionarLivro(livro4, 1);
        venda5.adicionarLivro(livro13, 5);

        Venda venda6 = new Venda(cliente4, dataVenda21, end4, null);
        venda6.adicionarLivro(livro1, 5);
        venda6.adicionarLivro(livro6, 2);
        venda6.adicionarLivro(livro4, 2);
        venda6.adicionarLivro(livro12, 2);

        Venda venda7 = new Venda(cliente5, dataVenda33, end5, null);
        venda7.adicionarLivro(livro3, 1);
        venda7.adicionarLivro(livro2, 1);
        venda7.adicionarLivro(livro8, 6);

        Venda venda8 = new Venda(cliente4, dataVenda39, end4, null);
        venda8.adicionarLivro(livro1, 1);
        venda8.adicionarLivro(livro4, 1);
        venda8.adicionarLivro(livro5, 2);
        venda8.adicionarLivro(livro6, 1);
        venda8.adicionarLivro(livro9, 4);
        venda8.adicionarLivro(livro10, 2);
        venda8.adicionarLivro(livro22, 1);

        Venda venda9 = new Venda(cliente1, dataVenda41, end1, null);
        venda9.adicionarLivro(livro1, 2);
        venda9.adicionarLivro(livro2, 2);
        venda9.adicionarLivro(livro4, 3);

        Venda venda10 = new Venda(cliente5, dataVenda10, end5, null);
        venda10.adicionarLivro(livro6, 1);
        venda10.adicionarLivro(livro2, 4);

        Venda venda11 = new Venda(cliente3, dataVenda12, end3, null);
        venda11.adicionarLivro(livro2, 1);
        venda11.adicionarLivro(livro3, 1);
        venda11.adicionarLivro(livro4, 4);

        Venda venda12 = new Venda(cliente3, dataVenda14, end3, null);
        venda12.adicionarLivro(livro19, 4);
        venda12.adicionarLivro(livro28, 2);
        venda12.adicionarLivro(livro4, 1);

        Venda venda13 = new Venda(cliente1, dataVenda21, end1, null);
        venda13.adicionarLivro(livro7, 5);
        venda13.adicionarLivro(livro25, 2);
        venda13.adicionarLivro(livro4, 2);

        Venda venda14 = new Venda(cliente4, dataVenda9, end4, null);
        venda14.adicionarLivro(livro1, 4);

        Venda venda15 = new Venda(cliente4, dataVenda32, end4, null);
        venda15.adicionarLivro(livro1, 2);
        venda15.adicionarLivro(livro6, 2);

        Venda venda16 = new Venda(cliente4, dataVenda36, end4, null);
        venda16.adicionarLivro(livro18, 5);
        venda16.adicionarLivro(livro2, 3);
        venda16.adicionarLivro(livro22, 1);
        venda16.adicionarLivro(livro23, 2);
        venda16.adicionarLivro(livro3, 2);

        Venda venda17 = new Venda(cliente4, dataVenda44, end4, null);
        venda17.adicionarLivro(livro3, 1);
        venda17.adicionarLivro(livro9, 1);
        venda17.adicionarLivro(livro4, 3);
        venda17.adicionarLivro(livro15, 4);

        Venda venda18 = new Venda(cliente4, dataVenda34, end4, null);
        venda18.adicionarLivro(livro16, 1);
        venda18.adicionarLivro(livro3, 1);
        venda18.adicionarLivro(livro18, 7);
        venda18.adicionarLivro(livro5, 7);
        venda18.adicionarLivro(livro25, 2);

        Venda venda19 = new Venda(cliente1, dataVenda35, end1, null);
        venda19.adicionarLivro(livro1, 2);
        venda19.adicionarLivro(livro2, 2);
        venda19.adicionarLivro(livro4, 4);

        Venda venda20 = new Venda(cliente1, dataVenda39, end1, null);
        venda20.adicionarLivro(livro1, 1);
        venda20.adicionarLivro(livro5, 4);
        venda20.adicionarLivro(livro9, 1);

        Venda venda21 = new Venda(cliente3, dataVenda48, end3, null);
        venda21.adicionarLivro(livro2, 1);
        venda21.adicionarLivro(livro3, 1);
        venda21.adicionarLivro(livro4, 2);

        Venda venda22 = new Venda(cliente3, dataVenda40, end3, null);
        venda22.adicionarLivro(livro1, 4);
        venda22.adicionarLivro(livro4, 2);

        Venda venda23 = new Venda(cliente2, dataVenda41, end2, null);
        venda23.adicionarLivro(livro2, 5);
        venda23.adicionarLivro(livro20, 2);
        venda23.adicionarLivro(livro21, 4);
        venda23.adicionarLivro(livro6, 2);

        Venda venda24 = new Venda(cliente2, dataVenda26, end2, null);
        venda24.adicionarLivro(livro1, 3);

        Venda venda25 = new Venda(cliente2, dataVenda1, end2, null);
        venda25.adicionarLivro(livro4, 1);
        venda25.adicionarLivro(livro3, 2);
        venda25.adicionarLivro(livro17, 3);
        venda25.adicionarLivro(livro19, 2);
        venda25.adicionarLivro(livro10, 4);

        Venda venda26 = new Venda(cliente4, dataVenda2, end4, null);
        venda26.adicionarLivro(livro1, 5);
        venda26.adicionarLivro(livro3, 1);
        venda26.adicionarLivro(livro4, 3);

        Venda venda27 = new Venda(cliente4, dataVenda3, end4, null);
        venda27.adicionarLivro(livro1, 2);
        venda27.adicionarLivro(livro19, 1);
        venda27.adicionarLivro(livro7, 3);
        venda27.adicionarLivro(livro18, 2);
        venda27.adicionarLivro(livro17, 1);

        Venda venda28 = new Venda(cliente4, dataVenda4, end4, null);
        venda28.adicionarLivro(livro1, 5);
        venda28.adicionarLivro(livro5, 4);
        venda28.adicionarLivro(livro4, 2);

        Venda venda29 = new Venda(cliente4, dataVenda5, end4, null);
        venda29.adicionarLivro(livro3, 3);
        venda29.adicionarLivro(livro8, 1);
        venda29.adicionarLivro(livro4, 3);

        Venda venda30 = new Venda(cliente4, dataVenda6, end4, null);
        venda30.adicionarLivro(livro1, 2);
        venda30.adicionarLivro(livro3, 1);
        venda30.adicionarLivro(livro7, 2);
        venda30.adicionarLivro(livro10, 3);
        venda30.adicionarLivro(livro12, 2);

        Venda venda31 = new Venda(cliente1, dataVenda42, end1, null);
        venda31.adicionarLivro(livro3, 2);
        venda31.adicionarLivro(livro2, 2);
        venda31.adicionarLivro(livro4, 1);

        Venda venda32 = new Venda(cliente1, dataVenda50, end1, null);
        venda32.adicionarLivro(livro1, 1);
        venda32.adicionarLivro(livro8, 4);
        venda32.adicionarLivro(livro10, 1);

        Venda venda33 = new Venda(cliente3, dataVenda15, end3, null);
        venda33.adicionarLivro(livro2, 1);
        venda33.adicionarLivro(livro6, 1);
        venda33.adicionarLivro(livro9, 3);

        Venda venda34 = new Venda(cliente3, dataVenda16, end3, null);
        venda34.adicionarLivro(livro20, 4);
        venda34.adicionarLivro(livro2, 2);
        venda34.adicionarLivro(livro5, 1);
        venda34.adicionarLivro(livro27, 2);
        venda34.adicionarLivro(livro26, 2);

        Venda venda35 = new Venda(cliente2, dataVenda17, end2, null);
        venda35.adicionarLivro(livro18, 5);
        venda35.adicionarLivro(livro2, 2);
        venda35.adicionarLivro(livro4, 1);
        venda35.adicionarLivro(livro21, 3);
        venda35.adicionarLivro(livro20, 2);

        Venda venda36 = new Venda(cliente2, dataVenda18, end2, null);
        venda36.adicionarLivro(livro1, 3);

        Venda venda37 = new Venda(cliente2, dataVenda19, end2, null);
        venda37.adicionarLivro(livro5, 2);
        venda37.adicionarLivro(livro3, 2);

        Venda venda38 = new Venda(cliente4, dataVenda20, end4, null);
        venda38.adicionarLivro(livro5, 3);
        venda38.adicionarLivro(livro3, 1);

        Venda venda39 = new Venda(cliente4, dataVenda21, end4, null);
        venda39.adicionarLivro(livro30, 2);
        venda39.adicionarLivro(livro2, 4);

        Venda venda40 = new Venda(cliente4, dataVenda26, end4, null);
        venda40.adicionarLivro(livro4, 3);
        venda40.adicionarLivro(livro2, 2);
        venda40.adicionarLivro(livro24, 2);

        Venda venda41 = new Venda(cliente4, dataVenda22, end4, null);
        venda41.adicionarLivro(livro20, 1);
        venda41.adicionarLivro(livro9, 1);
        venda41.adicionarLivro(livro4, 2);

        Venda venda42 = new Venda(cliente4, dataVenda24, end4, null);
        venda42.adicionarLivro(livro1, 2);
        venda42.adicionarLivro(livro3, 1);
        venda42.adicionarLivro(livro7, 3);
        venda42.adicionarLivro(livro10, 2);
        venda42.adicionarLivro(livro9, 1);
        venda42.adicionarLivro(livro12, 3);
        venda42.adicionarLivro(livro14, 4);

        Venda venda43 = new Venda(cliente1, dataVenda25, end1, null);
        venda43.adicionarLivro(livro3, 2);
        venda43.adicionarLivro(livro6, 3);
        venda43.adicionarLivro(livro4, 3);

        Venda venda44 = new Venda(cliente1, dataVenda26, end1, null);
        venda44.adicionarLivro(livro6, 1);
        venda44.adicionarLivro(livro7, 4);
        venda44.adicionarLivro(livro5, 1);

        Venda venda45 = new Venda(cliente3, dataVenda11, end3, null);
        venda45.adicionarLivro(livro26, 2);
        venda45.adicionarLivro(livro3, 1);
        venda45.adicionarLivro(livro9, 3);
        venda45.adicionarLivro(livro20, 3);

        Venda venda46 = new Venda(cliente3, dataVenda13, end3, null);
        venda46.adicionarLivro(livro9, 4);
        venda46.adicionarLivro(livro6, 1);
        venda46.adicionarLivro(livro4, 4);

        Venda venda47 = new Venda(cliente2, dataVenda7, end2, null);
        venda47.adicionarLivro(livro1, 3);

        Venda venda48 = new Venda(cliente2, dataVenda8, end2, null);
        venda48.adicionarLivro(livro12, 3);
        venda48.adicionarLivro(livro3, 2);

        Venda venda49 = new Venda(cliente3, dataVenda11, end3, null);
        venda49.adicionarLivro(livro2, 5);
        venda49.adicionarLivro(livro3, 1);
        venda49.adicionarLivro(livro19, 3);

        Venda venda50 = new Venda(cliente3, dataVenda13, end3, null);
        venda50.adicionarLivro(livro1, 1);
        venda50.adicionarLivro(livro6, 2);
        venda50.adicionarLivro(livro10, 3);
        venda50.adicionarLivro(livro9, 2);
        venda50.adicionarLivro(livro13, 3);
        venda50.adicionarLivro(livro7, 2);
        venda50.adicionarLivro(livro19, 5);

        Venda venda54 = new Venda(cliente2, dataVenda7, end2, null);
        venda54.adicionarLivro(livro7, 4);
        venda54.adicionarLivro(livro14, 3);
        venda54.adicionarLivro(livro3, 2);
        venda54.adicionarLivro(livro24, 1);
        venda54.adicionarLivro(livro22, 3);
        venda54.adicionarLivro(livro27, 1);

        Venda venda55 = new Venda(cliente2, dataVenda8, end2, null);
        venda55.adicionarLivro(livro4, 5);
        venda55.adicionarLivro(livro7, 2);

        Venda venda56 = new Venda(cliente3, dataVenda11, end3, null);
        venda56.adicionarLivro(livro6, 5);

        Venda venda57 = new Venda(cliente3, dataVenda13, end3, null);
        venda57.adicionarLivro(livro12, 4);
        venda57.adicionarLivro(livro6, 2);
        venda57.adicionarLivro(livro4, 3);

        Venda venda58 = new Venda(cliente2, dataVenda7, end2, null);
        venda58.adicionarLivro(livro11, 3);
        venda58.adicionarLivro(livro9, 3);
        venda58.adicionarLivro(livro12, 3);
        venda58.adicionarLivro(livro13, 3);
        venda58.adicionarLivro(livro18, 2);
        venda58.adicionarLivro(livro17, 1);
        venda58.adicionarLivro(livro19, 3);

        Venda venda59 = new Venda(cliente2, dataVenda8, end2, null);
        venda59.adicionarLivro(livro4, 5);
        venda59.adicionarLivro(livro7, 2);

        Venda venda60 = new Venda(cliente3, dataVenda11, end3, null);
        venda60.adicionarLivro(livro6, 2);
        venda60.adicionarLivro(livro9, 1);
        venda60.adicionarLivro(livro4, 1);

        Venda venda61 = new Venda(cliente3, dataVenda13, end3, null);
        venda61.adicionarLivro(livro1, 4);
        venda61.adicionarLivro(livro6, 1);
        venda61.adicionarLivro(livro3, 3);

        Venda venda62 = new Venda(cliente2, dataVenda7, end2, null);
        venda62.adicionarLivro(livro10, 3);

        Venda venda63 = new Venda(cliente2, dataVenda28, end2, null);
        venda63.adicionarLivro(livro4, 2);
        venda63.adicionarLivro(livro3, 2);

        Venda venda64 = new Venda(cliente3, dataVenda26, end3, null);
        venda64.adicionarLivro(livro22, 4);
        venda64.adicionarLivro(livro5, 1);
        venda64.adicionarLivro(livro29, 3);

        Venda venda65 = new Venda(cliente3, dataVenda27, end3, null);
        venda65.adicionarLivro(livro12, 4);
        venda65.adicionarLivro(livro6, 3);
        venda65.adicionarLivro(livro30, 3);
        venda65.adicionarLivro(livro9, 3);

        Venda venda66 = new Venda(cliente2, dataVenda29, end2, null);
        venda66.adicionarLivro(livro1, 1);
        venda66.adicionarLivro(livro25, 3);
        venda66.adicionarLivro(livro23, 1);
        venda66.adicionarLivro(livro28, 2);

        Venda venda67 = new Venda(cliente2, dataVenda30, end2, null);
        venda67.adicionarLivro(livro4, 2);
        venda67.adicionarLivro(livro6, 2);
        venda67.adicionarLivro(livro30, 2);

        Venda venda68 = new Venda(cliente3, dataVenda31, end3, null);
        venda68.adicionarLivro(livro2, 1);
        venda68.adicionarLivro(livro3, 1);
        venda68.adicionarLivro(livro4, 3);

        Venda venda69 = new Venda(cliente3, dataVenda38, end3, null);
        venda69.adicionarLivro(livro29, 2);
        venda69.adicionarLivro(livro6, 2);
        venda69.adicionarLivro(livro5, 3);
        venda69.adicionarLivro(livro1, 2);
        venda69.adicionarLivro(livro2, 1);
        venda69.adicionarLivro(livro30, 4);

        Venda venda70 = new Venda(cliente2, dataVenda7, end2, null);
        venda70.adicionarLivro(livro12, 3);
        venda70.adicionarLivro(livro16, 1);
        venda70.adicionarLivro(livro11, 3);
        venda70.adicionarLivro(livro30, 2);
        venda70.adicionarLivro(livro27, 1);
        venda70.adicionarLivro(livro13, 1);
        venda70.adicionarLivro(livro28, 2);

        Venda venda71 = new Venda(cliente2, dataVenda8, end2, null);
        venda71.adicionarLivro(livro4, 3);
        venda71.adicionarLivro(livro20, 2);
        venda71.adicionarLivro(livro6, 2);
        venda71.adicionarLivro(livro30, 2);

        Venda venda72 = new Venda(cliente5, dataVenda51, endereco2, null);
        venda72.adicionarLivro(livro14, 2);
        venda72.adicionarLivro(livro19, 3);
        venda72.adicionarLivro(livro16, 1);

        Venda venda73 = new Venda(cliente5 , dataVenda52, endereco1, null);
        venda73.adicionarLivro(livro9, 1);
        venda73.adicionarLivro(livro17, 1);
        venda73.adicionarLivro(livro18, 2);

        Venda venda74 = new Venda(cliente4, dataVenda53, end4, null);
        venda74.adicionarLivro(livro19, 1);
        venda74.adicionarLivro(livro12, 3);
        venda74.adicionarLivro(livro13, 2);
        venda74.adicionarLivro(livro27, 2);
        venda74.adicionarLivro(livro11,1);

        Venda venda75 = new Venda(cliente4, dataVenda54, end4, null);
        venda75.adicionarLivro(livro19, 1);
        venda75.adicionarLivro(livro2, 2);
        venda75.adicionarLivro(livro30, 2);
        venda75.adicionarLivro(livro10, 3);

        Venda venda76 = new Venda(cliente4, dataVenda55, end4, null);
        venda76.adicionarLivro(livro7, 1);

        Venda venda77 = new Venda(cliente1, dataVenda56, end4, null);
        venda77.adicionarLivro(livro11, 3);
        venda77.adicionarLivro(livro20, 2);
        venda77.adicionarLivro(livro21, 4);
        venda77.adicionarLivro(livro25, 1);
        venda77.adicionarLivro(livro16, 1);

        Venda venda78 = new Venda(cliente1, dataVenda57, end4, null);
        venda78.adicionarLivro(livro13, 1);
        venda78.adicionarLivro(livro12, 1);

        Venda venda79 = new Venda(cliente1, dataVenda58, end4, null);
        venda79.adicionarLivro(livro29, 1);
        venda79.adicionarLivro(livro4, 2);
        venda79.adicionarLivro(livro6, 4);
        venda79.adicionarLivro(livro28, 1);
        venda79.adicionarLivro(livro30, 2);
        venda79.adicionarLivro(livro23, 1);

        Venda venda80 = new Venda(cliente5, dataVenda59, end4, null);
        venda80.adicionarLivro(livro22, 2);
        venda80.adicionarLivro(livro20, 3);
        venda80.adicionarLivro(livro12, 1);
        venda80.adicionarLivro(livro19, 2);
        venda80.adicionarLivro(livro27, 1);

        Venda venda81 = new Venda(cliente5, dataVenda60, end4, null);
        venda81.adicionarLivro(livro16, 1);
        venda81.adicionarLivro(livro18, 2);

        Venda venda82 = new Venda(cliente3, dataVenda61, end4, null);
        venda82.adicionarLivro(livro19, 1);
        venda82.adicionarLivro(livro29, 3);
        venda82.adicionarLivro(livro15, 1);

        Venda venda83 = new Venda(cliente3, dataVenda62, end4, null);
        venda83.adicionarLivro(livro26, 1);

        Venda venda84 = new Venda(cliente3, dataVenda63, end4, null);
        venda84.adicionarLivro(livro28, 3);
        venda84.adicionarLivro(livro19, 1);
        venda84.adicionarLivro(livro24, 2);
        venda84.adicionarLivro(livro2, 1);

        Venda venda85 = new Venda(cliente2, dataVenda64, end4, null);
        venda85.adicionarLivro(livro14, 1);
        venda85.adicionarLivro(livro15, 1);

        Venda venda86 = new Venda(cliente1, dataVenda65, end4, null);
        venda86.adicionarLivro(livro27, 1);
        venda86.adicionarLivro(livro26, 2);
        venda86.adicionarLivro(livro20, 3);

        Venda venda87 = new Venda(cliente1, dataVenda66, end4, null);
        venda87.adicionarLivro(livro14, 4);

        Venda venda88 = new Venda(cliente1, dataVenda67, end4, null);
        venda88.adicionarLivro(livro13, 2);
        venda88.adicionarLivro(livro11, 1);
        venda88.adicionarLivro(livro10, 4);
        venda88.adicionarLivro(livro15, 3);

        Venda venda89 = new Venda(cliente4, dataVenda68, end4, null);
        venda89.adicionarLivro(livro23, 1);
        venda89.adicionarLivro(livro22, 1);

        Venda venda90 = new Venda(cliente4, dataVenda69, end4, null);
        venda90.adicionarLivro(livro28, 4);
        venda90.adicionarLivro(livro7, 3);

        Venda venda91 = new Venda(cliente2, dataVenda70, end4, null);
        venda90.adicionarLivro(livro9, 2);
        venda90.adicionarLivro(livro1, 1);

        Venda venda92 = new Venda(cliente2, dataVenda7, end4, null);
        venda92.adicionarLivro(livro21, 2);
        venda92.adicionarLivro(livro1, 1);
        venda92.adicionarLivro(livro4, 1);

        Venda venda93 = new Venda(cliente3, dataVenda42, end4, null);
        venda93.adicionarLivro(livro7, 2);
        venda93.adicionarLivro(livro19, 3);
        venda93.adicionarLivro(livro1, 4);
        venda93.adicionarLivro(livro18, 1);

        Venda venda94= new Venda(cliente3, dataVenda23, end4, null);
        venda94.adicionarLivro(livro20, 2);
        venda94.adicionarLivro(livro8, 1);
        venda94.adicionarLivro(livro4, 2);

        Venda venda95 = new Venda(cliente1, dataVenda39, end4, null);
        venda95.adicionarLivro(livro19, 1);

        Venda venda96 = new Venda(cliente1, dataVenda12, end4, null);
        venda96.adicionarLivro(livro16, 3);

        Venda venda97 = new Venda(cliente4, dataVenda37, end4, null);
        venda97.adicionarLivro(livro30, 1);

        Venda venda98 = new Venda(cliente5, dataVenda47, end4, null);
        venda98.adicionarLivro(livro3, 1);
        venda98.adicionarLivro(livro23, 1);
        venda98.adicionarLivro(livro21, 1);

        Venda venda99 = new Venda(cliente5, dataVenda43, end4, null);
        venda99.adicionarLivro(livro20, 4);
        venda99.adicionarLivro(livro18, 1);

        Venda venda100 = new Venda(cliente4, dataVenda23, end4, null);
        venda100.adicionarLivro(livro19, 1);
        venda100.adicionarLivro(livro10, 3);
        venda100.adicionarLivro(livro11, 1);
        venda100.adicionarLivro(livro6, 2);

        fachada.inserirVenda(venda1);
        fachada.inserirVenda(venda2);
        fachada.inserirVenda(venda3);
        fachada.inserirVenda(venda4);
        fachada.inserirVenda(venda5);
        fachada.inserirVenda(venda6);
        fachada.inserirVenda(venda7);
        fachada.inserirVenda(venda8);
        fachada.inserirVenda(venda9);
        fachada.inserirVenda(venda10);
        fachada.inserirVenda(venda11);
        fachada.inserirVenda(venda12);
        fachada.inserirVenda(venda13);
        fachada.inserirVenda(venda14);
        fachada.inserirVenda(venda15);
        fachada.inserirVenda(venda16);
        fachada.inserirVenda(venda17);
        fachada.inserirVenda(venda18);
        fachada.inserirVenda(venda19);
        fachada.inserirVenda(venda20);
        fachada.inserirVenda(venda21);
        fachada.inserirVenda(venda22);
        fachada.inserirVenda(venda23);
        fachada.inserirVenda(venda24);
        fachada.inserirVenda(venda25);
        fachada.inserirVenda(venda26);
        fachada.inserirVenda(venda27);
        fachada.inserirVenda(venda28);
        fachada.inserirVenda(venda29);
        fachada.inserirVenda(venda30);
        fachada.inserirVenda(venda31);
        fachada.inserirVenda(venda32);
        fachada.inserirVenda(venda33);
        fachada.inserirVenda(venda34);
        fachada.inserirVenda(venda35);
        fachada.inserirVenda(venda36);
        fachada.inserirVenda(venda37);
        fachada.inserirVenda(venda38);
        fachada.inserirVenda(venda39);
        fachada.inserirVenda(venda40);
        fachada.inserirVenda(venda41);
        fachada.inserirVenda(venda42);
        fachada.inserirVenda(venda43);
        fachada.inserirVenda(venda44);
        fachada.inserirVenda(venda45);
        fachada.inserirVenda(venda46);
        fachada.inserirVenda(venda47);
        fachada.inserirVenda(venda48);
        fachada.inserirVenda(venda49);
        fachada.inserirVenda(venda50);
        fachada.inserirVenda(venda54);
        fachada.inserirVenda(venda55);
        fachada.inserirVenda(venda56);
        fachada.inserirVenda(venda57);
        fachada.inserirVenda(venda58);
        fachada.inserirVenda(venda59);
        fachada.inserirVenda(venda60);
        fachada.inserirVenda(venda61);
        fachada.inserirVenda(venda62);
        fachada.inserirVenda(venda63);
        fachada.inserirVenda(venda64);
        fachada.inserirVenda(venda65);
        fachada.inserirVenda(venda66);
        fachada.inserirVenda(venda67);
        fachada.inserirVenda(venda68);
        fachada.inserirVenda(venda69);
        fachada.inserirVenda(venda70);
        fachada.inserirVenda(venda71);
        fachada.inserirVenda(venda72);
        fachada.inserirVenda(venda73);
        fachada.inserirVenda(venda74);
        fachada.inserirVenda(venda75);
        fachada.inserirVenda(venda76);
        fachada.inserirVenda(venda77);
        fachada.inserirVenda(venda78);
        fachada.inserirVenda(venda79);
        fachada.inserirVenda(venda80);
        fachada.inserirVenda(venda81);
        fachada.inserirVenda(venda82);
        fachada.inserirVenda(venda83);
        fachada.inserirVenda(venda84);
        fachada.inserirVenda(venda85);
        fachada.inserirVenda(venda86);
        fachada.inserirVenda(venda87);
        fachada.inserirVenda(venda88);
        fachada.inserirVenda(venda89);
        fachada.inserirVenda(venda90);
        fachada.inserirVenda(venda91);
        fachada.inserirVenda(venda92);
        fachada.inserirVenda(venda93);
        fachada.inserirVenda(venda94);
        fachada.inserirVenda(venda95);
        fachada.inserirVenda(venda96);
        fachada.inserirVenda(venda97);
        fachada.inserirVenda(venda98);
        fachada.inserirVenda(venda99);
        fachada.inserirVenda(venda100);

        try {
            fachada.diminuirQuantidadeEmEstoque(livro1, 70);
            fachada.diminuirQuantidadeEmEstoque(livro2, 47);
            fachada.diminuirQuantidadeEmEstoque(livro3, 31);
            fachada.diminuirQuantidadeEmEstoque(livro4, 82);
            fachada.diminuirQuantidadeEmEstoque(livro5, 34);
            fachada.diminuirQuantidadeEmEstoque(livro6, 39);
            fachada.diminuirQuantidadeEmEstoque(livro7, 29);
            fachada.diminuirQuantidadeEmEstoque(livro8, 16);
            fachada.diminuirQuantidadeEmEstoque(livro9, 33);
            fachada.diminuirQuantidadeEmEstoque(livro10, 31);
            fachada.diminuirQuantidadeEmEstoque(livro11, 12);
            fachada.diminuirQuantidadeEmEstoque(livro12, 29);
            fachada.diminuirQuantidadeEmEstoque(livro13, 17);
            fachada.diminuirQuantidadeEmEstoque(livro14, 15);
            fachada.diminuirQuantidadeEmEstoque(livro15, 10);
            fachada.diminuirQuantidadeEmEstoque(livro16, 7);
            fachada.diminuirQuantidadeEmEstoque(livro17, 8);
            fachada.diminuirQuantidadeEmEstoque(livro18, 27);
            fachada.diminuirQuantidadeEmEstoque(livro19, 32);
            fachada.diminuirQuantidadeEmEstoque(livro20, 28);
            fachada.diminuirQuantidadeEmEstoque(livro21, 14);
            fachada.diminuirQuantidadeEmEstoque(livro22, 7);
            fachada.diminuirQuantidadeEmEstoque(livro23, 6);
            fachada.diminuirQuantidadeEmEstoque(livro24, 5);
            fachada.diminuirQuantidadeEmEstoque(livro25, 8);
            fachada.diminuirQuantidadeEmEstoque(livro26, 7);
            fachada.diminuirQuantidadeEmEstoque(livro27, 8);
            fachada.diminuirQuantidadeEmEstoque(livro28, 13);
            fachada.diminuirQuantidadeEmEstoque(livro29, 9);
            fachada.diminuirQuantidadeEmEstoque(livro30, 20);

            //TODO FAZER O RESTO
            fachada.diminuirQuantidadeEmEstoque(livro14, 15);
            fachada.diminuirQuantidadeEmEstoque(livro14, 15);
            fachada.diminuirQuantidadeEmEstoque(livro14, 15);
            fachada.diminuirQuantidadeEmEstoque(livro14, 15);
            fachada.diminuirQuantidadeEmEstoque(livro14, 15);
        } catch (EstoqueInsuficienteException | ValorInvalidoException e) {
            System.out.println(e.getMessage());        }

        Promocao promocao1 = new Promocao("Livros 5", 5, 5, LocalDate.now(), LocalDate.now().plusDays(10));
        Promocao promocao2 = new Promocao("Livros 10", 10, 10, LocalDate.now(), LocalDate.now().plusDays(30));
        Promocao promocao3 = new Promocao("Livros 20", 20, 15, LocalDate.now(), LocalDate.now().plusDays(30));

        try {
            fachada.inserirPromocao(promocao1);
            fachada.inserirPromocao(promocao2);
            fachada.inserirPromocao(promocao3);
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
            int bytesLidos;
            while ((bytesLidos = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesLidos);
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