package br.ufrpe.readeasy.exceptions;

public class PromocaoInexistenteException extends Exception{
    private String titulo;

    public PromocaoInexistenteException(String titulo){
        super("Promoção inexistente.");
        this.titulo = titulo;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
}

