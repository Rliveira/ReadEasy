package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Fornecedor;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

    private static FornecedorPerfilController instance;
    private boolean ignorarInitialize;

    //construtor:
    public FornecedorPerfilController() {
        if(instance == null){
            instance = this;
            ignorarInitialize = true;
        }
    }

    //métodos:
    public void initialize()
    {
        ScreenManager screenManager = ScreenManager.getInstance();
        if(screenManager.getFuncionarioPerfilController() == null){
            screenManager.setFornecedorPerfilController(instance);
        }
        if(!ignorarInitialize){
            inicializarLabels();
        }
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

    //gets and sets:
    public static void setInstance(FornecedorPerfilController instance) {
        FornecedorPerfilController.instance = instance;
    }

    public void setIgnorarInitialize(boolean ignorarInitialize) {
        this.ignorarInitialize = ignorarInitialize;
    }
}