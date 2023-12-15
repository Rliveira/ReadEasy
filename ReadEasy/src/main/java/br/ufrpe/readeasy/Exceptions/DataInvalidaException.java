package br.ufrpe.readeasy.Exceptions;

import java.time.LocalDate;

public class DataInvalidaException extends Exception{
    LocalDate data;

    public DataInvalidaException (LocalDate dataInvalida){
        super("Data inválida.");
        this.data = dataInvalida;
    }
}
