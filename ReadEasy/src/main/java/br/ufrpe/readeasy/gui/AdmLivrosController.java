package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Genero;
import br.ufrpe.readeasy.beans.Livro;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class AdmLivrosController {

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
    private Button btnDownloadImagem;
    @FXML
    private Button btnAdicionar1;
    @FXML
    private Button btnDeletar1;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnAdicionar2;
    @FXML
    private Button btnDeletar2;

    @FXML
    private ImageView ivCapaDoLivro;

    @FXML
    private TextField tfTitulo;
    @FXML
    private TextField tfAutor;
    @FXML
    private TextField tfPreco;

    @FXML
    private ComboBox<Genero> cbGenero;
    @FXML
    private ComboBox<Fornecedor> cbFornecedor;

    @FXML
    private TableView<Livro> tvCatalogoLivros;
    @FXML
    private TableColumn <Livro, String> colTitulo;
    @FXML
    private TableColumn <Livro, String> colAutor;
    @FXML
    private TableColumn<Livro, String> colFornecedor;
    @FXML
    private TableColumn<Livro, String> colPreco;

    @FXML
    private ListView<Genero> lvTodosOsGeneros;
    @FXML
    private ListView<Genero> lvGenerosDoLivro;


}