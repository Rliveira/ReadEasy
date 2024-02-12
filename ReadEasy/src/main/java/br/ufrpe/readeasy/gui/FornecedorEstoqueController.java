package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.beans.Usuario;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class FornecedorEstoqueController {

    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnEstoque;
    @FXML
    private Button btnHistorico;
    @FXML
    private Button btnSair;

    @FXML
    private TextField tfPesquisar;

    @FXML
    private TableView<Livro> tvEstoqueLivrosFornecedor;
    @FXML
    private TableColumn<Livro, String> colTitulo;
    @FXML
    private TableColumn<Livro, String> colAutor;
    @FXML
    private TableColumn<Livro, Integer> colQuantidade;

    private List<Livro> listaDeLivrosFornecedor = new ArrayList<>();

    public void trocarTelaPerfilFornecedor(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("fornecedorPerfil.fxml", "ReadEasy - Perfil");
    }

    public void trocarTelaHistoricoFornecedor(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("fornecedorHistorico.fxml", "ReadEasy - Histórico");
    }

    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //Outros métodos:
    public void initialize(){
        construirTabela();
        inicializarTabela();
    }

    @FXML
    private void construirTabela(){
        colTitulo.setCellValueFactory(cellData -> {
            Livro livro = cellData.getValue();
            String titulo = livro.getTitulo();
            return new SimpleStringProperty(titulo);
        });

        colAutor.setCellValueFactory(cellData -> {
            Livro livro = cellData.getValue();
            String autor = livro.getAutor();
            return new SimpleStringProperty(autor);
        });

        colQuantidade.setCellValueFactory(cellData -> {
            Livro livro = cellData.getValue();
            Integer quantidade = livro.getQuantidade();
            return new SimpleIntegerProperty(quantidade).asObject();
        });
    }

    @FXML
    private void inicializarTabela(){
        tvEstoqueLivrosFornecedor.getItems().clear();

        Usuario usuario = SessaoUsuario.getUsuarioLogado();
        if(usuario instanceof Fornecedor){
            Fornecedor fornecedor = (Fornecedor) usuario ;
            ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
            List<Livro> listaDeLivrosFornecedor = servidorReadEasy.listarLivrosPorFornecedor(fornecedor);

            if(listaDeLivrosFornecedor.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Atenção!");
                alert.setHeaderText("Não existe nenhum livro associado a sua conta para que você" +
                        " possa visualizar o estoque de livros.");
                alert.setContentText("Entre em contato como o gerente ou funcionário" +
                        " da livraria para que ele associe um livro solucionar esse problema.");
            }
            setListaDeLivrosFornecedor(listaDeLivrosFornecedor);
            tvEstoqueLivrosFornecedor.setItems(FXCollections.observableArrayList(listaDeLivrosFornecedor));
        }
    }
    @FXML
    private void filtrarLivrosNaTabela() {
        String termoPesquisa = tfPesquisar.getText();

        if (termoPesquisa == null || termoPesquisa.trim().isEmpty()) {
            tvEstoqueLivrosFornecedor.setItems(FXCollections.observableArrayList(getListaDeLivrosFornecedor()));
        }
        else {
            FilteredList<Livro> livrosFiltrados = new FilteredList<>(FXCollections.observableArrayList(getListaDeLivrosFornecedor()));

            livrosFiltrados.setPredicate(livro -> {
                String termoLowerCase = termoPesquisa.toLowerCase();

                return livro.getTitulo().toLowerCase().contains(termoLowerCase) ||
                        livro.getAutor().toLowerCase().contains(termoLowerCase) ||
                        String.valueOf(livro.getQuantidade()).contains(termoLowerCase);
            });

            tvEstoqueLivrosFornecedor.setItems(livrosFiltrados);
        }
    }

    public void SairDaConta(){
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
    public List<Livro> getListaDeLivrosFornecedor() {
        return listaDeLivrosFornecedor;
    }

    public void setListaDeLivrosFornecedor(List<Livro> listaDeLivrosFornecedor) {
        this.listaDeLivrosFornecedor = listaDeLivrosFornecedor;
    }
}
