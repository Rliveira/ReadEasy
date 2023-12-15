package br.ufrpe.readeasy.Exceptions;

public class CampoVazioException extends Exception{
    public CampoVazioException (){
        super("Campo não preenchido.");
    }
}
