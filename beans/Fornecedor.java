package beans;

import java.time.LocalDate;

public class Fornecedor extends Usuario{

    private String tipoFornecedor;


    public Fornecedor(String nome, String cpf, LocalDate dataNascimento, String login, String senha, String endereco,
                      String telefone, String tipoFornecedor) {
        super(nome, cpf, dataNascimento, login, senha, endereco, telefone);
        this.tipoFornecedor = tipoFornecedor;
    }

    public String getTipoFornecedor() {
        return tipoFornecedor;
    }

    public void setTipoFornecedor(String tipoFornecedor) {
        this.tipoFornecedor = tipoFornecedor;
    }
}
