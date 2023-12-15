package br.ufrpe.readeasy.Exceptions;

public class PrecoInvalidoException extends Exception{
    public PrecoInvalidoException() {
        super("O preço do livro inválido, digite um valor maior que 0");
    }
}
