package br.ufrpe.readeasy.Exceptions;

public class LivroNuloException extends Exception{
    public LivroNuloException(){
        super("O Livro inválido, crie um objeto livro corretamente.");
    }
}