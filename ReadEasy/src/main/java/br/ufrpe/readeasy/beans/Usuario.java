package br.ufrpe.readeasy.beans;


import java.time.LocalDate;

public abstract class Usuario{

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String login;
    private String senha;
    private Endereco endereco;
    private String telefone;

    //CONSTRUTOR:
    public Usuario(String nome, String cpf, LocalDate dataNascimento, String login, String senha, Endereco endereco,
                   String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.login = login;
        this.senha = senha;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    //GETS AND SETS:
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getIdade() {
        return LocalDate.now().getYear() - dataNascimento.getYear();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Usuario)) {
            return false;
        }
        Usuario usuario = (Usuario) obj;
        return this.cpf.equals(usuario.cpf);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("+---------------------------------------------+\n");
        sb.append(String.format("| %-20s | %-20s |\n", "Nome", getNome()));
        sb.append(String.format("| %-20s | %-20s |\n", "CPF", getCpf()));
        sb.append(String.format("| %-20s | %-20s |\n", "Data de Nascimento", getDataNascimento()));
        sb.append(String.format("| %-20s | %-20s |\n", "Login", getLogin()));
        sb.append(String.format("| %-20s | %-20s |\n", "Senha", getSenha()));
        sb.append(String.format("| %-20s | %-20s |\n", "Endereço", getEndereco().getRua()));
        sb.append(String.format("| %-20s | %-20s |\n", "Telefone", getTelefone()));
        sb.append("+---------------------------------------------+\n");

        return sb.toString();
    }
}
