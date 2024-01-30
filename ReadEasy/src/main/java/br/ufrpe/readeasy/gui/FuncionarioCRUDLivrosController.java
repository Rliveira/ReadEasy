package br.ufrpe.readeasy.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

}
