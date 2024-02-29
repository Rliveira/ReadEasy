package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Promocao;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
    private TableColumn<Promocao, LocalDate> clnDtInicio;

    @FXML
    private TableColumn<Promocao, LocalDate> clnDtFim;

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
    public void initialize() {
        construirTabela();
        inicializarTbvPromocoesAtivas();
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
            return new SimpleObjectProperty<>(dtInicio);
        });

        clnDtFim.setCellValueFactory(cellData -> {
            Promocao promocao = cellData.getValue();
            LocalDate dtFim = promocao.getDataDeExpiracao();
            return new SimpleObjectProperty<>(dtFim);
        });
    }

    @FXML
    public void inicializarTbvPromocoesAtivas(){
        List<Promocao> promocoes = ServidorReadEasy.getInstance().listarTodasPromocoesAtivas();
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
                    ServidorReadEasy.getInstance().inserirPromocao(promocao);

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
                    boolean excessaoLevantada = false;
                    ServidorReadEasy.getInstance().removerPromocao(promocaoSelecionada);

                    if (!excessaoLevantada){
                        tbvPromocoesAtivas.getItems().remove(promocaoSelecionada);
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sucesso!");
                        alert.setHeaderText("Promoção removida com sucesso!");
                        alert.setContentText(null);
                        alert.showAndWait();
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
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Promocao promocaoSelecionada = tbvPromocoesAtivas.getSelectionModel().getSelectedItem();

        if (promocaoSelecionada != null) {
            String novoTitulo = tfTitulo.getText();
            String pct = (tfPorcentagemDeDesconto.getText());
            String qntMin = tfQuantidadeMinimaDeLivros.getText();
            LocalDate novaDataInicio = dtpDataDeInicioDaPromocao.getValue();
            LocalDate novaDataFim = dtpDataDeExpiracaoDaPromocao.getValue();

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
                    int novaPorcentagem = Integer.parseInt(pct);
                    int novaQuantidade = Integer.parseInt(qntMin);

                    ServidorReadEasy.getInstance().atualizarPromocao(promocaoSelecionada, novoTitulo, novaPorcentagem,
                                novaQuantidade, novaDataInicio, novaDataFim, true);

                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sucesso.");
                        alert.setHeaderText(null);
                        alert.setContentText("promoção editada com sucesso!");
                        alert.showAndWait();


                    limparCampos();
                    this.initialize();
                }
                else{
                    alert.setTitle("Erro!");
                    alert.setHeaderText("Campo de porcentagem ou quantidade minima" + '\n' + " preenchido incorretamente.");
                    alert.setContentText("Preencha ambos os campos números para continuar.");
                    alert.showAndWait();
                }
            }
        }
        else {
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhuma promoção selecionada para edição.");
            alert.setContentText("Selecione uma promoção da tabela ao lado para continuar.");
            alert.showAndWait();
        }
    }

    @FXML
    public void limparCampos(){
        tfTitulo.clear();
        tfPorcentagemDeDesconto.clear();
        tfQuantidadeMinimaDeLivros.clear();
        dtpDataDeExpiracaoDaPromocao.setValue(null);
        dtpDataDeInicioDaPromocao.setValue(null);
    }

    private boolean validarInputTf(String quantidadeDigitada){
        boolean inputDigitadoCorretamente = true;

        try {
            int quantidade = Integer.parseInt(quantidadeDigitada);
        } catch (NumberFormatException e) {
            inputDigitadoCorretamente = false;
        }

        return inputDigitadoCorretamente;
    }

    @FXML
    public void popularCamposDaPromocaoSelecionada(){
        Promocao promocao = tbvPromocoesAtivas.getSelectionModel().getSelectedItem();

        tfTitulo.setText(promocao.getTitulo());
        tfQuantidadeMinimaDeLivros.setText(String.valueOf(promocao.getQtdMinimaDeLivros()));
        tfPorcentagemDeDesconto.setText(String.valueOf(promocao.getPorcentagemDeDesconto()));
        dtpDataDeInicioDaPromocao.setValue(promocao.getDataDeCriacao());
        dtpDataDeExpiracaoDaPromocao.setValue(promocao.getDataDeExpiracao());
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
