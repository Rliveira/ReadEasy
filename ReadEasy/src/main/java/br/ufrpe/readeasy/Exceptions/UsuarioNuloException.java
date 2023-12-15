package br.ufrpe.readeasy.Exceptions;

public class UsuarioNuloException extends Exception{
    public UsuarioNuloException (){
        super("Parâmetros de usuário não preenchidos");
    }
}
