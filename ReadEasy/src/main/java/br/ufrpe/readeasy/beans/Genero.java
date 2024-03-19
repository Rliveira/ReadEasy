package br.ufrpe.readeasy.beans;
public enum Genero {
    ACADEMICO("Acadêmico"),
    ACAO("Ação"),
    AUTOBIOGRAFIA("Autobiografia"),
    AVENTURA("Aventura"),
    CRITICA_SOCIAL("Crítica social"),
    COMEDIA("Comédia"),
    DRAMA("Drama"),
    EPICO("Épico"),
    FANTASIA("Fantasia"),
    FICCAO("Ficção"),
    HISTORICO("Histórico"),
    HISTORIA_EM_QUADRINHOS("História em quadrinhos"),
    INFANTO_JUVENIL("Infantojuvenil"),
    LITERATURA_CLASSICA("Literatura clássica"),
    POESIA("Poesia"),
    NAO_FICCAO("Não Ficção"),
    ROMANCE("Romance"),
    RELIGIOSO("Religioso"),
    SOBRENATURAL("Sobrenatural"),
    SUSPENSE("Suspense"),
    TERROR("Terror");

    private final String descricaoEnum;

    Genero(String descricaoEnum) {
        this.descricaoEnum = descricaoEnum;
    }

    public String getDescricaoEnum() {
        return descricaoEnum;
    }
}
