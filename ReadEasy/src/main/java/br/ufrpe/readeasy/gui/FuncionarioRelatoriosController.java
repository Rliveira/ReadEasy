package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.business.Fachada;
import br.ufrpe.readeasy.exceptions.HistoricoVazioException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuncionarioRelatoriosController {

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
    private Button btnPesquisar1;
    @FXML
    private Button btnPesquisar2;

    @FXML
    private BarChart<String, Integer> bcRankingLivros;
    @FXML
    private CategoryAxis catXLivros;
    @FXML
    private NumberAxis catYNumeroDeVendas;

    @FXML
    private TableView<Map.Entry<String, Integer>> tvUsuariosMaisCompras;
    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> colUsuario1;
    @FXML
    private TableColumn<Map.Entry<String, Integer>, Integer> colTotal1;

    @FXML
    private TableView<Map.Entry<String, Double>> tvUsuariosMaisGasto;
    @FXML
    private TableColumn<Map.Entry<String, Double>, String> colUsuario2;
    @FXML
    private TableColumn<Map.Entry<String, Double>, String> colTotal2;

    @FXML
    private ComboBox<String> cbPeriodo1;
    @FXML
    private ComboBox<String> cbMesOuAno1;
    @FXML
    private ComboBox<String> cbPeriodo2;
    @FXML
    private ComboBox<String> cbMesOuAno2;

    @FXML
    private Label lblRanking;
    private Map<Livro, Integer> rankingLivros;
    boolean precisaApresentarOAlertDados;
    private static FuncionarioRelatoriosController instance;
    private boolean ignorarInitialize;

    //construtor:
    public FuncionarioRelatoriosController() {
        if(instance == null){
            instance = this;
            ignorarInitialize = true;
        }
    }

    //métodos:
    @FXML
    public void initialize(){
        ScreenManager screenManager =  ScreenManager.getInstance();

        if(screenManager.getFuncionariosRelatoriosController() == null){
            screenManager.setFuncionariosRelatoriosController(instance);
        }

        if(!ignorarInitialize){
            setPrecisaApresentarOAlertDados(ScreenManager.getInstance().isPrecisaApresentarOAlertDadosFuncionario());
            limparComboBox();
            inicializarCbCategoriaEPeriodo();
            inicializarTvUsuariosQueMaisGastam();
            inicializarTvUsuariosQueMaisCompram();
            inicializarBcRankingComDadosDoMesAtual();
            lblRanking.setText("Ranking de livros de " + LocalDate.now());
        }
    }

    @FXML
    private void inicializarCbCategoriaEPeriodo(){
        cbPeriodo1.getItems().addAll("Mensal" , "Ano atual");
        cbPeriodo2.getItems().addAll("Mensal" , "Ano atual");
        cbMesOuAno1.setDisable(true);
        cbMesOuAno2.setDisable(true);
    }

    @FXML
    private void limparComboBox(){
        cbPeriodo1.getItems().clear();
        cbPeriodo2.getItems().clear();
        cbMesOuAno1.getItems().clear();
        cbMesOuAno2.getItems().clear();
    }

    @FXML
    private void inicializarCbMesOuAno(ActionEvent event){
        ComboBox<String> comboBoxSelecionado = (ComboBox<String>) event.getSource();
        String periodoSelecionado;

        if(comboBoxSelecionado.equals(cbPeriodo1)){
            periodoSelecionado = cbPeriodo1.getValue();
        }
        else {
            periodoSelecionado = cbPeriodo2.getValue();
        }

        if(periodoSelecionado.equals("Mensal")){

            if(comboBoxSelecionado.equals(cbPeriodo1)){
                cbMesOuAno1.setDisable(false);
            }
            else {
                cbMesOuAno2.setDisable(false);
            }

            String[] mesesDoAno = {"Janeiro", "Fevereiro", "Março", "Abril",
                    "Maio", "Junho", "Julho", "Agosto",
                    "Setembro", "Outubro", "Novembro", "Dezembro"
            };

            int mesAtual = LocalDate.now().getMonth().getValue();
            for (int i = mesAtual; i >= 1; i--) {
                if(comboBoxSelecionado.equals(cbPeriodo1)){
                    cbMesOuAno1.getItems().add(mesesDoAno[i - 1]);
                }
                else {
                    cbMesOuAno2.getItems().add(mesesDoAno[i - 1]);
                }
            }
        }
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
            return new SimpleIntegerProperty(compras).asObject();
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
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

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
        catYNumeroDeVendas.setLabel("Quantidade de Vendas");
    }

    @FXML
    public void btnPesquisarRanking(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Button botaoAcionado = (Button) event.getSource();
        String periodoSelecionado, mes;

        if(botaoAcionado.equals(btnPesquisar1)){
            periodoSelecionado = cbPeriodo1.getValue();
        }
        else{
            periodoSelecionado = cbPeriodo2.getValue();
        }

        if (periodoSelecionado != null){
            if(periodoSelecionado.equals("Mensal")){
                if(botaoAcionado.equals(btnPesquisar1)){
                    mes = cbMesOuAno1.getValue();
                }
                else{
                    mes = cbMesOuAno2.getValue();
                }

                if (mes != null){
                    mes = converterParaIngles(mes);
                    Month mesSelecionado = Month.valueOf(mes.toUpperCase());

                    LocalDate dataInicio = LocalDate.of(LocalDate.now().getYear(), mesSelecionado, 1);
                    LocalDate dataFim = dataInicio.plusMonths(1).minusDays(1);
                    LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

                    if(botaoAcionado.equals(btnPesquisar1)){
                        pesquisarRankingLivro(dataInicio, dataEHoraFim);
                    }
                    else{
                       pesquisarRankingClientes(dataInicio, dataFim);
                    }

                    if (isPrecisaApresentarOAlertDados()){
                        apresentarAlert();
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
                int anoSelecionado = LocalDate.now().getYear();

                LocalDate dataInicio = LocalDate.of(anoSelecionado, Month.JANUARY, 1);
                LocalDate dataFim = LocalDate.of(anoSelecionado, Month.DECEMBER, 31);
                LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

                if(botaoAcionado.equals(btnPesquisar1)){
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

    private void pesquisarRankingLivro(LocalDate dataInicio, LocalDateTime dataEHoraFim){
        Map<Livro, Integer> rankingLivros = Fachada.getInstance().ranquearLivrosMaisVendidosEntreDatas(dataInicio.atStartOfDay(),dataEHoraFim);
        setRankingLivros(rankingLivros);
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

    public void apresentarLegendasNoGrafico() {
        for (XYChart.Series<String, Integer> serie : bcRankingLivros.getData()) {
            for (XYChart.Data<String, Integer> data : serie.getData()) {
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

    private void apresentarAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Atenção!");
        alert.setHeaderText("Ao mudar o periodo de análise" + '\n' +
                "clique 2 vezes no botão de pesquisar para" + '\n' +
                "que os dados sejam exibidos corretamente.");
        alert.showAndWait();
        setPrecisaApresentarOAlertDados(false);
    }

    //GETs and SETs:
    public boolean isPrecisaApresentarOAlertDados() {
        return precisaApresentarOAlertDados;
    }

    public void setPrecisaApresentarOAlertDados(boolean precisaApresentarOAlertDados) {
        this.precisaApresentarOAlertDados = precisaApresentarOAlertDados;
    }

    public Map<Livro, Integer> getRankingLivros() {
        return rankingLivros;
    }

    public void setRankingLivros(Map<Livro, Integer> rankingLivros) {
        this.rankingLivros = rankingLivros;
    }

    public void setBtnPerfil(Button btnPerfil) {
        this.btnPerfil = btnPerfil;
    }

    public static void setInstance(FuncionarioRelatoriosController instance) {
        FuncionarioRelatoriosController.instance = instance;
    }

    public void setIgnorarInitialize(boolean ignorarInitialize) {
        this.ignorarInitialize = ignorarInitialize;
    }
}