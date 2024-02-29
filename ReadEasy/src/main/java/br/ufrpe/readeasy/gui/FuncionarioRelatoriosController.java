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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
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
    private BarChart<String, Integer> bcRankingLivros;
    @FXML
    private CategoryAxis catXLivros;
    @FXML
    private NumberAxis catYNumeroDeVendas;

    @FXML
    private TableView<String> tvUsuariosMaisCompras;
    @FXML
    private TableColumn<String, String> colUsuario1;
    @FXML
    private TableColumn<String, Integer> colTotal1;

    @FXML
    private TableView<String> tvUsuariosMaisGasto;
    @FXML
    private TableColumn<String, String> colUsuario2;
    @FXML
    private TableColumn<Cliente, Double> colTotal2;

    @FXML
    private ComboBox<String> cbPeriodo;
    @FXML
    private ComboBox<String> cbMesOuAno;

    @FXML
    private Label lblRanking;

    //Métodos de troca de tela:

    @FXML
    public void trocarTelaLivroFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioCRUDLivros.fxml", "ReadEasy - Livros");
    }

    @FXML
    public void trocarTelaHistoricoFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioHistoricoComprasEVendas.fxml", "ReadEasy - Histórico");
    }

    @FXML
    public void trocarTelaEstoqueFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioEstoque.fxml", "ReadEasy - Estoque");
    }

    @FXML
    public void trocarTelaPerfilFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioPerfil.fxml", "ReadEasy - Perfil");
    }

    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //Outros métodos:

    @FXML
    public void initialize(){
        inicializarCbCategoriaEPeriodo();
        inicializarTvUsuariosQueMaisGastam();
        inicializarTvUsuariosQueMaisCompram();
        inicializarBcRankingComDadosDoMesAtual();
        lblRanking.setText("Ranking de livros de " + LocalDate.now());
    }

    @FXML
    private void inicializarCbCategoriaEPeriodo(){
        cbPeriodo.getItems().clear();
        cbPeriodo.getItems().addAll("Mensal" , "Ano atual");
        cbMesOuAno.setDisable(true);
    }
    @FXML
    private void inicializarCbPeriodo(){
        String periodoSelecionado = cbPeriodo.getValue();
        cbMesOuAno.getItems().clear();

        if(periodoSelecionado.equals("Mensal")){
            cbMesOuAno.setDisable(false);
            cbMesOuAno.getItems().clear();

            String[] mesesDoAno = {"janeiro", "fevereiro", "março", "abril",
                    "maio", "junho", "julho", "agosto",
                    "setembro", "outubro", "novembro", "dezembro"
            };

            int mesAtual = LocalDate.now().getMonth().getValue();
            for (int i = mesAtual; i >= 1; i--) {
                cbMesOuAno.getItems().add(mesesDoAno[i - 1]);
            }
        }
    }

    @FXML
    private void inicializarTvUsuariosQueMaisCompram() {
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        Map<String, Integer> clientesPorCompra = null;
        try {
            clientesPorCompra = servidorReadEasy.listarMelhoresClientesPorCompra();
        } catch (HistoricoVazioException e) {
            throw new RuntimeException(e);
        }

        ConstruirTvUsuariosQueMaisCompram(clientesPorCompra);
        tvUsuariosMaisCompras.getItems().addAll(clientesPorCompra.keySet());
    }

    @FXML
    private void ConstruirTvUsuariosQueMaisCompram(Map<String, Integer> melhoresClientesPorCompra) {
        tvUsuariosMaisCompras.getItems().clear();

        colUsuario1.setCellValueFactory(cellData -> {
            String nome = String.valueOf(cellData.getValue());
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
        Map<String, Double> clientesPorGasto = null;
        try {
            clientesPorGasto = servidorReadEasy.listarMelhoresClientesPorGasto();
        } catch (HistoricoVazioException e) {
            throw new RuntimeException(e);
        }

        ConstruirTvUsuariosQueMaisGastam(clientesPorGasto);
        tvUsuariosMaisGasto.getItems().addAll(clientesPorGasto.keySet());
    }

    @FXML
    private void ConstruirTvUsuariosQueMaisGastam(Map<String, Double> melhoresClientesPorGasto) {
        tvUsuariosMaisGasto.getItems().clear();

        colUsuario2.setCellValueFactory(cellData -> {
            String nome = String.valueOf(cellData.getValue());
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
    private void inicializarBcRankingComDadosDoMesAtual() {
        Month mesAtual = LocalDate.now().getMonth();

        LocalDate dataInicio = LocalDate.of(LocalDate.now().getYear(), mesAtual, 1);
        LocalDate dataFim = LocalDate.now();
        LocalDateTime dataEHoraFim = LocalDateTime.of(dataFim, LocalTime.MAX);

        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        Map<Livro, Integer> rankingLivros = servidorReadEasy.ranquearLivrosMaisVendidosEntreDatas(dataInicio.atStartOfDay(), dataEHoraFim);

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
    public void btnPesquisarRankingLivros(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String periodoSelecionado = cbPeriodo.getValue();

        if (periodoSelecionado != null){
            if(periodoSelecionado.equals("Mensal")){
                String mes = cbMesOuAno.getValue();

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
}