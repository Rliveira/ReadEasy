package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Usuario;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;

public class AdmTelaRelatoriosController {

    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnLivros;
    @FXML
    private Button btnEstoque;
    @FXML
    private Button btnUsuarios;
    @FXML
    private Button btnPromocoes;
    @FXML
    private Button btnHistorico;
    @FXML
    private Button btnRelatorios;
    @FXML
    private Button btnPesquisar1;
    @FXML
    private Button btnPesquisar2;

    @FXML
    private BarChart<String, Double> bcDados;
    @FXML
    private CategoryAxis catX;
    @FXML
    private NumberAxis catY;

    @FXML
    private BarChart<String, Integer> bcRankingLivros;
    @FXML
    private CategoryAxis catXLivros;
    @FXML
    private NumberAxis catYNumeroDeVendas;

    @FXML
    private ComboBox<String> cbCategoria;
    @FXML
    private ComboBox<String> cbMesOuAno1;
    @FXML
    private ComboBox<String> cbMesOuAno2;
    @FXML
    private ComboBox<String> cbPeriodo1;
    @FXML
    private ComboBox<String> cbPeriodo2;

    @FXML
    private Label LblVendasDiarias;
    @FXML
    private Label lblFaturamentoDiario;
    @FXML
    private Label lblComprasDiarias;

    @FXML
    private TableView<Usuario> tvUsuariosMaisCompras;
    @FXML
    private TableColumn<Usuario, String> colUsuario1;
    @FXML
    private TableColumn<Usuario, Integer> colTotal1;

    @FXML
    private TableView<Usuario> TVUsuariosMaisGasto;
    @FXML
    private TableColumn<Usuario, String> colUsuario2;
    @FXML
    private TableColumn<Usuario, Integer> colTotal2;


}
