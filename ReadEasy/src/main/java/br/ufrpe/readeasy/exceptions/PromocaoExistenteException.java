package br.ufrpe.readeasy.exceptions;

public class PromocaoExistenteException extends Exception{
    private String id;
    public PromocaoExistenteException(String id){
        super("Promoção Existente.");
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
