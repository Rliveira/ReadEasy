package br.ufrpe.readeasy;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.business.ControladorLivro;
import br.ufrpe.readeasy.business.ControladorUsuario;
import br.ufrpe.readeasy.exceptions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InicializadorDeDados {
    public static void inicializarDados(){
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
        Fornecedor fornecedor3 = new Fornecedor("Ronyzito", "1234568", dataNasc,"ronald wesley", "1234" ,endereco3, "98765-4321", tipoFornecedor3);

        Livro livro1 = new Livro("Memórias póstumas De Brás Cubas", "Machado De Assis", 30, fornecedor1);
        Livro livro2 = new Livro("Harry Potter e as relíquias da morte", "Jk roling", 20, fornecedor1);
        Livro livro3 = new Livro("As cronicas de narnia o leão", "C.S. Lewis", 70, fornecedor1);
        Livro livro4 = new Livro("O diário de um banana", "Jeff Kinney", 10, fornecedor2);
        Livro livro5 = new Livro("Dragon Ball", "Akira Toriyama", 79.50, fornecedor2);
        Livro livro6 = new Livro("Yuyu Hakusho", "Yoshihiro Togashi", 29.99, fornecedor2);
        Livro livro7 = new Livro("Dom Casmurro", "Machado de Assis", 50, fornecedor3);
        Livro livro8 = new Livro("Guerra e paz", "Liev Tolstói", 10, fornecedor3);

        ControladorLivro controladorLivro = ControladorLivro.getInstance();
        try {
            controladorLivro.adicionarLivro(livro1);
            controladorLivro.adicionarLivro(livro2);
            controladorLivro.adicionarLivro(livro3);
            controladorLivro.adicionarLivro(livro4);
            controladorLivro.adicionarLivro(livro5);
            controladorLivro.adicionarLivro(livro6);
            controladorLivro.adicionarLivro(livro7);
            controladorLivro.adicionarLivro(livro8);
        } catch ( PrecoInvalidoException | LivroExistenteException e) {
            System.out.println(e.getMessage());
        }

        ControladorUsuario controladorUsuario = ControladorUsuario.getInstance();

        try {
            controladorUsuario.cadastrarUsuario(fornecedor1);
            controladorUsuario.cadastrarUsuario(fornecedor2);
            controladorUsuario.cadastrarUsuario(fornecedor3);

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
            controladorLivro.adicionarGenero(livro1, genero1);
            controladorLivro.adicionarGenero(livro6, genero2);
            controladorLivro.adicionarGenero(livro2, genero2);
            controladorLivro.adicionarGenero(livro3, genero1);
            controladorLivro.adicionarGenero(livro3, genero2);
            controladorLivro.adicionarGenero(livro1, genero4);
            controladorLivro.adicionarGenero(livro1, genero3);
            controladorLivro.adicionarGenero(livro6, genero1);
            controladorLivro.adicionarGenero(livro4, genero5);
            controladorLivro.adicionarGenero(livro5, genero7);
            controladorLivro.adicionarGenero(livro2, genero4);
            controladorLivro.adicionarGenero(livro7, genero2);
            controladorLivro.adicionarGenero(livro8, genero4);
            controladorLivro.adicionarGenero(livro4, genero6);
            controladorLivro.adicionarGenero(livro6, genero5);
            controladorLivro.adicionarGenero(livro6, genero6);
            controladorLivro.adicionarGenero(livro2, genero3);
            controladorLivro.adicionarGenero(livro3, genero4);
            controladorLivro.adicionarGenero(livro3, genero6);
            controladorLivro.adicionarGenero(livro8, genero8);
            controladorLivro.adicionarGenero(livro1, genero7);
            controladorLivro.adicionarGenero(livro5, genero3);
            controladorLivro.adicionarGenero(livro4, genero2);
            controladorLivro.adicionarGenero(livro5, genero8);
            controladorLivro.adicionarGenero(livro1, genero6);
            controladorLivro.adicionarGenero(livro7, genero8);
            controladorLivro.adicionarGenero(livro7, genero6);
            controladorLivro.adicionarGenero(livro1, genero8);
            controladorLivro.adicionarGenero(livro8, genero1);
            controladorLivro.adicionarGenero(livro4, genero3);
        } catch (GeneroExistenteException e) {
            throw new RuntimeException(e);
        }
    }
}
