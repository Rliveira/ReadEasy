package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.beans.LivroVendido;
import br.ufrpe.readeasy.beans.Venda;
import br.ufrpe.readeasy.business.ControladorVenda;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.DataInvalidaException;
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
    private TableColumn<LivroVendido, String> clnAutorVendas;

    @FXML
    private TableColumn<LivroVendido, String> clnClienteVendas;

    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, LocalDate> clnDataCompra;

    @FXML
    private TableColumn<LivroVendido, LocalDateTime> clnDataVenda;

    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, String> clnFornecedorCompras;

    @FXML
    private TableColumn<LivroVendido, String> clnFornecedorVendas;

    @FXML
    private TableColumn<LivroVendido, Integer> clnPrecoVendas;

    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, Integer> clnQuantidadeCompras;

    @FXML
    private TableColumn<LivroVendido, Integer> clnQuantidadeVendas;

    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, String> clnTituloCompras;

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
    private TableView<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>> tableHistoricoCompras;

    private ObservableList<LivroVendido> listaVendas;

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


    protected ObservableList<LivroVendido> ListaVendas(LocalDate dataInicio, LocalDate dataFim) {

        List<Venda> vendas = ServidorReadEasy.getInstance().HistoricoDeVendasPorPeriodo(dataInicio, dataFim);
        listaVendas = FXCollections.observableArrayList();
        List<LivroVendido> livrosVendidos = new ArrayList<>();
        for (Venda venda : vendas) {
            livrosVendidos.addAll(venda.getLivrosVendidos());
            listaVendas.addAll(livrosVendidos);
        }

        return listaVendas;
    }

    @FXML
    protected void onBtnPesquisarVendasClick(ActionEvent event) { //FIXME - provavelmente isso tá errado

        listaVendas = ListaVendas(dtpkDataInicioVendas.getValue(), dtpkDataFimVendas.getValue());
        clnTituloVendas.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLivro().getTitulo()));
        clnAutorVendas.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLivro().getAutor()));
        clnFornecedorVendas.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLivro()
                .getFornecedor().getNome()));
        clnClienteVendas.setCellValueFactory(new PropertyValueFactory<>("cliente")); //TODO: tenho que dar um jeito de pegar o cliente e a data
        clnDataVenda.setCellValueFactory(new PropertyValueFactory<>("dataEHora"));
        clnQuantidadeVendas.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getLivro().getQuantidade()).asObject());
        clnPrecoVendas.setCellValueFactory(param -> new SimpleIntegerProperty((int) param.getValue().getLivro().getPreco()).asObject());
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
    protected void onBtnPesquisarComprasClick(ActionEvent event) {

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

    }
}
