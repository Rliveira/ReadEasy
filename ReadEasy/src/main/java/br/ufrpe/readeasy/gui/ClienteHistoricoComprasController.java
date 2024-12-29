package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.CompraClienteDTO;
import br.ufrpe.readeasy.business.Fachada;
import br.ufrpe.readeasy.exceptions.DataInvalidaException;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClienteHistoricoComprasController
{
    public Application app;

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnCatalogo;

    @FXML
    private Button btnHistorico;

    @FXML
    private Button btnSair;

    @FXML
    private Button btnPesquisar;

    @FXML
    private DatePicker dpDataInicio;

    @FXML
    private DatePicker dpDataFim;

    @FXML
    private TableView<CompraClienteDTO> tvTabelaCompras;

    @FXML
    private TableColumn<CompraClienteDTO, String> colTitulo;

    @FXML
    private TableColumn<CompraClienteDTO, String> colAutor;

    @FXML
    private TableColumn<CompraClienteDTO, Integer> colQtd;

    @FXML
    private TableColumn<CompraClienteDTO, String> colPreco;

    @FXML
    private TableColumn<CompraClienteDTO, String> colDataCompra;

    private static ClienteHistoricoComprasController instance;
    private boolean ignorarInitialize;

    //construtor:
    public ClienteHistoricoComprasController() {
        if(instance == null){
            instance = this;
            ignorarInitialize = true;
        }
    }

    //métodos:
    @FXML
    public void initialize()
    {
        ScreenManager screenManager = ScreenManager.getInstance();

        if(screenManager.getClienteMinhasComprasController() == null){
            screenManager.setClienteHistoricoComprasController(instance);
        }
        if(!ignorarInitialize){
            carregarDadosTabela();
            construirTabela();
            dpDataFim.setValue(LocalDate.now());
        }
    }

    private void carregarDadosTabela() {
        if(SessaoUsuario.getUsuarioLogado() instanceof Cliente) {
            LocalDate dataAtual = LocalDate.now();
            LocalDate datainicial = LocalDate.of(dataAtual.getYear(), 1, 1);
            List<CompraClienteDTO> comprasCliente;
            try {
                comprasCliente = Fachada.getInstance().historicoDeComprasDoCliente((Cliente) SessaoUsuario.getUsuarioLogado(), datainicial, dataAtual);
            } catch (DataInvalidaException e) {
                throw new RuntimeException(e);
            }
            tvTabelaCompras.setItems(FXCollections.observableArrayList(comprasCliente));
        }
    }

    private void construirTabela(){
        colTitulo.setCellValueFactory(cellData -> {
            String titulo = cellData.getValue().getTituloLivro();
            return new SimpleStringProperty(titulo);
        });

        colAutor.setCellValueFactory(cellData -> {
            String autor = cellData.getValue().getAutorLivro();
            return new SimpleStringProperty(autor);
        });

        colQtd.setCellValueFactory(cellData -> {
            int quantidade = cellData.getValue().getQuantidade();
            return new SimpleIntegerProperty(quantidade).asObject();
        });

        colDataCompra.setCellValueFactory(cellData -> {
            LocalDate dataDaCompra = cellData.getValue().getDataCompra().toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = dataDaCompra.format(formatter);
            return new SimpleStringProperty(dataFormatada);
        });

        colPreco.setCellValueFactory(cellData -> {
            double preco = cellData.getValue().getPreco();
            String precoLivro = String.format("%.2f", preco);
            return new SimpleStringProperty(precoLivro);
        });
    }

    public void btnFiltrarPorIntervaloDeTempo(){
        LocalDate dataInicio = dpDataInicio.getValue();
        LocalDate dataFim = dpDataFim.getValue();

        if (dataInicio != null && dataFim != null) {
            try {
                List<CompraClienteDTO> historicoCompras = Fachada.getInstance().historicoDeComprasDoCliente((Cliente) SessaoUsuario.getUsuarioLogado(), dataInicio, dataFim);
                tvTabelaCompras.getItems().clear();
                tvTabelaCompras.getItems().addAll(FXCollections.observableArrayList(historicoCompras));
            }catch (DataInvalidaException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Datas inválidas");
                alert.setContentText("Certifique de que a data inicio seja anterior a data fim" +
                        " e que ambas sejam iguais ou anteriores a data atual.");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Data de inicio ou data de fim não selecionada.");
            alert.setContentText("Certifique de selecinar ambas as datas para realizar a pesquisa");
            alert.showAndWait();
        }
    }

    //gets and sets:
    public static ClienteHistoricoComprasController getInstance() {
        return instance;
    }

    public void setIgnorarInitialize(boolean ignorarInitialize) {
        this.ignorarInitialize = ignorarInitialize;
    }
}