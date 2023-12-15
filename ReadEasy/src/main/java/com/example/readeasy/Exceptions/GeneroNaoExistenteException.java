package com.example.readeasy.Exceptions;

public class GeneroNaoExistenteException extends Exception{
    public GeneroNaoExistenteException(String titulo, String nomeDoGenero) {
        super("O livro " + titulo + "já possui o gênero" + nomeDoGenero);
    }
}
