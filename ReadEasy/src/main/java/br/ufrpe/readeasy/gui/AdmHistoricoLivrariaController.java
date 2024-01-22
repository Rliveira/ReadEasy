package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Venda;
import br.ufrpe.readeasy.business.ControladorVenda;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AdmHistoricoLivrariaController {

    @FXML
    private Button btnEstoque;

    @FXML
    private Button btnHistorico;

    @FXML
    private Button btnLivros;

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnPesquisar;

    @FXML
    private Button btnPromoções;

    @FXML
    private Button btnRelatorios;

    @FXML
    private Button btnSair;

    @FXML
    private Button btnUsuarios;

    @FXML
    private TableColumn<Venda, String> clnAutor;

    @FXML
    private TableColumn<Venda, Cliente> clnCliente;

    @FXML
    private TableColumn<Venda, LocalDateTime> clnDataVenda;

    @FXML
    private TableColumn<Venda, Integer> clnPreco;

    @FXML
    private TableColumn<Venda, Integer> clnQuantidade;

    @FXML
    private TableColumn<Venda, String> clnTitulo;

    @FXML
    private DatePicker dtpkDataFim;

    @FXML
    private DatePicker dtpkDataInicio;

    @FXML
    private TableView<Venda> tableHistoricoVendas;

    @FXML
    private void initialize() { //FIXME: Isso foi coisa do ChatGPT, então não sei se está certo
        // Configurar as colunas da tabela
        clnTitulo.setCellValueFactory(cellData -> cellData.getValue().getTituloProperty());
        //clnAutor.setCellValueFactory(cellData -> cellData.getValue().getAutorProperty());
        //clnCliente.setCellValueFactory(cellData -> cellData.getValue().getClienteProperty());  // Modificação aqui
        //clnDataVenda.setCellValueFactory(cellData -> cellData.getValue().getDataHoraVendaProperty());
        //clnQuantidade.setCellValueFactory(cellData -> cellData.getValue().getQuantidadeProperty().asObject());
        //clnPreco.setCellValueFactory(cellData -> cellData.getValue().getPrecoProperty().asObject());

        // Configurar os DatePickers para limitar o intervalo de datas
        ///dtpkDataInicio.setValue(LocalDate.now().minusMonths(1));
        //dtpkDataFim.setValue(LocalDate.now());

        // Carregar o histórico de vendas
        carregarHistoricoVendas();
    }

    // Método para carregar o histórico de vendas com base nas datas selecionadas
    @FXML
    private void carregarHistoricoVendas() {
        ControladorVenda controladorVenda = ControladorVenda.getInstance();
        LocalDate dataInicio = dtpkDataInicio.getValue();
        LocalDate dataFim = dtpkDataFim.getValue();

        // Chame o método do seu sistema para obter a lista de vendas no intervalo de datas
        List<Venda> historicoVendas = controladorVenda.HistoricoDeVendasPorPeriodo(dataInicio.atStartOfDay(), dataFim.atStartOfDay());

        // Atualizar a tabela com os dados do histórico de vendas
        tableHistoricoVendas.getItems().setAll(historicoVendas);
    }

}
