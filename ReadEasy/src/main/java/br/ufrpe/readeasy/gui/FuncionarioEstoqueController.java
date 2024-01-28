package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.beans.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class FuncionarioEstoqueController {

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
    private Button btnAdicionar;
    @FXML
    private Button btnRemover;

    @FXML
    private ImageView imCapaLivro;

    @FXML
    private ComboBox<String> cbLivro;

    @FXML
    private TextField tfQuantidade;
    @FXML
    private TextField tfPesquisar;

    @FXML
    private TableView<Livro> tbEstoqueLivraria;
    @FXML
    private TableColumn<Livro, String> colLivro;
    @FXML
    private TableColumn<Livro, Integer> colQuantidade;
    @FXML
    private TableColumn<Livro, String> colFornecedor;


}