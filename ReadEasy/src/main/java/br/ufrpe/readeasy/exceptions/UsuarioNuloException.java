package br.ufrpe.readeasy.exceptions;

public class UsuarioNuloException extends Exception{
    public UsuarioNuloException (){
        super("Parâmetros de usuário não preenchidos");
    }
}
