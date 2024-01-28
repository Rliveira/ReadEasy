package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Livro;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class AdmEstoqueController {

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
    private Button btnSair;
    @FXML
    private Button btnAdicionar;

    @FXML
    private ImageView ivCapaDoLivro;

    @FXML
    private ComboBox <String> cbLivro;

    @FXML
    private TextField tfPesquisar;
    @FXML
    private TextField tfQuantidade;

    @FXML
    private TableView<Livro> tvEstoque;
    @FXML
    private TableColumn<Livro, String> colLivro;
    @FXML
    private TableColumn<Livro, String> colFornecedor;
    @FXML
    private TableColumn<Livro, Integer> colQuantidade;


}