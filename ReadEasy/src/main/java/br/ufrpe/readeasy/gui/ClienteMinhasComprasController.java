package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClienteMinhasComprasController
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
    private TableColumn<CompraClienteDTO, Integer> colQTD;

    @FXML
    private TableColumn<CompraClienteDTO, Double> colPreco;

    @FXML
    private TableColumn<CompraClienteDTO, LocalDate> colDataCompra;

    //Métodos de troca de tela:
    @FXML
    private void trocarTelaCatalogoCliente(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("clienteCatalogo.fxml", "ReadEasy - Catálogo");
    }

    @FXML
    private void trocarTelaPerfilCliente(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("clientePerfil.fxml", "ReadEasy - Perfil");
    }

    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //Outros métodos:
    @FXML
    public void initialize()
    {
        carregarDadosTabela();
        construirTabela();
        dpDataFim.setValue(LocalDate.now());
    }

    public void onPesquisarClick()
    {
        filtrarPorIntervaloDeTempo();
    }

    public void filtrarPorIntervaloDeTempo()
    {
        LocalDate dataInicio = dpDataInicio.getValue();
        LocalDate dataFim = dpDataFim.getValue();

        if (dataInicio != null && dataFim != null)
        {
            ObservableList<CompraClienteDTO> itensTabela = FXCollections.observableArrayList(ServidorReadEasy.getInstance().
                    listarComprasDTO((Cliente) SessaoUsuario.getUsuarioLogado()));
            ObservableList<CompraClienteDTO> itensFiltrados = FXCollections.observableArrayList();

            for (CompraClienteDTO item : itensTabela) {
                LocalDate dataItem = item.getDataCompra().toLocalDate();
                if (!dataItem.isBefore(dataInicio) && !dataItem.isAfter(dataFim)) {
                    itensFiltrados.add(item);
                }
            }
            if(!dpDataInicio.getValue().isBefore(dataFim) && !dpDataFim.getValue().isAfter(dataInicio)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Selecione uma data com um intervalo válido");
                alert.showAndWait();
            }
            tvTabelaCompras.setItems(itensFiltrados);
        } else
        {
            tvTabelaCompras.setItems(FXCollections.observableArrayList());
        }
    }

    public void carregarDadosTabela()
    {
        SessaoUsuario.getInstance();
        if(SessaoUsuario.getUsuarioLogado() instanceof Cliente)
        {
            tvTabelaCompras.setItems(FXCollections.observableArrayList(obterComprasDoCliente((Cliente)SessaoUsuario.
                    getUsuarioLogado())));
        }
    }

    public List<CompraClienteDTO> obterComprasDoCliente(Cliente cliente){
        List<CompraClienteDTO> comprasDoCliente = new ArrayList<>();


        for (Venda venda : ServidorReadEasy.getInstance().historicoDeComprasDoCliente(cliente)) {

            for (LivroVendido livroVendido : venda.getLivrosVendidos()) {

                Livro livro = livroVendido.getLivro();
                String tituloLivro = livro.getTitulo();
                String autorLivro = livro.getAutor();
                int quantidade = livroVendido.getQuantidade();
                double preco = livro.getPreco();
                LocalDateTime dataCompra = venda.getDataEHora();

                CompraClienteDTO compraClienteDTO = new CompraClienteDTO(tituloLivro, autorLivro, quantidade, preco, dataCompra);
                comprasDoCliente.add(compraClienteDTO);
            }
        }
        return comprasDoCliente;
    }

    public void construirTabela()
    {
        colTitulo.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTituloLivro()));

        colAutor.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAutorLivro()));

        colQTD.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getQuantidade()).asObject());

        colPreco.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getPreco()).asObject());

        colDataCompra.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getDataCompra().toLocalDate()));
    }

    public void btnSairDaConta(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja realmente sair?");
        alert.setContentText("Escolha uma opção.");

        ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(simButton, naoButton);


        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                trocarTelaLogin();
            }
            else {
                alert.close();
            }
        });
    }

}