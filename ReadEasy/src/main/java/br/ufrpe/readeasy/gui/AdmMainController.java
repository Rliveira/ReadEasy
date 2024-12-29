package br.ufrpe.readeasy.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AdmMainController {

    @FXML
    private Node telaPerfil, telaPromocoes, telaLivros, telaEstoque, telaRelatorios, telaUsuarios, telaHistorico;

    //Métodos de troca de tela:
    @FXML
    public void trocarTelaEstoqueAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.getAdmEstoqueController().initialize();
        sm.trocarTelaAdm(telaEstoque, "ReadEasy - Estoque");
    }

    @FXML
    public void trocarTelaUsuariosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.getAdmCRUDUsuariosController().initialize();
        sm.trocarTelaAdm(telaUsuarios, "ReadEasy - Usuários");
    }

    @FXML
    public void trocarTelaHistoricoAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.getAdmHistoricoComprasEVendasController().initialize();
        sm.trocarTelaAdm(telaHistorico, "ReadEasy - Histórico");
    }

    @FXML
    public void trocarTelaLivrosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.trocarTelaAdm(telaLivros,"ReadEasy - Livros" );
    }

    @FXML
    public void trocarTelaPerfilAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.getAdmPerfilController().initialize();
        sm.trocarTelaAdm(telaPerfil, "ReadEasy - Perfil");
    }

    @FXML
    public void trocarTelaPromocoesAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.trocarTelaAdm(telaPromocoes, "ReadEasy - Promoções");
    }

    @FXML
    public void trocarTelaRelatoriosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.getAdmRelatoriosController().initialize();
        sm.trocarTelaAdm(telaRelatorios, "ReadEasy - Relatorios");
    }

    @FXML
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
    public Node getTelaPerfil() {
        return telaPerfil;
    }

    public Node getTelaPromocoes() {
        return telaPromocoes;
    }

    public Node getTelaLivros() {
        return telaLivros;
    }

    public Node getTelaEstoque() {
        return telaEstoque;
    }

    public Node getTelaRelatorios() {
        return telaRelatorios;
    }

    public Node getTelaUsuarios() {
        return telaUsuarios;
    }

    public Node getTelaHistorico() {
        return telaHistorico;
    }
}
