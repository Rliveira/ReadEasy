package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Livro;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

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
    private DatePicker DPDataInicio;

    @FXML
    private DatePicker DPDataFim;

    @FXML
    private TableView TVTabelaCompras;

    @FXML
    private TableColumn<Livro, String> colTitulo;

    @FXML
    private TableColumn<Livro, String> colAutor;

    @FXML
    private TableColumn<Livro, Integer> colQTD;

    @FXML
    private TableColumn<Livro, Double> colPreco;

    @FXML
    private TableColumn<Livro, LocalDate> colDataCompra;
    
}