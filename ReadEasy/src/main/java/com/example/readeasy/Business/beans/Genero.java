package com.example.readeasy.Business.beans;
public enum Genero {
    ACAO("Ação"),
    AVENTURA("Aventura"),
    SUSPENSE("Suspense"),
    TERROR("Terror"),
    FANTASIA("Fantasia"),
    INFANTO_JUVENIL("Infantojuvenil"),
    FICCAO_CIENTIFICA("Ficção Científica"),
    NAO_FICCAO("Não Ficção"),
    ROMANCE("Romance"),
    ACADEMICO("Acadêmico");

    private final String descricaoEnum;

    Genero(String descricaoEnum) {
        this.descricaoEnum = descricaoEnum;
    }

    public String getDescricaoEnum() {
        return descricaoEnum;
    }
}
