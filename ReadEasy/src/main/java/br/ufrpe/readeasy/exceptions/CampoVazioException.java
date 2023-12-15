package br.ufrpe.readeasy.exceptions;

public class CampoVazioException extends Exception{
    public CampoVazioException (){
        super("Campo não preenchido.");
    }
}
