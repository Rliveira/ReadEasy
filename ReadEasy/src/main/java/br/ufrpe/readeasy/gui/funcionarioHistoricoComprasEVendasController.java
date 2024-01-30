package br.ufrpe.readeasy.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class funcionarioHistoricoComprasEVendasController {

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
    private DatePicker dtpDataDeInicioVendas;

    @FXML
    private DatePicker dtpDataDeFimVendas;

    @FXML
    private Button btnPesquisarVendas;

    @FXML
    private TableView<?> tvVendas;

    @FXML
    private TableColumn<?, ?> clnTituloVendas;

    @FXML
    private TableColumn<?, ?> clnAutorVendas;

    @FXML
    private TableColumn<?, ?> clnFornecedorVendas;

    @FXML
    private TableColumn<?, ?> clnQuantidadeVendas;

    @FXML
    private TableColumn<?, ?> clnPrecoVendas;

    @FXML
    private TableColumn<?, ?> clnClienteVendas;

    @FXML
    private DatePicker dtpDataDeInicioCompras;

    @FXML
    private DatePicker dtpDataDeFimCompras;

    @FXML
    private Button btnPesquisarCompras;

    @FXML
    private TableView<?> tvCompras;

    @FXML
    private TableColumn<?, ?> clnTituloCompras;

    @FXML
    private TableColumn<?, ?> clnAutorCompras;

    @FXML
    private TableColumn<?, ?> clnFornecedorCompras;

    @FXML
    private TableColumn<?, ?> clnQuantidadeCompras;

    @FXML
    private TableColumn<?, ?> clnPrecoCompras;

    @FXML
    private TableColumn<?, ?> clnClienteCompras;

    @FXML
    void btnPesquisarComprasPorData(ActionEvent event) {

    }

    @FXML
    void btnPesquisarVendasPorData(ActionEvent event) {

    }

}
