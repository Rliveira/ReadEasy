package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Endereco;
import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Funcionario;
import br.ufrpe.readeasy.beans.Usuario;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

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
    private Label labelSenha;

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


    public Usuario usuarioLogado = new Funcionario("Lucas Albuquerque", "12384274165",
                                 LocalDate.of(2000, 1, 1), "admin", "admin1234",
        new Endereco(59624712, "Rua Fictícia", "Bairro",
                             "Cidade", "Estado"), "(81)99196-9420", true, null);
    @FXML
    public void initialize()
    {
        labelNome.setText(SessaoUsuario.getUsuarioLogado().getNome());
        labelCPF.setText(SessaoUsuario.getUsuarioLogado().getCpf());
        labelDataNascimento.setText(SessaoUsuario.getUsuarioLogado().getDataNascimento().toString());
        labelNomeUsuario.setText(SessaoUsuario.getUsuarioLogado().getLogin());
        labelSenha.setText(SessaoUsuario.getUsuarioLogado().getSenha());
        labelTelefone.setText(SessaoUsuario.getUsuarioLogado().getTelefone());
        if(SessaoUsuario.getUsuarioLogado() instanceof Fornecedor)
        {
            labelTipoFornecedor.setText(((Fornecedor) SessaoUsuario.getUsuarioLogado()).getTipoFornecedor().toString());
        }
        labelCEP.setText(String.valueOf(SessaoUsuario.getUsuarioLogado().getEndereco().getCep()));
        labelRua.setText(SessaoUsuario.getUsuarioLogado().getEndereco().getRua());
        labelBairro.setText(SessaoUsuario.getUsuarioLogado().getEndereco().getBairro());
        labelCidade.setText(SessaoUsuario.getUsuarioLogado().getEndereco().getCidade());
        labelEstado.setText(SessaoUsuario.getUsuarioLogado().getEndereco().getEstado());
    }

    //métodos de troca de tela
    public void trocarTelaEstoqueFornecedor(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("fornecedorEstoque.fxml", "ReadEasy - Estoque");
    }

    public void trocarTelaHistoricoFornecedor(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("fornecedorHistorico.fxml", "ReadEasy - Perfil");
    }

    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //Outros métodos:
    public void SairDaConta(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja realmente sair?");
        alert.setContentText("Escolha uma opção.");

        ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(simButton, naoButton);


        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                trocarTelaLogin();
            }
            else {
                alert.close();
            }
        });
    }

    public void setUsuarioLogado(Usuario usuario)
    {
        SessaoUsuario.setUsuarioLogado(usuario);
    }
}