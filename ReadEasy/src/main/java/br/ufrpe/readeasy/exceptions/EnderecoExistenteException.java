package br.ufrpe.readeasy.exceptions;

public class EnderecoExistenteException extends Exception{
    private int cep;

    public EnderecoExistenteException(int cep){
        super("Endereco ja cadastrado com o cep: " + cep);
        this.cep = cep;
    }

    public int getCep(){
        return cep;
    }
}
