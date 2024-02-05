package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.business.ControladorVenda;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.*;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClienteMinhasComprasController
{
    public Application app;

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnCatalogo;

    @FXML
    private Button btnHistorico;

    @FXML
    private Button btnSair;

    @FXML
    private Button btnPesquisar;

    @FXML
    private DatePicker dpDataInicio;

    @FXML
    private DatePicker dpDataFim;

    @FXML
    private TableView tvTabelaCompras;

    @FXML
    private TableColumn<CompraDTO, String> colTitulo;

    @FXML
    private TableColumn<CompraDTO, String> colAutor;

    @FXML
    private TableColumn<CompraDTO, Integer> colQTD;

    @FXML
    private TableColumn<CompraDTO, Double> colPreco;

    @FXML
    private TableColumn<CompraDTO, LocalDate> colDataCompra;

    @FXML
    public void initialize() throws UsuarioNuloException, VendaInvalidaException {
        colTitulo.setCellValueFactory(cellData ->
        {
            CompraDTO compra = cellData.getValue();
            String titulo = compra.getTituloLivro();
            return new SimpleStringProperty(titulo);
        });

        colAutor.setCellValueFactory(cellData ->
        {
            CompraDTO compra = cellData.getValue();
            String autor = compra.getAutorLivro();
            return new SimpleStringProperty(autor);
        });

        colQTD.setCellValueFactory(cellData ->
        {
            CompraDTO compra = cellData.getValue();
            int qtd = compra.getQuantidade();
            return new SimpleObjectProperty<>(qtd);
        });

        colPreco.setCellValueFactory(cellData ->
        {
            CompraDTO compra = cellData.getValue();
            double preco = compra.getPreco();
            return new SimpleObjectProperty<>(preco);
        });

        colDataCompra.setCellValueFactory(cellData -> {
            CompraDTO compra = cellData.getValue();
            LocalDateTime dataHora = compra.getDataCompra();
            LocalDate data = dataHora.toLocalDate();
            return new SimpleObjectProperty<>(data);
        });

        carregarDadosTabela();

        dpDataFim.setValue(LocalDate.now());

        if(SessaoUsuario.getUsuarioLogado() instanceof Cliente) {

            tvTabelaCompras.setItems(FXCollections.observableArrayList
                    (obterComprasDoCliente((Cliente) SessaoUsuario.getUsuarioLogado())));
        }
    }

    public void onPesquisarClick() throws UsuarioNuloException {
        filtrarPorIntervaloDeTempo();
    }

    public void filtrarPorIntervaloDeTempo() throws UsuarioNuloException
    {
        LocalDate dataInicio = dpDataInicio.getValue();
        LocalDate dataFim = dpDataFim.getValue();

        if (dataInicio != null && dataFim != null)
        {
            ObservableList<CompraDTO> itensTabela = FXCollections.observableArrayList(ServidorReadEasy.getInstance().
                    listarComprasDTO((Cliente) SessaoUsuario.getUsuarioLogado()));
            ObservableList<CompraDTO> itensFiltrados = FXCollections.observableArrayList();

            for (CompraDTO item : itensTabela) {
                LocalDate dataItem = item.getDataCompra().toLocalDate();
                if (!dataItem.isBefore(dataInicio) && !dataItem.isAfter(dataFim)) {
                    itensFiltrados.add(item);
                }
            }
            if(!dpDataInicio.getValue().isBefore(dataFim) && !dpDataFim.getValue().isAfter(dataInicio)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Selecione uma data com um intervalo válido");
            alert.showAndWait();
        }
            tvTabelaCompras.setItems(itensFiltrados);
        } else
        {
            tvTabelaCompras.setItems(FXCollections.observableArrayList(ServidorReadEasy.getInstance()
                    .historicoDeComprasDoCliente((Cliente) SessaoUsuario.getUsuarioLogado())));
        }
    }

    public void carregarDadosTabela() throws VendaInvalidaException, UsuarioNuloException
    {
        Cliente cliente = null;
        if(SessaoUsuario.getUsuarioLogado() instanceof Cliente)
        {
            cliente = (Cliente) SessaoUsuario.getUsuarioLogado();
        }
        else
        {
            return;
        }
        Endereco enderecoCliente = new Endereco(12345, "Rua Cliente", "Bairro Cliente", "Cidade Cliente", "Estado Cliente");

        Fornecedor fornecedor1 = new Fornecedor("Editora ABC", "12345678900", LocalDate.of(1990, 1, 1), "editora_abc", "senha123",
                new Endereco(12345, "Rua Fornecedor 1", "Bairro Fornecedor 1", "Cidade Fornecedor 1", "Estado Fornecedor 1"),
                "987654321", TipoFornecedor.EDITORA);

        Fornecedor fornecedor2 = new Fornecedor("Editora XYZ", "98765432100", LocalDate.of(1995, 5, 5), "editora_xyz", "senha456",
                new Endereco(54321, "Rua Fornecedor 2", "Bairro Fornecedor 2", "Cidade Fornecedor 2", "Estado Fornecedor 2"),
                "123456789", TipoFornecedor.EDITORA);

        Fornecedor fornecedor3 = new Fornecedor("Escritor Independente", "45678912300", LocalDate.of(1985, 10, 10), "escritor_ind", "senha789",
                new Endereco(67890, "Rua Fornecedor 3", "Bairro Fornecedor 3", "Cidade Fornecedor 3", "Estado Fornecedor 3"),
                "987654321", TipoFornecedor.ESCRITOR_INDEPENDENTE);

        Fornecedor fornecedor4 = new Fornecedor("Doador Anônimo", "78912345600", LocalDate.of(1975, 3, 15), "doador_anon", "senhaabc",
                new Endereco(13579, "Rua Fornecedor 4", "Bairro Fornecedor 4", "Cidade Fornecedor 4", "Estado Fornecedor 4"),
                "123456789", TipoFornecedor.DOADOR_ANONIMO);

        Livro livro1 = new Livro("Dom Quixote", "Miguel de Cervantes", 39.90, fornecedor1);
        Livro livro2 = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", 49.90, fornecedor2);
        Livro livro3 = new Livro("Harry Potter e a Pedra Filosofal", "J.K. Rowling", 29.90, fornecedor3);
        Livro livro4 = new Livro("Orgulho e Preconceito", "Jane Austen", 34.90, fornecedor4);
        Livro livro5 = new Livro("1984", "George Orwell", 27.90, fornecedor1);
        Livro livro6 = new Livro("A Revolução dos Bichos", "George Orwell", 22.90, fornecedor2);

        Venda venda1 = new Venda(cliente, LocalDateTime.of(2023, 5, 10, 12, 0));
        Venda venda2 = new Venda(cliente, LocalDateTime.of(2023, 6, 15, 13, 30));
        Venda venda3 = new Venda(cliente, LocalDateTime.of(2023, 7, 20, 15, 45));
        Venda venda4 = new Venda(cliente, LocalDateTime.of(2023, 8, 25, 10, 15));
        Venda venda5 = new Venda(cliente, LocalDateTime.of(2023, 9, 30, 14, 0));
        Venda venda6 = new Venda(cliente, LocalDateTime.of(2023, 10, 5, 16, 20));
        Venda venda7 = new Venda(cliente, LocalDateTime.of(2023, 11, 10, 11, 45));
        Venda venda8 = new Venda(cliente, LocalDateTime.of(2023, 12, 15, 9, 30));
        Venda venda9 = new Venda(cliente, LocalDateTime.of(2024, 1, 20, 12, 15));
        Venda venda10 = new Venda(cliente, LocalDateTime.of(2024, 2, 25, 14, 45));

        venda1.adicionarLivro(livro1, 2);
        venda1.adicionarLivro(livro2, 1);

        venda2.adicionarLivro(livro3, 3);
        venda2.adicionarLivro(livro4, 2);

        venda3.adicionarLivro(livro5, 1);
        venda3.adicionarLivro(livro6, 4);

        venda4.adicionarLivro(livro1, 3);
        venda4.adicionarLivro(livro2, 2);

        venda5.adicionarLivro(livro3, 1);
        venda5.adicionarLivro(livro4, 5);

        venda6.adicionarLivro(livro5, 2);
        venda6.adicionarLivro(livro6, 3);

        venda7.adicionarLivro(livro1, 1);
        venda7.adicionarLivro(livro2, 2);

        venda8.adicionarLivro(livro3, 3);
        venda8.adicionarLivro(livro4, 2);

        venda9.adicionarLivro(livro5, 4);
        venda9.adicionarLivro(livro6, 1);

        venda10.adicionarLivro(livro1, 2);
        venda10.adicionarLivro(livro2, 3);

        ServidorReadEasy.getInstance().inserirVenda(venda1);
        ServidorReadEasy.getInstance().inserirVenda(venda2);
        ServidorReadEasy.getInstance().inserirVenda(venda3);
        ServidorReadEasy.getInstance().inserirVenda(venda4);
        ServidorReadEasy.getInstance().inserirVenda(venda5);
        ServidorReadEasy.getInstance().inserirVenda(venda6);
        ServidorReadEasy.getInstance().inserirVenda(venda7);
        ServidorReadEasy.getInstance().inserirVenda(venda8);
        ServidorReadEasy.getInstance().inserirVenda(venda9);
        ServidorReadEasy.getInstance().inserirVenda(venda10);
    }

    public List<CompraDTO> obterComprasDoCliente(Cliente cliente) throws UsuarioNuloException {
        List<CompraDTO> comprasDoCliente = new ArrayList<>();


        for (Venda venda : ServidorReadEasy.getInstance().historicoDeComprasDoCliente(cliente)) {
            // Itera sobre os livros vendidos em cada venda
            for (LivroVendido livroVendido : venda.getLivrosVendidos()) {
                // Obtém informações do livro vendido
                Livro livro = livroVendido.getLivro();
                String tituloLivro = livro.getTitulo();
                String autorLivro = livro.getAutor();
                int quantidade = livroVendido.getQuantidade();
                double preco = livro.getPreco();
                LocalDateTime dataCompra = venda.getDataEHora();

                // Cria um objeto CompraDTO com as informações e adiciona à lista
                CompraDTO compraDTO = new CompraDTO(tituloLivro, autorLivro, quantidade, preco, dataCompra);
                comprasDoCliente.add(compraDTO);
            }
        }

        return comprasDoCliente;
    }



    //Métodos de troca de tela:
    @FXML
    private void trocarTelaCatalogoCliente(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("clienteCatalogo.fxml", "ReadEasy - Catálogo");
    }

    @FXML
    private void trocarTelaPerfilCliente(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("clientePerfil.fxml", "ReadEasy - Perfil");
    }

    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //outros métodos:
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