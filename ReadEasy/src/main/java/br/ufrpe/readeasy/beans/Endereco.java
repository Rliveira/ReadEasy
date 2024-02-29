package br.ufrpe.readeasy.beans;

import java.io.Serializable;

public class Endereco implements Serializable {
    private static final long serialVersionUID = 2L;
    private int cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;

    //CONSTRUTOR:
    public Endereco(int cep, String rua, String bairro, String cidade, String estado) {
        this.cep = cep;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    //GETS AND SETS:
    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean equals(Endereco endereco){
        if (this.cep == endereco.getCep() && this.rua.equals(endereco.getRua()) &&
                this.bairro.equals(endereco.getBairro()) && this.cidade.equals(endereco.getCidade()) &&
                this.estado.equals(endereco.getEstado())){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("+---------------------------------------------+\n");
        sb.append(String.format("| %-20s | %-20s |\n", "CEP", getCep()));
        sb.append(String.format("| %-20s | %-20s |\n", "Rua", getRua()));
        sb.append(String.format("| %-20s | %-20s |\n", "Bairro", getBairro()));
        sb.append(String.format("| %-20s | %-20s |\n", "Cidade", getCidade()));
        sb.append(String.format("| %-20s | %-20s |\n", "Estado", getEstado()));
        sb.append("+---------------------------------------------+\n");
        return sb.toString();
    }
}
