package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Funcionario;
import br.ufrpe.readeasy.beans.Usuario;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

import static br.ufrpe.readeasy.gui.SessaoUsuario.usuarioLogado;

public class LoginController {

    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfSenha;
    @FXML
    private Label lblCadastreSe;
    @FXML
    private Button btnLogin;

    @FXML
    public void onBtnLoginclick()
    {   boolean excecaoLevantada = false;
        List<Usuario> users = ServidorReadEasy.getInstance().listarUsuarios();
        String login = tfUsuario.getText();
        String senha = tfSenha.getText();

        try
        {
            if(ServidorReadEasy.getInstance().checarLogin(login, senha))
            {
                for(int i=0; i<users.size();i++)
                {
                    boolean achou = false;
                    if(((login.equals(users.get(i).getLogin()) && senha.equals(users.get(i).getSenha()))) || achou)
                    {
                        achou = true;
                        SessaoUsuario.getInstance();
                        SessaoUsuario.setUsuarioLogado(users.get(i));
                        tfSenha.clear();
                        tfUsuario.clear();
                    }
                }
                trocarTelaUsuario(SessaoUsuario.getUsuarioLogado());
            }
        } catch (CampoVazioException e)
        {
            excecaoLevantada = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
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
            excecaoLevantada = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
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

    //métodos de troca de tela:
    @FXML
    public void trocarTelaCadastro(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("clienteCadastro.fxml", "Cadastro - ReadEasy");
    }

    @FXML
    private void trocarTelaUsuario(Usuario usuario){
        if(usuario instanceof Funcionario){
            Funcionario funcionario = (Funcionario) usuario;
            if (funcionario.isAdm()){
                ScreenManager sm = ScreenManager.getInstance();
                SessaoUsuario.setUsuarioLogado(usuario);
                sm.TrocarTela("admRelatorios.fxml", "ReadEasy - Relatorios");
            }
            else{
                ScreenManager sm = ScreenManager.getInstance();
                SessaoUsuario.setUsuarioLogado(usuario);
                sm.TrocarTela("funcionarioEstoque.fxml", "ReadEasy - Estoque");
            }
        }
        else if(usuario instanceof Fornecedor){
            ScreenManager sm = ScreenManager.getInstance();
            SessaoUsuario.setUsuarioLogado(usuario);
            sm.TrocarTela("fornecedorEstoque.fxml", "ReadEasy - Estoque");
        }
        else{
            ScreenManager sm = ScreenManager.getInstance();
            SessaoUsuario.setUsuarioLogado(usuario);
            sm.TrocarTela("clienteCatalogo.fxml", "ReadEasy - Catálogo");
        }
    }

    public static void setUsuarioLogado(Usuario usuarioLogado) {
        LoginController.setUsuarioLogado(usuarioLogado);
    }
}
