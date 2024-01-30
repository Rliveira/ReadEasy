package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Endereco;
import br.ufrpe.readeasy.business.ControladorUsuario;
import br.ufrpe.readeasy.exceptions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ClienteCadastroController implements Initializable {

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

    //métodos de troca de tela:
    @FXML
    public void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //outros métodos:
    protected void onBtnCadastrarClick(ActionEvent event) throws IOException {
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
            try {
                ControladorUsuario.getInstance().cadastrarUsuario(cliente);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cadastro de Cliente");
                alert.setHeaderText(null);
                alert.setContentText("Cliente cadastrado com sucesso!");
                alert.showAndWait();
            } catch (TipoUsuarioInvalidoException e) {
                throw new RuntimeException(e);
            } catch (MenorDeIdadeException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro no preenchimento de dados");
                alert.setHeaderText(null);
                alert.setContentText("O usuário deve ser maior de idade!");
                alert.showAndWait();
            } catch (DataInvalidaException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro no preenchimento de dados");
                alert.setHeaderText(null);
                alert.setContentText("Data inválida!");
                alert.showAndWait();
            } catch (CampoVazioException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro no preenchimento de dados");
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos!");
                alert.showAndWait();
            } catch (UsuarioExistenteException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro no preenchimento de dados");
                alert.setHeaderText(null);
                alert.setContentText("Usuário já cadastrado!");
                alert.showAndWait();
            } catch (UsuarioNuloException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro no preenchimento de dados");
                alert.setHeaderText(null);
                alert.setContentText("Usuário nulo!");
                alert.showAndWait();
        }

    }

    protected void onBtnVoltarClick(ActionEvent event) throws IOException {
        // TODO implementação do método de voltar
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO implementação do método de inicialização (não sei o que colocar aqui)
    }
}
