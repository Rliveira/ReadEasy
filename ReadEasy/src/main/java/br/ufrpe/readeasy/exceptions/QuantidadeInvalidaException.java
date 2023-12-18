package br.ufrpe.readeasy.exceptions;

public class QuantidadeInvalidaException extends Exception {
    public QuantidadeInvalidaException() {
        super("A quantidade que você digitou é Inválida");
    }
}
