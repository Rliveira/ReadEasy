package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Venda;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDateTime;

public class AdmHistoricoComprasEVendasController {

    @FXML
    private Button btnEstoque;

    @FXML
    private Button btnHistorico;

    @FXML
    private Button btnLivros;

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnPesquisarCompras;

    @FXML
    private Button btnPesquisarVendas;

    @FXML
    private Button btnPromoções;

    @FXML
    private Button btnRelatorios;

    @FXML
    private Button btnSair;

    @FXML
    private Button btnUsuarios;

    @FXML
    private TableColumn<?, String> clnAutorCompras;

    @FXML
    private TableColumn<Venda, String> clnAutorVendas; //String = cliente.getNome

    @FXML
    private TableColumn<Venda, String> clnClienteVendas;

    @FXML
    private TableColumn<?, LocalDateTime> clnDataCompra;

    @FXML
    private TableColumn<Venda, LocalDateTime> clnDataVenda;

    @FXML
    private TableColumn<?, String> clnFornecedorCompras;

    @FXML
    private TableColumn<Venda, String> clnFornecedorVendas;

    @FXML
    private TableColumn<Venda, Integer> clnPrecoVendas;

    @FXML
    private TableColumn<?, Integer> clnQuantidadeCompras;

    @FXML
    private TableColumn<Venda, Integer> clnQuantidadeVendas;

    @FXML
    private TableColumn<?, String> clnTituloCompras;

    @FXML
    private TableColumn<Venda, String> clnTituloVendas;

    @FXML
    private DatePicker dtpkDataFimCompras;

    @FXML
    private DatePicker dtpkDataFimVendas;

    @FXML
    private DatePicker dtpkDataInicioCompras;

    @FXML
    private DatePicker dtpkDataInicioVendas;

    @FXML
    private Tab tabCompras;

    @FXML
    private Tab tabVendas;

    @FXML
    private TableView<Venda> tableHistoricoVendas;

    @FXML
    private TableView<Venda> tableHistoricoCompras;

}
