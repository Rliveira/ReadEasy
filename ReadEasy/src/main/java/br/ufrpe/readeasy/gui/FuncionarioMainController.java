package br.ufrpe.readeasy.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class FuncionarioMainController {
    @FXML
    private Node telaFuncionarioPerfil, telaFuncionarioRelatorios, telaFuncionarioCRUDLivros,
            telaFuncionarioEstoque, telaFuncionarioHistorico;

    @FXML
    public void trocarTelaEstoqueFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.getFuncionarioEstoqueController().initialize();
        sm.trocarTelaFuncionario(telaFuncionarioEstoque, "ReadEasy - Estoque");
    }

    @FXML
    public void trocarTelaHistoricoFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.getFuncionarioHistoricoComprasEVendasController().initialize();
        sm.trocarTelaFuncionario(telaFuncionarioHistorico, "ReadEasy - Histórico");
    }

    @FXML
    public void trocarTelaPerfilFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.trocarTelaFuncionario(telaFuncionarioPerfil, "ReadEasy - Perfil");
    }

    @FXML
    public void trocarTelaRelatoriosFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.getFuncionariosRelatoriosController().initialize();
        sm.trocarTelaFuncionario(telaFuncionarioRelatorios, "ReadEasy - Relatorios");
    }

    @FXML
    public void trocarTelaLivroFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.trocarTelaFuncionario(telaFuncionarioCRUDLivros, "ReadEasy - Livros");
    }

    public void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.trocartelasPrincipais("login.fxml", "ReadEasy - Login");
    }

    @FXML
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

    public Node getTelaFuncionarioPerfil() {
        return telaFuncionarioPerfil;
    }

    public Node getTelaFuncionarioRelatorios() {
        return telaFuncionarioRelatorios;
    }

    public Node getTelaFuncionarioCRUDLivros() {
        return telaFuncionarioCRUDLivros;
    }

    public Node getTelaFuncionarioEstoque() {
        return telaFuncionarioEstoque;
    }

    public Node getTelaFuncionarioHistorico() {
        return telaFuncionarioHistorico;
    }
}
