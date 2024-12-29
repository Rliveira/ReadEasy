package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.CompraLivrariaDTO;
import br.ufrpe.readeasy.beans.VendaLivrariaDTO;
import br.ufrpe.readeasy.business.Fachada;
import br.ufrpe.readeasy.exceptions.DataInvalidaException;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FuncionarioHistoricoComprasEVendasController {

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
    private DatePicker dtpkDataInicioVendas;

    @FXML
    private DatePicker dtpkDataFimVendas;

    @FXML
    private Button btnPesquisarVendas;

    @FXML
    private TableView<VendaLivrariaDTO> tableHistoricoVendas;

    @FXML
    private TableColumn<VendaLivrariaDTO, String> clnTituloVendas;

    @FXML
    private TableColumn<VendaLivrariaDTO, String> clnAutorVendas;

    @FXML
    private TableColumn<VendaLivrariaDTO, String> clnClienteVendas;

    @FXML
    private TableColumn<VendaLivrariaDTO, String> clnDataVenda;

    @FXML
    private TableColumn<VendaLivrariaDTO, String> clnFornecedorVendas;

    @FXML
    private TableColumn<VendaLivrariaDTO, Double> clnPrecoVendas;

    @FXML
    private TableColumn<VendaLivrariaDTO, Integer> clnQuantidadeVendas;


    @FXML
    private DatePicker dtpkDataFimCompras;

    @FXML
    private DatePicker dtpkDataInicioCompras;

    @FXML
    private Button btnPesquisarCompras;

    @FXML
    private TableView<CompraLivrariaDTO> tableHistoricoCompras;

    @FXML
    private TableColumn<CompraLivrariaDTO, String> clnTituloCompras;

    @FXML
    private TableColumn<CompraLivrariaDTO, String> clnAutorCompras;

    @FXML
    private TableColumn<CompraLivrariaDTO, String> clnFornecedorCompras;

    @FXML
    private TableColumn<CompraLivrariaDTO, Integer> clnQuantidadeCompras;

    @FXML
    private TableColumn<CompraLivrariaDTO, String> clnDataCompra;

    @FXML
    private TableColumn<CompraLivrariaDTO, String> clnValorTotal;

    @FXML
    private TableColumn<CompraLivrariaDTO, String> clnValorPorLivro;

    private static FuncionarioHistoricoComprasEVendasController instance;
    private boolean ignorarInitialize;

    //Construtor:
    public FuncionarioHistoricoComprasEVendasController() {
        if(instance == null){
            instance = this;
            ignorarInitialize = true;
        }
    }

    //Métodos:
    public void initialize() {
        ScreenManager screenManager = ScreenManager.getInstance();

        if(screenManager.getFuncionarioHistoricoComprasEVendasController() == null){
            screenManager.setFuncionarioHistoricoComprasEVendasController(instance);
        }
        if (!ignorarInitialize) {
            this.construirTableHistoricoVendas();
            this.inicializarTableHistoricoVendas();
            this.construirTableHistoricoCompras();
            this.inicializarTableHistoricoCompras();
            dtpkDataFimCompras.setValue(LocalDate.now());
        }
    }

    @FXML
    private void construirTableHistoricoVendas(){
        clnTituloVendas.setCellValueFactory(cellData -> {
            String titulo = cellData.getValue().getTitulo();
            return new SimpleStringProperty(titulo);
        });

        clnAutorVendas.setCellValueFactory(cellData -> {
            String autor = cellData.getValue().getAutor();
            return new SimpleStringProperty(autor);
        });

        clnFornecedorVendas.setCellValueFactory(cellData -> {
            String fornecedor = cellData.getValue().getNomeFornecedor();
            return new SimpleStringProperty(fornecedor);
        });

        clnClienteVendas.setCellValueFactory(cellData -> {
            String nomeCliente = cellData.getValue().getNomeCliente();
            return new SimpleStringProperty(nomeCliente);
        });

        clnDataVenda.setCellValueFactory(cellData -> {
            LocalDate dataVenda = cellData.getValue().getDataVenda();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = dataVenda.format(formatter);
            return new SimpleStringProperty(dataFormatada);
        });

        clnQuantidadeVendas.setCellValueFactory(cellData -> {
            int quantidade = cellData.getValue().getQuantidade();
            return new SimpleIntegerProperty(quantidade).asObject();
        });

        clnPrecoVendas.setCellValueFactory(cellData -> {
            double preco = cellData.getValue().getPreco();
            return new SimpleDoubleProperty(preco).asObject();
        });
    }

    public void inicializarTableHistoricoVendas() {
        List<VendaLivrariaDTO> vendas = null;
        try {
            vendas = Fachada.getInstance().listarVendasLivrariaDTO(null, null);

            // Ordena as vendas por data (data atual pra data mais antiga)
            Collections.sort(vendas, Comparator.comparing(VendaLivrariaDTO::getDataVenda).reversed());
        } catch (DataInvalidaException e) {
            System.out.println(e.getMessage()); // Essa exceção não irá ocorrer aqui.
        }
        tableHistoricoVendas.setItems(FXCollections.observableArrayList(vendas));
    }

    @FXML
    protected void onBtnPesquisarVendasClick() {
        LocalDate dataInicio = dtpkDataInicioVendas.getValue();
        LocalDate dataFim = dtpkDataFimVendas.getValue();

        List<VendaLivrariaDTO> vendasLivraria = null;
        try {
            vendasLivraria = Fachada.getInstance().listarVendasLivrariaDTO(dataInicio, dataFim);
        } catch (DataInvalidaException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Data inválida");
            alert.setContentText("Confira se a data de início não é posterior à data de fim e que ambas não são posteriores à data atual.");
            alert.showAndWait();
        }
        tableHistoricoVendas.setItems(FXCollections.observableArrayList(vendasLivraria));
    }

    private void construirTableHistoricoCompras(){
        clnTituloCompras.setCellValueFactory(cellData -> {
            String titulo = cellData.getValue().getTituloLivro();
            return new SimpleStringProperty(titulo);
        });

        clnAutorCompras.setCellValueFactory(cellData -> {
            String autor = cellData.getValue().getAutor();
            return new SimpleStringProperty(autor);
        });

        clnFornecedorCompras.setCellValueFactory(cellData -> {
            String fornecedor = cellData.getValue().getNomeFornecedor();
            return new SimpleStringProperty(fornecedor);
        });

        clnDataCompra.setCellValueFactory(cellData -> {
            LocalDate dataDaCompra = cellData.getValue().getDataDaCompra();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = dataDaCompra.format(formatter);
            return new SimpleStringProperty(dataFormatada);
        });

        clnQuantidadeCompras.setCellValueFactory(cellData -> {
            int quantidade = cellData.getValue().getQuantidade();
            return new SimpleIntegerProperty(quantidade).asObject();
        });

        clnValorPorLivro.setCellValueFactory(cellData -> {
            double preco = cellData.getValue().getValorTotalPago();
            int quantidade = cellData.getValue().getQuantidade();
            double valor = preco / quantidade;
            String valorPorLivro = String.format("%.2f", valor);
            return new SimpleStringProperty(valorPorLivro);
        });

        clnValorTotal.setCellValueFactory(cellData -> {
            double valor = cellData.getValue().getValorTotalPago();
            String valorTotalPago = String.format("%.2f", valor);
            return new SimpleStringProperty(valorTotalPago);
        });
    }

    public void inicializarTableHistoricoCompras() {
        List<CompraLivrariaDTO> historicoCompras = new ArrayList<>();
        try {
            historicoCompras = Fachada.getInstance().historicoLivrosCompradosLivraria(null, null);

            // Ordena as vendas por data (data atual pra data mais antiga)
            Collections.sort(historicoCompras, Comparator.comparing(CompraLivrariaDTO::getDataDaCompra).reversed());
        } catch (DataInvalidaException e) {
            System.out.println(e.getMessage()); // Essa exceção não irá ocorrer aqui.
        }

        tableHistoricoCompras.setItems(FXCollections.observableArrayList(historicoCompras));
    }

    @FXML
    protected void onBtnPesquisarComprasClick() {
        LocalDate dataInicio = dtpkDataInicioCompras.getValue();
        LocalDate dataFim = dtpkDataFimCompras.getValue();

        List<CompraLivrariaDTO> comprasLivraria = null;
        try {
            comprasLivraria = Fachada.getInstance().historicoLivrosCompradosLivraria(dataInicio, dataFim);
        } catch (DataInvalidaException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Data inválida");
            alert.setContentText("Confira se a data de início não é posterior à data de fim e que ambas não são posteriores à data atual.");
            alert.showAndWait();
        }
        tableHistoricoCompras.setItems(FXCollections.observableArrayList(comprasLivraria));
    }

    //gets and sets:

    public void setBtnPerfil(Button btnPerfil) {
        this.btnPerfil = btnPerfil;
    }

    public static void setInstance(FuncionarioHistoricoComprasEVendasController instance) {
        FuncionarioHistoricoComprasEVendasController.instance = instance;
    }

    public void setIgnorarInitialize(boolean ignorarInitialize) {
        this.ignorarInitialize = ignorarInitialize;
    }
}
