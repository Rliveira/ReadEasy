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

    @FXML
    public void onBtnLoginclick()
    {   boolean excecaoLevantada = false;
        List<Usuario> users = ServidorReadEasy.getInstance().listarUsuarios();
        String login = tfUsuario.getText();
        String senha = pfSenha.getText();

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
                inicializarTelas("adm");
                sm.TrocarTela("admPerfil.fxml", "ReadEasy - Relatorios");
            }
            else{
                ScreenManager sm = ScreenManager.getInstance();
                SessaoUsuario.setUsuarioLogado(usuario);
                inicializarTelas("funcionário");
                sm.TrocarTela("funcionarioPerfil.fxml", "ReadEasy - Estoque");
            }
        }
        else if(usuario instanceof Fornecedor){
            ScreenManager sm = ScreenManager.getInstance();
            SessaoUsuario.setUsuarioLogado(usuario);
            inicializarTelas("fornecedor");
            sm.TrocarTela("fornecedorPerfil.fxml", "ReadEasy - Estoque");
        }
        else{
            ScreenManager sm = ScreenManager.getInstance();
            SessaoUsuario.setUsuarioLogado(usuario);
            inicializarTelas("cliente");
            sm.TrocarTela("clientePerfil.fxml", "ReadEasy - Catálogo");
        }
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

            case "cliente":
                ClienteCatalogoController clienteCatalogoController = screenManager.getClienteCatalogoController();
                clienteCatalogoController.initialize();
                break;
        }
    }
}
