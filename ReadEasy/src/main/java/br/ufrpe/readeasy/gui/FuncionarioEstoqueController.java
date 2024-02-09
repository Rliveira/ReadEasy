package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.EstoqueInsuficienteException;
import br.ufrpe.readeasy.exceptions.QuantidadeInvalidaException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioEstoqueController {

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
    private Button btnAdicionar;
    @FXML
    private Button btnRemover;

    @FXML
    private ImageView ivCapaDoLivro;

    @FXML
    private ComboBox<String> cbLivros;

    @FXML
    private TextField tfPesquisar;
    @FXML
    private TextField tfQuantidade;

    @FXML
    private TableView<Livro> tvEstoque;
    @FXML
    private TableColumn<Livro, String> colLivro;
    @FXML
    private TableColumn<Livro, Integer> colQuantidade;
    @FXML
    private TableColumn<Livro, String> colFornecedor;

    //Métodos de troca de tela:
    @FXML
    public void trocarTelaPerfilFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioPerfil.fxml", "ReadEasy - Perfil");
    }

    @FXML
    public void trocarTelaHistoricoFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioHistoricoComprasEVendas.fxml", "ReadEasy - Histórico");
    }

    @FXML
    public void trocarTelaLivroFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioCRUDLivros.fxml", "ReadEasy - Livros");
    }

    @FXML
    public void trocarTelaRelatoriosFuncionario(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("funcionarioRelatorios.fxml", "ReadEasy - Relatorios");
    }

    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //Outros métodos:
    public void initialize(){
        inicializarComboBoxLivro();
        construirTabela();
        inicializarTabela();
    }

    @FXML
    private void inicializarComboBoxLivro(){
        cbLivros.getSelectionModel().clearSelection();
        cbLivros.getItems().clear();
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        List <Livro>livros = servidorReadEasy.listarTodosOslivrosEmOrdemAlfabetica();
        List<String> titulosLivro = new ArrayList<>();

        for (Livro livro : livros){
            titulosLivro.add(livro.getTitulo());
        }

        cbLivros.getItems().addAll(titulosLivro);

    }

    @FXML
    private void construirTabela(){
        colLivro.setCellValueFactory(cellData -> {
            Livro livro = cellData.getValue();
            String titulo = livro.getTitulo();
            return new SimpleStringProperty(titulo);
        });

        colFornecedor.setCellValueFactory(cellData -> {
            Livro livro = cellData.getValue();
            String fornecedor = livro.getFornecedor().getNome();
            return new SimpleStringProperty(fornecedor);
        });

        colQuantidade.setCellValueFactory(cellData -> {
            Livro livro = cellData.getValue();
            int quantidade = livro.getQuantidade();
            return new SimpleIntegerProperty(quantidade).asObject();
        });
    }

    @FXML
    private void inicializarTabela(){
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        List<Livro> listaDeLivros = servidorReadEasy.listarTodosOslivrosEmOrdemAlfabetica();
        tvEstoque.setItems(FXCollections.observableArrayList(listaDeLivros));
    }

    @FXML
    public void btnAdicionarEstoqueDoLivro(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        boolean excecaoLevantada = false;
        boolean resultado;

        String inputQuantidade = tfQuantidade.getText();
        String livroSelecionado = cbLivros.getValue();

        if(inputQuantidade.isEmpty() || livroSelecionado == null){
            alert.setTitle("Erro");
            alert.setHeaderText("Algum campo ou alguns campos estão vazios.");
            alert.setContentText("Preencha-os corretemente para continuar.");

            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                    alert.close();
                }
            });
        }
        else {
            resultado = validarInputTfQuantidade(inputQuantidade);

            if(resultado){
                Livro livro = servidorReadEasy.buscarLivroPorNome(livroSelecionado);
                int quantidade = Integer.parseInt(inputQuantidade);

                try {
                    servidorReadEasy.aumentarQuantidadeEmEstoque(livro, quantidade, LocalDate.now());
                } catch (QuantidadeInvalidaException e) {
                    excecaoLevantada = true;
                    alert.setTitle("Erro");
                    alert.setHeaderText("Quantidade nula ou negativa digitada");
                    alert.setContentText("Digite uma quantidade positiva para continuar.");

                    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(okButton);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            alert.close();
                        }
                    });
                    limparCampos();
                }
                if(!excecaoLevantada){
                    tvEstoque.getItems().clear();
                    inicializarTabela();
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensagem");
                    alert.setHeaderText("Sucesso!");
                    alert.setContentText("Quantidade de estoque do livro adicionado com êxito.");

                    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(okButton);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            alert.close();
                        }
                    });
                }
            }
        }
    }

    @FXML
    public void btnReduzirEstoqueDoLivro(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        boolean excecaoLevantada = false;
        boolean resultado;

        String inputQuantidade = tfQuantidade.getText();
        String livroSelecionado = cbLivros.getValue();

        if(inputQuantidade.isEmpty() || livroSelecionado == null){
            alert.setTitle("Erro");
            alert.setHeaderText("Algum campo ou alguns campos estão vazios.");
            alert.setContentText("Preencha-os corretemente para continuar.");

            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                    alert.close();
                }
            });
        }
        else{
            resultado = validarInputTfQuantidade(inputQuantidade);

            if(resultado){
                Livro livro = servidorReadEasy.buscarLivroPorNome(livroSelecionado);
                int quantidade = Integer.parseInt(inputQuantidade);

                try {
                    servidorReadEasy.diminuirQuantidadeEmEstoque(livro, quantidade);
                } catch (QuantidadeInvalidaException e) {
                    excecaoLevantada = true;
                    alert.setTitle("Erro");
                    alert.setHeaderText("Operação inválida!");
                    alert.setContentText("A quantidade digitada a ser removida é maior do que a quantidade em estoque.");

                    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(okButton);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            alert.close();
                        }
                    });
                } catch (EstoqueInsuficienteException e) {
                    excecaoLevantada = true;
                    alert.setTitle("Erro");
                    alert.setHeaderText("Operação inválida!");
                    alert.setContentText("A quantidade em estoque é 0.");

                    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(okButton);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            alert.close();
                        }
                    });
                }
                if(!excecaoLevantada){
                    tvEstoque.getItems().clear();
                    inicializarTabela();
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensagem");
                    alert.setHeaderText("Sucesso!");
                    alert.setContentText("Quantidade de estoque do livro adicionionado com êxito.");

                    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(okButton);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            alert.close();
                        }
                    });
                }
            }
        }
    }

    private boolean validarInputTfQuantidade(String quantidadeDigitada){
        boolean qtdDigitadaCorretamente = true;

        try {
            int quantidade = Integer.parseInt(quantidadeDigitada);
        } catch (NumberFormatException e) {
            qtdDigitadaCorretamente = false;
        }

        return qtdDigitadaCorretamente;
    }


    @FXML
    private void filtrarLivrosNaTabela() {
        String termoPesquisa = tfPesquisar.getText();
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();

        List<Livro> listaDeLivros = servidorReadEasy.listarTodosOslivrosEmOrdemAlfabetica();

        if (termoPesquisa == null || termoPesquisa.trim().isEmpty()) {
            tvEstoque.setItems(FXCollections.observableArrayList(listaDeLivros));
        }
        else {
            FilteredList<Livro> livrosFiltrados = new FilteredList<>(FXCollections.observableArrayList(listaDeLivros));

            livrosFiltrados.setPredicate(livro -> {
                String termoLowerCase = termoPesquisa.toLowerCase();

                return livro.getTitulo().toLowerCase().contains(termoLowerCase) ||
                        livro.getFornecedor().getNome().toLowerCase().contains(termoLowerCase) ||
                        String.valueOf(livro.getQuantidade()).contains(termoLowerCase);
            });

            tvEstoque.setItems(livrosFiltrados);
        }
    }

    @FXML
    public void popularCamposDoLivroSelecionadoPelaTabela() {
        Livro livroSelecionado = tvEstoque.getSelectionModel().getSelectedItem();
        InputStream inputStream;

        if (livroSelecionado != null) {
            tfQuantidade.setText(String.valueOf(livroSelecionado.getQuantidade()));
            cbLivros.setValue(livroSelecionado.getTitulo());
            try {
                inputStream = livroSelecionado.getCapaDoLivro().openStream();
            } catch (IOException e) {
                //excessão inútil que sou forçado a fazer o try catch
                throw new RuntimeException(e);
            }
            Image image = new Image(inputStream);
            ivCapaDoLivro.setImage(image);
        }

    }

    private void limparCampos() {
        tfQuantidade.clear();
        cbLivros.getSelectionModel().clearSelection();
        ivCapaDoLivro.setImage(null);
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