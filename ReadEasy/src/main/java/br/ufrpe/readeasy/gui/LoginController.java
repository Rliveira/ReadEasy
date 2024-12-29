package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Funcionario;
import br.ufrpe.readeasy.beans.Usuario;
import br.ufrpe.readeasy.business.Fachada;
import br.ufrpe.readeasy.exceptions.CampoVazioException;
import br.ufrpe.readeasy.exceptions.LoginInvalidoException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class LoginController {

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfSenha;
    @FXML
    private Label lblCadastreSe;
    @FXML
    private Button btnLogin;

    boolean telasAdmCarregadas = false;
    boolean telasFuncionarioCarregadas = false;
    boolean telasFornecedorCarregadas = false;
    boolean telasClienteCarregadas = false;
    private static LoginController instance;

    //Construtor:
    public LoginController() {
        if(instance == null){
            instance = this;
            telasAdmCarregadas = false;
            telasFuncionarioCarregadas = false;
            telasFornecedorCarregadas = false;
            telasClienteCarregadas = false;
        }
    }

    //métodos:
    @FXML
    public void trocarTelaCadastro(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.trocartelasPrincipais("clienteCadastro.fxml", "ReadEasy - Cadastro");
    }

    @FXML
    public void onBtnLoginclick()
    {
        List<Usuario> users = Fachada.getInstance().listarUsuarios();
        String login = tfUsuario.getText();
        String senha = pfSenha.getText();
        Alert alert = new Alert(Alert.AlertType.ERROR);

        try
        {
            if(Fachada.getInstance().checarLogin(login, senha))
            {
                Usuario usuarioLogadoSucesso = null;
                for(int i=0; i<users.size();i++)
                {
                    if(login.equals(users.get(i).getLogin()) && senha.equals(users.get(i).getSenha()))
                    {
                        usuarioLogadoSucesso = users.get(i);
                        pfSenha.clear();
                        tfUsuario.clear();
                    }
                }
                SessaoUsuario.getInstance();
                SessaoUsuario.setUsuarioLogado(usuarioLogadoSucesso);
                trocarTelaUsuario(SessaoUsuario.getUsuarioLogado());
            }
        } catch (CampoVazioException e)
        {
            alert.setHeaderText("Existem campos a serem preenchidos!");
            alert.setContentText(e.getMessage());

            ButtonType okButton = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    alert.close();
                }
            });
        }
        catch (LoginInvalidoException e)
        {
            alert.setContentText(e.getMessage());
            alert.setHeaderText("Login inválido!");
            ButtonType okButton = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    alert.close();
                }
            });
        }

    }

    @FXML
    private void trocarTelaUsuario(Usuario usuario){
        if(usuario instanceof Funcionario){
            Funcionario funcionario = (Funcionario) usuario;
            if (funcionario.isAdm()){
                ScreenManager sm = ScreenManager.getInstance();
                SessaoUsuario.setUsuarioLogado(usuario);

                if(!telasAdmCarregadas){
                    sm.carregarTelas(SessaoUsuario.getUsuarioLogado());
                    telasAdmCarregadas = true;
                }

                sm.getAdmCRUDPromocoesController().setIgnorarInitialize(false);
                sm.getAdmCRUDUsuariosController().setIgnorarInitialize(false);
                sm.getAdmEstoqueController().setIgnorarInitialize(false);
                sm.getAdmHistoricoComprasEVendasController().setIgnorarInitialize(false);
                sm.getAdmLivrosController().setIgnorarInitialize(false);
                sm.getAdmPerfilController().setIgnorarInitialize(false);
                sm.getAdmRelatoriosController().setIgnorarInitialize(false);

                sm.inicializarTelas("adm");
                sm.trocartelasPrincipais("admMain.fxml", "ReadEasy - Perfil");
                sm.getAdmMainController().trocarTelaPerfilAdm();
            }
            else{
                ScreenManager sm = ScreenManager.getInstance();
                SessaoUsuario.setUsuarioLogado(usuario);

                if(!telasFuncionarioCarregadas){
                    sm.carregarTelas(SessaoUsuario.getUsuarioLogado());
                    telasFuncionarioCarregadas = true;
                }

                sm.getFuncionarioPerfilController().setIgnorarInitialize(false);
                sm.getFuncionariosRelatoriosController().setIgnorarInitialize(false);
                sm.getFuncionarioEstoqueController().setIgnorarInitialize(false);
                sm.getFuncionarioHistoricoComprasEVendasController().setIgnorarInitialize(false);
                sm.getFuncionarioCRUDLivrosController().setIgnorarInitialize(false);

                sm.inicializarTelas("funcionário");
                sm.trocartelasPrincipais("funcionarioMain.fxml", "ReadEasy - Perfil");
                sm.getFuncionarioMainController().trocarTelaPerfilFuncionario();
            }
        }
        else if(usuario instanceof Fornecedor){
            ScreenManager sm = ScreenManager.getInstance();
            SessaoUsuario.setUsuarioLogado(usuario);

            if(!telasFornecedorCarregadas){
                sm.carregarTelas(SessaoUsuario.getUsuarioLogado());
                telasFornecedorCarregadas = true;
            }

            sm.getFornecedorEstoqueController().setIgnorarInitialize(false);
            sm.getFornecedorPerfilController().setIgnorarInitialize(false);
            sm.getFornecedorHistoricoController().setIgnorarInitialize(false);

            sm.inicializarTelas("fornecedor");
            sm.trocartelasPrincipais("fornecedorMain.fxml", "ReadEasy - Perfil");
            sm.getFornecedorMainController().trocarTelaPerfilFornecedor();
        }
        else{
            ScreenManager sm = ScreenManager.getInstance();
            SessaoUsuario.setUsuarioLogado(usuario);

            if(!telasClienteCarregadas){
                sm.carregarTelas(SessaoUsuario.getUsuarioLogado());
                telasClienteCarregadas = true;
            }

            sm.getClientePerfilController().setIgnorarInitialize(false);
            sm.getClienteCatalogoController().setIgnorarInitialize(false);
            sm.getClienteMinhasComprasController().setIgnorarInitialize(false);

            sm.inicializarTelas("cliente");
            sm.trocartelasPrincipais("clienteMain.fxml", "ReadEasy - Perfil");
            sm.getClienteMainController().trocarTelaPerfilCliente();
        }

        Stage stage = ScreenManager.getStage();
        stage.setResizable(true);
        stage.setMaximized(true);
    }
}
