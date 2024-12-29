package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Funcionario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


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

    private static FuncionarioPerfilController instance;
    private boolean ignorarInitialize;

    //construtor:


    public FuncionarioPerfilController() {
        if(instance == null){
            instance = this;
            ignorarInitialize = true;
        }
    }

    //métodos:
    @FXML
   public void initialize()
    {
        ScreenManager screenManager = ScreenManager.getInstance();

        if(screenManager.getFuncionarioPerfilController() == null){
            screenManager.setFuncionarioPerfilController(instance);
        }
        if(!ignorarInitialize){
            inicializarLabels();
        }
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

    //gets and sets:
    public void setBtnPerfil(Button btnPerfil) {
        this.btnPerfil = btnPerfil;
    }

    public static void setInstance(FuncionarioPerfilController instance) {
        FuncionarioPerfilController.instance = instance;
    }

    public void setIgnorarInitialize(boolean ignorarInitialize) {
        this.ignorarInitialize = ignorarInitialize;
    }
}