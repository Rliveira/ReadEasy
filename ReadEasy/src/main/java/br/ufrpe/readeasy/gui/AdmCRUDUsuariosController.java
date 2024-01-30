package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdmCRUDUsuariosController
{
    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnDeletar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnLivros;

    @FXML
    private Button btnEstoque;

    @FXML
    private Button btnUsuarios;

    @FXML
    private Button btnPromoções;

    @FXML
    private Button btnHistorico;

    @FXML
    private Button btnRelatorios;

    @FXML
    private Button btnSair;

    @FXML
    private ComboBox<?> cbCargo;

    @FXML
    private ComboBox<?> cbTipoFornecedor;

    @FXML
    private TextField txtFieldPesquisar;

    @FXML
    private TextField txtFieldNome;

    @FXML
    private TextField txtFieldCPF;

    @FXML
    private TextField txtFieldLogin;

    @FXML
    private TextField txtFieldTelefone;

    @FXML
    private TextField txtFieldADM;

    @FXML
    private TextField txtFieldCEP;

    @FXML
    private TextField txtFieldRua;

    @FXML
    private TextField txtFieldBairro;

    @FXML
    private TextField txtFieldCidade;

    @FXML
    private TextField txtFieldEstado;

    @FXML
    private TextField txtFieldSenha;

    @FXML
    private DatePicker DPDataNascimento;

    @FXML
    private TableView<Usuario> TVUsuarios;

    @FXML
    private TableColumn<Usuario, String> ColNome;

    @FXML
    private TableColumn<Usuario, String> ColCPF;

    @FXML
    private TableColumn<Usuario, String> ColNomeUsuario;

    @FXML
    private TableColumn<Usuario, String> ColDataNascimento;

    @FXML
    private TableColumn<Usuario, String> ColTelefone;

    @FXML
    private TableColumn<Usuario, String> ColCargo;

    @FXML
    private TableColumn<Usuario, String> ColADM;

    //Métodos de troca de tela:
    @FXML
    public void trocarTelaEstoqueAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admEstoque.fxml", "ReadEasy - Estoque");
    }

    @FXML
    public void trocarTelaHistoricoAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admRelatorios.fxml", "ReadEasy - Relatorios");
    }

    @FXML
    public void trocarTelaLivrosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admLivros.fxml", "ReadEasy - Livros");
    }

    @FXML
    public void trocarTelaPerfilAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admPerfil.fxml", "ReadEasy - Perfil");
    }

    @FXML
    public void trocarTelaPromocoesAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admCRUDPromocoes.fxml", "ReadEasy - Promoções");
    }

    @FXML
    public void trocarTelaRelatoriosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admRelatorios.fxml", "ReadEasy - Relatórios");
    }

    @FXML
    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //Outros métodos:
    @FXML
    public void btnSairDaConta(){
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

    //outros métodos:
    @FXML
    void cbEscolherCargo(ActionEvent event) {

    }

    @FXML
    void cbEscolherTipoFornecedor(ActionEvent event) {

    }

}