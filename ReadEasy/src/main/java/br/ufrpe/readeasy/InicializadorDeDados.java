package br.ufrpe.readeasy;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.business.ControladorUsuario;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InicializadorDeDados {
    public static void inicializarDados(){
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        String data = "2001-08-23";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataNasc = LocalDate.parse(data, formatter);
        Endereco endereco1 = new Endereco(123456789, "Rua Fictícia, 123", "Jardim São Paulo" , "Recife", "Pernambuco");
        Endereco endereco2 = new Endereco(987654321, "Rua Fictícia, 456", "le blon" , "Rio de Janeiro", "Rio de Janeiro");
        Endereco endereco3 = new Endereco(147258369, "Rua Fictícia, 789", "copacabana" , "Rio de Janeiro", "Rio de Janeiro");
        TipoFornecedor tipoFornecedor1 = TipoFornecedor.DOADOR_ANONIMO;
        TipoFornecedor tipoFornecedor2 = TipoFornecedor.DISTRIBUIDORA_DE_LIVRO;
        TipoFornecedor tipoFornecedor3 = TipoFornecedor.ESCRITOR_INDEPENDENTE;
        Fornecedor fornecedor1 = new Fornecedor("Jão", "1234567", dataNasc,"Jvfrost", "1234" ,endereco1, "912345678", tipoFornecedor1);
        Fornecedor fornecedor2 = new Fornecedor("lulu", "123456", dataNasc,"luluKillerPig123", "1234" ,endereco2, "987654321", tipoFornecedor2);
        Fornecedor fornecedor3 = new Fornecedor("Ronyzito", "1234568", dataNasc,"ronald wesley", "1234" ,endereco3, "987654321", tipoFornecedor3);
        Funcionario funcionario1 = new Funcionario("Ronyzito", "1234568515", dataNasc,"adm2", "1234" ,endereco3, "987654321", true, null);
        Funcionario funcionario2 = new Funcionario("Ronyzito", "1234568515", dataNasc,"ronyzito", "1234" ,endereco3, "987654321", false, funcionario1);


        String urlS1 = "https://lojasemear.com.br/product_images/u/691/p7733__91582_std.jpg";
        String urlS2 = "https://m.media-amazon.com/images/I/81PbdwXqKkL._SL1500_.jpg";
        String urlS3 = "https://m.media-amazon.com/images/I/7158aW38zxL._AC_UF1000,1000_QL80_.jpg";
        String urlS4 = "https://m.media-amazon.com/images/I/81TmHlRleJL._SL1500_.jpg";
        String urlS5 = "https://m.media-amazon.com/images/I/71IIA44vw1L._SL1000_.jpg";
        String urlS6 = "https://m.media-amazon.com/images/I/81uRHF8TGfL._SL1500_.jpg";
        String urlS7 = "https://m.media-amazon.com/images/I/81ObqL6bR5S._SL1500_.jpg";
        String urlS8 = "https://m.media-amazon.com/images/I/814J+usGFjL._SL1500_.jpg";

        URL url1 = null;
        URL url2 = null;
        URL url3 = null;
        URL url4 = null;
        URL url5 = null;
        URL url6 = null;
        URL url7 = null;
        URL url8 = null;

        try {
            url1 = new URL(urlS1);
            url2 = new URL(urlS2);
            url3 = new URL(urlS3);
            url4 = new URL(urlS4);
            url5 = new URL(urlS5);
            url6 = new URL(urlS6);
            url7 = new URL(urlS7);
            url8 = new URL(urlS8);


        } catch (MalformedURLException e) {
            System.out.println("URL mal formada.");
        }

        Livro livro1 = new Livro("Memórias póstumas De Brás Cubas", "Machado De Assis", 30, fornecedor1, url1);
        Livro livro2 = new Livro("Harry Potter e as relíquias da morte", "J.K. Rowling", 20, fornecedor1, url2);
        Livro livro3 = new Livro("As crônicas de narnia o leão, a Feiticeira e o Guarda roupa", "C.S. Lewis", 70, fornecedor1, url3);
        Livro livro4 = new Livro("Jujutsu Kaisen vol. 1", "Gege Akutami", 10, fornecedor2, url4);
        Livro livro5 = new Livro("Dragon Ball vol. 1", "Akira Toriyama", 79.50, fornecedor2, url5);
        Livro livro6 = new Livro("Yuyu Hakusho vol. 1", "Yoshihiro Togashi", 29.99, fornecedor2, url6);
        Livro livro7 = new Livro("Dom Casmurro", "Machado de Assis", 50, fornecedor3, url7);
        Livro livro8 = new Livro("Guerra e paz", "Liev Tolstói", 10, fornecedor3, url8);

        try {
            servidorReadEasy.adicionarLivro(livro1);
            servidorReadEasy.adicionarLivro(livro2);
            servidorReadEasy.adicionarLivro(livro3);
            servidorReadEasy.adicionarLivro(livro4);
            servidorReadEasy.adicionarLivro(livro5);
            servidorReadEasy.adicionarLivro(livro6);
            servidorReadEasy.adicionarLivro(livro7);
            servidorReadEasy.adicionarLivro(livro8);
        } catch ( PrecoInvalidoException | LivroExistenteException e) {
            System.out.println(e.getMessage());
        }

        ControladorUsuario controladorUsuario = ControladorUsuario.getInstance();

        try {
            servidorReadEasy.cadastrarUsuario(fornecedor1);
            servidorReadEasy.cadastrarUsuario(fornecedor2);
            servidorReadEasy.cadastrarUsuario(fornecedor3);
            servidorReadEasy.cadastrarUsuario(funcionario2);

        } catch (TipoUsuarioInvalidoException | MenorDeIdadeException | DataInvalidaException | CampoVazioException | UsuarioExistenteException | UsuarioNuloException e) {
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
            servidorReadEasy.adicionarGenero(livro1, genero1);
            servidorReadEasy.adicionarGenero(livro6, genero2);
            servidorReadEasy.adicionarGenero(livro2, genero2);
            servidorReadEasy.adicionarGenero(livro3, genero1);
            servidorReadEasy.adicionarGenero(livro3, genero2);
            servidorReadEasy.adicionarGenero(livro1, genero4);
            servidorReadEasy.adicionarGenero(livro1, genero3);
            servidorReadEasy.adicionarGenero(livro6, genero1);
            servidorReadEasy.adicionarGenero(livro4, genero5);
            servidorReadEasy.adicionarGenero(livro5, genero7);
            servidorReadEasy.adicionarGenero(livro2, genero4);
            servidorReadEasy.adicionarGenero(livro7, genero2);
            servidorReadEasy.adicionarGenero(livro8, genero4);
            servidorReadEasy.adicionarGenero(livro4, genero6);
            servidorReadEasy.adicionarGenero(livro6, genero5);
            servidorReadEasy.adicionarGenero(livro6, genero6);
            servidorReadEasy.adicionarGenero(livro2, genero3);
            servidorReadEasy.adicionarGenero(livro3, genero4);
            servidorReadEasy.adicionarGenero(livro3, genero6);
            servidorReadEasy.adicionarGenero(livro8, genero8);
            servidorReadEasy.adicionarGenero(livro1, genero7);
            servidorReadEasy.adicionarGenero(livro5, genero3);
            servidorReadEasy.adicionarGenero(livro4, genero2);
            servidorReadEasy.adicionarGenero(livro5, genero8);
            servidorReadEasy.adicionarGenero(livro1, genero6);
            servidorReadEasy.adicionarGenero(livro7, genero8);
            servidorReadEasy.adicionarGenero(livro7, genero6);
            servidorReadEasy.adicionarGenero(livro1, genero8);
            servidorReadEasy.adicionarGenero(livro8, genero1);
            servidorReadEasy.adicionarGenero(livro4, genero3);
        } catch (GeneroExistenteException e) {
            throw new RuntimeException(e);
        }

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
        Cliente cliente4 = new Cliente("Leandro", "979798453555", LocalDate.of(1998, 2,1),
                "prof leandro123", "tlou69", end4, "40028922");
        Cliente cliente5 = new Cliente("jão", "979798453", LocalDate.of(1998, 2,1),
                "jv.moraes", "tlou69", end4, "40028922");


        LocalDateTime data1 = LocalDateTime.of(2024, 2, 4, 12, 30);
        LocalDateTime data2 = LocalDateTime.of(2024, 2, 4, 13, 45);
        LocalDateTime data3 = LocalDateTime.of(2024, 2, 4, 14, 0);
        LocalDateTime data4 = LocalDateTime.of(2024, 2, 4, 15, 15);
        LocalDateTime data5 = LocalDateTime.of(2024, 1, 12, 16, 30);
        LocalDateTime data6 = LocalDateTime.of(2023, 6, 28, 10, 45);
        LocalDateTime data7 = LocalDateTime.of(2020, 7, 9, 11, 0);
        LocalDateTime data8 = LocalDateTime.of(2023, 8, 16, 12, 15);
        LocalDateTime data9 = LocalDateTime.of(2023, 9, 22, 13, 30);
        LocalDateTime data10 = LocalDateTime.of(2021, 10, 8, 14, 45);
        LocalDateTime data11 = LocalDateTime.of(2021, 11, 5, 15, 0);
        LocalDateTime data12 = LocalDateTime.of(2021, 12, 18, 16, 15);
        LocalDateTime data13 = LocalDateTime.of(2021, 1, 5, 12, 30);
        LocalDateTime data14 = LocalDateTime.of(2021, 2, 2, 13, 45);
        LocalDateTime data15 = LocalDateTime.of(2021, 3, 10, 14, 0);
        LocalDateTime data16 = LocalDateTime.of(2021, 4, 5, 15, 15);
        LocalDateTime data17 = LocalDateTime.of(2024, 1, 12, 16, 30);
        LocalDateTime data18 = LocalDateTime.of(2024, 1, 28, 10, 45);
        LocalDateTime data19 = LocalDateTime.of(2024, 2, 4, 11, 0);
        LocalDateTime data20 = LocalDateTime.of(2024, 1, 16, 12, 15);
        LocalDateTime data21 = LocalDateTime.of(2024, 1, 22, 13, 30);
        LocalDateTime data22 = LocalDateTime.of(2024, 1, 1, 14, 45);
        LocalDateTime data23 = LocalDateTime.of(2024, 2, 4, 15, 0);
        LocalDateTime data24 = LocalDateTime.of(2024, 1, 4, 16, 15);
        LocalDateTime data25 = LocalDateTime.of(2024, 2, 1, 12, 30);
        LocalDateTime data26 = LocalDateTime.of(2023, 2, 18, 13, 45);
        LocalDateTime data27 = LocalDateTime.of(2020, 3, 10, 14, 0);
        LocalDateTime data28 = LocalDateTime.of(2020, 4, 5, 15, 15);
        LocalDateTime data29 = LocalDateTime.of(2024, 2, 1, 16, 30);
        LocalDateTime data30 = LocalDateTime.of(2024, 2, 1, 10, 45);
        LocalDateTime data31 = LocalDateTime.of(2023, 7, 9, 11, 0);
        LocalDateTime data32 = LocalDateTime.of(2023, 8, 16, 12, 15);
        LocalDateTime data33 = LocalDateTime.of(2023, 9, 22, 13, 30);
        LocalDateTime data34 = LocalDateTime.of(2024, 2, 5, 14, 45);
        LocalDateTime data35 = LocalDateTime.of(2023, 11, 5, 15, 0);
        LocalDateTime data36 = LocalDateTime.of(2024, 2, 3, 16, 15);
        LocalDateTime data37 = LocalDateTime.of(2023, 1, 5, 12, 30);
        LocalDateTime data38 = LocalDateTime.of(2023, 2, 18, 13, 45);
        LocalDateTime data39 = LocalDateTime.of(2022, 3, 10, 14, 0);
        LocalDateTime data40 = LocalDateTime.of(2024, 2, 2, 15, 15);
        LocalDateTime data41 = LocalDateTime.of(2023, 5, 12, 16, 30);
        LocalDateTime data42 = LocalDateTime.of(2022, 6, 28, 10, 45);
        LocalDateTime data43 = LocalDateTime.of(2022, 7, 9, 11, 0);
        LocalDateTime data44 = LocalDateTime.of(2022, 8, 16, 12, 15);
        LocalDateTime data45 = LocalDateTime.of(2022, 9, 22, 13, 30);
        LocalDateTime data46 = LocalDateTime.of(2022, 10, 8, 14, 45);
        LocalDateTime data47 = LocalDateTime.of(2021, 11, 5, 15, 0);
        LocalDateTime data48 = LocalDateTime.of(2021, 12, 18, 16, 15);

        Venda venda1 = new Venda(cliente2, data1);
        venda1.adicionarLivro(livro1, 5);
        venda1.adicionarLivro(livro2, 2);
        venda1.adicionarLivro(livro4, 3);

        Venda venda2 = new Venda(cliente2, data2);
        venda2.adicionarLivro(livro5, 3);

        Venda venda3 = new Venda(cliente2, data3);
        venda3.adicionarLivro(livro6, 5);
        venda3.adicionarLivro(livro3, 2);

        Venda venda4 = new Venda(cliente5, data4);
        venda4.adicionarLivro(livro1, 5);
        venda4.adicionarLivro(livro8, 1);
        venda4.adicionarLivro(livro4, 3);

        Venda venda5 = new Venda(cliente4, data5);
        venda5.adicionarLivro(livro1, 2);
        venda5.adicionarLivro(livro8, 9);
        venda5.adicionarLivro(livro4, 3);

        Venda venda6 = new Venda(cliente4, data6);
        venda6.adicionarLivro(livro1, 5);
        venda6.adicionarLivro(livro6, 7);
        venda6.adicionarLivro(livro4, 2);

        Venda venda7 = new Venda(cliente5, data7);
        venda7.adicionarLivro(livro3, 11);
        venda7.adicionarLivro(livro2, 1);
        venda7.adicionarLivro(livro8, 6);

        Venda venda8 = new Venda(cliente4, data8);
        venda8.adicionarLivro(livro1, 21);
        venda8.adicionarLivro(livro4, 1);
        venda8.adicionarLivro(livro5, 7);

        Venda venda9 = new Venda(cliente1,data9);
        venda9.adicionarLivro(livro1, 2);
        venda9.adicionarLivro(livro2, 12);
        venda9.adicionarLivro(livro4, 3);

        Venda venda10 = new Venda(cliente5, data10);
        venda10.adicionarLivro(livro6, 1);
        venda10.adicionarLivro(livro2, 4);
        venda10.adicionarLivro(livro4, 1);

        Venda venda11 = new Venda(cliente3, data11);
        venda11.adicionarLivro(livro2, 10);
        venda11.adicionarLivro(livro3, 1);
        venda11.adicionarLivro(livro4, 3);

        Venda venda12 = new Venda(cliente3, data12);
        venda12.adicionarLivro(livro1, 4);
        venda12.adicionarLivro(livro8, 9);
        venda12.adicionarLivro(livro4, 3);

        Venda venda13 = new Venda(cliente1, data13);
        venda13.adicionarLivro(livro7, 5);
        venda13.adicionarLivro(livro2, 2);
        venda13.adicionarLivro(livro4, 3);

        Venda venda14 = new Venda(cliente4, data16);
        venda14.adicionarLivro(livro1, 5);
        venda14.adicionarLivro(livro3, 1);
        venda14.adicionarLivro(livro4, 3);

        Venda venda15 = new Venda(cliente4, data17);
        venda15.adicionarLivro(livro1, 2);
        venda15.adicionarLivro(livro6, 9);
        venda15.adicionarLivro(livro4, 3);

        Venda venda16 = new Venda(cliente4, data18);
        venda16.adicionarLivro(livro1, 5);
        venda16.adicionarLivro(livro2, 7);
        venda16.adicionarLivro(livro4, 2);

        Venda venda17 = new Venda(cliente4, data19);
        venda17.adicionarLivro(livro3, 11);
        venda17.adicionarLivro(livro2, 1);
        venda17.adicionarLivro(livro4, 6);

        Venda venda18 = new Venda(cliente4, data20);
        venda18.adicionarLivro(livro1, 21);
        venda18.adicionarLivro(livro3, 1);
        venda18.adicionarLivro(livro5, 7);

        Venda venda19 = new Venda(cliente1,data21);
        venda19.adicionarLivro(livro1, 2);
        venda19.adicionarLivro(livro2, 12);
        venda19.adicionarLivro(livro4, 3);

        Venda venda20 = new Venda(cliente1, data22);
        venda20.adicionarLivro(livro1, 1);
        venda20.adicionarLivro(livro5, 4);
        venda20.adicionarLivro(livro4, 1);

        Venda venda21 = new Venda(cliente3, data23);
        venda21.adicionarLivro(livro2, 10);
        venda21.adicionarLivro(livro3, 1);
        venda21.adicionarLivro(livro4, 3);

        Venda venda22 = new Venda(cliente3, data24);
        venda22.adicionarLivro(livro1, 4);
        venda22.adicionarLivro(livro4, 9);
        venda22.adicionarLivro(livro4, 3);

        Venda venda23 = new Venda(cliente2, data25);
        venda23.adicionarLivro(livro2, 5);
        venda23.adicionarLivro(livro2, 2);
        venda23.adicionarLivro(livro4, 3);

        Venda venda24 = new Venda(cliente2, data26);
        venda24.adicionarLivro(livro1, 3);

        Venda venda25 = new Venda(cliente2, data27);
        venda25.adicionarLivro(livro4, 5);
        venda25.adicionarLivro(livro3, 2);

        Venda venda26 = new Venda(cliente4, data28);
        venda26.adicionarLivro(livro1, 5);
        venda26.adicionarLivro(livro3, 1);
        venda26.adicionarLivro(livro4, 3);

        Venda venda27 = new Venda(cliente4, data29);
        venda27.adicionarLivro(livro1, 2);
        venda27.adicionarLivro(livro2, 9);
        venda27.adicionarLivro(livro7, 3);

        Venda venda28 = new Venda(cliente4, data30);
        venda28.adicionarLivro(livro1, 5);
        venda28.adicionarLivro(livro5, 7);
        venda28.adicionarLivro(livro4, 2);

        Venda venda29 = new Venda(cliente4, data31);
        venda29.adicionarLivro(livro3, 11);
        venda29.adicionarLivro(livro8, 1);
        venda29.adicionarLivro(livro4, 6);

        Venda venda30 = new Venda(cliente4, data32);
        venda30.adicionarLivro(livro1, 21);
        venda30.adicionarLivro(livro3, 1);
        venda30.adicionarLivro(livro4, 7);

        Venda venda31 = new Venda(cliente1,data33);
        venda31.adicionarLivro(livro3, 2);
        venda31.adicionarLivro(livro2, 12);
        venda31.adicionarLivro(livro4, 3);

        Venda venda32 = new Venda(cliente1, data34);
        venda32.adicionarLivro(livro1, 1);
        venda32.adicionarLivro(livro8, 4);
        venda32.adicionarLivro(livro4, 1);

        Venda venda33 = new Venda(cliente3, data35);
        venda33.adicionarLivro(livro2, 10);
        venda33.adicionarLivro(livro6, 1);
        venda33.adicionarLivro(livro4, 3);

        Venda venda34 = new Venda(cliente3, data36);
        venda34.adicionarLivro(livro1, 4);
        venda34.adicionarLivro(livro2, 9);
        venda34.adicionarLivro(livro4, 3);

        Venda venda35 = new Venda(cliente2, data37);
        venda35.adicionarLivro(livro1, 5);
        venda35.adicionarLivro(livro2, 2);
        venda35.adicionarLivro(livro4, 3);

        Venda venda36 = new Venda(cliente2, data38);
        venda36.adicionarLivro(livro1, 3);

        Venda venda37 = new Venda(cliente2, data39);
        venda37.adicionarLivro(livro5, 5);
        venda37.adicionarLivro(livro3, 2);

        Venda venda38 = new Venda(cliente4, data40);
        venda38.adicionarLivro(livro1, 5);
        venda38.adicionarLivro(livro3, 1);
        venda38.adicionarLivro(livro2, 3);

        Venda venda39 = new Venda(cliente4, data41);
        venda39.adicionarLivro(livro1, 2);
        venda39.adicionarLivro(livro2, 9);
        venda39.adicionarLivro(livro4, 3);

        Venda venda40 = new Venda(cliente4, data42);
        venda40.adicionarLivro(livro1, 5);
        venda40.adicionarLivro(livro2, 7);
        venda40.adicionarLivro(livro4, 2);

        Venda venda41 = new Venda(cliente4, data43);
        venda41.adicionarLivro(livro1, 11);
        venda41.adicionarLivro(livro2, 1);
        venda41.adicionarLivro(livro4, 6);

        Venda venda42 = new Venda(cliente4, data44);
        venda42.adicionarLivro(livro1, 21);
        venda42.adicionarLivro(livro3, 1);
        venda42.adicionarLivro(livro4, 7);

        Venda venda43 = new Venda(cliente1,data45);
        venda43.adicionarLivro(livro1, 2);
        venda43.adicionarLivro(livro5, 12);
        venda43.adicionarLivro(livro4, 3);

        Venda venda44 = new Venda(cliente1, data46);
        venda44.adicionarLivro(livro1, 1);
        venda44.adicionarLivro(livro2, 4);
        venda44.adicionarLivro(livro4, 1);

        Venda venda45 = new Venda(cliente3, data47);
        venda45.adicionarLivro(livro2, 10);
        venda45.adicionarLivro(livro3, 1);
        venda45.adicionarLivro(livro4, 3);

        Venda venda46 = new Venda(cliente3, data48);
        venda46.adicionarLivro(livro1, 4);
        venda46.adicionarLivro(livro6, 9);
        venda46.adicionarLivro(livro4, 3);

        Venda venda47 = new Venda(cliente2, data14);
        venda47.adicionarLivro(livro1, 3);

        Venda venda48 = new Venda(cliente2, data15);
        venda48.adicionarLivro(livro4, 5);
        venda48.adicionarLivro(livro3, 2);

        Venda venda49 = new Venda(cliente3, data47);
        venda49.adicionarLivro(livro2, 10);
        venda49.adicionarLivro(livro3, 1);
        venda49.adicionarLivro(livro4, 3);

        Venda venda50 = new Venda(cliente3, data48);
        venda50.adicionarLivro(livro1, 4);
        venda50.adicionarLivro(livro6, 9);
        venda50.adicionarLivro(livro4, 3);

        Venda venda54 = new Venda(cliente2, data14);
        venda54.adicionarLivro(livro1, 3);

        Venda venda55 = new Venda(cliente2, data15);
        venda55.adicionarLivro(livro4, 5);
        venda55.adicionarLivro(livro3, 2);

        Venda venda56 = new Venda(cliente3, data47);
        venda56.adicionarLivro(livro2, 10);
        venda56.adicionarLivro(livro3, 1);
        venda56.adicionarLivro(livro4, 3);

        Venda venda57 = new Venda(cliente3, data48);
        venda57.adicionarLivro(livro1, 4);
        venda57.adicionarLivro(livro6, 9);
        venda57.adicionarLivro(livro4, 3);

        Venda venda58 = new Venda(cliente2, data14);
        venda58.adicionarLivro(livro1, 3);

        Venda venda59 = new Venda(cliente2, data15);
        venda59.adicionarLivro(livro4, 5);
        venda59.adicionarLivro(livro3, 2);

        Venda venda60 = new Venda(cliente3, data47);
        venda60.adicionarLivro(livro2, 10);
        venda60.adicionarLivro(livro3, 1);
        venda60.adicionarLivro(livro4, 3);

        Venda venda61 = new Venda(cliente3, data48);
        venda61.adicionarLivro(livro1, 4);
        venda61.adicionarLivro(livro6, 9);
        venda61.adicionarLivro(livro4, 3);

        Venda venda62 = new Venda(cliente2, data14);
        venda62.adicionarLivro(livro1, 3);

        Venda venda63 = new Venda(cliente2, data15);
        venda63.adicionarLivro(livro4, 5);
        venda63.adicionarLivro(livro3, 2);

        Venda venda64 = new Venda(cliente3, data47);
        venda64.adicionarLivro(livro2, 10);
        venda64.adicionarLivro(livro3, 1);
        venda64.adicionarLivro(livro4, 3);

        Venda venda65 = new Venda(cliente3, data48);
        venda65.adicionarLivro(livro1, 4);
        venda65.adicionarLivro(livro6, 9);
        venda65.adicionarLivro(livro4, 3);

        Venda venda66 = new Venda(cliente2, data14);
        venda66.adicionarLivro(livro1, 3);

        Venda venda67 = new Venda(cliente2, data15);
        venda67.adicionarLivro(livro4, 5);
        venda67.adicionarLivro(livro3, 2);

        Venda venda68 = new Venda(cliente3, data47);
        venda68.adicionarLivro(livro2, 10);
        venda68.adicionarLivro(livro3, 1);
        venda68.adicionarLivro(livro4, 3);

        Venda venda69 = new Venda(cliente3, data48);
        venda69.adicionarLivro(livro1, 4);
        venda69.adicionarLivro(livro6, 9);
        venda69.adicionarLivro(livro4, 3);

        Venda venda70 = new Venda(cliente2, data14);
        venda70.adicionarLivro(livro1, 3);

        Venda venda71 = new Venda(cliente2, data15);
        venda71.adicionarLivro(livro4, 5);
        venda71.adicionarLivro(livro3, 2);



        try {
            servidorReadEasy.inserirVenda(venda1);
            servidorReadEasy.inserirVenda(venda2);
            servidorReadEasy.inserirVenda(venda3);
            servidorReadEasy.inserirVenda(venda4);
            servidorReadEasy.inserirVenda(venda5);
            servidorReadEasy.inserirVenda(venda6);
            servidorReadEasy.inserirVenda(venda7);
            servidorReadEasy.inserirVenda(venda8);
            servidorReadEasy.inserirVenda(venda9);
            servidorReadEasy.inserirVenda(venda10);
            servidorReadEasy.inserirVenda(venda11);
            servidorReadEasy.inserirVenda(venda12);
            servidorReadEasy.inserirVenda(venda13);
            servidorReadEasy.inserirVenda(venda14);
            servidorReadEasy.inserirVenda(venda15);
            servidorReadEasy.inserirVenda(venda16);
            servidorReadEasy.inserirVenda(venda17);
            servidorReadEasy.inserirVenda(venda18);
            servidorReadEasy.inserirVenda(venda19);
            servidorReadEasy.inserirVenda(venda20);
            servidorReadEasy.inserirVenda(venda21);
            servidorReadEasy.inserirVenda(venda22);
            servidorReadEasy.inserirVenda(venda23);
            servidorReadEasy.inserirVenda(venda24);
            servidorReadEasy.inserirVenda(venda25);
            servidorReadEasy.inserirVenda(venda26);
            servidorReadEasy.inserirVenda(venda27);
            servidorReadEasy.inserirVenda(venda28);
            servidorReadEasy.inserirVenda(venda29);
            servidorReadEasy.inserirVenda(venda30);
            servidorReadEasy.inserirVenda(venda31);
            servidorReadEasy.inserirVenda(venda32);
            servidorReadEasy.inserirVenda(venda33);
            servidorReadEasy.inserirVenda(venda34);
            servidorReadEasy.inserirVenda(venda35);
            servidorReadEasy.inserirVenda(venda36);
            servidorReadEasy.inserirVenda(venda37);
            servidorReadEasy.inserirVenda(venda38);
            servidorReadEasy.inserirVenda(venda39);
            servidorReadEasy.inserirVenda(venda40);
            servidorReadEasy.inserirVenda(venda41);
            servidorReadEasy.inserirVenda(venda42);
            servidorReadEasy.inserirVenda(venda43);
            servidorReadEasy.inserirVenda(venda44);
            servidorReadEasy.inserirVenda(venda45);
            servidorReadEasy.inserirVenda(venda46);
            servidorReadEasy.inserirVenda(venda47);
            servidorReadEasy.inserirVenda(venda48);
            servidorReadEasy.inserirVenda(venda49);
            servidorReadEasy.inserirVenda(venda50);
            servidorReadEasy.inserirVenda(venda54);
            servidorReadEasy.inserirVenda(venda55);
            servidorReadEasy.inserirVenda(venda56);
            servidorReadEasy.inserirVenda(venda57);
            servidorReadEasy.inserirVenda(venda58);
            servidorReadEasy.inserirVenda(venda59);
            servidorReadEasy.inserirVenda(venda60);
            servidorReadEasy.inserirVenda(venda61);
            servidorReadEasy.inserirVenda(venda62);
            servidorReadEasy.inserirVenda(venda63);
            servidorReadEasy.inserirVenda(venda64);
            servidorReadEasy.inserirVenda(venda65);
            servidorReadEasy.inserirVenda(venda66);
            servidorReadEasy.inserirVenda(venda67);
            servidorReadEasy.inserirVenda(venda68);
            servidorReadEasy.inserirVenda(venda69);
            servidorReadEasy.inserirVenda(venda70);
            servidorReadEasy.inserirVenda(venda71);
        } catch (VendaInvalidaException | UsuarioNuloException e) {
            System.out.println(e.getMessage());;
        }

    }
}