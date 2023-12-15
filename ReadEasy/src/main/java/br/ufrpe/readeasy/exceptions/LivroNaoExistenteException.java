package br.ufrpe.readeasy.exceptions;

public class LivroNaoExistenteException extends Exception{
    public LivroNaoExistenteException(String livroNome) {
        super("O livro " + livroNome + "Não existe" );
    }
}
