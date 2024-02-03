package br.ufrpe.readeasy.exceptions;

public class LivroSemGeneroException extends Exception{
    public LivroSemGeneroException() {
        super("O livro não pode ficar sem nenhum gênero.");
    }
}
