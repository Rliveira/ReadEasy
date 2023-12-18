package br.ufrpe.readeasy.exceptions;

public class ListaDeLivrosVaziaException extends Exception
{
    public ListaDeLivrosVaziaException()
    {
        super("Não existem livros nessa lista, ação inválida.");
    }
}
