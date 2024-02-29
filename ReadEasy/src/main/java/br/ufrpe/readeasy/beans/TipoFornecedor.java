package br.ufrpe.readeasy.beans;

public enum TipoFornecedor {
    EDITORA("Editora"),
    ESCRITOR_INDEPENDENTE("Escritor Independente"),
    DOADOR_ANONIMO("Doador Anônimo"),
    INSTITUICAO_PUBLICA("Instituição Pública"),
    INSTITUICAO_PRIVADA("Instituição Privada"),
    DISTRIBUIDORA_DE_LIVRO("Distribuidora de Livro"),
    ATACADISTAS("Atacadistas");

    private final String descricaoEnum;

    TipoFornecedor(String descricaoEnum) {
        this.descricaoEnum = descricaoEnum;
    }

    public String getDescricaoEnum() {
        return descricaoEnum;
    }
}
