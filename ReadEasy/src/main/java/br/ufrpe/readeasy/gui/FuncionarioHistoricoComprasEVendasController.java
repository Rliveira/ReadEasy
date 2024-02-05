package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.beans.VendaDTO;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.DataInvalidaException;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.*;

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
    private DatePicker dtpDataDeInicioVendas;

    @FXML
    private DatePicker dtpDataDeFimVendas;

    @FXML
    private Button btnPesquisarVendas;

    @FXML
    private TableView<VendaDTO> tvVendas;

    @FXML
    private TableColumn<VendaDTO, String> clnTituloVendas;

    @FXML
    private TableColumn<VendaDTO, String> clnAutorVendas;

    @FXML
    private TableColumn<VendaDTO, String> clnFornecedorVendas;

    @FXML
    private TableColumn<VendaDTO, Integer> clnQuantidadeVendas;

    @FXML
    private TableColumn<VendaDTO, Double> clnPrecoVendas;

    @FXML
    private TableColumn<VendaDTO, String> clnClienteVendas;

    @FXML
    private TableColumn<VendaDTO, LocalDate> clnDataDeVendas;

    @FXML
    private DatePicker dtpDataDeInicioCompras;

    @FXML
    private DatePicker dtpDataDeFimCompras;

    @FXML
    private Button btnPesquisarCompras;

    @FXML
    private TableView<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>> tvCompras;

    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, String> clnTituloCompras;

    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, String> clnAutorCompras;

    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, String> clnFornecedorCompras;

    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, Integer> clnQuantidadeCompras;

    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, LocalDate>clnDataDeCompras;

//    @FXML
//    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, Integer> clnPrecoCompras;
//
////    @FXML
//    private TableColumn<?, ?> clnClienteCompras;

    @FXML
    public void trocarTelaEstoqueFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioEstoque.fxml", "ReadEasy - Estoque");
    }

    @FXML
    public void trocarTelaLivroFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioCRUDLivros.fxml", "ReadEasy - Livros");
    }

    @FXML
    public void trocarTelaPerfilFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioPerfil.fxml", "ReadEasy - Perfil");
    }

    @FXML
    public void trocarTelaRelatoriosFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioRelatorios.fxml", "ReadEasy - Relatorios");
    }

    @FXML
    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //Outros métodos:

    protected ObservableList<VendaDTO> ListaVendas(LocalDate dataInicio, LocalDate dataFim) {

        List<VendaDTO> vendas = ServidorReadEasy.getInstance().listarVendasLivrariaDTO(dataInicio, dataFim);
        ObservableList<VendaDTO> listaVendas = FXCollections.observableArrayList();
        listaVendas.addAll(vendas);
        return listaVendas;
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
    void btnPesquisarComprasPorData(ActionEvent event) {

        Map<Livro, List<Map.Entry<LocalDate, Integer>>> livroDataMap = ListaCompras(dtpDataDeInicioCompras.getValue(), dtpDataDeFimCompras.getValue());

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
        clnDataDeCompras.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getKey()));
        clnQuantidadeCompras.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getValue().getValue()).asObject());

        tvCompras.setItems(items);

    }

    @FXML
    void btnPesquisarVendasPorData(ActionEvent event) {

        ObservableList<VendaDTO> listaVendas = ListaVendas(dtpDataDeInicioVendas.getValue(), dtpDataDeFimVendas.getValue());
        clnTituloVendas.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLivroDTO().getTitulo()));
        clnAutorVendas.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLivroDTO().getAutor()));
        clnFornecedorVendas.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLivroDTO().getFornecedor().getNome()));
        clnClienteVendas.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getClienteDTO().getNome()));
        clnDataDeVendas.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getDataVendaDTO()));
        clnQuantidadeVendas.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getQuantidadeDTO()).asObject());
        clnPrecoVendas.setCellValueFactory(param -> new SimpleDoubleProperty((int) param.getValue().getLivroDTO().getPreco()).asObject());
        tvVendas.setItems(listaVendas);

    }
    public void initialize() {

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
