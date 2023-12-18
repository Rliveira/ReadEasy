package br.ufrpe.readeasy.exceptions;

public class EnderecoInexistenteException extends Exception {
    private int cep;
    public EnderecoInexistenteException(int cep) {
        super("Endereco não cadastrado com o CEP: " + cep);
        this.cep = cep;
    }
}
