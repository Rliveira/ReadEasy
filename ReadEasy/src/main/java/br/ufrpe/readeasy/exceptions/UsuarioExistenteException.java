package br.ufrpe.readeasy.exceptions;

public class UsuarioExistenteException extends Exception{
    String cpf;

    public UsuarioExistenteException (String cpf){
        super("Usuário com CPF "+cpf+"já foi adicionado.");
        this.cpf = cpf;
    }
}
