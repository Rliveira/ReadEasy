package br.ufrpe.readeasy.exceptions;



public class ClienteNuloException extends Exception{
    String cpf;
    public ClienteNuloException(String cpf) {
        super("Usuário com CPF " + cpf + " não existe.");
        this.cpf = cpf;
    }
}
