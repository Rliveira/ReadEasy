package br.ufrpe.readeasy.exceptions;

public class PrecoInvalidoException extends Exception{
    public PrecoInvalidoException() {
        super("O preço do livro inválido, digite um valor maior que 0");
    }
}
