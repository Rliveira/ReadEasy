package br.ufrpe.readeasy.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;


public class ClienteCatalogoController {

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnCatalogo;

    @FXML
    private Button btnHistorico;

    @FXML
    private Button btnSair;

    @FXML
    private ScrollPane spCatalogoDaLivraria;

    @FXML
    private TableView<?> tbCarrinho;

    @FXML
    private TableColumn<?, ?> clnLivro;

    @FXML
    private TableColumn<?, ?> clnQuantidade;

    @FXML
    private TableColumn<?, ?> clnPrecoUnitario;

    @FXML
    private ComboBox<?> cbAplicarPromocao;

    @FXML
    private Label lblTotal;

    @FXML
    private Button btnPagar;

    @FXML
    private Button btnRemoverDoCarrinho;

    @FXML
    private Label lblNomeDoLivro;

    @FXML
    private Label lblPreco;

    @FXML
    private Spinner<?> spnSeletor;

    @FXML
    private Button btnAdicionar;

    @FXML
    private ImageView imgvLivro;

    @FXML
    void btnAdicionar(ActionEvent event) {

    }

    @FXML
    void btnAplicarPromocaoACompra(ActionEvent event) {

    }

    @FXML
    void btnPagar(ActionEvent event) {

    }

    @FXML
    void btnRemoverDoCarrinho(ActionEvent event) {

    }

}
