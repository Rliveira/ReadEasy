package br.ufrpe.readeasy.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FuncionarioHistoricoComprasEVendasController {

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
    private DatePicker dtpDataDeInicioVendas;

    @FXML
    private DatePicker dtpDataDeFimVendas;

    @FXML
    private Button btnPesquisarVendas;

    @FXML
    private TableView<?> tvVendas;

    @FXML
    private TableColumn<?, ?> clnTituloVendas;

    @FXML
    private TableColumn<?, ?> clnAutorVendas;

    @FXML
    private TableColumn<?, ?> clnFornecedorVendas;

    @FXML
    private TableColumn<?, ?> clnQuantidadeVendas;

    @FXML
    private TableColumn<?, ?> clnPrecoVendas;

    @FXML
    private TableColumn<?, ?> clnClienteVendas;

    @FXML
    private DatePicker dtpDataDeInicioCompras;

    @FXML
    private DatePicker dtpDataDeFimCompras;

    @FXML
    private Button btnPesquisarCompras;

    @FXML
    private TableView<?> tvCompras;

    @FXML
    private TableColumn<?, ?> clnTituloCompras;

    @FXML
    private TableColumn<?, ?> clnAutorCompras;

    @FXML
    private TableColumn<?, ?> clnFornecedorCompras;

    @FXML
    private TableColumn<?, ?> clnQuantidadeCompras;

    @FXML
    private TableColumn<?, ?> clnPrecoCompras;

    @FXML
    private TableColumn<?, ?> clnClienteCompras;

    @FXML
    public void trocarTelaEstoqueFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioEstoque.fxml", "ReadEasy - Estoque");
    }

    @FXML
    public void trocarTelaLivroFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioCRUDLivros.fxml", "ReadEasy - Livros");
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
    void btnPesquisarComprasPorData(ActionEvent event) {

    }

    @FXML
    void btnPesquisarVendasPorData(ActionEvent event) {

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
