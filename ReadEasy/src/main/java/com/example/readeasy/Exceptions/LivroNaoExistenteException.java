package com.example.readeasy.Exceptions;

public class LivroNaoExistenteException extends Exception{
    public LivroNaoExistenteException(String livroNome) {
        super("O livro " + livroNome + "Não existe" );
    }
}
