package br.ufrpe.readeasy.exceptions;

public class LoginInvalidoException extends Exception {

    public LoginInvalidoException() {
        super("Login ou senha inválidos.");
    }
}
