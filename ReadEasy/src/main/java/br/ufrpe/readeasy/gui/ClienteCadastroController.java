package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Endereco;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class ClienteCadastroController {

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnVoltar;

    @FXML
    private DatePicker dtpkDataNascimento;

    @FXML
    private TextField txtFBairro;

    @FXML
    private TextField txtFCEP;

    @FXML
    private TextField txtFCidade;

    @FXML
    private TextField txtFCpf;

    @FXML
    private TextField txtFEstado;

    @FXML
    private TextField txtFNome;

    @FXML
    private TextField txtFRua;

    @FXML
    private TextField txtFSenha;

    @FXML
    private TextField txtFTelefone;

    @FXML
    private TextField txtFUsuario;

    protected void onBtnCadastrarClick() {
        String nome = txtFNome.getText();
        String cpf = txtFCpf.getText();
        String usuario = txtFUsuario.getText();
        String senha = txtFSenha.getText();
        String rua = txtFRua.getText();
        String bairro = txtFBairro.getText();
        String cidade = txtFCidade.getText();
        String estado = txtFEstado.getText();
        int cep = Integer.parseInt(txtFCEP.getText());
        String telefone = txtFTelefone.getText();
        LocalDate dataNascimento = dtpkDataNascimento.getValue();

        Endereco endereco = new Endereco(cep, rua, bairro, cidade, estado);

        Cliente cliente = new Cliente(nome, cpf, dataNascimento, usuario, senha, endereco, telefone);

    }

}
