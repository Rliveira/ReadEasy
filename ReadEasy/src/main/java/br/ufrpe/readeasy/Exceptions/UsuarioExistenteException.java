package br.ufrpe.readeasy.Exceptions;

public class UsuarioExistenteException extends Exception{
    String cpf;

    public UsuarioExistenteException (String cpf){
        super("Usuário com CPF "+cpf+"já foi adicionado.");
        this.cpf = cpf;
    }
}
