package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Funcionario;
import br.ufrpe.readeasy.beans.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfSenha;
    @FXML
    private Label lblCadastreSe;
    @FXML
    private Button btnLogin;

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
                sm.getAdmPerfilController().setUsuarioLogado(usuario);
                sm.TrocarTela("admRelatorios.fxml", "ReadEasy - Relatorios");
            }
            else{
                ScreenManager sm = ScreenManager.getInstance();
                sm.getFuncionarioPerfilController().setUsuarioLogado(usuario);
                sm.TrocarTela("funcionarioEstoque.fxml", "ReadEasy - Estoque");
            }
        }
        else if(usuario instanceof Fornecedor){
            ScreenManager sm = ScreenManager.getInstance();
            sm.getFornecedorPerfilController().setUsuarioLogado(usuario);
            sm.TrocarTela("fornecedorEstoque.fxml", "ReadEasy - Estoque");
        }
        else{
            ScreenManager sm = ScreenManager.getInstance();
            sm.getAdmPerfilController().setUsuarioLogado(usuario);
            sm.TrocarTela("clienteCadastro.fxml", "ReadEasy - Catálogo");
        }
    }
}
