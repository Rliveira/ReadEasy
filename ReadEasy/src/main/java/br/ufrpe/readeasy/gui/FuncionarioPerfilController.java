package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.*;
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
    @FXML
    public void initialize()
    {
        inicializarLabels();
    }

    @FXML
    public void inicializarLabels(){
        if(SessaoUsuario.getUsuarioLogado() instanceof Funcionario)
        {
            labelTipoFornecedor.setText("Funcionário");
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