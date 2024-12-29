package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.CompraLivrariaDTO;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.business.Fachada;
import br.ufrpe.readeasy.exceptions.HistoricoVazioException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AdmRelatoriosController {

    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnLivros;
    @FXML
    private Button btnEstoque;
    @FXML
    private Button btnUsuarios;
    @FXML
    private Button btnPromocoes;
    @FXML
    private Button btnHistorico;
    @FXML
    private Button btnRelatorios;
    @FXML
    private Button btnPesquisar1;
    @FXML
    private Button btnPesquisar2;
    @FXML
    private Button btnPesquisar3;
    @FXML
    private Button btnPesquisar4;
    @FXML
    private Button btnPesquisar5;


    @FXML
    private BarChart<String, Number> bcDadosVenda;
    @FXML
    private CategoryAxis catX;
    @FXML
    private NumberAxis catY1;

    @FXML
    private AreaChart<String, Number> acDadosCompra;
    @FXML
    private CategoryAxis catX2;
    @FXML
    private NumberAxis catY2;

    @FXML
    private BarChart<String, Number> bcRankingLivros;
    @FXML
    private CategoryAxis catX3;
    @FXML
    private NumberAxis catY3;

    @FXML
    private ComboBox<String> cbCategoria1;
    @FXML
    private ComboBox<String> cbCategoria2;
    @FXML
    private ComboBox<String> cbMesOuAno1;
    @FXML
    private ComboBox<String> cbMesOuAno2;
    @FXML
    private ComboBox<String> cbMesOuAno3;
    @FXML
    private ComboBox<String> cbMesOuAno4;
    @FXML
    private ComboBox<String> cbMesOuAno5;

    @FXML
    private ComboBox<String> cbPeriodo1;
    @FXML
    private ComboBox<String> cbPeriodo2;
    @FXML
    private ComboBox<String> cbPeriodo3;
    @FXML
    private ComboBox<String> cbPeriodo4;
    @FXML
    private ComboBox<String> cbPeriodo5;

    @FXML
    private ComboBox<String> cbMes;
    @FXML
    private ComboBox<String> cbMes2;

    @FXML
    private Label lblQtdLivrosVendidosHoje;
    @FXML
    private Label lblFaturamentoDiario;
    @FXML
    private Label lblComprasDiarias;
    @FXML
    private Label lblRankingLivros;

    @FXML
    private TableView<Map.Entry<String, Integer>> tvUsuariosMaisCompras;
    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> colUsuario1;
    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> colTotal1;

    @FXML
    private TableView<Map.Entry<String, Double>> tvUsuariosMaisGasto;
    @FXML
    private TableColumn<Map.Entry<String, Double>, String> colUsuario2;
    @FXML
    private TableColumn<Map.Entry<String, Double>, String> colTotal2;

    @FXML
    private TableView<CompraLivrariaDTO> tvFornecedoresRanking;
    @FXML
    private TableColumn<CompraLivrariaDTO, String> colFornecedor;
    @FXML
    private TableColumn<CompraLivrariaDTO, String> colQtdLivros;
    @FXML
    private TableColumn<CompraLivrariaDTO, String> colMontantePago;

    @FXML
    private VBox vbMes1;
    @FXML
    private VBox vbMes2;

    private Map<LocalDate, Integer> dadosVendaPorData1;
    private Map<LocalDate, Double> dadosVendaPorData2;
    private Map<LocalDate, Integer> dadosCompraPorData1;
    private Map<LocalDate, Double> dadosCompraPorData2;
    String categoriaDePesquisaVenda;
    String categoriaDePesquisaCompra;
    String periodoDeAnalise;
    boolean precisaApresentarOAlertDados;
    private static AdmRelatoriosController instance;
    private boolean ignorarInitialize;

    public AdmRelatoriosController() {
        if(instance == null){
            instance = this;
            ignorarInitialize = true;
        }
    }

    @FXML
    public void initialize(){
        ScreenManager screenManager = ScreenManager.getInstance();

        if(screenManager.getAdmRelatoriosController() == null){
            screenManager.setAdmRelatoriosController(instance);
        }
        if(!ignorarInitialize){
            setPrecisaApresentarOAlertDados(ScreenManager.getInstance().isPrecisaApresentarOAlertDadosAdm());
            limparComboBox();
            inicializarLabels();
            inicializarCbCategoriaEPeriodo();
            inicializarBcRankingComDadosDoMesAtual();
            inicializarBcDadosVendaComDadosDoMesAtual();
            inicializarLcDadosCompraComDadosDoMesAtual();
            inicializarTvUsuariosQueMaisGastam();
            inicializarTvUsuariosQueMaisCompram();
            inicializarTvRankingFornecedor();
        }
    }

    private void inicializarLabels() {
        Fachada fachada = Fachada.getInstance();

        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioDoDia = agora.toLocalDate().atStartOfDay();
        LocalDateTime fimDoDia = LocalDateTime.of(agora.toLocalDate(), LocalTime.MAX);

        int numeroDeLivrosVendidos = fachada.calcularTotalLivrosVendidosEntreDatas(inicioDoDia, fimDoDia);
        int numeroDeVendas = fachada.calcularTotalDeVendasDiarias(inicioDoDia, fimDoDia);
        Map<LocalDate, Double> faturamento = fachada.listarLucroPorData(inicioDoDia, fimDoDia);
        Double faturamentoDiario = faturamento.get(LocalDate.now());
        String faturamentoFormatado = String.format("%.2f", faturamentoDiario);

        // Atualiza os labels
        lblRankingLivros.setText("Ranking de livros de " + LocalDate.now().getYear());
        lblQtdLivrosVendidosHoje.setText(String.valueOf(numeroDeLivrosVendidos));
        lblComprasDiarias.setText(String.valueOf(numeroDeVendas));
        lblFaturamentoDiario.setText("R$: " + faturamentoFormatado);
    }

    private void limparComboBox(){
        cbCategoria1.getItems().clear();
        cbCategoria2.getItems().clear();
        cbPeriodo1.getItems().clear();
        cbPeriodo2.getItems().clear();
        cbPeriodo3.getItems().clear();
        cbPeriodo4.getItems().clear();
        cbPeriodo5.getItems().clear();
        cbMesOuAno1.getItems().clear();
        cbMesOuAno2.getItems().clear();
        cbMesOuAno3.getItems().clear();
        cbMesOuAno4.getItems().clear();
        cbMesOuAno5.getItems().clear();
        cbMes.getItems().clear();
        cbMes2.getItems().clear();
    }

    @FXML
    private void inicializarCbCategoriaEPeriodo(){
        cbCategoria1.getItems().addAll("Quantidade de livros", "N° de vendas", "Faturamento");
        cbCategoria2.getItems().addAll("Quantidade de livros", "Despesa total com fornecedores");

        cbPeriodo1.getItems().addAll("Mensal" , "Ano atual", "Anos anteriores", "Comparar com anos anteriores");
        cbPeriodo2.getItems().addAll("Mensal" , "Ano atual");
        cbPeriodo3.getItems().addAll("Mensal" , "Ano atual", "Anos anteriores", "Comparar com anos anteriores");
        cbPeriodo4.getItems().addAll("Mensal" , "Ano atual");
        cbPeriodo5.getItems().addAll("Mensal" , "Ano atual");

        cbMesOuAno1.setDisable(true);
        cbMesOuAno2.setDisable(true);
        cbMesOuAno3.setDisable(true);
        cbMesOuAno4.setDisable(true);
        cbMesOuAno5.setDisable(true);

        vbMes1.setVisible(false);
        vbMes2.setVisible(false);
    }

    @FXML
    private void inicializarCbMesOuAno1Estendido(){
        String periodoSelecionado = cbPeriodo1.getValue();
        inicializarCbMesOuAnoEstendido(periodoSelecionado, cbMesOuAno1, vbMes1 , cbMes);
    }

    @FXML
    private void inicializarCbMesOuAno2Estendido(){
        String periodoSelecionado = cbPeriodo3.getValue();
        inicializarCbMesOuAnoEstendido(periodoSelecionado, cbMesOuAno3, vbMes2, cbMes2);
    }

    private void inicializarCbMesOuAnoEstendido(String periodoSelecionado, ComboBox<String> cbMesOuAno, VBox vbMes, ComboBox<String> cbMes){

        if(periodoSelecionado.equals("Mensal")){
            vbMes.setVisible(false);
            cbMesOuAno.setDisable(false);
            cbMesOuAno.getItems().clear();

            String[] mesesDoAno = {"Janeiro", "Fevereiro", "Março", "Abril",
                    "Maio", "Junho", "Julho", "Agosto",
                    "Setembro", "Outubro", "Novembro", "Dezembro"
            };
            int mesAtual = LocalDate.now().getMonth().getValue();
            for (int i = mesAtual; i >= 1; i--) {
                cbMesOuAno.getItems().add(mesesDoAno[i - 1]);
            }
        }

        else if (periodoSelecionado.equals("Ano atual") || periodoSelecionado.equals("Comparar com anos anteriores")) {
            cbMesOuAno.setDisable(true);
            cbMesOuAno.getItems().clear();
            vbMes.setVisible(false);
            cbMes.getItems().clear();
        }

        else if (periodoSelecionado.equals("Anos anteriores")) {
            cbMesOuAno.setDisable(false);
            cbMesOuAno.getItems().clear();

            int anoAtual = LocalDate.now().getYear();
            for (int i = anoAtual - 1; i >= 2020; i--) {
                cbMesOuAno.getItems().add(Integer.toString(i));
            }
        }
    }

    @FXML
    public void inicializarCbMesOuAnoSimplificado(ActionEvent event){
        String periodoSelecionado;
        ComboBox<String> comboBoxAcionado = (ComboBox<String>) event.getSource();
        String[] mesesDoAno = {"Janeiro", "Fevereiro", "Março", "Abril",
                "Maio", "Junho", "Julho", "Agosto",
                "Setembro", "Outubro", "Novembro", "Dezembro"
        };

        if(comboBoxAcionado.equals(cbPeriodo2)){
            periodoSelecionado = cbPeriodo2.getValue();
        }
        else if(comboBoxAcionado.equals(cbPeriodo4)){
            periodoSelecionado = cbPeriodo4.getValue();
        }
        else{
            periodoSelecionado = cbPeriodo5.getValue();
        }
        if(periodoSelecionado != null){
            if(periodoSelecionado.equals("Mensal")){
                int mesAtual = LocalDate.now().getMonth().getValue();

                if(comboBoxAcionado.equals(cbPeriodo2)){
                    cbMesOuAno2.setDisable(false);
                    cbMesOuAno2.getItems().clear();
                }
                else if(comboBoxAcionado.equals(cbPeriodo4)){
                    cbMesOuAno4.setDisable(false);
                    cbMesOuAno4.getItems().clear();
                }
                else{
                    cbMesOuAno5.setDisable(false);
                    cbMesOuAno5.getItems().clear();
                }

                for (int i = mesAtual; i >= 1; i--) {
                    if(comboBoxAcionado.equals(cbPeriodo2)){
                        cbMesOuAno2.getItems().add(mesesDoAno[i - 1]);
                    }
                    else if(comboBoxAcionado.equals(cbPeriodo4)){
                        cbMesOuAno4.getItems().add(mesesDoAno[i - 1]);
                    }
                    else{
                        cbMesOuAno5.getItems().add(mesesDoAno[i - 1]);
                    }
                }
            }
            else{
                if(comboBoxAcionado.equals(cbPeriodo2)){
                    cbMesOuAno2.setDisable(true);
                    cbMesOuAno2.getItems().clear();
                }
                else if(comboBoxAcionado.equals(cbPeriodo4)){
                    cbMesOuAno4.setDisable(true);
                    cbMesOuAno4.getItems().clear();
                }
                else{
                    cbMesOuAno5.setDisable(true);
                    cbMesOuAno5.getItems().clear();

                }
            }
        }
    }

    @FXML
    public void inicializarCbMes(ActionEvent event){
        ComboBox<String> comboBoxAcionado = (ComboBox<String>) event.getSource();
        String[] mesesDoAno = {"Janeiro", "Fevereiro", "Março", "Abril",
                "Maio", "Junho", "Julho", "Agosto",
                "Setembro", "Outubro", "Novembro", "Dezembro"
        };

        if(comboBoxAcionado.equals(cbMesOuAno1)){
            if(cbPeriodo1.getValue().equals("Anos anteriores") && cbMesOuAno1.getValue() != null){
                vbMes1.setVisible(true);
                cbMes.getItems().clear();

                for (int i = 0; i < 12; i++) {
                    cbMes.getItems().add(mesesDoAno[i]);
                }
            }
            else{
                vbMes1.setVisible(false);
            }
        }
        else{
            if(cbPeriodo3.getValue().equals("Anos anteriores") && cbMesOuAno3.getValue() != null){
                vbMes1.setVisible(true);
                cbMes2.getItems().clear();

                for (int i = 0; i < 12; i++) {
                    cbMes2.getItems().add(mesesDoAno[i]);
                }
            }
            else{
                vbMes2.setVisible(false);
            }
        }
    }

    @FXML
    private void inicializarBcRankingComDadosDoMesAtual() {
        Month mesAtual = LocalDate.now().getMonth();
        LocalDate dataInicio = LocalDate.of(LocalDate.now().getYear(), mesAtual, 1);
        LocalDate dataFim = LocalDate.now();
        LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

        Fachada fachada = Fachada.getInstance();
        Map<Livro, Integer> rankingLivros = fachada.ranquearLivrosMaisVendidosEntreDatas(dataInicio.atStartOfDay(), dataEHoraFim);

        inicializarBcRankingDeLivros(rankingLivros);
    }

    public void inicializarBcRankingDeLivros(Map<Livro, Integer> rankingLivros) {
        bcRankingLivros.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Limitar apenas aos 10 primeiros livros
        int contador = 0;
        for (Map.Entry<Livro, Integer> entry : rankingLivros.entrySet()) {
            if (contador >= 10) {
                break; // Sai do loop após adicionar os 10 primeiros livros
            }

            Livro livro = entry.getKey();
            String tituloLivro = livro.getTitulo();

            // Limitar o título do livro a 22 caracteres e adicionar reticências se for maior
            if (tituloLivro.length() > 22) {
                tituloLivro = tituloLivro.substring(0, 22) + "...";
            }

            Integer quantidadeVendas = entry.getValue();
            series.getData().add(new XYChart.Data<>(tituloLivro, quantidadeVendas));
            contador++;
        }

        bcRankingLivros.getData().add(series);
        catY1.setLabel("Quantidade de Vendas");
    }

    @FXML
    public void inicializarBcDadosVendaComDadosDoMesAtual(){
        Month mesAtual = LocalDate.now().getMonth();
        LocalDate dataInicio = LocalDate.of(LocalDate.now().getYear(), mesAtual, 1);
        LocalDate dataFim = LocalDate.now();
        LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

        setCategoriaDePesquisaVenda("Faturamento");
        setPeriodoDeAnalise("Mensal");
        pesquisarCategoria(getCategoriaDePesquisaVenda(), dataInicio.atStartOfDay(), dataEHoraFim, "Venda");
        inicializarBcDadosVenda();
    }

    @FXML
    private void inicializarBcDadosVenda() {
        bcDadosVenda.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        switch (categoriaDePesquisaVenda) {
            case "Quantidade de livros":
                catY1.setLabel("Quantidade de Livros");
                catX.setLabel("Livros vendidos");
                configurarExibicaoDosDados(series, dadosVendaPorData1);
                break;

            case "N° de vendas":
                catY1.setLabel("Quantidade de Vendas");
                catX.setLabel("Número de vendas");
                configurarExibicaoDosDados(series, dadosVendaPorData1);
                break;

            case "Faturamento":
                catY1.setLabel("Valor da receita em R$");
                catX.setLabel("Faturamento");
                configurarExibicaoDosDados(series, dadosVendaPorData2);
                break;
        }

        bcDadosVenda.getData().add(series);
    }

    @FXML
    public void inicializarLcDadosCompraComDadosDoMesAtual(){
        Month mesAtual = LocalDate.now().getMonth();
        LocalDate dataInicio = LocalDate.of(LocalDate.now().getYear(), mesAtual, 1);
        LocalDate dataFim = LocalDate.now();
        LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

        setCategoriaDePesquisaCompra("Quantidade de livros");
        setPeriodoDeAnalise("Mensal");
        pesquisarCategoria(getCategoriaDePesquisaCompra(), dataInicio.atStartOfDay(), dataEHoraFim, "Compra");
        inicializarAcDadosCompra();
    }

    @FXML
    private void inicializarAcDadosCompra() {
        acDadosCompra.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        switch (categoriaDePesquisaCompra) {
            case "Quantidade de livros":
                catY2.setLabel("Quantidade de livros");
                catX2.setLabel("Livros Comprados");
                configurarExibicaoDosDados(series, dadosCompraPorData1);
                break;

            case "Despesa total com fornecedores":
                catY2.setLabel("Valor das depesas em R$");
                catX2.setLabel("Despesas");
                configurarExibicaoDosDados(series, dadosCompraPorData2);
                break;
        }
        acDadosCompra.getData().add(series);
    }

    @FXML
    private void inicializarTvUsuariosQueMaisCompram() {
        Fachada fachada = Fachada.getInstance();
        Map<String, Integer> clientesPorCompra;

        Month mesAtual = LocalDate.now().getMonth();
        LocalDate dataInicio = LocalDate.of(LocalDate.now().getYear(), mesAtual, 1);
        LocalDate dataFim = dataInicio.plusMonths(1).minusDays(1);

        try {
            clientesPorCompra = fachada.ranquearClientesPorQuantidadeDeCompraEntreDatas(dataInicio, dataFim);
        } catch (HistoricoVazioException e) {
            throw new RuntimeException(e);
        }

        construirTvUsuariosQueMaisCompram();
        tvUsuariosMaisCompras.getItems().addAll(clientesPorCompra.entrySet());
    }

    @FXML
    private void construirTvUsuariosQueMaisCompram() {
        tvUsuariosMaisCompras.getItems().clear();

        colUsuario1.setCellValueFactory(cellData -> {
            String nome = String.valueOf(cellData.getValue().getKey());
            return new SimpleStringProperty(nome);
        });

        colTotal1.setCellValueFactory(cellData -> {
            int compras = cellData.getValue().getValue();
            return new SimpleStringProperty(String.valueOf(compras));
        });
    }

    @FXML
    private void inicializarTvUsuariosQueMaisGastam() {
        Fachada fachada = Fachada.getInstance();
        Map<String, Double> clientesPorGasto;

        Month mesAtual = LocalDate.now().getMonth();
        LocalDate dataInicio = LocalDate.of(LocalDate.now().getYear(), mesAtual, 1);
        LocalDate dataFim = dataInicio.plusMonths(1).minusDays(1);

        try {
            clientesPorGasto = fachada.raquearClientesPorGastoEntreDatas(dataInicio, dataFim);
        } catch (HistoricoVazioException e) {
            throw new RuntimeException(e);
        }

        construirTvUsuariosQueMaisGastam();
        tvUsuariosMaisGasto.getItems().addAll(clientesPorGasto.entrySet());
    }

    @FXML
    private void construirTvUsuariosQueMaisGastam() {
        tvUsuariosMaisGasto.getItems().clear();

        colUsuario2.setCellValueFactory(cellData -> {
            String nome = String.valueOf(cellData.getValue().getKey());
            return new SimpleStringProperty(nome);
        });

        colTotal2.setCellValueFactory(cellData -> {
            Double gasto = cellData.getValue().getValue();
            String gastoFormatado = String.format("%.2f", gasto);
            return new SimpleStringProperty(gastoFormatado);
        });
    }

   @FXML
    private void inicializarTvRankingFornecedor() {
        Fachada fachada = Fachada.getInstance();
        List<CompraLivrariaDTO> fornecedoresRanking;

        Month mesAtual = LocalDate.now().getMonth();
        LocalDate dataInicio = LocalDate.of(LocalDate.now().getYear(), mesAtual, 1);
        LocalDate dataFim = dataInicio.plusMonths(1).minusDays(1);
        fornecedoresRanking = fachada.ranquearFornecedoresMaisCompradosPorPeriodo(dataInicio, dataFim);

        construirTvRankingFornecedores();
        tvFornecedoresRanking.setItems(FXCollections.observableArrayList(fornecedoresRanking));
    }

    @FXML
    private void construirTvRankingFornecedores(){
        tvFornecedoresRanking.getItems().clear();

        colFornecedor.setCellValueFactory(cellData -> {
            String nome = String.valueOf(cellData.getValue().getNomeFornecedor());
            return new SimpleStringProperty(nome);
        });

        colQtdLivros.setCellValueFactory(cellData -> {
            int quantidadeLivro = cellData.getValue().getQuantidade();
            return new SimpleStringProperty(String.valueOf(quantidadeLivro));
        });

        colMontantePago.setCellValueFactory(cellData -> {
            Double gasto = cellData.getValue().getValorTotalPago();
            String gastoFormatado = String.format("R$: %.2f", gasto);
            return new SimpleStringProperty(gastoFormatado);
        });
    }

    @FXML
    public void btnPesquisarRanking(ActionEvent event){
        Button botaoAcionado = (Button) event.getSource();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String periodoSelecionado, mes;

        if(botaoAcionado.equals(btnPesquisar2)){
            periodoSelecionado = cbPeriodo2.getValue();
        }
        else if(botaoAcionado.equals(btnPesquisar4)){
            periodoSelecionado = cbPeriodo4.getValue();
        }
        else{
            periodoSelecionado = cbPeriodo5.getValue();
        }

        if (periodoSelecionado != null){
            if(periodoSelecionado.equals("Mensal")){
                if(botaoAcionado.equals(btnPesquisar2)){
                    mes = cbMesOuAno2.getValue();
                }
                else if(botaoAcionado.equals(btnPesquisar4)){
                    mes = cbMesOuAno4.getValue();
                }
                else{
                    mes = cbMesOuAno5.getValue();
                }
                if (mes != null){
                    mes = converterParaIngles(mes);
                    Month mesSelecionado = Month.valueOf(mes.toUpperCase());

                    LocalDate dataInicio = LocalDate.of(LocalDate.now().getYear(), mesSelecionado, 1);
                    LocalDate dataFim = dataInicio.plusMonths(1).minusDays(1);
                    LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

                    if(botaoAcionado.equals(btnPesquisar2)){
                        pesquisarRankingFornecedores(dataInicio, dataFim);
                    }
                    else if(botaoAcionado.equals(btnPesquisar4)){
                        pesquisarRankingLivro(dataInicio, dataEHoraFim);
                    }
                    else{
                        pesquisarRankingClientes(dataInicio, dataFim);
                    }

                    if (isPrecisaApresentarOAlert()){
                        apresentarAlertClique2x();
                    }
                }
                else{
                    alert.setTitle("Erro");
                    alert.setHeaderText("Mês não selecionado!");
                    alert.setContentText("Escolha um mês para continuar.");
                    alert.showAndWait();
                }
            }
            else{
                int anoAtual = LocalDate.now().getYear();

                LocalDate dataInicio = LocalDate.of(anoAtual, Month.JANUARY, 1);
                LocalDate dataFim = LocalDate.of(anoAtual, Month.DECEMBER, 31);
                LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

                if(botaoAcionado.equals(btnPesquisar2)){
                    pesquisarRankingFornecedores(dataInicio, dataFim);
                }
                else if(botaoAcionado.equals(btnPesquisar4)){
                    pesquisarRankingLivro(dataInicio, dataEHoraFim);
                }
                else{
                    pesquisarRankingClientes(dataInicio, dataFim);
                }
            }
        }
        else{
            alert.setTitle("Erro");
            alert.setHeaderText("Opção não selecionada.");
            alert.setContentText("Escolha uma opção para continuar.");
            alert.showAndWait();
        }
    }

    @FXML
    public void btnPesquisarDados(ActionEvent event) {
        Button botaoClicado = (Button) event.getSource();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String categoriaSelecionada;
        String periodoSelecionado;

        //Verifica qual dos botões foi clicado e inicializa as variáveis
        if(botaoClicado.equals(btnPesquisar1)){
            categoriaSelecionada = cbCategoria1.getValue();
            periodoSelecionado = cbPeriodo1.getValue();
        }
        else{
            categoriaSelecionada = cbCategoria2.getValue();
            periodoSelecionado = cbPeriodo3.getValue();
        }

        if (categoriaSelecionada != null) {
            if (periodoSelecionado != null) {
                switch (periodoSelecionado) {
                    case "Mensal":
                        if(botaoClicado.equals(btnPesquisar1)){
                            pesquisarDadosMensais(categoriaSelecionada, "Venda");
                            setCategoriaDePesquisaVenda(categoriaSelecionada);
                        }
                        else{
                            pesquisarDadosMensais(categoriaSelecionada, "Compra");
                            setCategoriaDePesquisaCompra(categoriaSelecionada);
                        }
                        setPeriodoDeAnalise("Mensal");
                        break;

                    case "Ano atual":
                        if(botaoClicado.equals(btnPesquisar1)){
                            pesquisarDadosAnoAtual(categoriaSelecionada, "Venda");
                            setCategoriaDePesquisaVenda(categoriaSelecionada);
                        }
                        else{
                            pesquisarDadosAnoAtual(categoriaSelecionada, "Compra");
                            setCategoriaDePesquisaCompra(categoriaSelecionada);
                        }
                        setPeriodoDeAnalise("Ano atual");
                        break;

                    case "Anos anteriores":
                        if(botaoClicado.equals(btnPesquisar1)){
                            pesquisarDadosAnosAnteriores(categoriaSelecionada,  "Venda");
                            setCategoriaDePesquisaVenda(categoriaSelecionada);
                        }
                        else{
                            pesquisarDadosAnosAnteriores(categoriaSelecionada,  "Compra");
                            setCategoriaDePesquisaVenda(categoriaSelecionada);
                        }
                        setPeriodoDeAnalise("Anos anteriores");
                        break;
                    case "Comparar com anos anteriores":
                        if(botaoClicado.equals(btnPesquisar1)){
                            pesquisarDadosCompararComAnosAnteriores(categoriaSelecionada,  "Venda");
                            setCategoriaDePesquisaVenda(categoriaSelecionada);
                        }
                        else{
                            pesquisarDadosCompararComAnosAnteriores(categoriaSelecionada,  "Compra");
                            setCategoriaDePesquisaVenda(categoriaSelecionada);
                        }
                        setPeriodoDeAnalise("Comparar com anos anteriores");
                }

                if(botaoClicado.equals(btnPesquisar1)){
                    inicializarBcDadosVenda();
                    //Verifica se os Values do map só são 0
                    if(categoriaSelecionada.equals("Faturamento")){
                        verificarValoresMap(getDadosVendaPorData2());
                    }
                    else{
                        verificarValoresMap(getDadosVendaPorData1());
                    }
                }
                else{
                    if(categoriaSelecionada.equals("Despesa total com fornecedores")){
                        verificarValoresMap(getDadosCompraPorData2());
                        setCategoriaDePesquisaCompra("Despesa total com fornecedores");
                    }
                    else{
                        verificarValoresMap(getDadosCompraPorData1());
                        setCategoriaDePesquisaCompra("Quantidade de livros");
                    }
                    inicializarAcDadosCompra();
                }

                if (isPrecisaApresentarOAlert()){
                    apresentarAlertClique2x();
                }
            }
            else{
                alert.setTitle("Erro");
                alert.setHeaderText("Opção não selecionada.");
                alert.setContentText("Escolha uma opção para continuar.");
                alert.showAndWait();
            }
        }
        else{
            alert.setTitle("Erro");
            alert.setHeaderText("Opção de categoria não selecionada.");
            alert.setContentText("Escolha uma opção de categoriaSelecionada para continuar.");
            alert.showAndWait();
        }
    }

    private void pesquisarDadosMensais(String categoriaSelecionada, String tipoDadoPesquisado){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String mes;

        if (tipoDadoPesquisado.equals("Venda")) {
             mes = cbMesOuAno1.getValue();
        }
        else{
            mes = cbMesOuAno3.getValue();
        }

        if (mes != null){
            mes = converterParaIngles(mes);
            Month mesSelecionado = Month.valueOf(mes.toUpperCase());

            LocalDate dataInicio = LocalDate.of(LocalDate.now().getYear(), mesSelecionado, 1);
            LocalDate dataFim = dataInicio.plusMonths(1).minusDays(1);

            if(mesSelecionado.equals(LocalDate.now().getMonth())){
                dataFim = LocalDate.now();
            }
            LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);
            pesquisarCategoria(categoriaSelecionada, dataInicio.atStartOfDay(), dataEHoraFim, tipoDadoPesquisado);
        }
        else{
            alert.setTitle("Erro");
            alert.setHeaderText("Mês não selecionado!");
            alert.setContentText("Escolha um mês para continuar.");
            alert.showAndWait();
        }
    }

    private void pesquisarDadosAnoAtual(String categoriaSelecionada, String tipoDadoPesquisado) {
        int anoAtual = LocalDate.now().getYear();

        LocalDate dataInicio = LocalDate.of(anoAtual, Month.JANUARY, 1);
        LocalDate dataFim = LocalDate.of(anoAtual, Month.DECEMBER, 31);
        LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

        pesquisarCategoria(categoriaSelecionada, dataInicio.atStartOfDay(), dataEHoraFim, tipoDadoPesquisado);
    }

    private void pesquisarDadosAnosAnteriores(String categoriaSelecionada, String tipoDadoPesquisado){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String ano;

        if(tipoDadoPesquisado.equals("Venda")) {
            ano = cbMesOuAno1.getValue();
        }
        else{
            ano = cbMesOuAno3.getValue();
        }

        if (ano != null){
            Year anoSelecionado = Year.of(Integer.parseInt(ano));
            String mes;

            if(tipoDadoPesquisado.equals("Venda")) {
                mes = cbMes.getValue();
            }
            else{
                mes = cbMes2.getValue();
            }

            if(mes != null){
                mes = converterParaIngles(mes);
                Month mesSelecionado = Month.valueOf(mes.toUpperCase());

                LocalDate dataInicio = LocalDate.of(anoSelecionado.getValue(), mesSelecionado, 1);
                LocalDate dataFim = dataInicio.plusMonths(1).minusDays(1);
                LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

                pesquisarCategoria(categoriaSelecionada, dataInicio.atStartOfDay(), dataEHoraFim, tipoDadoPesquisado);
            }
            else{
                alert.setTitle("Erro");
                alert.setHeaderText("Mês não selecionado!");
                alert.setContentText("Escolha um mês para continuar.");
                alert.showAndWait();
            }
        }
        else{
            alert.setTitle("Erro");
            alert.setHeaderText("Mês não selecionado!");
            alert.setContentText("Escolha um mês para continuar.");
            alert.showAndWait();
        }
    }

    private void pesquisarDadosCompararComAnosAnteriores(String categoriaSelecionada, String tipoDadoPesquisado){
        LocalDate dataAtual = LocalDate.now();

        LocalDate dataInicio = LocalDate.of(2020, 1, 1);
        LocalDate dataFim = LocalDate.of(dataAtual.getYear(), dataAtual.getMonth(), dataAtual.getDayOfMonth());
        LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

        pesquisarCategoria(categoriaSelecionada, dataInicio.atStartOfDay(), dataEHoraFim, tipoDadoPesquisado);
    }

    private void configurarExibicaoDosDados(XYChart.Series<String, Number> series, Map<LocalDate, ? extends Number> dados) {
        switch (getPeriodoDeAnalise()) {
            case "Mensal":
            case "Anos anteriores":
                for (Map.Entry<LocalDate, ? extends Number> entry : dados.entrySet()) {
                    String chaveFormatada = entry.getKey().format(DateTimeFormatter.ofPattern("dd/MM"));
                    Integer valor = entry.getValue().intValue(); // Converte para Integer

                    series.getData().add(new XYChart.Data<>(chaveFormatada, valor));
                }
                break;
            case "Ano atual":
                Map<Integer, Double> dadosMensais = new HashMap<>();
                int mesAtual = LocalDate.now().getMonthValue();

                for (Map.Entry<LocalDate, ? extends Number> entry : dados.entrySet()) {
                    int entryMonth = entry.getKey().getMonthValue();
                    double entryValue = entry.getValue() != null ? entry.getValue().doubleValue() : 0.0;

                    if (entryMonth <= mesAtual && entryValue != 0) {
                        dadosMensais.put(entryMonth, dadosMensais.getOrDefault(entryMonth, 0.0) + entryValue);
                    }
                }

                for (Map.Entry<Integer, Double> entry : dadosMensais.entrySet()) {
                    // Formatando o rótulo para exibir apenas o número do mês
                    String chaveFormatada = String.valueOf(entry.getKey());
                    series.getData().add(new XYChart.Data<>(chaveFormatada, entry.getValue().intValue())); // Converte para Integer
                }
                break;
            case "Comparar com anos anteriores":
                Map<Integer, Double> totalPorAno = new HashMap<>();

                for (Map.Entry<LocalDate, ? extends Number> entry : dados.entrySet()) {
                    int ano = entry.getKey().getYear();
                    double valor = entry.getValue() != null ? entry.getValue().doubleValue() : 0.0;

                    totalPorAno.put(ano, totalPorAno.getOrDefault(ano, 0.0) + valor);
                }

                for (Map.Entry<Integer, Double> entry : totalPorAno.entrySet()) {
                    series.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue().intValue()));
                }
                break;
        }
    }

    private void pesquisarRankingFornecedores(LocalDate dataInicio, LocalDate dataFim){
        List<CompraLivrariaDTO> fornecedores = Fachada.getInstance().ranquearFornecedoresMaisCompradosPorPeriodo(dataInicio, dataFim);
        tvFornecedoresRanking.setItems(FXCollections.observableArrayList(fornecedores));
        if(fornecedores.isEmpty()){
            apresentarAlertNenhumResultado();
        }
    }
    private void pesquisarRankingLivro(LocalDate dataInicio, LocalDateTime dataEHoraFim){
        Map<Livro, Integer> rankingLivros = Fachada.getInstance().ranquearLivrosMaisVendidosEntreDatas(dataInicio.atStartOfDay(),dataEHoraFim);
        inicializarBcRankingDeLivros(rankingLivros);
        if(!rankingLivros.isEmpty()){
            verificarValoresMap(rankingLivros);
        }
        else{
            apresentarAlertNenhumResultado();
        }
    }

    private void pesquisarRankingClientes(LocalDate dataInicio, LocalDate dataFim){
        Map<String, Integer> clientesQueMaisCompram = new HashMap<>();
        Map<String, Double> clientesQueMaisGastam = new HashMap<>();
        try {
            clientesQueMaisCompram = Fachada.getInstance().ranquearClientesPorQuantidadeDeCompraEntreDatas(dataInicio, dataFim);
            clientesQueMaisGastam = Fachada.getInstance().raquearClientesPorGastoEntreDatas(dataInicio, dataFim);
        } catch (HistoricoVazioException e) {
            System.out.println(e.getMessage());
        }

        //Verifica se os values estão preenchidos somente com 0 ou não tem nenhum resultado.
        verificarValoresMap(clientesQueMaisCompram);
        verificarValoresMap(clientesQueMaisCompram);

        if(clientesQueMaisCompram.isEmpty() || clientesQueMaisGastam.isEmpty()){
            apresentarAlertNenhumResultado();
            apresentarAlertNenhumResultado();
        }

        tvUsuariosMaisGasto.getItems().clear();
        tvUsuariosMaisCompras.getItems().clear();
        tvUsuariosMaisGasto.getItems().addAll(clientesQueMaisGastam.entrySet());
        tvUsuariosMaisCompras.getItems().addAll(clientesQueMaisCompram.entrySet());
    }

    private void pesquisarCategoria(String categoria, LocalDateTime dataHoraInicio, LocalDateTime dataEHoraFim, String tipoDadoPesquisado){
        Fachada fachada = Fachada.getInstance();

        switch (categoria){
            case "Quantidade de livros":
                Map<LocalDate, Integer> livrosPorData;

                if(tipoDadoPesquisado.equals("Venda")){
                    livrosPorData = fachada.listarLivrosVendidosPorData(dataHoraInicio, dataEHoraFim);
                    setDadosVendaPorData1(livrosPorData);
                }
                else{
                    LocalDate dataInicio = dataHoraInicio.toLocalDate();
                    LocalDate dataFim = dataEHoraFim.toLocalDate();
                    livrosPorData = fachada.calcularQtdDeLivrosCompradosPorPeriodo(dataInicio, dataFim);
                    setDadosCompraPorData1(livrosPorData);
                }
                break;

            case "N° de vendas":
                Map<LocalDate, Integer> vendasPorData = fachada.listarVendasPorData(dataHoraInicio, dataEHoraFim);
                vendasPorData = new TreeMap<>(vendasPorData);
                setDadosVendaPorData1(vendasPorData);
                break;

            case "Faturamento":
                Map<LocalDate, Double> lucroPorData = fachada.listarLucroPorData(dataHoraInicio, dataEHoraFim);
                lucroPorData = new TreeMap<>(lucroPorData);
                setDadosVendaPorData2(lucroPorData);
                break;

            case "Despesa total com fornecedores":
                LocalDate dataInicio = dataHoraInicio.toLocalDate();
                LocalDate dataFim = dataEHoraFim.toLocalDate();
                Map<LocalDate, Double> gastoPorData = fachada.calcularValorTotalPagoDeLivrosCompradosPorPeriodo(dataInicio, dataFim);
                setDadosCompraPorData2(gastoPorData);
        }
    }

    @FXML
    public void apresentarLegendasNoGraficoCompras(){
        apresentarLegendasNoGrafico(acDadosCompra);
    }
    @FXML
    public void apresentarLegendasNoGraficoVendas(){
        apresentarLegendasNoGrafico(bcDadosVenda);
    }
    @FXML
    public void apresentarLegendasNoGraficoRankingLivros(){
        apresentarLegendasNoGrafico(bcRankingLivros);
    }

    private void apresentarLegendasNoGrafico(XYChart<String, Number> chart) {
        for (XYChart.Series<String, Number> serie : chart.getData()) {
            for (XYChart.Data<String, Number> data : serie.getData()) {
                Tooltip tooltip = new Tooltip(data.getYValue().toString());
                Tooltip.install(data.getNode(), tooltip);

                data.getNode().setOnMouseEntered(event -> {
                    Point2D pointInScene = new Point2D(event.getSceneX(), event.getSceneY());
                    tooltip.show(data.getNode(), pointInScene.getX(), pointInScene.getY() + 10);
                });

                data.getNode().setOnMouseExited(event -> tooltip.hide());
            }
        }
    }

    private void verificarValoresMap(Map<?, ? extends Number> map) {
        List<Number> valores = new ArrayList<>();
        for (Number value : map.values()) {
            if (value.doubleValue() == 0) {
                valores.add(0);
            }
        }
        if (map.size() == valores.size()) {
            apresentarAlertValores0();
        }
    }

    private void apresentarAlertNenhumResultado(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Atenção!");
        alert.setHeaderText("Nenhum resultado do perídodo" +
                "\n selecionado foi encontrado.");
        alert.showAndWait();
    }

    private void apresentarAlertClique2x(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Atenção!");
        alert.setHeaderText("Ao mudar o periodo de análise" + '\n' +
                "clique 2 vezes no botão de pesquisar para" + '\n' +
                "que os dados sejam exibidos corretamente.");
        alert.showAndWait();
        setPrecisaApresentarOAlertDados(false);
    }

    private void apresentarAlertValores0(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Atenção!");
        alert.setHeaderText("todos os dados da sua pesquisa tem o valor 0");
        alert.showAndWait();
    }

    public String converterParaIngles(String mesEmPortugues) {
        String mes = "";

        switch (mesEmPortugues.toLowerCase()) {
            case "janeiro":
                mes = "January";
                break;
            case "fevereiro":
                mes = "February";
                break;
            case "março":
                mes = "March";
                break;
            case "abril":
                mes = "April";
                break;
            case "maio":
                mes = "May";
                break;
            case "junho":
                mes = "June";
                break;
            case "julho":
                mes = "July";
                break;
            case "agosto":
                mes = "August";
                break;
            case "setembro":
                mes = "September";
                break;
            case "outubro":
                mes = "October";
                break;
            case "novembro":
                mes = "November";
                break;
            case "dezembro":
                mes = "December";
                break;
        }
        return mes;
    }

    //gets and sets:
    public void setDadosVendaPorData1(Map<LocalDate, Integer> dadosVendaPorData1) {
        this.dadosVendaPorData1 = dadosVendaPorData1;
    }

    public void setDadosVendaPorData2(Map<LocalDate, Double> dadosVendaPorData2) {
        this.dadosVendaPorData2 = dadosVendaPorData2;
    }

    public String getCategoriaDePesquisaVenda() {
        return categoriaDePesquisaVenda;
    }

    public void setCategoriaDePesquisaVenda(String categoriaDePesquisaVenda) {
        this.categoriaDePesquisaVenda = categoriaDePesquisaVenda;
    }

    public String getPeriodoDeAnalise() {
        return periodoDeAnalise;
    }

    public void setPeriodoDeAnalise(String periodoDeAnalise) {
        this.periodoDeAnalise = periodoDeAnalise;
    }

    public boolean isPrecisaApresentarOAlert() {
        return precisaApresentarOAlertDados;
    }

    public void setPrecisaApresentarOAlertDados(boolean precisaApresentarOAlertDados) {
        this.precisaApresentarOAlertDados = precisaApresentarOAlertDados;
    }

    public void setDadosCompraPorData1(Map<LocalDate, Integer> dadosCompraPorData1) {
        this.dadosCompraPorData1 = dadosCompraPorData1;
    }

    public Map<LocalDate, Double> getDadosCompraPorData2() {
        return dadosCompraPorData2;
    }

    public void setDadosCompraPorData2(Map<LocalDate, Double> dadosCompraPorData2) {
        this.dadosCompraPorData2 = dadosCompraPorData2;
    }

    public String getCategoriaDePesquisaCompra() {
        return categoriaDePesquisaCompra;
    }

    public void setCategoriaDePesquisaCompra(String categoriaDePesquisaCompra) {
        this.categoriaDePesquisaCompra = categoriaDePesquisaCompra;
    }

    public Map<LocalDate, Integer> getDadosVendaPorData1() {
        return dadosVendaPorData1;
    }

    public Map<LocalDate, Double> getDadosVendaPorData2() {
        return dadosVendaPorData2;
    }

    public Map<LocalDate, Integer> getDadosCompraPorData1() {
        return dadosCompraPorData1;
    }

    public static AdmRelatoriosController getInstance() {
        return instance;
    }

    public void setIgnorarInitialize(boolean ignorarInitialize) {
        this.ignorarInitialize = ignorarInitialize;
    }
}