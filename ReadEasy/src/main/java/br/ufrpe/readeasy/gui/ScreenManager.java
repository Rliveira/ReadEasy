package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Funcionario;
import br.ufrpe.readeasy.beans.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static br.ufrpe.readeasy.gui.SessaoUsuario.usuarioLogado;

public class ScreenManager {
    private static ScreenManager instance;
    private static Stage stage;

    private Scene admCRUDPromocoesScene;
    private AdmCRUDPromocoesController admCRUDPromocoesController;

    private Scene admCRUDUsuariosScene;
    private AdmCRUDUsuariosController admCRUDUsuariosController;

    private Scene admEstoqueScene;
    private AdmEstoqueController admEstoqueController;

    private Scene admHistoricoComprasEvendasScene;
    private AdmHistoricoComprasEVendasController admHistoricoComprasEVendasController;

    private Scene admLivrosScene;
    private AdmLivrosController admLivrosController;

    private Scene admPerfilScene;
    private AdmPerfilController admPerfilController;

    private Scene admRelatoriosScene;
    private AdmRelatoriosController admRelatoriosController;

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

    public static ScreenManager getInstance(){
        if(instance == null){
            instance = new ScreenManager();
        }
        return instance;
    }

    //MÉTODOS:
    public void carregarTelas(Usuario usuarioLogado){
        try {
            if(usuarioLogado != null){
                if(usuarioLogado instanceof Funcionario && ((Funcionario) usuarioLogado).isAdm()){
                    FXMLLoader admTelaCRUDPromocoesPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/admCRUDPromocoes.fxml"));
                    this.admCRUDPromocoesScene = new Scene(admTelaCRUDPromocoesPane.load());
                    this.admCRUDPromocoesController = admTelaCRUDPromocoesPane.getController();

                    FXMLLoader admTelaCRUDUsuariosPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/admCRUDUsuarios.fxml"));
                    this.admCRUDUsuariosScene = new Scene(admTelaCRUDUsuariosPane.load());
                    this.admCRUDUsuariosController = admTelaCRUDUsuariosPane.getController();

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
                    this.admRelatoriosController = admTelaRelatoriosPane.getController();
                }

                else if (usuarioLogado instanceof Cliente){
                    FXMLLoader clientecatalogoPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/clienteCatalogo.fxml"));
                    this.clienteCatalogoScene = new Scene(clientecatalogoPane.load());
                    this.clienteCatalogoController = clientecatalogoPane.getController();

                    FXMLLoader clienteMinhasComprasPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/clienteMinhasCompras.fxml"));
                    this.clienteMinhasComprasScene = new Scene(clienteMinhasComprasPane.load());
                    this.clienteMinhasComprasController = clienteMinhasComprasPane.getController();

                    FXMLLoader clientePerfilPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/clientePerfil.fxml"));
                    this.clientePerfilScene= new Scene(clientePerfilPane.load());
                    this.clientePerfilController = clientePerfilPane.getController();
                }

                else if(usuarioLogado instanceof Fornecedor){
                    FXMLLoader fornecedorEstoquePane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/fornecedorEstoque.fxml"));
                    this.fornecedorEstoqueScene = new Scene(fornecedorEstoquePane.load());
                    this.fornecedorEstoqueController = fornecedorEstoquePane.getController();

                    FXMLLoader fornecedorHistoricoPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/fornecedorHistorico.fxml"));
                    this.fornecedorHistoricoScene = new Scene(fornecedorHistoricoPane.load());
                    this.fornecedorHistoricoController = fornecedorHistoricoPane.getController();

                    FXMLLoader fornecedorPerfilPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/fornecedorPerfil.fxml"));
                    this.fornecedorPerfilScene = new Scene(fornecedorPerfilPane.load());
                    this.fornecedorPerfilController = fornecedorPerfilPane.getController();
                }

                else{
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
                }
            }

            FXMLLoader clientecadastroPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/clienteCadastro.fxml"));
            this.clienteCadastroScene = new Scene(clientecadastroPane.load());
            this.clienteCadastroController = clientecadastroPane.getController();

            FXMLLoader LoginPane = new FXMLLoader(getClass().getResource("/br/ufrpe/readeasy/Login.fxml"));
            this.loginScene = new Scene(LoginPane.load());
            this.loginController = LoginPane.getController();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void TrocarTela(String fxml, String title) {
        switch (fxml) {
            case "Login.fxml":
                stage.setScene(loginScene);
                break;

            case "admCRUDPromocoes.fxml":
                stage.setScene(admCRUDPromocoesScene);
                break;

            case "admCRUDUsuarios.fxml":
                admCRUDUsuariosController.initialize();
                stage.setScene(admCRUDUsuariosScene);
                break;

            case "admEstoque.fxml":
                admEstoqueController.initialize();
                stage.setScene(admEstoqueScene);
                break;

            case "admHistoricoComprasEVendas.fxml":
                admHistoricoComprasEVendasController.initialize();
                stage.setScene(admHistoricoComprasEvendasScene);
                break;

            case "admLivros.fxml":
                stage.setScene(admLivrosScene);
                break;

            case "admPerfil.fxml":
                admPerfilController.initialize();
                stage.setScene(admPerfilScene);
                break;

            case "admRelatorios.fxml":
                stage.setScene(admRelatoriosScene);
                break;

            case "clienteCadastro.fxml":
                stage.setScene(clienteCadastroScene);
                break;

            case "clienteMinhasCompras.fxml":
                clienteMinhasComprasController.initialize();
                stage.setScene(clienteMinhasComprasScene);
                break;

            case "clienteCatalogo.fxml":
                stage.setScene(clienteCatalogoScene);
                clienteCatalogoController.initialize();
                break;

            case "clientePerfil.fxml":
                clientePerfilController.initialize();
                stage.setScene(clientePerfilScene);
                break;

            case "fornecedorEstoque.fxml":
                stage.setScene(fornecedorEstoqueScene);
                break;

            case "fornecedorHistorico.fxml":
                fornecedorHistoricoController.initialize();
                stage.setScene(fornecedorHistoricoScene);
                break;

            case "fornecedorPerfil.fxml":
                fornecedorPerfilController.initialize();
                stage.setScene(fornecedorPerfilScene);
                break;

            case "funcionarioEstoque.fxml":
                funcionarioEstoqueController.initialize();
                stage.setScene(funcionarioEstoqueScene);
                break;

            case "funcionarioPerfil.fxml":
                funcionarioPerfilController.initialize();
                stage.setScene(funcionarioPefilScene);
                break;

            case "funcionarioRelatorios.fxml":
                stage.setScene(funcionariosRelatoriosScene);
                break;

            case "funcionarioCRUDLivros.fxml":
                funcionarioCRUDLivrosController.initialize();
                stage.setScene(funcionarioCRUDLivrosScene);
                break;

            case "funcionarioHistoricoComprasEVendas.fxml":
                stage.setScene(funcionarioHistoricoComprasEVendasScene);
                break;
        }
        stage.setTitle(title);
    }

    public void inicializarTelas(String tipoUsuario){
        ScreenManager screenManager = ScreenManager.getInstance();

        //As telas abaixo só precisam ser atualizados 1 vez por login realizado.
        switch (tipoUsuario){
            case "adm":
                AdmCRUDPromocoesController admCRUDPromocoesController = screenManager.getAdmCRUDPromocoesController();
                AdmLivrosController admLivrosController = ScreenManager.getInstance().getAdmLivrosController();
                AdmRelatoriosController admRelatoriosController = screenManager.getAdmRelatoriosController();

                admCRUDPromocoesController.initialize();
                admLivrosController.initialize();
                admRelatoriosController.initialize();
                break;

            case "funcionário":
                FuncionarioRelatoriosController funcionarioRelatoriosController = screenManager.getFuncionariosRelatoriosController();
                funcionarioRelatoriosController.initialize();
                break;

            case "fornecedor":
                FornecedorEstoqueController fornecedorEstoqueController = screenManager.getFornecedorEstoqueController();
                FornecedorHistoricoController fornecedorHistoricoController = screenManager.getFornecedorHistoricoController();

                fornecedorEstoqueController.initialize();
                fornecedorHistoricoController.initialize();
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

    public Scene getAdmCRUDPromocoesScene() {
        return admCRUDPromocoesScene;
    }
    public AdmCRUDPromocoesController getAdmCRUDPromocoesController() {
        return admCRUDPromocoesController;
    }

    public Scene getAdmCRUDUsuariosScene() {
        return admCRUDUsuariosScene;
    }
    public AdmCRUDUsuariosController getAdmCRUDUsuariosController() {
        return admCRUDUsuariosController;
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
    public AdmRelatoriosController getAdmRelatoriosController() {
        return admRelatoriosController;
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