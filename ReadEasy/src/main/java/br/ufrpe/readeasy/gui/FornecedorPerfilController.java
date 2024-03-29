package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Fornecedor;
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
    private Label labelNome;

    @FXML
    private Label labelCPF;

    @FXML
    private Label labelDataNascimento;

    @FXML
    private Label labelNomeUsuario;

    @FXML
    private Label labelTelefone;

    @FXML
    private Label labelTipoFornecedor;

    @FXML
    private Label labelCEP;

    @FXML
    private Label labelRua;

    @FXML
    private Label labelBairro;

    @FXML
    private Label labelCidade;

    @FXML
    private Label labelEstado;

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
    public void initialize()
    {
        inicializarLabels();
    }

    @FXML
    public void inicializarLabels(){
        if(SessaoUsuario.getUsuarioLogado() instanceof Fornecedor)
        {
            labelTipoFornecedor.setText(((Fornecedor) SessaoUsuario.getUsuarioLogado()).getTipoFornecedor().toString());
            labelNome.setText(SessaoUsuario.getUsuarioLogado().getNome());
            labelCPF.setText(SessaoUsuario.getUsuarioLogado().getCpf());
            labelDataNascimento.setText(SessaoUsuario.getUsuarioLogado().getDataNascimento().toString());
            labelNomeUsuario.setText(SessaoUsuario.getUsuarioLogado().getLogin());
            labelTelefone.setText(SessaoUsuario.getUsuarioLogado().getTelefone());
            labelCEP.setText(String.valueOf(SessaoUsuario.getUsuarioLogado().getEndereco().getCep()));
            labelRua.setText(SessaoUsuario.getUsuarioLogado().getEndereco().getRua());
            labelBairro.setText(SessaoUsuario.getUsuarioLogado().getEndereco().getBairro());
            labelCidade.setText(SessaoUsuario.getUsuarioLogado().getEndereco().getCidade());
            labelEstado.setText(SessaoUsuario.getUsuarioLogado().getEndereco().getEstado());
        }
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
            }
            else {
                alert.close();
            }
        });
    }
}