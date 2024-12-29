package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Promocao;
import br.ufrpe.readeasy.business.Fachada;
import br.ufrpe.readeasy.exceptions.DataInvalidaException;
import br.ufrpe.readeasy.exceptions.PromocaoExistenteException;
import br.ufrpe.readeasy.exceptions.ValorInvalidoException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdmCRUDPromocoesController {

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
    private Button btnAdicionar;

    @FXML
    private Button btnDeletar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnLimpar;

    @FXML
    private TextField tfTitulo;

    @FXML
    private TextField tfPorcentagemDeDesconto;

    @FXML
    private TextField tfQuantidadeMinimaDeLivros;

    @FXML
    private DatePicker dtpDataDeInicioDaPromocao;

    @FXML
    private DatePicker dtpDataDeExpiracaoDaPromocao;


    @FXML
    private TableView<Promocao> tbvPromocoesAtivas;

    @FXML
    private TableColumn<Promocao, String> clnTitulo;

    @FXML
    private TableColumn<Promocao, Integer> clnPorcentagem;

    @FXML
    private TableColumn<Promocao, Integer> clnQtdMin;

    @FXML
    private TableColumn<Promocao, String> clnDtInicio;

    @FXML
    private TableColumn<Promocao, String> clnDtFim;

    private static AdmCRUDPromocoesController instance;
    private boolean ignorarInitialize;

    //Construtor:
    public AdmCRUDPromocoesController() {
        if(instance == null){
            instance = this;
            ignorarInitialize = true;
        }
    }

    //métodos:
    @FXML
    public void initialize() {
        ScreenManager screenManager = ScreenManager.getInstance();

        if(screenManager.getAdmCRUDPromocoesController() == null){
            screenManager.setAdmCRUDPromocoesController(instance);
        }
        if(!ignorarInitialize){
            construirTabela();
            inicializarTbvPromocoesAtivas();
        }
    }

    @FXML
    public void construirTabela(){
        clnTitulo.setCellValueFactory(cellData -> {
            Promocao promocao = cellData.getValue();
            String titulo = promocao.getTitulo();
            return new SimpleStringProperty(titulo);
        });

        clnPorcentagem.setCellValueFactory(cellData -> {
            Promocao promocao = cellData.getValue();
            int porcentagem = promocao.getPorcentagemDeDesconto();
            return new SimpleIntegerProperty(porcentagem).asObject();
        });
        clnQtdMin.setCellValueFactory(cellData -> {
            Promocao promocao = cellData.getValue();
            int qtdMin = promocao.getQtdMinimaDeLivros();
            return new SimpleIntegerProperty(qtdMin).asObject();
        });

        clnDtInicio.setCellValueFactory(cellData -> {
            Promocao promocao = cellData.getValue();
            LocalDate dtInicio = promocao.getDataDeCriacao();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = dtInicio.format(formatter);
            return new SimpleStringProperty(dataFormatada);
        });

        clnDtFim.setCellValueFactory(cellData -> {
            Promocao promocao = cellData.getValue();
            LocalDate dtFim = promocao.getDataDeExpiracao();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = dtFim.format(formatter);
            return new SimpleStringProperty(dataFormatada);
        });
    }

    @FXML
    public void inicializarTbvPromocoesAtivas(){
        tbvPromocoesAtivas.getItems().clear();
        List<Promocao> promocoes = Fachada.getInstance().listarTodasPromocoesAtivas();
        tbvPromocoesAtivas.setItems(FXCollections.observableArrayList(promocoes));
    }

    @FXML
    void btnAdicionarPromocao() {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        String titulo = tfTitulo.getText();
        String pct = (tfPorcentagemDeDesconto.getText());
        String qntMin = tfQuantidadeMinimaDeLivros.getText();
        LocalDate dataDeCriacao = dtpDataDeInicioDaPromocao.getValue();
        LocalDate dataDeExpiracao = dtpDataDeExpiracaoDaPromocao.getValue();

        if(pct.isEmpty() || qntMin.isEmpty()){
            alert.setTitle("Erro!");
            alert.setHeaderText("Campos não preenchidos.");
            alert.setContentText("Preencha todos os campos para continuar.");
            alert.showAndWait();
        }
        else{
             boolean resultado1 = validarInputTf(pct);
             boolean resultado2 = validarInputTf(qntMin);

            if(resultado1 && resultado2){
                int porcentagemDeDesconto = Integer.parseInt(pct);
                int qtdMinimaDeLivros = Integer.parseInt(qntMin);

                Promocao promocao = new Promocao(titulo, porcentagemDeDesconto, qtdMinimaDeLivros, dataDeCriacao,dataDeExpiracao);

                try {
                    Fachada.getInstance().inserirPromocao(promocao);

                    tbvPromocoesAtivas.getItems().add(promocao);

                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cadastro de Promocao");
                    alert.setHeaderText(null);
                    alert.setContentText("Promocao cadastrada com sucesso!");
                    alert.showAndWait();
                    limparCampos();

                } catch (PromocaoExistenteException e) {
                    alert.setTitle("Erro");
                    alert.setHeaderText("A promoção que você está tentando cadastrar já existe.");
                    alert.setContentText("Cadastre uma promoção nova para continuar");
                    alert.showAndWait();
                } catch (ValorInvalidoException e) {
                    alert.setTitle("Erro");
                    alert.setHeaderText("Valor de porcentagem de desconto inválido.");
                    alert.setContentText("Certifique de preencher o campo com um valor entre 1 e 100");
                    alert.showAndWait();
                    limparCampos();
                } catch (DataInvalidaException e) {
                    alert.setTitle("Erro");
                    alert.setHeaderText("Data inválida de desconto inválido.");
                    alert.setContentText("Certifique de preencher a data de criação e expiração" +
                            " da promoção com datas válidas.");
                    alert.showAndWait();
                    limparCampos();
                }
            }
            else{
                alert.setTitle("Erro!");
                alert.setHeaderText("Campo de porcentagem ou quantidade minima" + '\n' + " preenchido incorretamente.");
                alert.setContentText("Preencha ambos os campos apenas com números para continuar.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void btnDeletarPromocao() {
        Promocao promocaoSelecionada = tbvPromocoesAtivas.getSelectionModel().getSelectedItem();

        if (promocaoSelecionada != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText(null);
            alert.setContentText("Tem certeza que deseja excluir a promoção selecionada?");

            ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
            ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(simButton, naoButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                    alert.close();
                    boolean excessaoLevantada = false;
                    Fachada.getInstance().removerPromocao(promocaoSelecionada);

                    if (!excessaoLevantada){
                        tbvPromocoesAtivas.getItems().remove(promocaoSelecionada);

                        Alert alertInformacao = new Alert(Alert.AlertType.INFORMATION);
                        alertInformacao.setAlertType(Alert.AlertType.INFORMATION);
                        alertInformacao.setTitle("Sucesso!");
                        alertInformacao.setHeaderText("Promoção removida com sucesso!");
                        alertInformacao.setContentText(null);
                        alertInformacao.showAndWait();
                        limparCampos();
                    }
                }
                else {
                    alert.close();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhuma promoção selecionada para a remoção.");
            alert.setContentText("Selecione uma promoção na tabela ao lado para continuar.");
            alert.showAndWait();
        }
    }

    @FXML
    void btnEditarPromocao() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja realmente editar a promoção?");
        alert.setContentText("Escolha uma opção.");

        ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(simButton, naoButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                alert.close();
                Alert alertErro = new Alert(Alert.AlertType.ERROR);

                Promocao promocaoSelecionada = tbvPromocoesAtivas.getSelectionModel().getSelectedItem();

                if (promocaoSelecionada != null) {
                    String novoTitulo = tfTitulo.getText();
                    String pct = (tfPorcentagemDeDesconto.getText());
                    String qntMin = tfQuantidadeMinimaDeLivros.getText();
                    LocalDate novaDataInicio = dtpDataDeInicioDaPromocao.getValue();
                    LocalDate novaDataFim = dtpDataDeExpiracaoDaPromocao.getValue();

                    if(pct.isEmpty() || qntMin.isEmpty()){
                        alertErro.setTitle("Erro!");
                        alertErro.setHeaderText("Campos não preenchidos.");
                        alertErro.setContentText("Preencha todos os campos para continuar.");
                        alertErro.showAndWait();
                    }
                    else{
                        boolean resultado1 = validarInputTf(pct);
                        boolean resultado2 = validarInputTf(qntMin);

                        if(resultado1 && resultado2){
                            int novaPorcentagem = Integer.parseInt(pct);
                            int novaQuantidade = Integer.parseInt(qntMin);

                            try {
                                Fachada.getInstance().atualizarPromocao(promocaoSelecionada, novoTitulo, novaPorcentagem,
                                        novaQuantidade, novaDataInicio, novaDataFim, true);
                                alertErro.setAlertType(Alert.AlertType.INFORMATION);
                                alertErro.setTitle("Sucesso.");
                                alertErro.setHeaderText(null);
                                alertErro.setContentText("promoção editada com sucesso!");
                                alertErro.showAndWait();

                                limparCampos();
                                inicializarTbvPromocoesAtivas();

                            }  catch (PromocaoExistenteException e) {
                                alertErro.setTitle("Erro");
                                alertErro.setHeaderText("A promoção que você está tentando cadastrar já existe.");
                                alertErro.setContentText("Cadastre uma promoção nova para continuar");
                                alertErro.showAndWait();
                            } catch (ValorInvalidoException e) {
                                alertErro.setTitle("Erro");
                                alertErro.setHeaderText("Valor de porcentagem de desconto inválido.");
                                alertErro.setContentText("Certifique de preencher o campo com um valor entre 1 e 100");
                                alertErro.showAndWait();
                                limparCampos();
                            } catch (DataInvalidaException e) {
                                alertErro.setTitle("Erro");
                                alertErro.setHeaderText("Data inválida de desconto inválido.");
                                alertErro.setContentText("Certifique de preencher a data de criação e expiração" +
                                        " da promoção com datas válidas.");
                                alertErro.showAndWait();
                                limparCampos();
                            }
                        }
                        else{
                            alertErro.setTitle("Erro!");
                            alertErro.setHeaderText("Campo de porcentagem ou quantidade minima" + '\n' + " preenchido incorretamente.");
                            alertErro.setContentText("Preencha ambos os campos números para continuar.");
                            alertErro.showAndWait();
                        }
                    }
                }
                else {
                    alertErro.setTitle("Aviso");
                    alertErro.setHeaderText("Nenhuma promoção selecionada para edição.");
                    alertErro.setContentText("Selecione uma promoção da tabela ao lado para continuar.");
                    alertErro.showAndWait();
                }
            }
            else {
                alert.close();
            }
        });
    }

    @FXML
    public void limparCampos(){
        tfTitulo.clear();
        tfPorcentagemDeDesconto.clear();
        tfQuantidadeMinimaDeLivros.clear();
        dtpDataDeExpiracaoDaPromocao.setValue(null);
        dtpDataDeInicioDaPromocao.setValue(null);
    }

    private boolean validarInputTf(String valorDigitado){
        boolean inputDigitadoCorretamente = true;

        try {
            int valor = Integer.parseInt(valorDigitado);
        } catch (NumberFormatException e) {
            inputDigitadoCorretamente = false;
        }

        return inputDigitadoCorretamente;
    }

    @FXML
    public void popularCamposDaPromocaoSelecionada(){
        Promocao promocao = tbvPromocoesAtivas.getSelectionModel().getSelectedItem();

        if(promocao != null){
            tfTitulo.setText(promocao.getTitulo());
            tfQuantidadeMinimaDeLivros.setText(String.valueOf(promocao.getQtdMinimaDeLivros()));
            tfPorcentagemDeDesconto.setText(String.valueOf(promocao.getPorcentagemDeDesconto()));
            dtpDataDeInicioDaPromocao.setValue(promocao.getDataDeCriacao());
            dtpDataDeExpiracaoDaPromocao.setValue(promocao.getDataDeExpiracao());
        }
    }

    //gets and sets:
    public static AdmCRUDPromocoesController getInstance() {
        return instance;
    }

    public void setIgnorarInitialize(boolean ignorarInitialize) {
        this.ignorarInitialize = ignorarInitialize;
    }
}
