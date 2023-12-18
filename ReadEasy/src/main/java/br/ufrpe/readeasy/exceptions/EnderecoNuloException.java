package br.ufrpe.readeasy.exceptions;

import br.ufrpe.readeasy.beans.Endereco;

public class EnderecoNuloException extends Exception{
    String endereco;

    public EnderecoNuloException (Endereco endereco){
        super("Usuário com esse endereço " + endereco + " não existe.");
        this.endereco = String.valueOf(endereco);
    }
}
