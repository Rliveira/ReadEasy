package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.LivroVendido;
import br.ufrpe.readeasy.beans.Venda;
import br.ufrpe.readeasy.business.ControladorVenda;
import br.ufrpe.readeasy.exceptions.HistoricoVazioException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FornecedorHistoricoController implements Initializable{

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
    private DatePicker btnpkDataFim;

    @FXML
    private TableView<LivroVendido> tableHistorico;
    @FXML
    private TableColumn<LivroVendido, LocalDateTime> clnDataVenda;
    @FXML
    private TableColumn<LivroVendido, Integer> clnQuantidade;
    @FXML
    private TableColumn<LivroVendido, String> clnTitulo;
    @FXML
    private TableColumn<LivroVendido, String> clnAutor;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.onBtnPesquisarClick();

    }

    //esse método tem exceções pq só é usado por outro método que já trata as exceções
    private ObservableList<LivroVendido> getVendas
            (Fornecedor fornecedor, LocalDateTime dataInicio, LocalDateTime dataFim) throws HistoricoVazioException {
        List<Venda> vendas = ControladorVenda.getInstance().listarVendasPorFornecedor
                (fornecedor.getNome(), dataInicio, dataFim);
        if (vendas.isEmpty()) {
            throw new HistoricoVazioException();
        } else {
            ObservableList<LivroVendido> listaVendas = FXCollections.observableArrayList();
            List<LivroVendido> livrosVendidos = new ArrayList<>();
            for (Venda venda : vendas) {
                livrosVendidos.addAll(venda.getLivrosVendidos());
            }
            listaVendas.addAll(livrosVendidos);
            return listaVendas;
        }
    }

    public void onBtnPesquisarClick() { //FIXME deve ser algo desse tipo
        try {
            Fornecedor fornecedor = ControladorFornecedor.getInstance().getFornecedorLogado();
            LocalDateTime dataInicio = dtpkDataInicio.getValue().atStartOfDay();
            LocalDateTime dataFim = btnpkDataFim.getValue().atTime(23, 59, 59);
            ObservableList<LivroVendido> listaVendas = getVendas(fornecedor, dataInicio, dataFim);
            //clnDataVenda.setCellValueFactory(cellData -> cellData.getValue().getVenda().dataEHoraProperty());
            //precisa de um método pra pegar a data da venda
            clnQuantidade.setCellValueFactory(cellData -> {
                // Assume que "quantidade" é o nome do campo ou propriedade na sua classe
                return new SimpleObjectProperty<>(cellData.getValue().getQuantidade());
            });
            clnTitulo.setCellValueFactory(cellData -> {
                // Assume que "titulo" é o nome do campo ou propriedade na sua classe
                return new SimpleObjectProperty<>(cellData.getValue().getLivro().getTitulo());
            });
            clnAutor.setCellValueFactory(cellData -> {
                // Assume que "autor" é o nome do campo ou propriedade na sua classe
                return new SimpleObjectProperty<>(cellData.getValue().getLivro().getAutor());
            });
            tableHistorico.setItems(listaVendas);
        } catch (HistoricoVazioException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Histórico de vendas");
            alert.setHeaderText(null);
            alert.setContentText("Não há vendas nesse período!");
            alert.showAndWait();
        }

    }


}