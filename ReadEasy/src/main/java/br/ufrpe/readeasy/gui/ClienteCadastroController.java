package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Endereco;
import br.ufrpe.readeasy.business.ControladorUsuario;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ClienteCadastroController{

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
    private TextField txtFNome;
    @FXML
    private TextField txtFRua;
    @FXML
    private TextField txtFSenha;
    @FXML
    private TextField txtFTelefone;
    @FXML
    private TextField txtFUsuario;

    @FXML
    private ComboBox<String> cbEstado;

    //métodos de troca de tela:
    @FXML
    public void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //outros métodos:
    public void initialize(){
        inicializarCbEstado();
    }

    @FXML
    protected void onBtnCadastrarClick(ActionEvent event){
        String nome = txtFNome.getText();
        String cpf = txtFCpf.getText();
        String usuario = txtFUsuario.getText();
        String senha = txtFSenha.getText();
        String rua = txtFRua.getText();
        String bairro = txtFBairro.getText();
        String cidade = txtFCidade.getText();
        String estado = cbEstado.getValue();
        String cep = txtFCEP.getText();
        String telefone = txtFTelefone.getText();
        LocalDate dataNascimento = dtpkDataNascimento.getValue();

        if (!validarInputTf(cep) || !validarInputTf(telefone) || !validarInputTf(cpf)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Campo de telefone, CEP ou CPF apresenta letras ou caracteres especiais");
            alert.setContentText("Digite apenas números para continuar");
            ButtonType buttonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(buttonType);

            alert.showAndWait().ifPresent(buttonType1 -> {
                if (buttonType1.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    alert.close();
                }
            });
            return;
        }

            Endereco endereco = new Endereco(Integer.parseInt(cep), rua, bairro, cidade, estado);
            Cliente cliente = new Cliente(nome, cpf, dataNascimento, usuario, senha, endereco, telefone);
            try {
                ServidorReadEasy.getInstance().cadastrarUsuario(cliente);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cadastro de Cliente");
                alert.setHeaderText(null);
                alert.setContentText("Cliente cadastrado com sucesso!");
                alert.showAndWait();

                ScreenManager sm = ScreenManager.getInstance();
                sm.TrocarTela("Login.fxml", "ReadEasy - Login");

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

    private boolean validarInputTf(String inputTf) {
        return inputTf.matches("\\d+");
    }

    private void inicializarCbEstado() {
        String[] estados = {
                "Acre", "Alagoas", "Amapá", "Amazonas", "Bahia", "Ceará", "Distrito Federal",
                "Espírito Santo", "Goiás", "Maranhão", "Mato Grosso", "Mato Grosso do Sul",
                "Minas Gerais", "Pará", "Paraíba", "Paraná", "Pernambuco", "Piauí", "Rio de Janeiro",
                "Rio Grande do Norte", "Rio Grande do Sul", "Rondônia", "Roraima", "Santa Catarina",
                "São Paulo", "Sergipe", "Tocantins"
        };

        cbEstado.getItems().addAll(estados);
    }
}
