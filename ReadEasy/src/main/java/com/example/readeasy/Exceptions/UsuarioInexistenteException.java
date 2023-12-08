package com.example.readeasy.Exceptions;

import com.example.readeasy.Business.beans.Usuario;

public class UsuarioInexistenteException extends Exception{
    String cpf;

    public UsuarioInexistenteException (String cpf){
        super("Usuário com CPF " + cpf + " não existe.");
        this.cpf = cpf;
    }
}
