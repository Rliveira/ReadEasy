package com.example.readeasy.Exceptions;

public class UsuarioExistenteException extends Exception{
    String cpf;

    public UsuarioExistenteException (String cpf){
        super("Usuário com CPF "+cpf+"já foi adicionado.");
        this.cpf = cpf;
    }
}
