package br.ufrpe.readeasy.beans;


import java.time.LocalDate;


public class Funcionario extends Usuario {
    private boolean Adm;
    private Funcionario admResponsavel;

    //CONSTRUTOR:
    public Funcionario(String nome, String cpf, LocalDate dataNascimento, String login, String senha, Endereco endereco,
                       String telefone, boolean ehAdm, Funcionario admResponsavel) {
        super(nome, cpf, dataNascimento, login, senha, endereco, telefone);
        this.Adm = Adm;
        this.admResponsavel = admResponsavel;
    }

    //GETS AND SETS:
    public boolean isAdm() {
        return Adm;
    }

    public void setAdm(boolean Adm) {
        this.Adm = Adm;
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
                "ehAdm=" + Adm +
                ", admResponsavel=" + admResponsavel +
                '}' + super.toString();
    }
}


