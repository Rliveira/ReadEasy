package br.ufrpe.readeasy.exceptions;

public class HistoricoVazioException extends Exception
{
    public HistoricoVazioException()
    {
        super("Não existem vendas a serem exibidas.");
    }
}
