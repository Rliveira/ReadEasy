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

}