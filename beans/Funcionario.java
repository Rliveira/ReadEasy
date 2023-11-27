package beans;

import java.time.LocalDate;


public class Funcionario extends Usuario {

    private boolean ehAdm;

    private Funcionario admResponsavel;


    public Funcionario(String nome, String cpf, LocalDate dataNascimento, String login, String senha, String endereco,
                       String telefone, boolean ehAdm, Funcionario admResponsavel) {
        super(nome, cpf, dataNascimento, login, senha, endereco, telefone);
        this.ehAdm = ehAdm;
        this.admResponsavel = admResponsavel;
    }

    public boolean isEhAdm() {
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

}
