package br.ufrpe.readeasy.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenManager {
    private static ScreenManager instance;
    private static Stage stage;

    private Scene admCRUDPromocoesScene;
    private AdmCRUDPromocoesController admadmCRUDPromocoesController;

    private Scene admCRUDUsuariosScene;
    private AdmCRUDUsuariosController admadmCRUDUsuariosController;

    private Scene admEstoqueScene;
    private AdmEstoqueController admEstoqueController;

    private Scene admHistoricoComprasEvendasScene;
    private AdmHistoricoComprasEVendasController admHistoricoComprasEVendasController;

    private Scene admLivrosScene;
    private AdmLivrosController admLivrosController;

    private Scene admPerfilScene;
    private AdmPerfilController admPerfilController;

    private Scene admRelatoriosScene;
    private AdmRelatoriosController admTelaRelatoriosController;

    private Scene clienteCadastroScene;
    private ClienteCadastroController clienteCadastroController;

    private Scene clienteCatalogoScene;
    private ClienteCatalogoController clienteCatalogoController;

    private Scene clienteMinhasComprasScene;
    private ClienteMinhasComprasController clienteMinhasComprasController;

    private Scene clientePerfilScene;
    private ClientePerfilController clientePerfilController;

    private Scene fornecedorEstoqueScene;
    private FornecedorEstoqueController fornecedorEstoqueController;

    private Scene fornecedorHistoricoScene;
    private FornecedorHistoricoController fornecedorHistoricoController;

    private Scene fornecedorPerfilScene;
    private FornecedorPerfilController fornecedorPerfilController;

    private Scene funcionarioCRUDLivrosScene;
    private FuncionarioCRUDLivrosController funcionarioCRUDLivrosController;

    private Scene funcionarioEstoqueScene;
    private FuncionarioEstoqueController funcionarioEstoqueController;

    private Scene funcionarioHistoricoComprasEVendasScene;
    private FuncionarioHistoricoComprasEVendasController funcionarioHistoricoComprasEVendasController;

    private Scene funcionarioPefilScene;
    private FuncionarioPerfilController funcionarioPerfilController;

    private Scene funcionariosRelatoriosScene;
    private FuncionarioRelatoriosController funcionariosRelatoriosController;

    private Scene loginScene;
    private LoginController loginController;

    //CONSTRUTOR:
    private ScreenManager(){
        this.carregarTelas();
    }

    public static ScreenManager getInstance(){
        if(instance == null){
            instance = new ScreenManager();
        }
        return instance;
    }

    //MÉTODOS:
    private void carregarTelas(){
        try {
            FXMLLoader admTelaCRUDPromocoesPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/admCRUDPromocoes.fxml"));
            this.admCRUDPromocoesScene = new Scene(admTelaCRUDPromocoesPane.load());
            this.admadmCRUDPromocoesController = admTelaCRUDPromocoesPane.getController();

            FXMLLoader admTelaCRUDUsuariosPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/admCRUDUsuarios.fxml"));
            this.admCRUDUsuariosScene = new Scene(admTelaCRUDUsuariosPane.load());
            this.admadmCRUDUsuariosController = admTelaCRUDUsuariosPane.getController();

            FXMLLoader admEstoquePane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/admEstoque.fxml"));
            this.admEstoqueScene = new Scene(admEstoquePane.load());
            this.admEstoqueController = admEstoquePane.getController();

            FXMLLoader admHistoricoComprasEVendasPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/admHistoricoComprasEVendas.fxml"));
            this.admHistoricoComprasEvendasScene = new Scene(admHistoricoComprasEVendasPane.load());
            this.admHistoricoComprasEVendasController = admHistoricoComprasEVendasPane.getController();

            FXMLLoader admLivrosPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/admLivros.fxml"));
            this.admLivrosScene = new Scene(admLivrosPane.load());
            this.admLivrosController = admLivrosPane.getController();

            FXMLLoader admPerfilPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/admPerfil.fxml"));
            this.admPerfilScene = new Scene(admPerfilPane.load());
            this.admPerfilController = admPerfilPane.getController();

            FXMLLoader admTelaRelatoriosPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/admRelatorios.fxml"));
            this.admRelatoriosScene = new Scene(admTelaRelatoriosPane.load());
            this.admTelaRelatoriosController = admTelaRelatoriosPane.getController();

            FXMLLoader clientecadastroPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/clienteCadastro.fxml"));
            this.clienteCadastroScene = new Scene(clientecadastroPane.load());
            this.clienteCadastroController = clientecadastroPane.getController();

            FXMLLoader clientecatalogoPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/clienteCatalogo.fxml"));
            this.clienteCatalogoScene = new Scene(clientecatalogoPane.load());
            this.clienteCatalogoController = clientecatalogoPane.getController();

            FXMLLoader clienteMinhasComprasPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/clienteMinhasCompras.fxml"));
            this.clienteMinhasComprasScene = new Scene(clienteMinhasComprasPane.load());
            this.clienteMinhasComprasController = clienteMinhasComprasPane.getController();

            FXMLLoader clientePerfilPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/clientePerfil.fxml"));
            this.clientePerfilScene= new Scene(clientePerfilPane.load());
            this.clientePerfilController = clientePerfilPane.getController();

            FXMLLoader fornecedorEstoquePane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/fornecedorEstoque.fxml"));
            this.fornecedorEstoqueScene = new Scene(fornecedorEstoquePane.load());
            this.fornecedorEstoqueController = fornecedorEstoquePane.getController();

            FXMLLoader fornecedorHistoricoPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/fornecedorHistorico.fxml"));
            this.fornecedorHistoricoScene = new Scene(fornecedorHistoricoPane.load());
            this.fornecedorHistoricoController = fornecedorHistoricoPane.getController();

            FXMLLoader fornecedorPerfilPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/fornecedorPerfil.fxml"));
            this.fornecedorPerfilScene = new Scene(fornecedorPerfilPane.load());
            this.fornecedorPerfilController = fornecedorPerfilPane.getController();

            FXMLLoader funcionarioCRUDLivrosPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/funcionarioCRUDLivros.fxml"));
            this.funcionarioCRUDLivrosScene = new Scene(funcionarioCRUDLivrosPane.load());
            this.funcionarioCRUDLivrosController = funcionarioCRUDLivrosPane.getController();

            FXMLLoader funcionarioEstoquePane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/funcionarioEstoque.fxml"));
            this.funcionarioEstoqueScene = new Scene(funcionarioEstoquePane.load());
            this.funcionarioEstoqueController = funcionarioEstoquePane.getController();

            FXMLLoader funcionarioHistoricoComprasEVendasPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/funcionarioHistoricoComprasEVendas.fxml"));
            this.funcionarioHistoricoComprasEVendasScene = new Scene(funcionarioHistoricoComprasEVendasPane.load());
            this.funcionarioHistoricoComprasEVendasController = funcionarioHistoricoComprasEVendasPane.getController();

            FXMLLoader funcionarioPerfilPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/funcionarioPerfil.fxml"));
            this.funcionarioPefilScene = new Scene(funcionarioPerfilPane.load());
            this.funcionarioPerfilController = funcionarioPerfilPane.getController();

            FXMLLoader funcionarioRelatoriosPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/funcionarioRelatorios.fxml"));
            this.funcionariosRelatoriosScene = new Scene(funcionarioRelatoriosPane.load());
            this.funcionariosRelatoriosController = funcionarioRelatoriosPane.getController();

            FXMLLoader LoginPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/Login.fxml"));
            this.loginScene = new Scene(LoginPane.load());
            this.loginController = LoginPane.getController();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void TrocarTela(String fxml, String title) {
        switch (fxml){
            case "Login.fxml" -> stage.setScene(loginScene);
            case "admCRUDPromocoes.fxml" -> stage.setScene(admCRUDPromocoesScene);
            case "admCRUDUsuarios.fxml" -> stage.setScene(admCRUDUsuariosScene);
            case "admEstoque.fxml" -> stage.setScene(admEstoqueScene);
            case "admHistoricoComprasEVendas.fxml" -> stage.setScene(admHistoricoComprasEvendasScene);
            case "admLivros.fxml" -> stage.setScene(admLivrosScene);
            case "admPerfil.fxml" -> stage.setScene(admPerfilScene);
            case "admRelatorios.fxml" -> stage.setScene(admRelatoriosScene);
            case "clienteCadastro.fxml" -> stage.setScene(clienteCadastroScene);
            case "clienteMinhasCompras.fxml" -> stage.setScene(clienteMinhasComprasScene);
            case "clienteCatalogo.fxml" -> stage.setScene(clienteCatalogoScene);
            case "clientePerfil.fxml" -> stage.setScene(clientePerfilScene);
            case "fornecedorEstoque.fxml" -> stage.setScene(fornecedorEstoqueScene);
            case "fornecedorHistorico.fxml" -> stage.setScene(fornecedorHistoricoScene);
            case "fornecedorPerfil.fxml" -> stage.setScene(fornecedorPerfilScene);
            case "funcionarioEstoque.fxml" -> stage.setScene(funcionarioEstoqueScene);
            case "funcionarioPerfil.fxml" -> stage.setScene(funcionarioPefilScene);
            case "funcionarioRelatorios.fxml" -> stage.setScene(funcionariosRelatoriosScene);
            case "funcionarioCRUDLivros.fxml" -> stage.setScene(funcionarioCRUDLivrosScene);
            case "funcionarioHistoricoComprasEVendas.fxml" -> stage.setScene(funcionarioHistoricoComprasEVendasScene);
        }
        stage.setTitle(title);
    }

    //GETS and SETS:
    public static Stage getStage() {
        return stage;
    }
    public static void setStage(Stage stage) {
        ScreenManager.stage = stage;
    }

    public Scene getAdmCRUDPromocoesScene() {
        return admCRUDPromocoesScene;
    }
    public AdmCRUDPromocoesController getAdmadmCRUDPromocoesController() {
        return admadmCRUDPromocoesController;
    }

    public Scene getAdmCRUDUsuariosScene() {
        return admCRUDUsuariosScene;
    }
    public AdmCRUDUsuariosController getAdmadmCRUDUsuariosController() {
        return admadmCRUDUsuariosController;
    }

    public Scene getAdmEstoqueScene() {
        return admEstoqueScene;
    }
    public AdmEstoqueController getAdmEstoqueController() {
        return admEstoqueController;
    }

    public Scene getAdmHistoricoComprasEvendasScene() {
        return admHistoricoComprasEvendasScene;
    }
    public AdmHistoricoComprasEVendasController getAdmHistoricoComprasEVendasController() {
        return admHistoricoComprasEVendasController;
    }

    public Scene getAdmLivrosScene() {
        return admLivrosScene;
    }
    public AdmLivrosController getAdmLivrosController() {
        return admLivrosController;
    }

    public Scene getAdmPerfilScene() {
        return admPerfilScene;
    }
    public AdmPerfilController getAdmPerfilController() {
        return admPerfilController;
    }

    public Scene getAdmRelatoriosScene() {
        return admRelatoriosScene;
    }
    public AdmRelatoriosController getAdmTelaRelatoriosController() {
        return admTelaRelatoriosController;
    }

    public Scene getClienteCadastroScene() {
        return clienteCadastroScene;
    }
    public ClienteCadastroController getClienteCadastroController() {
        return clienteCadastroController;
    }

    public Scene getClienteCatalogoScene() {
        return clienteCatalogoScene;
    }
    public ClienteCatalogoController getClienteCatalogoController() {
        return clienteCatalogoController;
    }

    public Scene getClienteMinhasComprasScene() {
        return clienteMinhasComprasScene;
    }
    public ClienteMinhasComprasController getClienteMinhasComprasController() {
        return clienteMinhasComprasController;
    }

    public Scene getClientePerfilScene() {
        return clientePerfilScene;
    }
    public ClientePerfilController getClientePerfilController() {
        return clientePerfilController;
    }

    public Scene getFornecedorEstoqueScene() {
        return fornecedorEstoqueScene;
    }
    public FornecedorEstoqueController getFornecedorEstoqueController() {
        return fornecedorEstoqueController;
    }

    public Scene getFornecedorHistoricoScene() {
        return fornecedorHistoricoScene;
    }
    public FornecedorHistoricoController getFornecedorHistoricoController() {
        return fornecedorHistoricoController;
    }

    public Scene getFornecedorPerfilScene() {
        return fornecedorPerfilScene;
    }
    public FornecedorPerfilController getFornecedorPerfilController() {
        return fornecedorPerfilController;
    }

    public Scene getFuncionarioCRUDLivrosScene() {
        return funcionarioCRUDLivrosScene;
    }
    public FuncionarioCRUDLivrosController getFuncionarioCRUDLivrosController() {
        return funcionarioCRUDLivrosController;
    }

    public Scene getFuncionarioEstoqueScene() {
        return funcionarioEstoqueScene;
    }
    public FuncionarioEstoqueController getFuncionarioEstoqueController() {
        return funcionarioEstoqueController;
    }

    public Scene getFuncionarioPefilScene() {
        return funcionarioPefilScene;
    }
    public FuncionarioPerfilController getFuncionarioPerfilController() {
        return funcionarioPerfilController;
    }

    public Scene getFuncionarioHistoricoComprasEVendasScene() {
        return funcionarioHistoricoComprasEVendasScene;
    }
    public FuncionarioHistoricoComprasEVendasController getFuncionarioHistoricoComprasEVendasController() {
        return funcionarioHistoricoComprasEVendasController;
    }

    public Scene getFuncionariosRelatoriosScene() {
        return funcionariosRelatoriosScene;
    }
    public FuncionarioRelatoriosController getFuncionariosRelatoriosController() {
        return funcionariosRelatoriosController;
    }

    public Scene getLoginScene() {
        return loginScene;
    }
    public LoginController getLoginController() {
        return loginController;
    }
}