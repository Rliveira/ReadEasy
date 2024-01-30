package br.ufrpe.readeasy.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ClientePerfilController {

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnCatalogo;

    @FXML
    private Button btnHistorico;

    @FXML
    private Button btnSair;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblCPF;

    @FXML
    private Label lblDataDeNascimento;

    @FXML
    private Label lblLogin;

    @FXML
    private Label lblSenha;

    @FXML
    private Label lblTelefone;

    @FXML
    private TextField tfEditarPerfilNome;

    @FXML
    private TextField tfEditarPerfilCPF;

    @FXML
    private TextField tfEditarPerfilLogin;

    @FXML
    private TextField tfEditarPerfilSenha;

    @FXML
    private TextField tfEditarPerfilTelefone;

    @FXML
    private DatePicker dtpEditarPerfilDataDeNascimento;

    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnCadastrarEndereço;

    @FXML
    private TableView<?> tbvEnderecosCadastrados;

    @FXML
    private TableColumn<?, ?> clnCEP;

    @FXML
    private TableColumn<?, ?> clnRua;

    @FXML
    private TableColumn<?, ?> clnBairro;

    @FXML
    private TableColumn<?, ?> clnCidade;

    @FXML
    private TableColumn<?, ?> clnEstado;

    @FXML
    private Button btnRemoverEndereco;

    @FXML
    private Button btnAlterarEndereco;

    //Métodos de troca de tela:
    @FXML
    private void trocarTelaCatalogoCliente(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("clienteCatalogo.fxml", "ReadEasy - Catálogo");
    }

    @FXML
    private void trocarTelaHistoricoCliente(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("clienteMinhasCompras.fxml", "ReadEasy - Histórico");
    }

    @FXML
    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }
    @FXML
    void btnAtualizarPerfil(ActionEvent event) {


    }

    @FXML
    void btnAlterarEndereco(ActionEvent event) {

    }

    @FXML
    void btnCadastrarEndereço(ActionEvent event) {

    }

    @FXML
    void btnRemoverEndereco(ActionEvent event) {

    }

    public void btnSairDaConta(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja realmente sair?");
        alert.setContentText("Escolha uma opção.");

        ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(simButton, naoButton);


        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                trocarTelaLogin();
            }
            else {
                alert.close();
            }
        });
    }
}
