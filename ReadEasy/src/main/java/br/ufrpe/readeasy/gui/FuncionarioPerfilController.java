package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FuncionarioPerfilController
{

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnLivros;

    @FXML
    private Button btnEstoque;

    @FXML
    private Button btnHistorico;

    @FXML
    private Button btnRelatorios;

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

    //métodos de troca de tela:
    @FXML
    public void trocarTelaEstoqueFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioEstoque.fxml", "ReadEasy - Estoque");
    }

    @FXML
    public void trocarTelaHistoricoFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioHistoricoComprasEVendas.fxml", "ReadEasy - Histórico");
    }

    @FXML
    public void trocarTelaRelatoriosFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioRelatorios.fxml", "ReadEasy - Relatorios");
    }

    @FXML
    public void trocarTelaLivroFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioCRUDLivros.fxml", "ReadEasy - Livros");
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

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}