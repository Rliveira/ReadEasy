package br.ufrpe.readeasy.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class FuncionarioCRUDLivrosController {

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnLivros;

    @FXML
    private Button btnEstoque;

    @FXML
    private Button btnHistorico;

    @FXML
    private Button btnRelatorios;

    @FXML
    private Button btnSair;

    @FXML
    private ImageView imgvCapaLivro;

    @FXML
    private Button btnDownloadImagem;

    @FXML
    private TextField tfTitulo;

    @FXML
    private TextField tfAutor;

    @FXML
    private TextField tfPreco;

    @FXML
    private ComboBox<?> cbGenero;

    @FXML
    private ComboBox<?> cbFornecedor;

    @FXML
    private Button btnAdicionarLivro;

    @FXML
    private TextField tfPesquisar;

    @FXML
    private TableView<?> tbvCatálogo;

    @FXML
    private TableColumn<?, ?> clnTitulo;

    @FXML
    private TableColumn<?, ?> clnAutor;

    @FXML
    private TableColumn<?, ?> clnFornecedor;

    @FXML
    private TableColumn<?, ?> clnPreco;

    @FXML
    private Button btnDeletarLivro;

    @FXML
    private Button btnEditarLivro;

    @FXML
    private TableView<?> tbvGeneros;

    @FXML
    private TableColumn<?, ?> clnTodosOsGeneros;

    @FXML
    private Button btnAdicionarGenero;

    @FXML
    private Button btnDeletarGenero;

    //métodos de troca de tela
    @FXML
    public void trocarTelaEstoqueFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioEstoque.fxml", "ReadEasy - Estoque");
    }

    @FXML
    public void trocarTelaHistoricoFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioHistoricoComprasEVendas.fxml", "ReadEasy - Histórico");
    }

    @FXML
    public void trocarTelaPerfilFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioPerfil.fxml", "ReadEasy - Perfil");
    }

    @FXML
    public void trocarTelaRelatoriosFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioRelatorios.fxml", "ReadEasy - Relatorios");
    }

    @FXML
    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //Outros métodos:
    @FXML
    void btnCadastrarGenero(ActionEvent event) {

    }

    @FXML
    void btnCadastrarLivro(ActionEvent event) {

    }

    @FXML
    void btnDeletarGenero(ActionEvent event) {

    }

    @FXML
    void btnDeletarLivro(ActionEvent event) {

    }

    @FXML
    void btnDownloadImagem(ActionEvent event) {

    }

    @FXML
    void btnEditarLivro(ActionEvent event) {

    }

    @FXML
    void cbSelecionarFornecedor(ActionEvent event) {

    }

    @FXML
    void cbSelecionarGênero(ActionEvent event) {

    }

    @FXML
    void tfPesquisarLivro(ActionEvent event) {

    }

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
}
