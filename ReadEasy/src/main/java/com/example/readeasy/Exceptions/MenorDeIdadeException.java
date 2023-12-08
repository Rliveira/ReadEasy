package com.example.readeasy.Exceptions;

public class MenorDeIdadeException extends Exception{
    int idade;

    public MenorDeIdadeException(int idade){
        super("Usuário menor de Idade.");
        this.idade = idade;
    }
}
