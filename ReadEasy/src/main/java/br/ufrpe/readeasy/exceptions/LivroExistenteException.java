package br.ufrpe.readeasy.exceptions;

public class LivroExistenteException extends Exception{
    public LivroExistenteException() {
        super("O livro que você tentou acabar de cadastrar já existe no sistema.");
    }
}
