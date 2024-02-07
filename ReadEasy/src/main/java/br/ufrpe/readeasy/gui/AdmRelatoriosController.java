package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.HistoricoVazioException;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
    private BarChart<String, Number> bcDados;
    @FXML
    private CategoryAxis catX;
    @FXML
    private NumberAxis catY;

    @FXML
    private BarChart<String, Integer> bcRankingLivros;
    @FXML
    private CategoryAxis catXLivros;
    @FXML
    private NumberAxis catYNumeroDeVendas;

    @FXML
    private ComboBox<String> cbCategoria;
    @FXML
    private ComboBox<String> cbMesOuAno1;
    @FXML
    private ComboBox<String> cbMes2;
    @FXML
    private ComboBox<String> cbPeriodo1;
    @FXML
    private ComboBox<String> cbPeriodo2;
    @FXML
    private ComboBox<String> cbMes;

    @FXML
    private Label lblQtdLivrosVendidosHoje;
    @FXML
    private Label lblFaturamentoDiario;
    @FXML
    private Label lblComprasDiarias;
    @FXML
    private Label lblRankingLivros;

    @FXML
    private TableView<Cliente> tvUsuariosMaisCompras;
    @FXML
    private TableColumn<Cliente, String> colUsuario1;
    @FXML
    private TableColumn<Cliente, Integer> colTotal1;

    @FXML
    private TableView<Cliente> tvUsuariosMaisGasto;
    @FXML
    private TableColumn<Cliente, String> colUsuario2;
    @FXML
    private TableColumn<Cliente, Double> colTotal2;

    private Map<LocalDate, Integer> dadosPorData1;
    private Map<LocalDate, Double> dadosPorData2;
    String categoriaDePesquisa;
    String periodoDeAnalise;

    //Métodos de troca de tela:
    public void trocarTelaEstoqueAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admEstoque.fxml", "ReadEasy - Estoque");
    }

    public void trocarTelaLivrosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admLivros.fxml", "ReadEasy - Livros");
    }

    public void trocarTelaHistoricoAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admHistoricoComprasEVendas.fxml", "ReadEasy - Histórico");
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

    public void trocarTelaUsuariosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admCRUDUsuarios.fxml", "ReadEasy - Usuários");
    }

    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //outros métodos:
    @FXML
    public void initialize(){
        limparComboBox();
        inicializarLabels();
        inicializarCbCategoriaEPeriodo();
        inicializarBcRankingComDadosDoMesAtual();
        inicializarBcDadosComDadosDoMesAtual();
        inicializarTvUsuariosQueMaisGastam();
        inicializarTvUsuariosQueMaisCompram();
    }

    private void inicializarLabels() {
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();

        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioDoDia = agora.toLocalDate().atStartOfDay();
        LocalDateTime fimDoDia = LocalDateTime.of(agora.toLocalDate(), LocalTime.MAX);

        int numeroDeLivrosVendidos = servidorReadEasy.calcularTotalLivrosVendidosEntreDatas(inicioDoDia, fimDoDia);
        int numeroDeVendas = servidorReadEasy.calcularTotalDeVendasDiarias(inicioDoDia, fimDoDia);
        double faturamentoDiario = servidorReadEasy.calcularTotalLucroEntreDatas(inicioDoDia, fimDoDia);

        DecimalFormat df = new DecimalFormat("#.##");
        String faturamentoFormatado = df.format(faturamentoDiario);

        // Atualiza os rótulos (labels)
        lblRankingLivros.setText("Ranking de livros de " + LocalDate.now().getYear());
        lblQtdLivrosVendidosHoje.setText(String.valueOf(numeroDeLivrosVendidos));
        lblComprasDiarias.setText(String.valueOf(numeroDeVendas));
        lblFaturamentoDiario.setText(faturamentoFormatado);
    }

    private void limparComboBox(){
        cbCategoria.getItems().clear();
        cbPeriodo1.getItems().clear();
        cbPeriodo2.getItems().clear();
        cbMesOuAno1.getItems().clear();
        cbCategoria.getItems().clear();
        cbMes.getItems().clear();
        cbMes2.getItems().clear();
    }

    @FXML
    private void inicializarCbCategoriaEPeriodo(){

        cbCategoria.getItems().addAll("Quantidade de livros", "N° de vendas", "Faturamento");
        cbPeriodo1.getItems().addAll("Mensal" , "Ano atual", "Anos anteriores");
        cbPeriodo2.getItems().addAll("Mensal" , "Ano atual");
        cbMesOuAno1.setDisable(true);
        cbMes2.setDisable(true);
        cbMes.setVisible(false);
    }

    @FXML
    private void inicializarCbMesOuAno1(){

        String periodoSelecionado = cbPeriodo1.getValue();
        if(periodoSelecionado.equals("Mensal")){
            cbMes.setVisible(false);
            cbMesOuAno1.setDisable(false);
            cbMesOuAno1.getItems().clear();

            String[] mesesDoAno = {"janeiro", "fevereiro", "março", "abril",
                                     "maio", "junho", "julho", "agosto",
                                   "setembro", "outubro", "novembro", "dezembro"
            };
            int mesAtual = LocalDate.now().getMonth().getValue();
            for (int i = mesAtual; i >= 1; i--) {
                cbMesOuAno1.getItems().add(mesesDoAno[i - 1]);
            }
        }

        else if (periodoSelecionado.equals("Ano atual")) {
            cbMesOuAno1.setDisable(true);
            cbMesOuAno1.getItems().clear();
            cbMes.setVisible(false);
            cbMes.getItems().clear();
        }

        else if (periodoSelecionado.equals("Anos anteriores")) {
            cbMesOuAno1.setDisable(false);
            cbMesOuAno1.getItems().clear();

            int anoAtual = LocalDate.now().getYear();
            for (int i = anoAtual - 1; i >= 2020; i--) {
                cbMesOuAno1.getItems().add(Integer.toString(i));
            }
        }
    }

    @FXML
    private void inicializarCbMes2(){
        String periodoSelecionado = cbPeriodo2.getValue();

        if(periodoSelecionado.equals("Mensal")){
            cbMes2.setDisable(false);
            cbMes2.getItems().clear();

            String[] mesesDoAno = {"janeiro", "fevereiro", "março", "abril",
                    "maio", "junho", "julho", "agosto",
                    "setembro", "outubro", "novembro", "dezembro"
            };

            int mesAtual = LocalDate.now().getMonth().getValue();
            for (int i = mesAtual; i >= 1; i--) {
                cbMes2.getItems().add(mesesDoAno[i - 1]);
            }
        }

        else {
            cbMes.setDisable(true);
            cbMes.getItems().clear();
        }
    }

    @FXML
    public void inicializarcbMes(){
        if(cbPeriodo1.getValue().equals("Anos anteriores") && cbMesOuAno1.getValue() != null){
            cbMes.setVisible(true);
            cbMes.getItems().clear();

            if(cbPeriodo1.getValue().equals("Anos anteriores")){
                String[] mesesDoAno = {"janeiro", "fevereiro", "março", "abril",
                        "maio", "junho", "julho", "agosto",
                        "setembro", "outubro", "novembro", "dezembro"
                };

                for (int i = 0; i < 12; i++) {
                    cbMes.getItems().add(mesesDoAno[i]);
                }
            }
        }
    }

    @FXML
    private void inicializarBcRankingComDadosDoMesAtual() {
        Month mesAtual = LocalDate.now().getMonth();

        LocalDate dataInicio = LocalDate.of(LocalDate.now().getYear(), mesAtual, 1);
        LocalDate dataFim = LocalDate.now();
        LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        Map<Livro, Integer> rankingLivros = servidorReadEasy.ranquearLivrosMaisVendidosEntreDatas(dataInicio.atStartOfDay(), dataEHoraFim);

        inicializarBcRankingDeLivros(rankingLivros);
    }

    @FXML
    public void inicializarBcRankingDeLivros(Map<Livro, Integer> rankingLivros) {
        bcRankingLivros.getData().clear();

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        for (Map.Entry<Livro, Integer> entry : rankingLivros.entrySet()) {
            Livro livro = entry.getKey();
            String tituloLivro = livro.getTitulo();
            Integer quantidadeVendas = entry.getValue();

            series.getData().add(new XYChart.Data<>(tituloLivro, quantidadeVendas));
        }

        bcRankingLivros.getData().add(series);
        catXLivros.setLabel("Livros Mais vendidos do dia");
        catYNumeroDeVendas.setLabel("Quantidade de Vendas");
    }

    @FXML
    public void inicializarBcDadosComDadosDoMesAtual(){
        Month mesAtual = LocalDate.now().getMonth();

        LocalDate dataInicio = LocalDate.of(LocalDate.now().getYear(), mesAtual, 1);
        LocalDate dataFim = LocalDate.now();
        LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

        setCategoriaDePesquisa("Faturamento");
        setPeriodoDeAnalise("Mensal");
        pesquisarCategoria(getCategoriaDePesquisa(), dataInicio.atStartOfDay(), dataEHoraFim);
        inicializarBcDados();
    }

    @FXML
    private void inicializarBcDados() {
        bcDados.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        switch (categoriaDePesquisa) {
            case "Quantidade de livros":
                series.setName("Quantidade de Livros");
                configurarExibicaoDosDados(series, dadosPorData1);
                break;
            case "N° de vendas":
                series.setName("N° de Vendas");
                configurarExibicaoDosDados(series, dadosPorData1);
                break;
            case "Faturamento":
                series.setName("Faturamento");
                configurarExibicaoDosDados(series, dadosPorData2);
                break;
        }

        bcDados.getData().add(series);
    }

    @FXML
    private void inicializarTvUsuariosQueMaisCompram() {
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        Map<Cliente, Integer> clientesPorCompra = null;
        try {
            clientesPorCompra = servidorReadEasy.listarMelhoresClientesPorCompra();
        } catch (HistoricoVazioException e) {
            throw new RuntimeException(e);
        }

        ConstruirTvUsuariosQueMaisCompram(clientesPorCompra);
        tvUsuariosMaisCompras.getItems().addAll(clientesPorCompra.keySet());
    }

    @FXML
    private void ConstruirTvUsuariosQueMaisCompram(Map<Cliente, Integer> melhoresClientesPorCompra) {
        tvUsuariosMaisCompras.getItems().clear();

        colUsuario1.setCellValueFactory(cellData -> {
            Cliente cliente = cellData.getValue();
            String nome = cliente.getNome();
            return new SimpleStringProperty(nome);
        });

        colTotal1.setCellValueFactory(cellData -> {
            Integer compras = melhoresClientesPorCompra.get(cellData.getValue());
            return new SimpleIntegerProperty(compras).asObject();
        });
    }

    @FXML
    private void inicializarTvUsuariosQueMaisGastam() {
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        Map<Cliente, Double> clientesPorGasto = null;
        try {
            clientesPorGasto = servidorReadEasy.listarMelhoresClientesPorGasto();
        } catch (HistoricoVazioException e) {
            throw new RuntimeException(e);
        }

        ConstruirTvUsuariosQueMaisGastam(clientesPorGasto);
        tvUsuariosMaisGasto.getItems().addAll(clientesPorGasto.keySet());
    }

    @FXML
    private void ConstruirTvUsuariosQueMaisGastam(Map<Cliente, Double> melhoresClientesPorGasto) {
        tvUsuariosMaisGasto.getItems().clear();

        colUsuario2.setCellValueFactory(cellData -> {
            Cliente cliente = cellData.getValue();
            String nome = cliente.getNome();
            return new SimpleStringProperty(nome);
        });

        colTotal2.setCellValueFactory(cellData -> {
            Double gasto = melhoresClientesPorGasto.get(cellData.getValue());
            return new SimpleDoubleProperty(gasto).asObject();
        });

        // Formatação da célula para exibir apenas dois dígitos após a vírgula
        colTotal2.setCellFactory(column -> {
            return new TableCell<Cliente, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(String.format("%.2f", item));
                    }
                }
            };
        });
    }

    @FXML
    public void btnPesquisarRankingLivros(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String periodoSelecionado = cbPeriodo2.getValue();

        if (periodoSelecionado != null){
            if(periodoSelecionado.equals("Mensal")){
                String mes = cbMes2.getValue();

                if (mes != null){
                    mes = converterParaIngles(mes);
                    Month mesSelecionado = Month.valueOf(mes.toUpperCase());

                    LocalDate dataInicio = LocalDate.of(LocalDate.now().getYear(), mesSelecionado, 1);
                    LocalDate dataFim = dataInicio.plusMonths(1).minusDays(1);
                    LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

                    ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
                    Map<Livro, Integer> rankingLivros = servidorReadEasy.ranquearLivrosMaisVendidosEntreDatas(dataInicio.atStartOfDay(),dataEHoraFim);

                    inicializarBcRankingDeLivros(rankingLivros);
                }
                else{
                    alert.setTitle("Erro");
                    alert.setHeaderText("Mês não selecionado!");
                    alert.setContentText("Escolha um mês para continuar.");

                    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(okButton);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                            alert.close();
                        }
                    });
                }
            }
            else{
                int anoSelecionado = LocalDate.now().getYear();

                LocalDate dataInicio = LocalDate.of(anoSelecionado, Month.JANUARY, 1);
                LocalDate dataFim = LocalDate.of(anoSelecionado, Month.DECEMBER, 31);
                LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

                ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
                Map<Livro, Integer> rankingLivros = servidorReadEasy.ranquearLivrosMaisVendidosEntreDatas(dataInicio.atStartOfDay(), dataEHoraFim);

                inicializarBcRankingDeLivros(rankingLivros);
            }
        }
        else{
            alert.setTitle("Erro");
            alert.setHeaderText("Opção não selecionada.");
            alert.setContentText("Escolha uma opção para continuar.");

            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    alert.close();
                }
            });;
        }
    }

    @FXML
    public void btnPesquisarDados() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String categoriaSelecionada = cbCategoria.getValue();

        if (categoriaSelecionada != null) {
            String periodoSelecionado = cbPeriodo1.getValue();

            if (periodoSelecionado != null) {
                switch (periodoSelecionado) {
                    case "Mensal":
                        pesquisarDadosMensais(categoriaSelecionada);
                        break;

                    case "Ano atual":
                        pesquisarDadosAnoAtual(categoriaSelecionada);
                        break;

                    case "Anos anteriores":
                        pesquisarDadosAnosAnteriores(categoriaSelecionada);
                        break;
                }
            }
            else{
                alert.setTitle("Erro");
                alert.setHeaderText("Opção não selecionada.");
                alert.setContentText("Escolha uma opção para continuar.");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                        alert.close();
                    }
                });
            }
        }
        else{
            alert.setTitle("Erro");
            alert.setHeaderText("Opção de categoria não selecionada.");
            alert.setContentText("Escolha uma opção de categoriaSelecionada para continuar.");

            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    alert.close();
                }
            });
        }
    }

    private void pesquisarDadosMensais(String categoriaSelecionada){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String mes = cbMesOuAno1.getValue();

        if (mes != null){
            mes = converterParaIngles(mes);
            Month mesSelecionado = Month.valueOf(mes.toUpperCase());

            LocalDate dataInicio = LocalDate.of(LocalDate.now().getYear(), mesSelecionado, 1);
            LocalDate dataFim = dataInicio.plusMonths(1).minusDays(1);

            if(mesSelecionado.equals(LocalDate.now().getMonth())){
                dataFim = LocalDate.now();
            }
            LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

            pesquisarCategoria(categoriaSelecionada, dataInicio.atStartOfDay(), dataEHoraFim);
            setCategoriaDePesquisa(categoriaSelecionada);
            setPeriodoDeAnalise("Mensal");
            inicializarBcDados();
        }
        else{
            alert.setTitle("Erro");
            alert.setHeaderText("Mês não selecionado!");
            alert.setContentText("Escolha um mês para continuar.");

            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    alert.close();
                }
            });
        }
    }

    private void pesquisarDadosAnoAtual(String categoriaSelecionada) {
        int anoAtual = LocalDate.now().getYear();

        LocalDate dataInicio = LocalDate.of(anoAtual, Month.JANUARY, 1);
        LocalDate dataFim = LocalDate.of(anoAtual, Month.DECEMBER, 31);
        LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

        pesquisarCategoria(categoriaSelecionada, dataInicio.atStartOfDay(), dataEHoraFim);
        setCategoriaDePesquisa(categoriaSelecionada);
        setPeriodoDeAnalise("Ano atual");
        inicializarBcDados();
    }

    private void pesquisarDadosAnosAnteriores(String categoriaSelecionada){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String ano = cbMesOuAno1.getValue();

        if (ano != null){
            Year anoSelecionado = Year.of(Integer.parseInt(ano));
            String mes = cbMes.getValue();

            if(mes != null){
                mes = converterParaIngles(mes);
                Month mesSelecionado = Month.valueOf(mes.toUpperCase());

                LocalDate dataInicio = LocalDate.of(anoSelecionado.getValue(), mesSelecionado, 1);
                LocalDate dataFim = dataInicio.plusMonths(1).minusDays(1);
                LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

                pesquisarCategoria(categoriaSelecionada, dataInicio.atStartOfDay(), dataEHoraFim);
                setCategoriaDePesquisa(categoriaSelecionada);
                setPeriodoDeAnalise("Anos anteriores");
                inicializarBcDados();
            }
            else{
                alert.setTitle("Erro");
                alert.setHeaderText("Mês não selecionado!");
                alert.setContentText("Escolha um mês para continuar.");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                        alert.close();
                    }
                });
            }
        }
        else{
            alert.setTitle("Erro");
            alert.setHeaderText("Mês não selecionado!");
            alert.setContentText("Escolha um mês para continuar.");

            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    alert.close();
                }
            });
        }
    }

    private void configurarExibicaoDosDados(XYChart.Series<String, Number> series, Map<LocalDate, ? extends Number> dados) {
        switch (getPeriodoDeAnalise()) {
            case "Mensal", "Anos anteriores":
                for (Map.Entry<LocalDate, ? extends Number> entry : dados.entrySet()) {
                    series.getData().add(new XYChart.Data<>(entry.getKey().format(DateTimeFormatter.ofPattern("dd/MM")), entry.getValue()));
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
                    series.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
                }
                break;
        }
    }

    private void pesquisarCategoria(String categoria, LocalDateTime dataHoraInicio, LocalDateTime dataEHoraFim){
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();

        switch (categoria){
            case "Quantidade de livros":
                Map<LocalDate, Integer> livrosPorData = servidorReadEasy.listarLivrosVendidosPorData(dataHoraInicio, dataEHoraFim);
                livrosPorData = new TreeMap<>(livrosPorData);
                setDadosPorData1(livrosPorData);
                break;
            case "N° de vendas":
                Map<LocalDate, Integer> vendasPorData = servidorReadEasy.listarVendasPorData(dataHoraInicio, dataEHoraFim);
                vendasPorData = new TreeMap<>(vendasPorData);
                setDadosPorData1(vendasPorData);
                break;
            case "Faturamento":
                Map<LocalDate, Double> lucroPorData = servidorReadEasy.listarLucroPorData(dataHoraInicio, dataEHoraFim);
                lucroPorData = new TreeMap<>(lucroPorData);
                setDadosPorData2(lucroPorData);
                break;
        }
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

    //gets and sets:
    public void setDadosPorData1(Map<LocalDate, Integer> dadosPorData1) {
        this.dadosPorData1 = dadosPorData1;
    }

    public void setDadosPorData2(Map<LocalDate, Double> dadosPorData2) {
        this.dadosPorData2 = dadosPorData2;
    }

    public String getCategoriaDePesquisa() {
        return categoriaDePesquisa;
    }

    public void setCategoriaDePesquisa(String categoriaDePesquisa) {
        this.categoriaDePesquisa = categoriaDePesquisa;
    }

    public String getPeriodoDeAnalise() {
        return periodoDeAnalise;
    }

    public void setPeriodoDeAnalise(String periodoDeAnalise) {
        this.periodoDeAnalise = periodoDeAnalise;
    }
}
