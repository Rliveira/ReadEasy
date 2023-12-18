package br.ufrpe.readeasy.exceptions;

public class VendaNaoExisteException extends Exception
{
    public VendaNaoExisteException()
    {
        super("Essa venda não consta nos registros.");
    }
}
