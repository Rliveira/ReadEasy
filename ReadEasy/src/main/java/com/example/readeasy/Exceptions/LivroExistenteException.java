package com.example.readeasy.Exceptions;

public class LivroExistenteException extends Exception{
    public LivroExistenteException(String livroTitulo) {
        super("O livro " + livroTitulo + " ja existe.");
    }
}
