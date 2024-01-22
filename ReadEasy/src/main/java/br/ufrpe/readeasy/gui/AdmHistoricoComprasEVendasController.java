package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.LivroVendido;
import br.ufrpe.readeasy.beans.Venda;
import br.ufrpe.readeasy.business.ControladorUsuario;
import br.ufrpe.readeasy.business.ControladorVenda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private TableColumn<LivroVendido, String> clnAutorVendas; //String = cliente.getNome

    @FXML
    private TableColumn<LivroVendido, String> clnClienteVendas;

    @FXML
    private TableColumn<?, LocalDateTime> clnDataCompra;

    @FXML
    private TableColumn<LivroVendido, LocalDateTime> clnDataVenda;

    @FXML
    private TableColumn<?, String> clnFornecedorCompras;

    @FXML
    private TableColumn<LivroVendido, String> clnFornecedorVendas;

    @FXML
    private TableColumn<LivroVendido, Integer> clnPrecoVendas;

    @FXML
    private TableColumn<?, Integer> clnQuantidadeCompras;

    @FXML
    private TableColumn<LivroVendido, Integer> clnQuantidadeVendas;

    @FXML
    private TableColumn<?, String> clnTituloCompras;

    @FXML
    private TableColumn<LivroVendido, String> clnTituloVendas;

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
    private TableView<LivroVendido> tableHistoricoVendas;

    @FXML
    private TableView<LivroVendido> tableHistoricoCompras;

    private ObservableList<LivroVendido> listaVendas;

    private ObservableList<Venda> listaCompras;

    protected ObservableList<LivroVendido> ListaVendas(LocalDate dataInicio, LocalDate dataFim) {

        List<Venda> vendas = ControladorVenda.getInstance().HistoricoDeVendasPorPeriodo(dataInicio.atStartOfDay()
                , dataFim.atTime(23,59,59));
        listaVendas = FXCollections.observableArrayList();
        List<LivroVendido> livrosVendidos = new ArrayList<>();
        for (Venda venda : vendas) {
            livrosVendidos.addAll(venda.getLivrosVendidos());
            listaVendas.addAll(livrosVendidos);
        }

        return listaVendas;
    }

    protected void onBtnPesquisarVendasClick(ActionEvent event) { //FIXME - provavelmente isso tá errado

        listaVendas = ListaVendas(dtpkDataInicioVendas.getValue(), dtpkDataFimVendas.getValue());
        clnTituloVendas.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        clnAutorVendas.setCellValueFactory(new PropertyValueFactory<>("autor"));
        clnFornecedorVendas.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        clnClienteVendas.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        clnDataVenda.setCellValueFactory(new PropertyValueFactory<>("dataEHora"));
        clnQuantidadeVendas.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        clnPrecoVendas.setCellValueFactory(new PropertyValueFactory<>("preco"));
        tableHistoricoVendas.setItems(listaVendas);

    }

}
