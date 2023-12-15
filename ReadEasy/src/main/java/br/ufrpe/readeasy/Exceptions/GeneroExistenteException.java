package br.ufrpe.readeasy.Exceptions;

public class GeneroExistenteException extends Exception{
    public GeneroExistenteException(String titulo, String nomeDoGenero) {
        super("O livro " + titulo + "já possui o gênero" + nomeDoGenero);
    }
}
