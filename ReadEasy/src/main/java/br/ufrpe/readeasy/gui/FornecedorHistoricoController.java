package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.CompraLivrariaDTO;
import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Usuario;
import br.ufrpe.readeasy.business.Fachada;
import br.ufrpe.readeasy.exceptions.DataInvalidaException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    private TableView<CompraLivrariaDTO> tableHistorico;
    @FXML
    private TableColumn<CompraLivrariaDTO, String> clnDataCompra;
    @FXML
    private TableColumn<CompraLivrariaDTO, Integer> clnQuantidade;
    @FXML
    private TableColumn<CompraLivrariaDTO, String> clnTitulo;
    @FXML
    private TableColumn<CompraLivrariaDTO, String> clnAutor;
    @FXML
    private TableColumn<CompraLivrariaDTO, String> clnValorTotal;
    @FXML
    private TableColumn<CompraLivrariaDTO, String> clnValorPorLivro;

    private Usuario usuarioLogado;
    private Fornecedor fornecedor;
    private boolean ignorarInitialize;
    private static FornecedorHistoricoController instance;

    //construtor:
    public FornecedorHistoricoController() {
        if(instance == null){
            instance = this;
            ignorarInitialize = true;
        }
    }

    //métodos:
    public void initialize(){
        ScreenManager screenManager = ScreenManager.getInstance();

        if(screenManager.getFornecedorHistoricoController() == null){
            screenManager.setFornecedorHistoricoController(instance);
        }
        if(!ignorarInitialize){
            construirTableHistoricoCompras();
            inicializarTableHistoricoCompras();
            dtpkDataFim.setValue(LocalDate.now());
        }
    }

    private void construirTableHistoricoCompras(){
        clnTitulo.setCellValueFactory(cellData -> {
            String titulo = cellData.getValue().getTituloLivro();
            return new SimpleStringProperty(titulo);
        });

        clnAutor.setCellValueFactory(cellData -> {
            String autor = cellData.getValue().getAutor();
            return new SimpleStringProperty(autor);
        });

        clnDataCompra.setCellValueFactory(cellData -> {
            LocalDate dataDaCompra = cellData.getValue().getDataDaCompra();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = dataDaCompra.format(formatter);
            return new SimpleStringProperty(dataFormatada);
        });

        clnQuantidade.setCellValueFactory(cellData -> {
            int quantidade = cellData.getValue().getQuantidade();
            return new SimpleIntegerProperty(quantidade).asObject();
        });

        clnValorPorLivro.setCellValueFactory(cellData -> {
            double preco = cellData.getValue().getValorTotalPago();
            int quantidade = cellData.getValue().getQuantidade();
            double valor = preco / quantidade;
            String valorPorLivro = String.format("%.2f", valor);
            return new SimpleStringProperty(valorPorLivro);
        });

        clnValorTotal.setCellValueFactory(cellData -> {
            double valor = cellData.getValue().getValorTotalPago();
            String valorTotalPago = String.format("%.2f", valor);
            return new SimpleStringProperty(valorTotalPago);
        });
    }

    public void inicializarTableHistoricoCompras(){
        List<CompraLivrariaDTO> historicoCompras = new ArrayList<>();
        try {
            usuarioLogado = SessaoUsuario.getUsuarioLogado();
            if(usuarioLogado instanceof Fornecedor){
                fornecedor = (Fornecedor) usuarioLogado;
            }

            historicoCompras = Fachada.getInstance().ListarHistoricoDeVendasFornecedor(fornecedor, null, null);

            // Ordena as vendas por data (data atual pra data mais antiga)
            Collections.sort(historicoCompras, Comparator.comparing(CompraLivrariaDTO::getDataDaCompra).reversed());
        } catch (DataInvalidaException e) {
            System.out.println(e.getMessage()); //Essa exceção não irá ocorrer aqui.
        }

        tableHistorico.setItems(FXCollections.observableArrayList(historicoCompras));
    }

    @FXML
    protected void onBtnPesquisarClick() {
        LocalDate dataInicio = dtpkDataInicio.getValue();
        LocalDate dataFim = dtpkDataFim.getValue();

        List<CompraLivrariaDTO> comprasLivraria = null;
        try {
            comprasLivraria = Fachada.getInstance().ListarHistoricoDeVendasFornecedor((Fornecedor) getUsuarioLogado(), dataInicio, dataFim);
        } catch (DataInvalidaException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Data inválida");
            alert.setContentText("Confira se a data de início não é posterior à data de fim e que ambas não são posteriores à data atual.");
            alert.showAndWait();
        }
        tableHistorico.setItems(FXCollections.observableArrayList(comprasLivraria));
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public void setIgnorarInitialize(boolean ignorarInitialize) {
        this.ignorarInitialize = ignorarInitialize;
    }

    public void setBtnEstoque(Button btnEstoque) {
        this.btnEstoque = btnEstoque;
    }

    public static void setInstance(FornecedorHistoricoController instance) {
        FornecedorHistoricoController.instance = instance;
    }
}