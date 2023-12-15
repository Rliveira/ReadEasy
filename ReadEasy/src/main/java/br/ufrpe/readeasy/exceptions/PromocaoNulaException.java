package br.ufrpe.readeasy.exceptions;

public class PromocaoNulaException extends Exception{
    private String titulo;

    public PromocaoNulaException(String titulo){
        super("Promoção Nula.");
        this.titulo = titulo;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
}

