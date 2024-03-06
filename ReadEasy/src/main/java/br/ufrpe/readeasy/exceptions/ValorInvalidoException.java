package br.ufrpe.readeasy.exceptions;

public class ValorInvalidoException extends Exception{
    public ValorInvalidoException() {
        super("Valor inválido, digite um valor maior que 0");
    }
}
