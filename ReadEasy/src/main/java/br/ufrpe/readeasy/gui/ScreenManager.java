package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Funcionario;
import br.ufrpe.readeasy.beans.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenManager {
    private static ScreenManager instance;
    private static Stage stage;

    private Scene admMainScene;
    private AdmMainController admMainController;

    private AdmCRUDPromocoesController admCRUDPromocoesController;

    private AdmCRUDUsuariosController admCRUDUsuariosController;

    private AdmEstoqueController admEstoqueController;

    private AdmHistoricoComprasEVendasController admHistoricoComprasEVendasController;

    private AdmLivrosController admLivrosController;

    private AdmPerfilController admPerfilController;

    private AdmRelatoriosController admRelatoriosController;

    private Scene clienteCadastroScene;
    private ClienteCadastroController clienteCadastroController;

    private ClienteCatalogoController clienteCatalogoController;

    private ClienteHistoricoComprasController clienteHistoricoComprasController;

    private ClientePerfilController clientePerfilController;

    private Scene clienteMainScene;
    private ClienteMainController clienteMainController;

    private FornecedorEstoqueController fornecedorEstoqueController;

    private FornecedorHistoricoController fornecedorHistoricoController;

    private FornecedorPerfilController fornecedorPerfilController;

    private Scene fornecedorMainScene;
    private FornecedorMainController fornecedorMainController;

    private FuncionarioCRUDLivrosController funcionarioCRUDLivrosController;

    private FuncionarioEstoqueController funcionarioEstoqueController;

    private FuncionarioHistoricoComprasEVendasController funcionarioHistoricoComprasEVendasController;

    private FuncionarioPerfilController funcionarioPerfilController;

    private FuncionarioRelatoriosController funcionariosRelatoriosController;

    private Scene funcionarioMainScene;
    private FuncionarioMainController funcionarioMainController;

    private Scene loginScene;
    private LoginController loginController;

    boolean precisaApresentarOAlertDadosAdm;
    boolean precisaApresentarOAlertDadosFuncionario;


    public static ScreenManager getInstance(){
        if(instance == null){
            instance = new ScreenManager();
        }
        return instance;
    }

    //MÉTODOS:
    public void carregarTelas(Usuario usuarioLogado){
        try {
            precisaApresentarOAlertDadosAdm = true;
            precisaApresentarOAlertDadosFuncionario = true;

            if(usuarioLogado != null){
                if(usuarioLogado instanceof Funcionario && ((Funcionario) usuarioLogado).isAdm()){
                    FXMLLoader admtelaMainPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/admMain.fxml"));
                    this.admMainScene = new Scene(admtelaMainPane.load());
                    this.admMainController = admtelaMainPane.getController();
                }

                else if (usuarioLogado instanceof Cliente){
                    FXMLLoader clienteMainPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/clienteMain.fxml"));
                    this.clienteMainScene = new Scene(clienteMainPane.load());
                    this.clienteMainController = clienteMainPane.getController();
                }

                else if(usuarioLogado instanceof Fornecedor){
                    FXMLLoader fornecedorMainPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/fornecedorMain.fxml"));
                    this.fornecedorMainScene = new Scene(fornecedorMainPane.load());
                    this.fornecedorMainController = fornecedorMainPane.getController();
                }

                else{
                    FXMLLoader funcionarioMainPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/funcionarioMain.fxml"));
                    this.funcionarioMainScene = new Scene(funcionarioMainPane.load());
                    this.funcionarioMainController = funcionarioMainPane.getController();
                }
            }

            FXMLLoader clientecadastroPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/clienteCadastro.fxml"));
            this.clienteCadastroScene = new Scene(clientecadastroPane.load());
            this.clienteCadastroController = clientecadastroPane.getController();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void trocarTelaAdm(Node telaSelecionada, String titulo){
        admMainController.getTelaPerfil().setVisible(false);
        admMainController.getTelaLivros().setVisible(false);
        admMainController.getTelaEstoque().setVisible(false);
        admMainController.getTelaUsuarios().setVisible(false);
        admMainController.getTelaPromocoes().setVisible(false);
        admMainController.getTelaHistorico().setVisible(false);
        admMainController.getTelaRelatorios().setVisible(false);

        telaSelecionada.setVisible(true);
        stage.setTitle(titulo);
    }

    public void trocarTelaCliente(Node telaSelecionada, String titulo){
        clienteMainController.getTelaPerfil().setVisible(false);
        clienteMainController.getTelaHistoricoCompras().setVisible(false);
        clienteMainController.getTelaCatalogo().setVisible(false);

        telaSelecionada.setVisible(true);
        stage.setTitle(titulo);
    }

    public void trocarTelaFornecedor(Node telaSelecionada, String titulo){
        fornecedorMainController.getTelaFornecedorEstoque().setVisible(false);
        fornecedorMainController.getTelaFornecedorHistorico().setVisible(false);
        fornecedorMainController.getTelaFornecedorPerfil().setVisible(false);

        telaSelecionada.setVisible(true);
        stage.setTitle(titulo);
    }

    public void trocarTelaFuncionario(Node telaSelecionada, String titulo){
        funcionarioMainController.getTelaFuncionarioEstoque().setVisible(false);
        funcionarioMainController.getTelaFuncionarioHistorico().setVisible(false);
        funcionarioMainController.getTelaFuncionarioPerfil().setVisible(false);
        funcionarioMainController.getTelaFuncionarioRelatorios().setVisible(false);
        funcionarioMainController.getTelaFuncionarioCRUDLivros().setVisible(false);

        telaSelecionada.setVisible(true);
        stage.setTitle(titulo);
    }


    public void trocartelasPrincipais(String fxml, String title) {
        switch (fxml) {
            case "login.fxml":
                stage.setScene(loginScene);
                break;

            case "clienteCadastro.fxml":
                stage.setScene(clienteCadastroScene);
                break;

            case "admMain.fxml":
                stage.setScene(admMainScene);
                break;

            case "clienteMain.fxml":
                stage.setScene(clienteMainScene);
                break;

            case "funcionarioMain.fxml":
                stage.setScene(funcionarioMainScene);
                break;

            case "fornecedorMain.fxml":
                stage.setScene(fornecedorMainScene);
                break;
        }
        stage.setTitle(title);
    }

    public void inicializarTelas(String tipoUsuario){
        ScreenManager screenManager = ScreenManager.getInstance();

        //As telas abaixo só precisam ser atualizados 1 vez por login realizado.
        switch (tipoUsuario){
            case "adm":
                AdmPerfilController admPerfilController = screenManager.getAdmPerfilController();
                AdmCRUDPromocoesController admCRUDPromocoesController = screenManager.getAdmCRUDPromocoesController();
                AdmLivrosController admLivrosController = ScreenManager.getInstance().getAdmLivrosController();

                admPerfilController.initialize();
                admCRUDPromocoesController.initialize();
                admLivrosController.initialize();
                break;

            case "funcionário":
                FuncionarioRelatoriosController funcionarioRelatoriosController = screenManager.getFuncionariosRelatoriosController();
                FuncionarioCRUDLivrosController funcionarioCRUDLivrosController = screenManager.getFuncionarioCRUDLivrosController();
                FuncionarioPerfilController funcionarioPerfilController = screenManager.getFuncionarioPerfilController();

                funcionarioRelatoriosController.initialize();
                funcionarioCRUDLivrosController.initialize();
                funcionarioPerfilController.initialize();
                break;

            case "fornecedor":
                FornecedorEstoqueController fornecedorEstoqueController = screenManager.getFornecedorEstoqueController();
                FornecedorHistoricoController fornecedorHistoricoController = screenManager.getFornecedorHistoricoController();
                FornecedorPerfilController fornecedorPerfilController = screenManager.getFornecedorPerfilController();

                fornecedorEstoqueController.initialize();
                fornecedorHistoricoController.initialize();
                fornecedorPerfilController.initialize();
                break;

            case "cliente":
                ClienteCatalogoController clienteCatalogoController = screenManager.getClienteCatalogoController();
                ClientePerfilController clientePerfilController = screenManager.getClientePerfilController();

                clientePerfilController.initialize();
                clienteCatalogoController.initialize();
                break;
        }
    }

    //GETS and SETS:
    public static Stage getStage() {
        return stage;
    }
    public static void setStage(Stage stage) {
        ScreenManager.stage = stage;
    }

    public AdmCRUDPromocoesController getAdmCRUDPromocoesController() {
        return admCRUDPromocoesController;
    }

    public AdmCRUDUsuariosController getAdmCRUDUsuariosController() {
        return admCRUDUsuariosController;
    }

    public AdmEstoqueController getAdmEstoqueController() {
        return admEstoqueController;
    }

    public AdmHistoricoComprasEVendasController getAdmHistoricoComprasEVendasController() {
        return admHistoricoComprasEVendasController;
    }
    public Scene getAdmMainScene() {
        return admMainScene;
    }
    public AdmMainController getAdmMainController() {
        return admMainController;
    }

    public AdmLivrosController getAdmLivrosController() {
        return admLivrosController;
    }

    public AdmPerfilController getAdmPerfilController() {
        return admPerfilController;
    }

    public AdmRelatoriosController getAdmRelatoriosController() {
        return admRelatoriosController;
    }

    public Scene getClienteCadastroScene() {
        return clienteCadastroScene;
    }
    public ClienteCadastroController getClienteCadastroController() {
        return clienteCadastroController;
    }

    public ClienteCatalogoController getClienteCatalogoController() {
        return clienteCatalogoController;
    }

    public ClienteHistoricoComprasController getClienteMinhasComprasController() {
        return clienteHistoricoComprasController;
    }

    public ClientePerfilController getClientePerfilController() {
        return clientePerfilController;
    }

    public Scene getClienteMainScene() {
        return clienteMainScene;
    }
    public ClienteMainController getClienteMainController() {
        return clienteMainController;
    }

    public FornecedorEstoqueController getFornecedorEstoqueController() {
        return fornecedorEstoqueController;
    }

    public FornecedorHistoricoController getFornecedorHistoricoController() {
        return fornecedorHistoricoController;
    }

    public FornecedorPerfilController getFornecedorPerfilController() {
        return fornecedorPerfilController;
    }

    public Scene getFornecedorMainScene() {
        return fornecedorMainScene;
    }
    public FornecedorMainController getFornecedorMainController() {
        return fornecedorMainController;
    }

    public FuncionarioCRUDLivrosController getFuncionarioCRUDLivrosController() {
        return funcionarioCRUDLivrosController;
    }

    public FuncionarioEstoqueController getFuncionarioEstoqueController() {
        return funcionarioEstoqueController;
    }

    public FuncionarioPerfilController getFuncionarioPerfilController() {
        return funcionarioPerfilController;
    }

    public FuncionarioHistoricoComprasEVendasController getFuncionarioHistoricoComprasEVendasController() {
        return funcionarioHistoricoComprasEVendasController;
    }

    public FuncionarioRelatoriosController getFuncionariosRelatoriosController() {
        return funcionariosRelatoriosController;
    }

    public Scene getFuncionarioMainScene() {
        return funcionarioMainScene;
    }
    public FuncionarioMainController getFuncionarioMainController() {
        return funcionarioMainController;
    }

    public Scene getLoginScene() {
        return loginScene;
    }
    public LoginController getLoginController() {
        return loginController;
    }

    public boolean isPrecisaApresentarOAlertDadosAdm() {
        return precisaApresentarOAlertDadosAdm;
    }

    public boolean isPrecisaApresentarOAlertDadosFuncionario() {
        return precisaApresentarOAlertDadosFuncionario;
    }

    public void setAdmCRUDPromocoesController(AdmCRUDPromocoesController admCRUDPromocoesController) {
        this.admCRUDPromocoesController = admCRUDPromocoesController;
    }

    public void setAdmCRUDUsuariosController(AdmCRUDUsuariosController admCRUDUsuariosController) {
        this.admCRUDUsuariosController = admCRUDUsuariosController;
    }

    public void setAdmEstoqueController(AdmEstoqueController admEstoqueController) {
        this.admEstoqueController = admEstoqueController;
    }

    public void setAdmHistoricoComprasEVendasController(AdmHistoricoComprasEVendasController admHistoricoComprasEVendasController) {
        this.admHistoricoComprasEVendasController = admHistoricoComprasEVendasController;
    }

    public void setAdmLivrosController(AdmLivrosController admLivrosController) {
        this.admLivrosController = admLivrosController;
    }

    public void setAdmPerfilController(AdmPerfilController admPerfilController) {
        this.admPerfilController = admPerfilController;
    }

    public void setAdmRelatoriosController(AdmRelatoriosController admRelatoriosController) {
        this.admRelatoriosController = admRelatoriosController;
    }

    public void setClienteCatalogoController(ClienteCatalogoController clienteCatalogoController) {
        this.clienteCatalogoController = clienteCatalogoController;
    }

    public void setClienteHistoricoComprasController(ClienteHistoricoComprasController clienteHistoricoComprasController) {
        this.clienteHistoricoComprasController = clienteHistoricoComprasController;
    }

    public void setClientePerfilController(ClientePerfilController clientePerfilController) {
        this.clientePerfilController = clientePerfilController;
    }

    public void setClienteCadastroController(ClienteCadastroController clienteCadastroController) {
        this.clienteCadastroController = clienteCadastroController;
    }

    public static void setInstance(ScreenManager instance) {
        ScreenManager.instance = instance;
    }

    public void setFornecedorEstoqueController(FornecedorEstoqueController fornecedorEstoqueController) {
        this.fornecedorEstoqueController = fornecedorEstoqueController;
    }

    public void setFornecedorHistoricoController(FornecedorHistoricoController fornecedorHistoricoController) {
        this.fornecedorHistoricoController = fornecedorHistoricoController;
    }

    public void setFornecedorPerfilController(FornecedorPerfilController fornecedorPerfilController) {
        this.fornecedorPerfilController = fornecedorPerfilController;
    }

    public void setFuncionarioCRUDLivrosController(FuncionarioCRUDLivrosController funcionarioCRUDLivrosController) {
        this.funcionarioCRUDLivrosController = funcionarioCRUDLivrosController;
    }

    public void setFuncionarioEstoqueController(FuncionarioEstoqueController funcionarioEstoqueController) {
        this.funcionarioEstoqueController = funcionarioEstoqueController;
    }

    public void setFuncionarioHistoricoComprasEVendasController(FuncionarioHistoricoComprasEVendasController funcionarioHistoricoComprasEVendasController) {
        this.funcionarioHistoricoComprasEVendasController = funcionarioHistoricoComprasEVendasController;
    }

    public void setFuncionarioPerfilController(FuncionarioPerfilController funcionarioPerfilController) {
        this.funcionarioPerfilController = funcionarioPerfilController;
    }

    public void setFuncionariosRelatoriosController(FuncionarioRelatoriosController funcionariosRelatoriosController) {
        this.funcionariosRelatoriosController = funcionariosRelatoriosController;
    }

    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}