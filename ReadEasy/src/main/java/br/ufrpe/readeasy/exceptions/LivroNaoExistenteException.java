package br.ufrpe.readeasy.exceptions;

public class LivroNaoExistenteException extends Exception{
    public LivroNaoExistenteException() {
        super("O livro Não existe no sistema");
    }
}
