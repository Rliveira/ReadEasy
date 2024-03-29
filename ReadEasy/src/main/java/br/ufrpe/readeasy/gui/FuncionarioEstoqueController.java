package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.EstoqueInsuficienteException;
import br.ufrpe.readeasy.exceptions.ValorInvalidoException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
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
    private TextField tfValorTotalCompra;

    @FXML
    private TableView<Livro> tvEstoque;
    @FXML
    private TableColumn<Livro, String> colLivro;
    @FXML
    private TableColumn<Livro, Integer> colQuantidade;
    @FXML
    private TableColumn<Livro, String> colFornecedor;

    private List<Livro> livros;

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
        ivCapaDoLivro.setImage(null);
        inicializarComboBoxLivro();
        construirTabela();
        inicializarTabela();
    }

    @FXML
    private void inicializarComboBoxLivro(){
        cbLivros.getSelectionModel().clearSelection();
        cbLivros.getItems().clear();
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        List<Livro> livros = servidorReadEasy.listarTodosOslivrosEmOrdemAlfabetica();
        setLivros(livros);
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
        tvEstoque.getItems().clear();
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        List<Livro> listaDeLivros = servidorReadEasy.listarTodosOslivrosEmOrdemAlfabetica();
        tvEstoque.setItems(FXCollections.observableArrayList(listaDeLivros));
    }

    @FXML
    public void btnAdicionarEstoqueDoLivro(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        boolean excecaoLevantada = false;

        String inputQuantidade = tfQuantidade.getText();
        String inputValorPago = tfValorTotalCompra.getText();
        String livroSelecionado = cbLivros.getValue();

        if(inputValorPago.isEmpty() || inputQuantidade.isEmpty() || livroSelecionado == null){
            alert.setTitle("Erro");
            alert.setHeaderText("Algum campo ou alguns campos estão vazios.");
            alert.setContentText("Preencha-os corretemente para continuar.");
            alert.showAndWait();
        }
        else {
            if(validarInputTf(inputQuantidade) && validarInputTf(inputValorPago)){
                Livro livro = servidorReadEasy.buscarLivroPorNome(livroSelecionado);
                int quantidade = Integer.parseInt(inputQuantidade);
                double valorTotal = Double.parseDouble(inputValorPago);

                try {
                    servidorReadEasy.aumentarQuantidadeEmEstoque(livro, quantidade, LocalDate.now(), valorTotal);
                } catch (ValorInvalidoException e) {
                    excecaoLevantada = true;
                    alert.setTitle("Erro");
                    alert.setHeaderText("O campo de quantidade ou valor total pago " +
                            "possui valor nulo ou negativa digitada");
                    alert.setContentText("Digite uma quantidade positiva para continuar.");
                    alert.showAndWait();
                    limparCampos();
                }
                if(!excecaoLevantada){
                    tvEstoque.getItems().clear();
                    inicializarTabela();
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensagem");
                    alert.setHeaderText("Sucesso!");
                    alert.setContentText("Quantidade de estoque do livro adicionado com êxito.");
                    alert.showAndWait();
                    limparCampos();

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
            alert.showAndWait();
        }
        else{
            resultado = validarInputTf(inputQuantidade);

            if(resultado){
                Livro livro = servidorReadEasy.buscarLivroPorNome(livroSelecionado);
                int quantidade = Integer.parseInt(inputQuantidade);

                try {
                    servidorReadEasy.diminuirQuantidadeEmEstoque(livro, quantidade);
                } catch (ValorInvalidoException e) {
                    excecaoLevantada = true;
                    alert.setTitle("Erro");
                    alert.setHeaderText("Operação inválida!");
                    alert.setContentText("A quantidade digitada a ser removida é maior do que a quantidade em estoque.");
                    alert.showAndWait();
                } catch (EstoqueInsuficienteException e) {
                    excecaoLevantada = true;
                    alert.setTitle("Erro");
                    alert.setHeaderText("Operação inválida!");
                    alert.setContentText("A quantidade em estoque é 0.");
                    alert.showAndWait();
                }
                if(!excecaoLevantada){
                    tvEstoque.getItems().clear();
                    inicializarTabela();
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensagem");
                    alert.setHeaderText("Sucesso!");
                    alert.setContentText("Quantidade de estoque do livro adicionionado com êxito.");
                    alert.showAndWait();
                }
            }
        }
    }

    private boolean validarInputTf(String quantidadeDigitada){
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

        if (livroSelecionado != null) {
            cbLivros.setValue(livroSelecionado.getTitulo());
            ByteArrayInputStream inputStream = new ByteArrayInputStream(livroSelecionado.getCapaDoLivro());
            Image image = new Image(inputStream);
            ivCapaDoLivro.setImage(image);
        }
    }

    @FXML
    public void atualizarImageview(){
        String nomeLivro = cbLivros.getValue();

        if(nomeLivro != null){
            Livro livroSelecionado = procurarLivroPeloNome(nomeLivro);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(livroSelecionado.getCapaDoLivro());
            Image image = new Image(inputStream);
            ivCapaDoLivro.setImage(image);
        }
    }

    private void limparCampos() {
        tfQuantidade.clear();
        tfValorTotalCompra.clear();
        cbLivros.getSelectionModel().clearSelection();
        ivCapaDoLivro.setImage(null);
    }

    private Livro procurarLivroPeloNome(String nomeLivro){
        Livro livroSelecionado = null;
        boolean achou = false;

        for (int i = 0; i < getLivros().size() && !achou; i++){
            if (livros.get(i).getTitulo().equals(nomeLivro)){
                livroSelecionado = livros.get(i);
                achou = true;
            }
        }

        return livroSelecionado;
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

    //GETS AND SETS:
    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }
}