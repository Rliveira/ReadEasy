package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Venda;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class FornecedorHistoricoController {

    @FXML
    private Button btnEstoque;

    @FXML
    private Button btnHistorico;

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnPesquisar;

    @FXML
    private Button btnSair;

    @FXML
    private DatePicker btnpkDataFim;

    @FXML
    private TableColumn<Venda, LocalDateTime> clnDataVenda;

    @FXML
    private TableColumn<Venda, Integer> clnQuantidade;

    @FXML
    private TableColumn<Venda, String> clnTitulo;

    @FXML
    private TableColumn<Venda, String> clnAutor;

    @FXML
    private DatePicker dtpkDataInicio;

    @FXML
    private TableView<Venda> tableHistorico;

}