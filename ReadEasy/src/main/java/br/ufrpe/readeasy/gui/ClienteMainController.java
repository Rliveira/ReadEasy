package br.ufrpe.readeasy.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class ClienteMainController {

    @FXML
    private Node telaPerfil, telaCatalogo, telaHistoricoCompras;

    @FXML
    public void trocarTelaCatalogoCliente(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.trocarTelaCliente(telaCatalogo, "ReadEasy - Catálogo");
    }

    @FXML
    public void trocarTelaPerfilCliente(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.trocarTelaCliente(telaPerfil, "ReadEasy - Perfil");
    }

    @FXML
    public void trocarTelaHistoricoCliente(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.getClienteMinhasComprasController().initialize();
        sm.trocarTelaCliente(telaHistoricoCompras, "ReadEasy - Histórico");
    }

    @FXML
    public void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.trocartelasPrincipais("login.fxml", "ReadEasy - Login");
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
                Stage stage = ScreenManager.getStage();
                stage.setResizable(false);
                stage.setMaximized(false);
            }
            else {
                alert.close();
            }
        });
    }

    //gets and sets:
    public Node getTelaCatalogo() {
        return telaCatalogo;
    }

    public Node getTelaPerfil() {
        return telaPerfil;
    }

    public Node getTelaHistoricoCompras() {
        return telaHistoricoCompras;
    }
}
