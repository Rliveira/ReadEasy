package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Livro;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FornecedorEstoqueController {

    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnEstoque;
    @FXML
    private Button btnHistorico;
    @FXML
    private Button btnSair;

    @FXML
    private TableView<Livro> tbEstoqueLivrosFornecedor;
    @FXML
    private TableColumn<Livro, String> colLivro;
    @FXML
    private TableColumn<Livro, String> colAutor;
    @FXML
    private TableColumn<Livro, Integer> colQuantidade;

}
