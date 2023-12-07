package com.example.readeasy.Business.beans;

import java.time.LocalDate;


public class Funcionario extends Usuario {
    private boolean ehAdm;
    private Funcionario admResponsavel;

    //CONSTRUTOR:
    public Funcionario(String nome, String cpf, LocalDate dataNascimento, String login, String senha, Endereco endereco,
                       String telefone, boolean ehAdm, Funcionario admResponsavel) {
        super(nome, cpf, dataNascimento, login, senha, endereco, telefone);
        this.ehAdm = ehAdm;
        this.admResponsavel = admResponsavel;
    }

    //GETS AND SETS:
    public boolean EhAdm() {
        return ehAdm;
    }

    public void setEhAdm(boolean ehAdm) {
        this.ehAdm = ehAdm;
    }

    public Funcionario getAdmResponsavel() {
        return admResponsavel;
    }

    public void setAdmResponsavel(Funcionario admResponsavel) {
        this.admResponsavel = admResponsavel;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "ehAdm=" + ehAdm +
                ", admResponsavel=" + admResponsavel +
                '}' + super.toString();
    }
}
