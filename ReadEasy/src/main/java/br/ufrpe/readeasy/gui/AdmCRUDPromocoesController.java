package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Promocao;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    private boolean editMode = false;

    //Métodos de troca de tela:
    @FXML
    public void trocarTelaEstoqueAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admEstoque.fxml", "ReadEasy - Estoque");
    }

    @FXML
    public void trocarTelaUsuariosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admCRUDUsuarios.fxml", "ReadEasy - Usuários");
    }

    @FXML
    public void trocarTelaHistoricoAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admHistoricoComprasEVendas.fxml", "ReadEasy - Histórico");
    }

    @FXML
    public void trocarTelaLivrosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admLivros.fxml", "ReadEasy - Livros");
    }

    @FXML
    public void trocarTelaPerfilAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admPerfil.fxml", "ReadEasy - Perfil");
    }

    @FXML
    public void trocarTelaRelatoriosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admRelatorios.fxml", "ReadEasy - Relatorios");
    }

    @FXML
    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //Outros métodos:

    @FXML
    void btnAdicionarPromocao() {

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

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                tbvPromocoesAtivas.getItems().remove(promocaoSelecionada);

                try {
                    ServidorReadEasy.getInstance().removerPromocao(promocaoSelecionada);
                } catch (PromocaoInexistenteException | PromocaoNulaException e) {
                    throw new RuntimeException(e);
                }
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
    void btnEditarPromocao() {

        Promocao promocaoSelecionada = tbvPromocoesAtivas.getSelectionModel().getSelectedItem();

        if (promocaoSelecionada != null) {
            if (!editMode) {
                // Se não estiver no modo de edição, preencha os campos com os dados da promoção
                tfTitulo.setText(promocaoSelecionada.getTitulo());
                tfPorcentagemDeDesconto.setText(String.valueOf(promocaoSelecionada.getPorcentagemDeDesconto()));
                tfQuantidadeMinimaDeLivros.setText(String.valueOf(promocaoSelecionada.getQtdMinimaDeLivros()));
                dtpDataDeInicioDaPromocao.setValue(promocaoSelecionada.getDataDeCriacao());
                dtpDataDeExpiracaoDaPromocao.setValue(promocaoSelecionada.getDataDeExpiracao());

                btnAdicionar.setDisable(true);
                btnDeletar.setDisable(true);
                editMode = true;  // Altera para o modo de edição
            } else {
                // Se estiver no modo de edição, capture os dados alterados e atualize a promoção
                String novoTitulo = tfTitulo.getText();
                int novaPorcentagem = Integer.parseInt(tfPorcentagemDeDesconto.getText());
                int novaQuantidade = Integer.parseInt(tfQuantidadeMinimaDeLivros.getText());
                LocalDate novaDataInicio = dtpDataDeInicioDaPromocao.getValue();
                LocalDate novaDataFim = dtpDataDeExpiracaoDaPromocao.getValue();

                try {
                    ServidorReadEasy.getInstance().atualizarPromocao(promocaoSelecionada, novoTitulo, novaPorcentagem,
                            novaQuantidade, novaDataInicio, novaDataFim, true);
                } catch (PromocaoNulaException e) {
                    System.out.println("Promoção nula");
                } catch (PromocaoInexistenteException e) {
                    System.out.println("Promoção inexistente");
                }

                // Volte ao estado inicial
                btnAdicionar.setDisable(false);
                btnDeletar.setDisable(false);
                editMode = false;  // Sai do modo de edição

                this.initialize();
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
    public void initialize() {

        List<Promocao> promocoesAtivas = ServidorReadEasy.getInstance().listarTodasPromocoesAtivas();

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
            return new SimpleObjectProperty<>(dtInicio);
        });

        clnDtFim.setCellValueFactory(cellData -> {
            Promocao promocao = cellData.getValue();
            LocalDate dtFim = promocao.getDataDeExpiracao();
            return new SimpleObjectProperty<>(dtFim);
        });

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
                                    btnDeletarPromocao();
                                } else if (clickedButton == btnEditar) {
                                    btnEditarPromocao();
                                }
                            }
                        }
                    });
                }
            });
            return row;
        });
    }

    @FXML
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
