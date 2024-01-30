package br.ufrpe.readeasy.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;


public class ClienteCatalogoController {

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnCatalogo;

    @FXML
    private Button btnHistorico;

    @FXML
    private Button btnSair;

    @FXML
    private ScrollPane spCatalogoDaLivraria;

    @FXML
    private TableView<?> tbCarrinho;

    @FXML
    private TableColumn<?, ?> clnLivro;

    @FXML
    private TableColumn<?, ?> clnQuantidade;

    @FXML
    private TableColumn<?, ?> clnPrecoUnitario;

    @FXML
    private ComboBox<?> cbAplicarPromocao;

    @FXML
    private Label lblTotal;

    @FXML
    private Button btnPagar;

    @FXML
    private Button btnRemoverDoCarrinho;

    @FXML
    private Label lblNomeDoLivro;

    @FXML
    private Label lblPreco;

    @FXML
    private Spinner<?> spnSeletor;

    @FXML
    private Button btnAdicionar;

    @FXML
    private ImageView imgvLivro;

    //Métodos de troca de tela:
    @FXML
    private void trocarTelaHistoricoCliente(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("clienteMinhasCompras.fxml", "ReadEasy - Histórico");
    }

    @FXML
    private void trocarTelaPerfilCliente(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("clientePerfil.fxml", "ReadEasy - Perfil");
    }

    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //outros métodos:

    @FXML
    void btnAdicionar(ActionEvent event) {

    }

    @FXML
    void btnAplicarPromocaoACompra(ActionEvent event) {

    }

    @FXML
    void btnPagar(ActionEvent event) {

    }

    @FXML
    void btnRemoverDoCarrinho(ActionEvent event) {

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
