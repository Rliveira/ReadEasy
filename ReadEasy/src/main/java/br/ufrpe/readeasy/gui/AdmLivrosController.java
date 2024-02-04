package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Genero;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdmLivrosController {

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
    private Button btnSair;
    @FXML
    private Button btnDownloadImagem;
    @FXML
    private Button btnAdicionar1;
    @FXML
    private Button btnDeletar1;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnAdicionar2;
    @FXML
    private Button btnDeletar2;

    @FXML
    private ImageView ivCapaDoLivro;

    @FXML
    private TextField tfTitulo;
    @FXML
    private TextField tfAutor;
    @FXML
    private TextField tfPreco;
    @FXML
    private TextField tfPesquisar;

    @FXML
    private ComboBox<String> cbFornecedor;
    @FXML
    private ComboBox<String> cbLivros;
    @FXML
    private ComboBox<String> cbGenero;


    @FXML
    private TableView<Livro> tvCatalogoLivros;
    @FXML
    private TableColumn <Livro, String> colTitulo;
    @FXML
    private TableColumn <Livro, String> colAutor;
    @FXML
    private TableColumn<Livro, String> colFornecedor;
    @FXML
    private TableColumn<Livro, Double> colPreco;

    @FXML
    private ListView<Genero> lvTodosOsGeneros;
    @FXML
    private ListView<Genero> lvGenerosDoLivro;

    private List<Fornecedor> fornecedores;
    List<Genero> generos;
    private boolean atualizarComboBoxLivros = false;
    private Livro livroSelecionado;

    //Métodos de troca de tela:
    public void trocarTelaEstoqueAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admEstoque.fxml", "ReadEasy - Estoque");
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

    public void trocarTelaRelatoriosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admRelatorios.fxml", "ReadEasy - Relatorios");
    }

    public void trocarTelaUsuariosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admCRUDUsuarios.fxml", "ReadEasy - Usuarios");
    }

    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //Outros métodos:
    @FXML
    public void initialize(){
        inicializarComboBoxFornecedor();
        inicializarComboBoxGenero();
        inicializarListViewTodosOsGeneros();
        construirTabela();
        inicializarTabela();
    }

    @FXML
    public void inicializarOuAtualizarCbLivros(){
        if(cbLivros.getItems().isEmpty() || atualizarComboBoxLivros){
            cbLivros.getItems().clear();
            lvGenerosDoLivro.getItems().clear();
            inicializarComboBoxLivro();
            setAtualizarComboBoxLivros(false);
        }
    }


    private void inicializarComboBoxGenero(){
        this.generos = Arrays.asList(Genero.values());

        List<String> nomesGenero = new ArrayList<>();

        for (Genero genero : generos){
            nomesGenero.add(genero.getDescricaoEnum());
        }
        cbGenero.getItems().addAll(nomesGenero);
    }

    @FXML
    private void inicializarComboBoxFornecedor(){
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        List<Fornecedor> fornecedores = servidorReadEasy.listarFornecedores();
        List<String> nomesFornecedores = new ArrayList<>();

        setFornecedores(fornecedores);
        for (Fornecedor fornecedor : fornecedores){
            nomesFornecedores.add(fornecedor.getNome());
        }

        cbFornecedor.getItems().addAll(nomesFornecedores);
    }

    @FXML
    private void inicializarComboBoxLivro(){
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        List <Livro>livros = servidorReadEasy.listarTodosOslivrosEmOrdemAlfabetica();
        List<String> titulosLivro = new ArrayList<>();

        for (Livro livro : livros){
            titulosLivro.add(livro.getTitulo());
        }

        cbLivros.getItems().addAll(titulosLivro);

    }

    @FXML
    private void inicializarListViewTodosOsGeneros() {
        lvTodosOsGeneros.setItems(FXCollections.observableArrayList(Arrays.asList(Genero.values())));
        lvTodosOsGeneros.setCellFactory(param -> new ListCell<Genero>() {
            @Override
            protected void updateItem(Genero genero, boolean empty) {
                super.updateItem(genero, empty);

                if (empty || genero == null) {
                    setText(null);
                } else {
                    setText(genero.getDescricaoEnum());
                }
            }
        });

        lvTodosOsGeneros.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void inicializarListViewGenerosDoLivro() {
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        String tituloLivro = cbLivros.getValue();
        Livro livro = servidorReadEasy.buscarLivroPorNome(tituloLivro);
        lvGenerosDoLivro.setItems(FXCollections.observableArrayList(livro.getGeneros()));
        lvGenerosDoLivro.setCellFactory(param -> new ListCell<Genero>() {
            @Override
            protected void updateItem(Genero genero, boolean empty) {
                super.updateItem(genero, empty);

                if (empty || genero == null) {
                    setText(null);
                } else {
                    setText(genero.getDescricaoEnum());
                }
            }
        });

        lvGenerosDoLivro.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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

        colFornecedor.setCellValueFactory(cellData -> {
            Livro livro = cellData.getValue();
            String fornecedor = livro.getFornecedor().getNome();
            return new SimpleStringProperty(fornecedor);
        });

        colPreco.setCellValueFactory(cellData -> {
            Livro livro = cellData.getValue();
            double preco = livro.getPreco();
            return new SimpleDoubleProperty(preco).asObject();
        });
    }

    @FXML
    private void inicializarTabela(){
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        List<Livro> listaDeLivros = servidorReadEasy.listarTodosOslivrosEmOrdemAlfabetica();
        tvCatalogoLivros.setItems(FXCollections.observableArrayList(listaDeLivros));
    }

    @FXML
    public void btnAdicionarLivro(ActionEvent event){
        boolean excecaoLevantada = false;
        boolean precoDigitadoCorretamente;
        Alert alert = new Alert(Alert.AlertType.ERROR);

        String titulo = tfTitulo.getText();
        String autor = tfAutor.getText();
        String textoPreco = tfPreco.getText();
        String nomeFornecedor = cbFornecedor.getValue();
        Fornecedor fornecedor = procurarFornecedorPeloNome(nomeFornecedor);
        String nomeGenero = cbGenero.getValue();
        Genero genero = procurarGeneroPeloNome(nomeGenero);
        precoDigitadoCorretamente = validarInputTfPreco(textoPreco);

        if(precoDigitadoCorretamente){
            if(titulo.isBlank() || autor.isBlank() || fornecedor == null || genero == null){
                alert.setTitle("Erro");
                alert.setHeaderText("Algum campo ou alguns campos estão vazios.");
                alert.setContentText("Preencha-os corretemente para continuar");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
            }
            else{
                textoPreco = textoPreco.replace(',', '.');
                double preco = Double.parseDouble(textoPreco);
                Livro livro = new Livro(titulo, autor, preco, fornecedor);

                ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
                try {
                    servidorReadEasy.adicionarLivro(livro);
                    servidorReadEasy.adicionarGenero(livro, genero);
                }  catch (PrecoInvalidoException e) {
                    excecaoLevantada = true;
                    alert.setTitle("Erro");
                    alert.setHeaderText("Preço de livro inválido!");
                    alert.setContentText("Você digitou um preço negativo, digite um valor positivo para continuar");

                    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(okButton);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            alert.close();
                        }
                    });
                } catch (LivroExistenteException e) {
                    excecaoLevantada = true;
                    alert.setTitle("Erro");
                    alert.setHeaderText("Cadastro inválido!");
                    alert.setContentText("você tentou cadastrar um livro já existente no catálogo.");

                    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(okButton);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            alert.close();
                        }
                    });
                } catch (GeneroExistenteException e) {
                    throw new RuntimeException(e);
                }
                if (!excecaoLevantada){
                    tvCatalogoLivros.getItems().add(livro);
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensagem");
                    alert.setHeaderText("Sucesso!");
                    alert.setContentText("Livro cadastrado com êxito.");

                    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(okButton);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            alert.close();
                        }
                    });
                    limparCampos();
                    setAtualizarComboBoxLivros(true);
                }
            }
        }
        else {
            alert.setTitle("Erro!");
            alert.setHeaderText("");
            alert.setContentText("Campo de preço não preenchido corretamente." + '\n' +
                    "O conteúdo preço digitado não é um número.");

            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                    alert.close();
                }
            });
        }
    }

    @FXML
    public void btnremoverLivro(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        boolean excecaoLevantada = false;

        Livro livroSelecionado = getLivroSelecionado();

        if(livroSelecionado != null){
            try {
                servidorReadEasy.removerLivro(livroSelecionado);
            } catch (LivroNaoExistenteException e) {
                excecaoLevantada = true;
                alert.setTitle("erro!");
                alert.setHeaderText("Operação inválida!");
                alert.setContentText("Você está entando remover um  livro não existente no catálogo");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
            }
            if(!excecaoLevantada){
                tvCatalogoLivros.getItems().remove(livroSelecionado);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Mensagem");
                alert.setHeaderText("Sucesso!");
                alert.setContentText("Livro removido com êxito.");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
                limparCampos();
                setAtualizarComboBoxLivros(true);
            }
        }
        else{
            alert.setTitle("Erro!");
            alert.setHeaderText("Operação Inválida!");
            alert.setContentText("Selecione uma linha da tabela que contenha os dados de um livro.");

            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                    alert.close();
                }
            });
        }
    }

    @FXML
    public void btnEditarLivro(ActionEvent event){
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        boolean excecaoLevantada = false;
        boolean resultado;
        Alert alert = new Alert(Alert.AlertType.ERROR);

        String titulo = tfTitulo.getText();
        String autor = tfAutor.getText();
        String textoPreco = tfPreco.getText();
        String nomeFornecedor = cbFornecedor.getValue();
        Fornecedor fornecedor = procurarFornecedorPeloNome(nomeFornecedor);

        resultado = validarInputTfPreco(textoPreco);

        if(resultado){
            if(titulo.isBlank() || autor.isBlank() || fornecedor == null){
                alert.setTitle("Erro");
                alert.setHeaderText("Operação inválida!");
                alert.setContentText("Algum(s) campo(s) estão vazio(s), Preencha todos os campos corretemente para continuar");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
            }
            else{
                textoPreco = textoPreco.replace(',', '.');
                double preco = Double.parseDouble(textoPreco);

                try {
                    servidorReadEasy.atualizarLivro(getLivroSelecionado(),titulo, autor, preco, fornecedor);
                }  catch (LivroExistenteException e) {
                    excecaoLevantada = true;
                    alert.setTitle("Erro");
                    alert.setHeaderText("Operação inválida!");
                    alert.setContentText("você tentou editar um livro pra um nome de um livro já existente no catálogo.");

                    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(okButton);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            alert.close();
                        }
                    });
                }
                catch (PrecoInvalidoException e) {
                    excecaoLevantada = true;
                    alert.setTitle("Erro");
                    alert.setHeaderText("Operação inválida!");
                    alert.setContentText("Você digitou um preço negativo, digite um valor maior ou igual a 0 para continuar");

                    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(okButton);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            alert.close();
                        }
                    });
                }
                if (!excecaoLevantada){
                    tvCatalogoLivros.getItems().clear();
                    inicializarTabela();
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensagem");
                    alert.setHeaderText("Sucesso!");
                    alert.setContentText("Livro editado com êxito.");

                    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(okButton);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            alert.close();
                        }
                    });
                    limparCampos();
                    setAtualizarComboBoxLivros(true);
                }
            }
        }
        else {
            alert.setTitle("Erro!");
            alert.setHeaderText("Operação inválida!");
            alert.setContentText("Selecione um item da tabela ao lado e preencha o campo de preço com um número");

            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                    alert.close();
                }
            });
        }
    }

    @FXML
    public void btnAdicionarGenero(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();

        Genero generoSelecionado = lvTodosOsGeneros.getSelectionModel().getSelectedItem();
        String tituloLivro = cbLivros.getValue();
        Livro livroSelecionado = servidorReadEasy.buscarLivroPorNome(tituloLivro);

        if(livroSelecionado != null){
            if (generoSelecionado != null) {
                boolean exceptionLevantada = false;

                try {
                    servidorReadEasy.adicionarGenero(livroSelecionado, generoSelecionado);
                } catch (GeneroExistenteException e) {
                    exceptionLevantada = true;
                    alert.setTitle("Erro!");
                    alert.setHeaderText("Você selecionou um gênero já existente no livro.");
                    alert.setContentText("Selecione um gênero novo no livro para continuar.");

                    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(okButton);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            alert.close();
                        }
                    });
                }
                if(!exceptionLevantada){
                    lvGenerosDoLivro.getItems().add(generoSelecionado);
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensagem");
                    alert.setHeaderText("Sucesso!");
                    alert.setContentText("Gênero adicionado com êxito");

                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStyleClass().add("my-alert-style");


                    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(okButton);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            alert.close();
                        }
                    });
                }
            }
            else{
                alert.setTitle("Erro!");
                alert.setHeaderText("Você não selecionou nenhum gênero para adicionar");
                alert.setContentText("Selecione algum gênero para continuar.");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
            }
        }
        else{
            alert.setTitle("Erro!");
            alert.setHeaderText("Você não selecionou nenhum Livro!");
            alert.setContentText("Selecione algum livro para continuar.");

            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                    alert.close();
                }
            });
        }
    }

    @FXML
    public void btnRemoverGenero(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();

        Genero generoSelecionado = lvGenerosDoLivro.getSelectionModel().getSelectedItem();
        String tituloLivro = cbLivros.getValue();
        Livro livroSelecionado = servidorReadEasy.buscarLivroPorNome(tituloLivro);

        if(livroSelecionado != null){
            if (generoSelecionado != null) {

                boolean exceptionLevantada = false;

                try {
                    servidorReadEasy.removerGenero(livroSelecionado, generoSelecionado);
                } catch (GeneroNaoExistenteException e) {
                    exceptionLevantada = true;
                    alert.setTitle("Erro!");
                    alert.setHeaderText("Você não selecionou um gênero já existente no livro.");
                    alert.setContentText("Selecione um gênero novo no livro para continuar.");

                    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(okButton);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            alert.close();
                        }
                    });
                } catch (LivroSemGeneroException e){
                    exceptionLevantada = true;
                    alert.setTitle("Erro!");
                    alert.setHeaderText("Remoção de gênero inválida");
                    alert.setContentText("O Livro deve conter ao menos 1 gênero.");

                    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(okButton);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            alert.close();
                        }
                    });
                }
                if(!exceptionLevantada){
                    lvGenerosDoLivro.getItems().remove(generoSelecionado);
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensagem");
                    alert.setHeaderText("Sucesso");
                    alert.setContentText("Gênero removido com êxito");

                    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().setAll(okButton);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                            alert.close();
                        }
                    });
                }
            }
            else{
                alert.setTitle("Erro!");
                alert.setHeaderText("Você não selecionou nenhum gênero para remover");
                alert.setContentText("Selecione algum gênero para continuar.");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
            }
        }
        else{
            alert.setTitle("Erro!");
            alert.setHeaderText("Você não selecionou nenhum Livro!");
            alert.setContentText("Selecione algum livro para continuar.");

            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                    alert.close();
                }
            });
        }
    }

    @FXML
    private void filtrarLivrosNaTabela() {
        String termoPesquisa = tfPesquisar.getText();
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();

        List<Livro> listaDeLivros = servidorReadEasy.listarTodosOslivrosEmOrdemAlfabetica();

        if (termoPesquisa == null || termoPesquisa.trim().isEmpty()) {
            tvCatalogoLivros.setItems(FXCollections.observableArrayList(listaDeLivros));
        }
        else {
            FilteredList<Livro> livrosFiltrados = new FilteredList<>(FXCollections.observableArrayList(listaDeLivros));

            livrosFiltrados.setPredicate(livro -> {
                String termoLowerCase = termoPesquisa.toLowerCase();

                return livro.getTitulo().toLowerCase().contains(termoLowerCase) ||
                        livro.getAutor().toLowerCase().contains(termoLowerCase) ||
                        livro.getFornecedor().getNome().toLowerCase().contains(termoLowerCase) ||
                        String.valueOf(livro.getPreco()).contains(termoLowerCase);
            });

            tvCatalogoLivros.setItems(livrosFiltrados);
        }
    }

    private Fornecedor procurarFornecedorPeloNome(String nomeFornecedor){
        Fornecedor fornecedorEncrontado = null;
        boolean achou = false;

        for(int i = 0; i < fornecedores.size() && !achou; i++){
            if (fornecedores.get(i).getNome().equals(nomeFornecedor)){
                fornecedorEncrontado = fornecedores.get(i);
                achou = true;
            }
        }

        return fornecedorEncrontado;
    }

    private Genero procurarGeneroPeloNome(String nomeGenero){
        Genero generoEncrontado = null;
        boolean achou = false;

        for(int i = 0; i < generos.size() && !achou; i++){
            if (generos.get(i).getDescricaoEnum().equals(nomeGenero)){
                generoEncrontado = generos.get(i);
                achou = true;
            }
        }

        return generoEncrontado;
    }

    @FXML
    public void popularCamposDoLivroSelecionadoPelaTabela() {
        Livro livroSelecionado = tvCatalogoLivros.getSelectionModel().getSelectedItem();

        if (livroSelecionado != null) {
            tfTitulo.setText(livroSelecionado.getTitulo());
            tfAutor.setText(livroSelecionado.getAutor());
            tfPreco.setText(String.valueOf(livroSelecionado.getPreco()));
            cbFornecedor.setValue(livroSelecionado.getFornecedor().getNome());
            cbGenero.setValue(livroSelecionado.getGeneros().get(0).getDescricaoEnum());
        }
        setLivroSelecionado(livroSelecionado);
    }


    private void limparCampos() {
        tfTitulo.clear();
        tfAutor.clear();
        tfPreco.clear();
    }

    private boolean validarInputTfPreco(String textoTfPreco) {
        boolean precoDigitadoCorretamente = true;

        textoTfPreco = textoTfPreco.replace(',', '.');

        try {
            double preco = Double.parseDouble(textoTfPreco);
        } catch (NumberFormatException e) {
            precoDigitadoCorretamente = false;
        }

        return precoDigitadoCorretamente;
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

    //gets and Sets:
    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }
    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }


    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public void setAtualizarComboBoxLivros(boolean atualizarComboBoxLivros) {
        this.atualizarComboBoxLivros = atualizarComboBoxLivros;
    }

    public Livro getLivroSelecionado() {
        return livroSelecionado;
    }

    public void setLivroSelecionado(Livro livroSelecionado) {
        this.livroSelecionado = livroSelecionado;
    }
}