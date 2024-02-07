package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.business.ControladorVenda;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class FornecedorHistoricoController {

    @FXML
    private Button btnEstoque;
    @FXML
    private Button btnHistorico;
    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnPesquisar;
    @FXML
    private Button btnSair;

    @FXML
    private DatePicker dtpkDataInicio;
    @FXML
    private DatePicker dtpkDataFim;

    @FXML
    private TableView<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>> tableHistorico;
    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, LocalDate> clnDataVenda;
    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, Integer> clnQuantidade;
    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, String> clnTitulo;
    @FXML
    private TableColumn<Map.Entry<Livro, Map.Entry<LocalDate, Integer>>, String> clnAutor;

    private Usuario usuarioLogado;

    //Métodos de troca de tela:
    @FXML
    public void trocarTelaPerfilFornecedor(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("fornecedorPerfil.fxml", "ReadEasy - Perfil");
    }

    @FXML
    public void trocarTelaEstoqueFornecedor(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("fornecedorEstoque.fxml", "ReadEasy - Estoque");
    }

    @FXML
    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //Outros métodos:

    public void initialize() {
        this.setUsuarioLogado(SessaoUsuario.getUsuarioLogado());
        this.onBtnPesquisarClick();
    }

    private Map<Livro, List<Map.Entry<LocalDate, Integer>>> getVendas
            (Fornecedor fornecedor, LocalDate dataInicio, LocalDate dataFim) throws DataInvalidaException {
        Map<Livro, List<Map.Entry<LocalDate, Integer>>> listaCompras = new HashMap<>();
        for (Livro livro : ServidorReadEasy.getInstance()
                .ListarHistoricoDeVendasFornecedor(fornecedor, dataInicio, dataFim).keySet()) {
            listaCompras.put(livro, new ArrayList<>(livro.getRegistroAtualizacaoEstoque().entrySet()));
        }
        return listaCompras;
    }

    public void onBtnPesquisarClick() {
        if (SessaoUsuario.getUsuarioLogado() instanceof Fornecedor){
            try {
                Map<Livro, List<Map.Entry<LocalDate, Integer>>> livroDataMap = getVendas((Fornecedor) SessaoUsuario.getUsuarioLogado(), dtpkDataInicio.getValue(), dtpkDataFim.getValue());

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

                clnTitulo.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().getTitulo()));
                clnAutor.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().getAutor()));
                clnDataVenda.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getValue().getKey()));
                clnQuantidade.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getValue().getValue()).asObject());

                tableHistorico.setItems(items);

            } catch (DataInvalidaException e) {
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Data inválida");
                alert.setContentText("Confira se a data de início não é posterior à data de fim e que ambas não são posteriores à data atual.");
                alert.showAndWait();
            } catch (FornecedorNaoEncontradoException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Fornecedor não encontrado");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

        @FXML
    public void SairDaConta(){
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

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}