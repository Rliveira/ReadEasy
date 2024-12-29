package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Genero;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.beans.Usuario;
import br.ufrpe.readeasy.business.Fachada;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
    private Button btnCatalogoCompleto;

    @FXML
    private TextField tfPesquisar;

    @FXML
    private TableView<Livro> tvEstoqueLivrosFornecedor;
    @FXML
    private TableColumn<Livro, String> colTitulo;
    @FXML
    private TableColumn<Livro, String> colAutor;
    @FXML
    private TableColumn<Livro, Integer> colQuantidade;

    @FXML
    private GridPane gpCatalogolivrosFornecedor;

    @FXML
    private ScrollPane spCatalogo;

    @FXML
    private ComboBox<String> cbGenero;

    private static FornecedorEstoqueController instance;
    private boolean ignorarInitialize;
    private int numeroDeColunas;
    private List<Genero> generos;
    private List<Livro> listaDeLivrosCatalogoFornecedor = new ArrayList<>();
    private List<VBox> cartoesLivroCatalogoFornecedor = new ArrayList<>();
    private List<FornecedorCartaoLivroController> controladoresCartaoLivro = new ArrayList<>();

    //Construtor:


    public FornecedorEstoqueController() {
        if(instance == null){
            instance = this;
            this.ignorarInitialize = true;
        }
    }

    //Métodos:
    public void initialize(){
        ScreenManager screenManager = ScreenManager.getInstance();

        if(screenManager.getFornecedorEstoqueController() == null) {
            screenManager.setFornecedorEstoqueController(instance);
        }
        if(!ignorarInitialize){
            construirTabela();
            inicializarTabela();
            inicializarCbGenero();
            inicializarCatalogoDeLivrosFornecedor();
        }
    }

    private void inicializarCbGenero(){
        this.generos = Arrays.asList(Genero.values());

        List<String> nomesGenero = new ArrayList<>();

        for (Genero genero : generos){
            nomesGenero.add(genero.getDescricaoEnum());
        }
        cbGenero.getItems().clear();
        cbGenero.getItems().addAll(nomesGenero);
    }

    public void inicializarCatalogoDeLivrosFornecedor() {
        listaDeLivrosCatalogoFornecedor.clear();
        Usuario usuario = SessaoUsuario.getUsuarioLogado();

        if(usuario instanceof Fornecedor){
            Fornecedor fornecedor = (Fornecedor) usuario ;
            Fachada fachada = Fachada.getInstance();
            List<Livro> listaDeLivrosFornecedor = fachada.listarLivrosPorFornecedor(fornecedor);
            listaDeLivrosFornecedor.sort(Comparator.comparing(Livro::getTitulo));
            listaDeLivrosCatalogoFornecedor.addAll(listaDeLivrosFornecedor);

            int coluna = 0;
            int linha = 1;

            gpCatalogolivrosFornecedor.getChildren().clear();
            cartoesLivroCatalogoFornecedor.clear();
            controladoresCartaoLivro.clear();

            try {
                for (int i = 0; i < listaDeLivrosCatalogoFornecedor.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/br/ufrpe/readeasy/FornecedorcartaoLivro.fxml"));
                    VBox cartaoLivro = fxmlLoader.load();

                    FornecedorCartaoLivroController cardController = fxmlLoader.getController();
                    cardController.setInformacoesDoLivro(listaDeLivrosCatalogoFornecedor.get(i));

                    this.cartoesLivroCatalogoFornecedor.add(cartaoLivro);
                    this.controladoresCartaoLivro.add(cardController);

                    if (coluna == 2) {
                        coluna = 0;
                        linha++;
                    }

                    adicionarCartaoLivroNoGridPane(cartaoLivro, linha, coluna, 530);
                    coluna++;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void filtrarCartoesLivroPorTF() {
        cbGenero.getSelectionModel().clearSelection();
        String termoPesquisa = tfPesquisar.getText().toLowerCase();

        if (termoPesquisa.trim().isEmpty()) {
            int numeroDeColunas = ScreenManager.getStage().isMaximized()? 3 : 2;
            montarCatatalogo(cartoesLivroCatalogoFornecedor);
        }
        else {
            List<VBox> cartoesFiltrados = new ArrayList<>();

            for (int i = 0; i < cartoesLivroCatalogoFornecedor.size(); i++) {
                FornecedorCartaoLivroController fornecedorCartaoLivroController = controladoresCartaoLivro.get(i);
                Livro livro = fornecedorCartaoLivroController.getLivro();

                if (livro != null) {
                    boolean correspondeAoFiltro = livro.getTitulo().toLowerCase().contains(termoPesquisa) ||
                            livro.getAutor().toLowerCase().contains(termoPesquisa);

                    if (correspondeAoFiltro) {
                        cartoesFiltrados.add(cartoesLivroCatalogoFornecedor.get(i));
                    }
                }
            }
            montarCatatalogo(cartoesFiltrados);
        }
    }

    @FXML
    public void filtrarLivrosPeloGenero(){
        String generoSeleciodado = cbGenero.getValue();

        if(generoSeleciodado != null && !generoSeleciodado.isEmpty()){
            List<VBox> cartoesFiltrados = new ArrayList<>();

            for (int i = 0; i < cartoesLivroCatalogoFornecedor.size(); i++) {
                FornecedorCartaoLivroController fornecedorCartaoLivroController = controladoresCartaoLivro.get(i);
                Livro livro = fornecedorCartaoLivroController.getLivro();

                if (livro != null) {
                    List<Genero> generosDoLivro = livro.getGeneros();
                    boolean correspondeAoFiltro = false;

                    for(Genero genero : generosDoLivro){
                        if (genero.getDescricaoEnum().equals(generoSeleciodado)){
                            correspondeAoFiltro = true;
                        }
                    }

                    if (correspondeAoFiltro) {
                        cartoesFiltrados.add(cartoesLivroCatalogoFornecedor.get(i));
                    }
                }
            }
            montarCatatalogo(cartoesFiltrados);
        }
    }

    private void filtrarCartoesLivroPorTermo(String termoPesquisa) {
        cbGenero.getSelectionModel().clearSelection();

        if (termoPesquisa.trim().isEmpty()) {
            montarCatatalogo(cartoesLivroCatalogoFornecedor);
        } else {
            List<VBox> cartoesFiltrados = new ArrayList<>();

            for (int i = 0; i < cartoesLivroCatalogoFornecedor.size(); i++) {
                FornecedorCartaoLivroController fornecedorCartaoLivroController = controladoresCartaoLivro.get(i);
                Livro livro = fornecedorCartaoLivroController.getLivro();

                if (livro != null) {
                    boolean correspondeAoFiltro = livro.getTitulo().contains(termoPesquisa) ||
                            livro.getAutor().toLowerCase().contains(termoPesquisa);

                    if (correspondeAoFiltro) {
                       cartoesFiltrados.add(cartoesLivroCatalogoFornecedor.get(i));
                    }
                }
            }
            montarCatatalogo(cartoesFiltrados);
        }
    }

    private void montarCatatalogo(List<VBox> cartoesFiltrados){
        gpCatalogolivrosFornecedor.getChildren().clear();
        int coluna = 0;
        int linha = 0;
        int prefWidth = numeroDeColunas == 3? 840 : 530;


        for(int i = 0; i < cartoesFiltrados.size(); i++){
            if (coluna == numeroDeColunas) {
                coluna = 0;
                linha++;
            }

            adicionarCartaoLivroNoGridPane(cartoesFiltrados.get(i), linha, coluna, prefWidth);
            coluna++;
        }
    }

    private void adicionarCartaoLivroNoGridPane(VBox cartaoLivro, int linha, int coluna, int prefWidth){
        gpCatalogolivrosFornecedor.add(cartaoLivro, coluna, linha);

        gpCatalogolivrosFornecedor.setMinWidth(Region.USE_PREF_SIZE);
        gpCatalogolivrosFornecedor.setPrefWidth(prefWidth);
        gpCatalogolivrosFornecedor.setMaxWidth(Region.USE_PREF_SIZE);

        gpCatalogolivrosFornecedor.setMinHeight(Region.USE_COMPUTED_SIZE);
        gpCatalogolivrosFornecedor.setPrefHeight(Region.USE_COMPUTED_SIZE);
        gpCatalogolivrosFornecedor.setMaxHeight(Region.USE_PREF_SIZE);

        gpCatalogolivrosFornecedor.setVgap(10);
        gpCatalogolivrosFornecedor.setHgap(10);

        GridPane.setMargin(cartaoLivro, new Insets(10));
    }

    @FXML
    public void btnApresentarCatalogoCompleto(){
        cbGenero.getSelectionModel().clearSelection();
        tfPesquisar.clear();
        montarCatatalogo(cartoesLivroCatalogoFornecedor);
    }

    public void mostrarlegendaBtnCatalogo() {
        Tooltip tooltip = new Tooltip("Voltar ao catálogo completo");
        Tooltip.install(btnCatalogoCompleto, tooltip);
    }

    @FXML
    private void construirTabela(){
        colTitulo.setCellValueFactory(cellData -> {
            Livro livro = cellData.getValue();
            String titulo = livro.getTitulo();
            return new SimpleStringProperty(titulo);
        });

        colAutor.setCellValueFactory(cellData -> {
            Livro livro = cellData.getValue();
            String autor = livro.getAutor();
            return new SimpleStringProperty(autor);
        });

        colQuantidade.setCellValueFactory(cellData -> {
            Livro livro = cellData.getValue();
            Integer quantidade = livro.getQuantidade();
            return new SimpleIntegerProperty(quantidade).asObject();
        });
    }

    @FXML
    private void inicializarTabela(){
        tvEstoqueLivrosFornecedor.getItems().clear();

        Usuario usuario = SessaoUsuario.getUsuarioLogado();
        if(usuario instanceof Fornecedor){
            Fornecedor fornecedor = (Fornecedor) usuario ;
            Fachada fachada = Fachada.getInstance();
            List<Livro> listaDeLivrosFornecedor = fachada.listarLivrosPorFornecedor(fornecedor);

            if(listaDeLivrosFornecedor.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Atenção!");
                alert.setHeaderText("Não existe nenhum livro associado a sua conta para que você" +
                        " possa visualizar o estoque de livros.");
                alert.setContentText("Entre em contato como o gerente ou funcionário" +
                        " da livraria para que ele associe um livro solucionar esse problema.");
                alert.showAndWait();
            }
            setListaDeLivrosCatalogoFornecedor(listaDeLivrosFornecedor);
            tvEstoqueLivrosFornecedor.setItems(FXCollections.observableArrayList(listaDeLivrosFornecedor));
        }
    }

    @FXML
    public void filtrarLivroSelecionado(){
        Livro livroSelecionado = tvEstoqueLivrosFornecedor.getSelectionModel().getSelectedItem();
        String nomeLivro = livroSelecionado.getTitulo();

        filtrarCartoesLivroPorTermo(nomeLivro);
    }


    //gets and sets:
    public List<Livro> getListaDeLivrosCatalogoFornecedor() {
        return listaDeLivrosCatalogoFornecedor;
    }

    public void setListaDeLivrosCatalogoFornecedor(List<Livro> listaDeLivrosCatalogoFornecedor) {
        this.listaDeLivrosCatalogoFornecedor = listaDeLivrosCatalogoFornecedor;
    }

    public List<VBox> getCartoesLivroCatalogoFornecedor() {
        return cartoesLivroCatalogoFornecedor;
    }

    public static void setInstance(FornecedorEstoqueController instance) {
        FornecedorEstoqueController.instance = instance;
    }

    public void setIgnorarInitialize(boolean ignorarInitialize) {
        this.ignorarInitialize = ignorarInitialize;
    }

    public void setNumeroDeColunas(int numeroDeColunas) {
        this.numeroDeColunas = numeroDeColunas;
    }
}
