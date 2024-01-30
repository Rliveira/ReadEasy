package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Usuario;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;

public class FuncionarioRelatoriosController {

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
    private BarChart<String, Integer> bcRankingLivros;
    @FXML
    private CategoryAxis catXLivros;
    @FXML
    private NumberAxis catYNumeroDeVendas;

    @FXML
    private TableView<Usuario> tvUsuariosMaisCompras;
    @FXML
    private TableColumn<Usuario, String> colUsuario1;
    @FXML
    private TableColumn<Usuario, Integer> colTotal1;

    @FXML
    private TableView<Usuario> tvUsuariosMaisGasto;
    @FXML
    private TableColumn<Usuario, String> colUsuario2;
    @FXML
    private TableColumn<Usuario, Integer> colTotal2;

    //Métodos de troca de tela:

    @FXML
    public void trocarTelaLivroFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioCRUDLivros.fxml", "ReadEasy - Livros");
    }

    @FXML
    public void trocarTelaHistoricoFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioHistoricoComprasEVendas.fxml", "ReadEasy - Histórico");
    }

    @FXML
    public void trocarTelaEstoqueFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioEstoque.fxml", "ReadEasy - Estoque");
    }

    @FXML
    public void trocarTelaPerfilFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioPerfil.fxml", "ReadEasy - Perfil");
    }

    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //Outros métodos:
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