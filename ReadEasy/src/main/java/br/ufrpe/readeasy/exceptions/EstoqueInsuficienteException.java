package br.ufrpe.readeasy.exceptions;

public class EstoqueInsuficienteException extends Exception{
    public EstoqueInsuficienteException() {
        super("O livro está com o estoque vazio");
    }
}
