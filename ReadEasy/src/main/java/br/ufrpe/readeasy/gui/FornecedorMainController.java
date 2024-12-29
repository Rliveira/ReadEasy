package br.ufrpe.readeasy.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class FornecedorMainController {

    @FXML
    private Node telaFornecedorPerfil, telaFornecedorEstoque, telaFornecedorHistorico;

    public void trocarTelaPerfilFornecedor(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.trocarTelaFornecedor(telaFornecedorPerfil, "ReadEasy - Perfil");
    }

    public void trocarTelaHistoricoFornecedor(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.getFornecedorHistoricoController().setIgnorarInitialize(false);
        sm.getFornecedorHistoricoController().initialize();
        sm.trocarTelaFornecedor(telaFornecedorHistorico, "ReadEasy - Histórico");
    }

    @FXML
    public void trocarTelaEstoqueFornecedor(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.trocarTelaFornecedor(telaFornecedorEstoque, "ReadEasy - Estoque");
    }

    public void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.trocartelasPrincipais("login.fxml", "ReadEasy - Login");
    }

    public void SairDaConta(){
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
    public Node getTelaFornecedorPerfil() {
        return telaFornecedorPerfil;
    }

    public Node getTelaFornecedorEstoque() {
        return telaFornecedorEstoque;
    }

    public Node getTelaFornecedorHistorico() {
        return telaFornecedorHistorico;
    }
}
