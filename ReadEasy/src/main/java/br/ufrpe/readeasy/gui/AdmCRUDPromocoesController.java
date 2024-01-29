package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Promocao;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Optional;


import java.time.LocalDate;
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
    private Button btnAdicionar;

    @FXML
    private Button btnDeletar;

    @FXML
    private Button btnEditar;

    @FXML
    private TableView<Promocao> tbvPromocoesAtivas;

    @FXML
    private TableColumn<Promocao, String> clnTitulo;

    @FXML
    private TableColumn<Promocao, Integer> clnPorcentagem;

    @FXML
    private TableColumn<Promocao, Integer> clnQtdMin;

    @FXML
    private TableColumn<Promocao, LocalDate> clnDtInicio;

    @FXML
    private TableColumn<Promocao, LocalDate> clnDtFim;

    @FXML
    void btnAdicionarPromocao(ActionEvent event) {

        String titulo = tfTitulo.getText();
        int porcentagemDeDesconto = Integer.parseInt(tfPorcentagemDeDesconto.getText());
        int qtdMinimaDeLivros = Integer.parseInt(tfQuantidadeMinimaDeLivros.getText());;
        LocalDate dataDeCriacao = dtpDataDeInicioDaPromocao.getValue();
        LocalDate dataDeExpiracao = dtpDataDeExpiracaoDaPromocao.getValue();
        boolean ativa = true;

        Promocao promocao = new Promocao(titulo, porcentagemDeDesconto, qtdMinimaDeLivros, dataDeCriacao,dataDeExpiracao, ativa);
        try {
            ServidorReadEasy.getInstance().inserirPromocao(promocao);

            List<Promocao> promocoesAtivas = ServidorReadEasy.getInstance().listarTodasPromocoesAtivas();
            promocoesAtivas.add(promocao);

            tbvPromocoesAtivas.setItems(FXCollections.observableArrayList(promocoesAtivas));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cadastro de Promocao");
            alert.setHeaderText(null);
            alert.setContentText("Promocao cadastrada com sucesso!");
            alert.showAndWait();
        } catch (PromocaoExistenteException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no preenchimento de dados");
            alert.setHeaderText(null);
            alert.setContentText("Esta promoção já existe");
            alert.showAndWait();
        } catch (PromocaoNulaException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no preenchimento de dados");
            alert.setHeaderText(null);
            alert.setContentText("Promoção nula.");
            alert.showAndWait();
        } catch (PromocaoInseridaComSucessoException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cadastro de Promocao");
            alert.setHeaderText(null);
            alert.setContentText("Promocao cadastrada com sucesso!");
            alert.showAndWait();
        }
    }

    @FXML
    void btnDeletarPromocao(ActionEvent event) throws PromocaoNulaException, PromocaoInexistenteException {

        Promocao promocaoSelecionada = tbvPromocoesAtivas.getSelectionModel().getSelectedItem();

        if (promocaoSelecionada != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText(null);
            alert.setContentText("Tem certeza que deseja excluir a promoção selecionada?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                tbvPromocoesAtivas.getItems().remove(promocaoSelecionada);

                ServidorReadEasy.getInstance().removerPromocao(promocaoSelecionada);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Nenhuma promoção selecionada para edição.");
            alert.showAndWait();
        }
    }

    @FXML
    void btnEditarPromocao(ActionEvent event) {

        Promocao promocaoSelecionada = tbvPromocoesAtivas.getSelectionModel().getSelectedItem();

        if (promocaoSelecionada != null) {

            tfTitulo.setText(promocaoSelecionada.getTitulo());
            tfPorcentagemDeDesconto.setText(String.valueOf(promocaoSelecionada.getPorcentagemDeDesconto()));
            tfQuantidadeMinimaDeLivros.setText(String.valueOf(promocaoSelecionada.getQtdMinimaDeLivros()));
            dtpDataDeInicioDaPromocao.setValue(promocaoSelecionada.getDataDeCriacao());
            dtpDataDeExpiracaoDaPromocao.setValue(promocaoSelecionada.getDataDeExpiracao());


            btnAdicionar.setDisable(true);
            btnDeletar.setDisable(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Nenhuma promoção selecionada para edição.");
            alert.showAndWait();
        }
    }
    @FXML
    public void initialize() {

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
            int qtdMin = promocao.getPorcentagemDeDesconto();
            return new SimpleIntegerProperty(qtdMin).asObject();
        });

        clnDtInicio.setCellValueFactory(cellData -> {
            Promocao promocao = cellData.getValue();
            LocalDate dtInicio = promocao.getDataDeCriacao();
            return new SimpleObjectProperty<>(dtInicio);
        });

        clnDtFim.setCellValueFactory(cellData -> {
            Promocao promocao = cellData.getValue();
            LocalDate dtFim = promocao.getDataDeCriacao();
            return new SimpleObjectProperty<>(dtFim);
        });

        List<Promocao> promocoesAtivas = ServidorReadEasy.getInstance().listarTodasPromocoesAtivas();
        tbvPromocoesAtivas.setItems(FXCollections.observableArrayList(promocoesAtivas));

        tbvPromocoesAtivas.setRowFactory(tv -> {
            TableRow<Promocao> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Promocao promocaoSelecionada = row.getItem();

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Aviso");
                    alert.setHeaderText(null);
                    alert.setContentText("Selecione se você deseja Editar ou Deletar esta Promoção.");
                    alert.showAndWait();

                    row.setOnMouseClicked(secondEvent -> {
                        if (secondEvent.getClickCount() == 1) {
                            if (secondEvent.getTarget() instanceof Button) {
                                Button clickedButton = (Button) secondEvent.getTarget();
                                if (clickedButton == btnDeletar) {
                                    try {
                                        btnDeletarPromocao(new ActionEvent(clickedButton, btnDeletar));
                                    } catch (PromocaoNulaException | PromocaoInexistenteException e) {
                                        e.printStackTrace();
                                    }
                                } else if (clickedButton == btnEditar) {
                                    btnEditarPromocao(new ActionEvent(clickedButton, btnEditar));
                                }
                            }
                        }
                    });
                }
            });
            return row;
        });
    }
}
