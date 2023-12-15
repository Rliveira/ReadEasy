package br.ufrpe.readeasy.exceptions;

public class TipoUsuarioInvalidoException extends Exception{
    public TipoUsuarioInvalidoException (){
        super("Tipo de usuário é inválido");
    }
}
