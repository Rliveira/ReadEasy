package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Funcionario;
import br.ufrpe.readeasy.beans.Usuario;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.CampoVazioException;
import br.ufrpe.readeasy.exceptions.LoginInvalidoException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    //métodos de troca de tela:
    @FXML
    public void trocarTelaCadastro(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("clienteCadastro.fxml", "Cadastro - ReadEasy");
    }

    //Outros métodos:
    @FXML
    public void onBtnLoginclick()
    {
        List<Usuario> users = ServidorReadEasy.getInstance().listarUsuarios();
        String login = tfUsuario.getText();
        String senha = pfSenha.getText();
        Alert alert = new Alert(Alert.AlertType.ERROR);

        try
        {
            if(ServidorReadEasy.getInstance().checarLogin(login, senha))
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
                sm.inicializarTelas("adm");
                sm.TrocarTela("admPerfil.fxml", "ReadEasy - Relatorios");
            }
            else{
                ScreenManager sm = ScreenManager.getInstance();
                SessaoUsuario.setUsuarioLogado(usuario);

                if(!telasFuncionarioCarregadas){
                    sm.carregarTelas(SessaoUsuario.getUsuarioLogado());
                    telasFuncionarioCarregadas = true;
                }
                sm.inicializarTelas("funcionário");
                sm.TrocarTela("funcionarioPerfil.fxml", "ReadEasy - Estoque");
            }
        }
        else if(usuario instanceof Fornecedor){
            ScreenManager sm = ScreenManager.getInstance();
            SessaoUsuario.setUsuarioLogado(usuario);

            if(!telasFornecedorCarregadas){
                sm.carregarTelas(SessaoUsuario.getUsuarioLogado());
                telasFornecedorCarregadas = true;
            }
            sm.inicializarTelas("fornecedor");
            sm.TrocarTela("fornecedorPerfil.fxml", "ReadEasy - Estoque");
        }
        else{
            ScreenManager sm = ScreenManager.getInstance();
            SessaoUsuario.setUsuarioLogado(usuario);

            if(!telasClienteCarregadas){
                sm.carregarTelas(SessaoUsuario.getUsuarioLogado());
                telasClienteCarregadas = true;
            }
            sm.TrocarTela("clientePerfil.fxml", "ReadEasy - Catálogo");
        }
    }
}
