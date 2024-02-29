package br.ufrpe.readeasy.exceptions;

import java.util.UUID;

public class PromocaoExistenteException extends Exception{
    private UUID id;
    public PromocaoExistenteException(UUID id){
        super("Promoção Existente.");
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
