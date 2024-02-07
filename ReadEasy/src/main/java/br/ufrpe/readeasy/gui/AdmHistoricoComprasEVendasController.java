package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.business.ControladorVenda;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.DataInvalidaException;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
    private Button btnPromocoees;

    @FXML
    private Button btnRelatorios;

    @FXML
    private Button btnSair;

    @FXML
    private Button btnUsuarios;

    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, String> clnAutorCompras;

    @FXML
    private TableColumn<VendaDTO, String> clnAutorVendas;

    @FXML
    private TableColumn<VendaDTO, String> clnClienteVendas;

    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, LocalDate> clnDataCompra;

    @FXML
    private TableColumn<VendaDTO, LocalDate> clnDataVenda;

    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, String> clnFornecedorCompras;

    @FXML
    private TableColumn<VendaDTO, String> clnFornecedorVendas;

    @FXML
    private TableColumn<VendaDTO, Double> clnPrecoVendas;

    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, Integer> clnQuantidadeCompras;

    @FXML
    private TableColumn<VendaDTO, Integer> clnQuantidadeVendas;

    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, String> clnTituloCompras;

    @FXML
    private TableColumn<VendaDTO, String> clnTituloVendas;

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
    private TableView<VendaDTO> tableHistoricoVendas;

    @FXML
    private TableView<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>> tableHistoricoCompras;

    //Métodos de troca de tela:
    @FXML
    public void trocarTelaEstoqueAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admEstoque.fxml", "ReadEasy - Estoque");
    }

    @FXML
    public void trocarTelaUsuariosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admCRUDUsuarios.fxml", "ReadEasy - Usuários");
    }

    @FXML
    public void trocarTelaLivrosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admLivros.fxml", "ReadEasy - Livros");
    }

    @FXML
    public void trocarTelaPerfilAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admPerfil.fxml", "ReadEasy - Perfil");
    }

    @FXML
    public void trocarTelaPromocoesAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admCRUDPromocoes.fxml", "ReadEasy - Promoções");
    }

    @FXML
    public void trocarTelaRelatoriosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admRelatorios.fxml", "ReadEasy - Relatorios");
    }

    @FXML
    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }


    protected ObservableList<VendaDTO> ListaVendas(LocalDate dataInicio, LocalDate dataFim) {

        List<VendaDTO> vendas = ServidorReadEasy.getInstance().listarVendasLivrariaDTO(dataInicio, dataFim);
        ObservableList<VendaDTO> listaVendas = FXCollections.observableArrayList();
        listaVendas.addAll(vendas);
        return listaVendas;
    }

    @FXML
    protected void onBtnPesquisarVendasClick() {

        ObservableList<VendaDTO> listaVendas = ListaVendas(dtpkDataInicioVendas.getValue(), dtpkDataFimVendas.getValue());
        clnTituloVendas.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLivroDTO().getTitulo()));
        clnAutorVendas.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLivroDTO().getAutor()));
        clnFornecedorVendas.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLivroDTO().getFornecedor().getNome()));
        clnClienteVendas.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getClienteDTO().getNome()));
        clnDataVenda.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getDataVendaDTO()));
        clnQuantidadeVendas.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getQuantidadeDTO()).asObject());
        clnPrecoVendas.setCellValueFactory(param -> new SimpleDoubleProperty((int) param.getValue().getLivroDTO().getPreco()).asObject());
        tableHistoricoVendas.setItems(listaVendas);

    }

    protected Map<Livro, List<Map.Entry<LocalDate, Integer>>> ListaCompras(LocalDate dataInicio, LocalDate dataFim) {



        Map<Livro, List<Map.Entry<LocalDate, Integer>>> listaCompras = new HashMap<>();
        try {
            for (Livro livro : ServidorReadEasy.getInstance().historicoLivrosCompradosLivraria(dataInicio, dataFim)) {
                listaCompras.put(livro, new ArrayList<>(livro.getRegistroAtualizacaoEstoque().entrySet()));
            }
        } catch (DataInvalidaException e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Data inválida");
            alert.setContentText("Confira se a data de início não é posterior à data de fim e que ambas não são posteriores à data atual.");
            alert.showAndWait();
        }
        return listaCompras;
    }

    @FXML
    protected void onBtnPesquisarComprasClick() {

        Map<Livro, List<Map.Entry<LocalDate, Integer>>> livroDataMap = ListaCompras(dtpkDataInicioCompras.getValue(), dtpkDataFimCompras.getValue());

        List<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>> todasAsEntradas = new ArrayList<>();
        for (Map.Entry<Livro, List<Map.Entry<LocalDate, Integer>>> livroListEntry : livroDataMap.entrySet()) {
            for (Map.Entry<LocalDate, Integer> dataEntry : livroListEntry.getValue()) {
                todasAsEntradas.add(Map.entry(livroListEntry.getKey(), dataEntry));
            }
        }

        Comparator<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>> comparadorPorData = Comparator
                .comparing((Map.Entry<Livro, Map.Entry<LocalDate, Integer>> entry) -> entry.getValue().getKey())
                .reversed();

        todasAsEntradas.sort(comparadorPorData);
        ObservableList<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>> items = FXCollections.observableArrayList(todasAsEntradas);

        clnTituloCompras.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().getTitulo()));
        clnAutorCompras.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().getAutor()));
        clnFornecedorCompras.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().getFornecedor().getNome()));
        clnDataCompra.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getKey()));
        clnQuantidadeCompras.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getValue().getValue()).asObject());

        tableHistoricoCompras.setItems(items);

    }

    @FXML
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

    public void initialize() {
        this.onBtnPesquisarComprasClick();
        this.onBtnPesquisarVendasClick();
    }
}
