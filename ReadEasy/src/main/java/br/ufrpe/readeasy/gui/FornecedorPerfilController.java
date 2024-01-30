package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Usuario;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FornecedorPerfilController
{
    private Application app;

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnEstoque;

    @FXML
    private Button btnHistorico;

    @FXML
    private Button btnSair;

    @FXML
    private Label LabelNome;

    @FXML
    private Label LabelCPF;

    @FXML
    private Label LabelDataNascimento;

    @FXML
    private Label LabelNomeUsuario;

    @FXML
    private Label LabelSenha;

    @FXML
    private Label LabelTelefone;

    @FXML
    private Label LabelTipoFornecedor;

    @FXML
    private Label LabelCEP;

    @FXML
    private Label LabelRua;

    @FXML
    private Label LabelBairro;

    @FXML
    private Label LabelCidade;

    @FXML
    private Label LabelEstado;

    private Usuario usuarioLogado;

    //métodos de troca de tela
    public void trocarTelaEstoqueFornecedor(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("fornecedorEstoque.fxml", "ReadEasy - Estoque");
    }

    public void trocarTelaHistoricoFornecedor(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("fornecedorHistorico.fxml", "ReadEasy - Perfil");
    }

    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //Outros métodos:
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
            }
            else {
                alert.close();
            }
        });
    }

    //Gets and Sets:
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}