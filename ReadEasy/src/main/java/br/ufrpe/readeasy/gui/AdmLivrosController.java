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
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
    private Button btnLimpar;
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
    private TextField tfURLCapaDoLivro;

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
    private boolean atualizarComboBoxGenero = false;
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
        limparCampos();
        limparComboBox();
        inicializarCbFornecedor();
        inicializarCbGenero();
        inicializarListViewTodosOsGeneros();
        construirTabela();
        inicializarTabela();
        atualizarCbLivros();
    }

    public void atualizarImageView() {
        String urlString = tfURLCapaDoLivro.getText();
        Alert alert = new Alert(Alert.AlertType.ERROR);

        if (!urlString.isBlank()) {
            try {
                URL url = new URL(urlString);
                URI uri = url.toURI(); // Verifica se a URL é bem formada
                InputStream inputStream = url.openStream();
                Image image = new Image(inputStream);
                ivCapaDoLivro.setImage(image);
            } catch (MalformedURLException | URISyntaxException e) {
                alert.setTitle("Erro");
                alert.setHeaderText("Link incompleto ou link inválido.");
                alert.setContentText("Copie e cole um link completo e válido para continuar.");
                ivCapaDoLivro.setImage(null);

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                        alert.close();
                    }
                });
            } catch (IOException e) {
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao carregar a imagem.");
                alert.setContentText("Copie e cole um link completo e válido para continuar.");
                ivCapaDoLivro.setImage(null);

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                        alert.close();
                    }
                });
            }
        }
    }

    @FXML
    public void atualizarCbLivros(){
        if(cbLivros.getItems().isEmpty() || atualizarComboBoxLivros){
            lvGenerosDoLivro.getItems().clear();
            inicializarCbLivro();
            atualizarComboBoxLivros = false;
        }
    }

    @FXML
    public void atualizarCbGenero(){
        if(cbLivros != null ){
            String nomelivro =  cbLivros.getValue();
            String nomeLivro2 = tfTitulo.getText();

            if(atualizarComboBoxGenero && !nomeLivro2.isEmpty() && nomelivro.equals(nomeLivro2)){
                Livro livro = ServidorReadEasy.getInstance().buscarLivroPorNome(nomelivro);
                Genero genero = livro.getGeneros().get(0);
                cbGenero.setValue(genero.getDescricaoEnum());
                atualizarComboBoxGenero = false;
            }
        }
    }

    private void inicializarCbGenero(){
        this.generos = Arrays.asList(Genero.values());

        List<String> nomesGenero = new ArrayList<>();

        for (Genero genero : generos){
            nomesGenero.add(genero.getDescricaoEnum());
        }
        cbGenero.getItems().addAll(nomesGenero);
    }

    @FXML
    private void inicializarCbFornecedor(){
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
    private void inicializarCbLivro(){
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
        if(livro != null){
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
    public void btnAdicionarLivro(){
        boolean excecaoLevantada = false;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        URL urlLivro;

        String titulo = tfTitulo.getText();
        String autor = tfAutor.getText();
        String textoPreco = tfPreco.getText();
        String nomeFornecedor = cbFornecedor.getValue();
        Fornecedor fornecedor = procurarFornecedorPeloNome(nomeFornecedor);
        String nomeGenero = cbGenero.getValue();
        Genero genero = procurarGeneroPeloNome(nomeGenero);
        String urlString = tfURLCapaDoLivro.getText();

        if(validarInputTfPreco(textoPreco)){
            if(titulo.isBlank() || autor.isBlank() || fornecedor == null || genero == null){
                alert.setTitle("Erro");
                alert.setHeaderText("Algum campo ou alguns campos estão vazios.");
                alert.setContentText("Preencha-os corretemente para continuar");
                alert.showAndWait();
            }
            else{
                InputStream inputStream;
                try {
                    urlLivro = new URL(urlString);
                    inputStream = urlLivro.openStream();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Ler os bytes da imagem em um array de bytes
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;

                while (true) {
                    try {
                        if ((bytesRead = inputStream.read(buffer)) == -1) break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    outputStream.write(buffer, 0, bytesRead);
                }

                // Armazenar os bytes da imagem em um array
                byte[] imageBytes = outputStream.toByteArray();

                textoPreco = textoPreco.replace(',', '.');
                double preco = Double.parseDouble(textoPreco);
                Livro livro = new Livro(titulo, autor, preco, fornecedor, imageBytes, urlLivro);

                ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
                try {
                    servidorReadEasy.adicionarLivro(livro);
                    servidorReadEasy.adicionarGenero(livro, genero);
                }  catch (ValorInvalidoException e) {
                    excecaoLevantada = true;
                    alert.setTitle("Erro");
                    alert.setHeaderText("Preço de livro inválido!");
                    alert.setContentText("Você digitou um preço negativo, digite um valor positivo para continuar");
                    alert.showAndWait();
                } catch (LivroExistenteException e) {
                    excecaoLevantada = true;
                    alert.setTitle("Erro");
                    alert.setHeaderText("Cadastro inválido!");
                    alert.setContentText("você tentou cadastrar um livro já existente no catálogo.");
                    alert.showAndWait();
                } catch (GeneroExistenteException e) {
                    throw new RuntimeException(e);
                }
                if (!excecaoLevantada){
                    tvCatalogoLivros.getItems().add(livro);

                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensagem");
                    alert.setHeaderText("Sucesso!");
                    alert.setContentText("Livro cadastrado com êxito.");
                    alert.showAndWait();

                    limparCampos();
                    atualizarComboBoxLivros = true;
                }
            }
        }
        else {
            alert.setTitle("Erro!");
            alert.setHeaderText("");
            alert.setContentText("Campo de preço não preenchido corretamente." + '\n' +
                    "O conteúdo preço digitado não é um número.");
            alert.showAndWait();
        }
    }

    @FXML
    public void btnremoverLivro(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();

        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja realmente remover o livro?");
        alert.setContentText("Escolha uma opção.");

        ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(simButton, naoButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                alert.close();
                Alert alertErro = new Alert(Alert.AlertType.ERROR);

                boolean excecaoLevantada = false;
                Livro livroSelecionado = getLivroSelecionado();

                if(livroSelecionado != null){
                    servidorReadEasy.removerLivro(livroSelecionado);

                    if(!excecaoLevantada){
                        tvCatalogoLivros.getItems().remove(livroSelecionado);

                        alertErro.setAlertType(Alert.AlertType.INFORMATION);
                        alertErro.setTitle("Mensagem");
                        alertErro.setHeaderText("Sucesso!");
                        alertErro.setContentText("Livro removido com êxito.");
                        alertErro.showAndWait();

                        limparCampos();
                        atualizarComboBoxLivros = true;
                    }
                }
                else{
                    alertErro.setTitle("Erro!");
                    alertErro.setHeaderText("Operação Inválida!");
                    alertErro.setContentText("Selecione uma linha da tabela que contenha os dados de um livro.");
                    alertErro.showAndWait();
                }
            }
            else {
                alert.close();
            }
        });

    }

    @FXML
    public void btnEditarLivro(){
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja realmente editar o livro?");
        alert.setContentText("Escolha uma opção.");

        ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(simButton, naoButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                alert.close();
                Alert alertErro = new Alert(Alert.AlertType.ERROR);

                URL urlLivro;
                boolean excecaoLevantada = false;

                String titulo = tfTitulo.getText();
                String autor = tfAutor.getText();
                String textoPreco = tfPreco.getText();
                String nomeFornecedor = cbFornecedor.getValue();
                Fornecedor fornecedor = procurarFornecedorPeloNome(nomeFornecedor);
                String urlString = tfURLCapaDoLivro.getText();

                if(validarInputTfPreco(textoPreco)){
                    if(titulo.isBlank() || autor.isBlank() || fornecedor == null || urlString.isBlank()){
                        alertErro.setTitle("Erro");
                        alertErro.setHeaderText("Operação inválida!");
                        alertErro.setContentText("Algum(s) campo(s) estão vazio(s), Preencha todos os campos corretemente para continuar");
                        alertErro.showAndWait();
                    }
                    else{
                        InputStream inputStream;
                        try {
                            urlLivro = new URL(urlString);
                            inputStream = urlLivro.openStream();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        // Ler os bytes da imagem em um array de bytes
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int bytesRead;

                        while (true) {
                            try {
                                if ((bytesRead = inputStream.read(buffer)) == -1) break;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            outputStream.write(buffer, 0, bytesRead);
                        }

                        // Armazenar os bytes da imagem em um array
                        byte[] imageBytes = outputStream.toByteArray();

                        textoPreco = textoPreco.replace(',', '.');
                        double preco = Double.parseDouble(textoPreco);

                        try {
                            servidorReadEasy.atualizarLivro(getLivroSelecionado(),titulo, autor, preco, fornecedor, imageBytes, urlLivro);
                        }  catch (LivroExistenteException e) {
                            excecaoLevantada = true;
                            alertErro.setTitle("Erro");
                            alertErro.setHeaderText("Operação inválida!");
                            alertErro.setContentText("você tentou editar um livro pra um nome de um livro já existente no catálogo.");
                            alertErro.showAndWait();

                        }
                        catch (ValorInvalidoException e) {
                            excecaoLevantada = true;
                            alertErro.setTitle("Erro");
                            alertErro.setHeaderText("Operação inválida!");
                            alertErro.setContentText("Você digitou um preço negativo, digite um valor maior ou igual a 0 para continuar");
                            alertErro.showAndWait();
                        }
                        if (!excecaoLevantada){
                            tvCatalogoLivros.getItems().clear();
                            inicializarTabela();

                            alertErro.setAlertType(Alert.AlertType.INFORMATION);
                            alertErro.setTitle("Mensagem");
                            alertErro.setHeaderText("Sucesso!");
                            alertErro.setContentText("Livro editado com êxito.");
                            alertErro.showAndWait();

                            limparCampos();
                            atualizarComboBoxLivros = true;
                        }
                    }
                }
                else {
                    alertErro.setTitle("Erro!");
                    alertErro.setHeaderText("Operação inválida!");
                    alertErro.setContentText("Selecione um item da tabela ao lado e preencha o campo de preço com um número");
                    alertErro.showAndWait();
                }
            }
            else {
                alert.close();
            }
        });
    }

    @FXML
    public void btnAdicionarGenero(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();

        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja realmente adicionar o gênero?");
        alert.setContentText("Escolha uma opção.");

        ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(simButton, naoButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                alert.close();
                Alert alertErro = new Alert(Alert.AlertType.ERROR);

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
                            alertErro.setTitle("Erro!");
                            alertErro.setHeaderText("Você selecionou um gênero já existente no livro.");
                            alertErro.setContentText("Selecione um gênero novo no livro para continuar.");
                            alertErro.showAndWait();
                        }
                        if(!exceptionLevantada){
                            lvGenerosDoLivro.getItems().add(generoSelecionado);
                            alertErro.setAlertType(Alert.AlertType.INFORMATION);
                            alertErro.setTitle("Mensagem");
                            alertErro.setHeaderText("Sucesso!");
                            alertErro.setContentText("Gênero adicionado com êxito");
                            alertErro.showAndWait();
                        }
                    }
                    else{
                        alertErro.setTitle("Erro!");
                        alertErro.setHeaderText("Você não selecionou nenhum gênero para adicionar");
                        alertErro.setContentText("Selecione algum gênero para continuar.");
                        alertErro.showAndWait();
                    }
                }
                else{
                    alertErro.setTitle("Erro!");
                    alertErro.setHeaderText("Você não selecionou nenhum Livro!");
                    alertErro.setContentText("Selecione algum livro para continuar.");
                    alertErro.showAndWait();
                }
            }
            else {
                alert.close();
            }
        });
    }

    @FXML
    public void btnRemoverGenero(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();

        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja realmente remover o gênero?");
        alert.setContentText("Escolha uma opção.");

        ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(simButton, naoButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                alert.close();
                Alert alertErro = new Alert(Alert.AlertType.ERROR);

                Genero generoSelecionado = lvGenerosDoLivro.getSelectionModel().getSelectedItem();
                String tituloLivro = cbLivros.getValue();
                Livro livroSelecionado = servidorReadEasy.buscarLivroPorNome(tituloLivro);

                if(generoSelecionado != null && generoSelecionado.equals(livroSelecionado.getGeneros().get(0))){
                    atualizarComboBoxGenero = true;
                }

                if(livroSelecionado != null){
                    if (generoSelecionado != null) {

                        boolean exceptionLevantada = false;

                        try {
                            servidorReadEasy.removerGenero(livroSelecionado, generoSelecionado);
                        } catch (GeneroNaoExistenteException e) {
                            exceptionLevantada = true;
                            alertErro.setTitle("Erro!");
                            alertErro.setHeaderText("Você não selecionou um gênero já existente no livro.");
                            alertErro.setContentText("Selecione um gênero novo no livro para continuar.");
                            alertErro.showAndWait();
                        } catch (LivroSemGeneroException e){
                            exceptionLevantada = true;
                            alertErro.setTitle("Erro!");
                            alertErro.setHeaderText("Remoção de gênero inválida");
                            alertErro.setContentText("O Livro deve conter ao menos 1 gênero.");
                            alertErro.showAndWait();
                        }
                        if(!exceptionLevantada){
                            lvGenerosDoLivro.getItems().remove(generoSelecionado);
                            alertErro.setAlertType(Alert.AlertType.INFORMATION);
                            alertErro.setTitle("Mensagem");
                            alertErro.setHeaderText("Sucesso");
                            alertErro.setContentText("Gênero removido com êxito");
                            alertErro.showAndWait();
                        }
                    }
                    else{
                        alertErro.setTitle("Erro!");
                        alertErro.setHeaderText("Você não selecionou nenhum gênero para remover");
                        alertErro.setContentText("Selecione algum gênero para continuar.");
                        alertErro.showAndWait();
                    }
                }
                else{
                    alertErro.setTitle("Erro!");
                    alertErro.setHeaderText("Você não selecionou nenhum Livro!");
                    alertErro.setContentText("Selecione algum livro para continuar.");
                    alertErro.showAndWait();
                }
            }
            else {
                alert.close();
            }
        });
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

            ByteArrayInputStream inputStream = new ByteArrayInputStream(livroSelecionado.getCapaDoLivro());
            Image image = new Image(inputStream);
            ivCapaDoLivro.setImage(image);

            String urlLivro = String.valueOf(livroSelecionado.getUrlLivro());
            tfURLCapaDoLivro.setText(urlLivro);

            setLivroSelecionado(livroSelecionado);
        }
    }

    @FXML
    private void limparCampos() {
        tfTitulo.clear();
        tfAutor.clear();
        tfPreco.clear();
        tfURLCapaDoLivro.clear();
        cbFornecedor.getSelectionModel().clearSelection();
        cbGenero.getSelectionModel().clearSelection();
        cbLivros.getSelectionModel().clearSelection();
        lvTodosOsGeneros.getSelectionModel().clearSelection();
        lvGenerosDoLivro.getSelectionModel().clearSelection();
        ivCapaDoLivro.setImage(null);
    }

    private void limparComboBox() {
        cbGenero.getItems().clear();
        cbLivros.getItems().clear();
        cbFornecedor.getItems().clear();
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



    public Livro getLivroSelecionado() {
        return livroSelecionado;
    }

    public void setLivroSelecionado(Livro livroSelecionado) {
        this.livroSelecionado = livroSelecionado;
    }
}