package br.ufrpe.readeasy.Exceptions;

public class UsuarioInexistenteException extends Exception{
    String cpf;

    public UsuarioInexistenteException (String cpf){
        super("Usuário com CPF " + cpf + " não existe.");
        this.cpf = cpf;
    }
}
