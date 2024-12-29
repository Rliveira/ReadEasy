package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.business.Fachada;
import br.ufrpe.readeasy.exceptions.EstoqueInsuficienteException;
import br.ufrpe.readeasy.exceptions.ValorInvalidoException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class ClienteCatalogoController {

    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnCatalogo;
    @FXML
    private Button btnHistorico;
    @FXML
    private Button btnSair;
    @FXML
    private Button btnPagar;
    @FXML
    private Button btnRemoverDoCarrinho;
    @FXML
    private Button btnCatalogoCompleto;

    @FXML
    private ScrollPane spCatalogoDaLivraria;

    @FXML
    private TableView<LivroVendido> tbCarrinho;
    @FXML
    private TableColumn<LivroVendido, String> clnLivro;
    @FXML
    private TableColumn<LivroVendido, Integer> clnQuantidade;
    @FXML
    private TableColumn<LivroVendido, String> clnPrecoUnitario;

    @FXML
    private ComboBox<String> cbAplicarPromocao;
    @FXML
    private ComboBox<String> cbEnderecoEntrega;
    @FXML
    private ComboBox<String> cbGenero;

    @FXML
    private Label lblPreco;

    @FXML
    private GridPane gpCatalogoLivraria;

    @FXML
    private TextField tfPesquisar;

    private List<Genero> generos;
    private Usuario usuarioLogado;
    private List<Promocao> promocoes;
    private List<Endereco> enderecosUsuario;
    private List<Livro> listaDeLivrosDoCatalogo = new ArrayList<>();
    private List<LivroVendido> carrinho = new ArrayList<>();
    private List<VBox> cartoesLivroCatalogo = new ArrayList<>();
    private List<ClienteCartaoLivroController> controladoresCartaoLivro = new ArrayList<>();
    private static ClienteCatalogoController instance;
    private int numeroDeColunas;
    private boolean ignorarInitialize;

    //Construtor:
    public ClienteCatalogoController() {
        if(instance == null){
            instance = this;
            ignorarInitialize = true;
        }
    }

    //Métodos:
    public void initialize(){
        ScreenManager screenManager = ScreenManager.getInstance();

        if (screenManager.getClienteCatalogoController() == null){
            screenManager.setClienteCatalogoController(instance);
        }
        if (!ignorarInitialize){
            setUsuarioLogado(SessaoUsuario.getUsuarioLogado());
            inicializarCatalogoDeLivros();
            inicializarTabelaCarrinho();
            inicializarCbPromocoes();
            inicializarCbEndereco();
            inicializarCbGenero();
        }
    }

    private void inicializarCbGenero(){
        this.generos = Arrays.asList(Genero.values());

        List<String> nomesGenero = new ArrayList<>();

        for (Genero genero : generos){
            nomesGenero.add(genero.getDescricaoEnum());
        }
        cbGenero.getItems().clear();
        cbGenero.getItems().addAll(nomesGenero);
    }

    public void inicializarCatalogoDeLivros() {
        listaDeLivrosDoCatalogo.clear();
        List<Livro> livrosDisponiveis = Fachada.getInstance().listarLivrosComEstoqueDisponivel();
        livrosDisponiveis.sort(Comparator.comparing(Livro::getTitulo));
        listaDeLivrosDoCatalogo.addAll(livrosDisponiveis);

        int coluna = 0;
        int linha = 1;

        gpCatalogoLivraria.getChildren().clear();
        cartoesLivroCatalogo.clear();
        controladoresCartaoLivro.clear();

        try {
            for (int i = 0; i < listaDeLivrosDoCatalogo.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/br/ufrpe/readeasy/clienteCartaoLivro.fxml"));
                VBox cartaoLivro = fxmlLoader.load();

                ClienteCartaoLivroController cardController = fxmlLoader.getController();
                cardController.setInformacoesDoLivro(listaDeLivrosDoCatalogo.get(i));

                this.cartoesLivroCatalogo.add(cartaoLivro);
                this.controladoresCartaoLivro.add(cardController);

                if (coluna == 2) {
                    coluna = 0;
                    linha++;
                }

                adicionarCartaoLivroNoGridPane(cartaoLivro, linha, coluna, 590);
                coluna++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void filtrarCartoesLivroPorTF() {
        cbGenero.getSelectionModel().clearSelection();
        String termoPesquisa = tfPesquisar.getText().toLowerCase();

        if (termoPesquisa.trim().isEmpty()) {
            montarCatatalogo(cartoesLivroCatalogo);
        }
        else {
            List<VBox> cartoesFiltrados = new ArrayList<>();

            for (int i = 0; i < cartoesLivroCatalogo.size(); i++) {
                ClienteCartaoLivroController clienteCartaoLivroController = controladoresCartaoLivro.get(i);
                Livro livro = clienteCartaoLivroController.getLivro();

                if (livro != null) {
                    boolean correspondeAoFiltro = livro.getTitulo().toLowerCase().contains(termoPesquisa) ||
                            livro.getAutor().toLowerCase().contains(termoPesquisa) ||
                            String.valueOf(livro.getPreco()).toLowerCase().contains(termoPesquisa);

                    if (correspondeAoFiltro) {
                        cartoesFiltrados.add(cartoesLivroCatalogo.get(i));
                    }
                }
            }
            montarCatatalogo(cartoesFiltrados);
        }
    }


    @FXML
    public void filtrarLivrosPeloGenero(){
        String generoSeleciodado = cbGenero.getValue();

        if(generoSeleciodado != null && !generoSeleciodado.isEmpty()){
            List<VBox> cartoesFiltrados = new ArrayList<>();

            for (int i = 0; i < cartoesLivroCatalogo.size(); i++) {
                ClienteCartaoLivroController clienteCartaoLivroController = controladoresCartaoLivro.get(i);
                Livro livro = clienteCartaoLivroController.getLivro();

                if (livro != null) {
                    List<Genero> generosDoLivro = livro.getGeneros();
                    boolean correspondeAoFiltro = false;

                    for(Genero genero : generosDoLivro){
                        if (genero.getDescricaoEnum().equals(generoSeleciodado)){
                            correspondeAoFiltro = true;
                        }
                    }

                    if (correspondeAoFiltro) {
                        cartoesFiltrados.add(cartoesLivroCatalogo.get(i));
                    }
                }
            }

            montarCatatalogo(cartoesFiltrados);
        }
    }

    private void montarCatatalogo(List<VBox> cartoesFiltrados){
        gpCatalogoLivraria.getChildren().clear();
        int coluna = 0;
        int linha = 1;
        int prefWidth = numeroDeColunas == 3? 910 : 590;

        for(int i = 0; i < cartoesFiltrados.size(); i++){
            if (coluna == numeroDeColunas) {
                coluna = 0;
                linha++;
            }

            adicionarCartaoLivroNoGridPane(cartoesFiltrados.get(i), linha, coluna, prefWidth);
            coluna++;
        }
    }

    private void adicionarCartaoLivroNoGridPane(VBox cartaoLivro, int linha, int coluna, int prefWidth){
        gpCatalogoLivraria.add(cartaoLivro, coluna, linha);

        gpCatalogoLivraria.setMinWidth(Region.USE_PREF_SIZE);
        gpCatalogoLivraria.setPrefWidth(prefWidth);
        gpCatalogoLivraria.setMaxWidth(Region.USE_PREF_SIZE);

        gpCatalogoLivraria.setMinHeight(Region.USE_COMPUTED_SIZE);
        gpCatalogoLivraria.setPrefHeight(Region.USE_COMPUTED_SIZE);
        gpCatalogoLivraria.setMaxHeight(Region.USE_PREF_SIZE);

        gpCatalogoLivraria.setVgap(10);
        gpCatalogoLivraria.setHgap(10);

        GridPane.setMargin(cartaoLivro, new Insets(10));
    }

    @FXML
    void btnFinalizarACompra() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert alertErro = new Alert(Alert.AlertType.ERROR);

        if (!tbCarrinho.getItems().isEmpty()){
            if(cbEnderecoEntrega.getValue() != null){
                alert.setTitle("Confirmação");
                alert.setHeaderText("Deseja realmente finalizar a compra?");
                alert.setContentText("Escolha uma opção.");

                ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
                ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(simButton, naoButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {

                        Cliente cliente = (Cliente) usuarioLogado;
                        Endereco enderecoDeEntrega = procurarEnderecoPelaRua(cbEnderecoEntrega.getValue());
                        String nomePromocao = cbAplicarPromocao.getValue();
                        Promocao promocao = procurarPromocaoPeloNome(nomePromocao);
                        Venda venda = new Venda(cliente, LocalDateTime.now(), enderecoDeEntrega, promocao);

                        for (int i = 0; i < tbCarrinho.getItems().size(); i++){
                            Livro livro = tbCarrinho.getItems().get(i).getLivro();
                            int quantidade = tbCarrinho.getItems().get(i).getQuantidade();
                            venda.adicionarLivro(livro, quantidade);
                        }
                        Fachada.getInstance().inserirVenda(venda);

                        alertErro.setAlertType(Alert.AlertType.INFORMATION);
                        alertErro.setTitle("Sucesso!");
                        alertErro.setHeaderText("Compra realizada com sucesso!");
                        alertErro.setContentText(null);
                        alertErro.showAndWait();

                        tbCarrinho.getItems().clear();
                        lblPreco.setText("0.00");

                        //atualização do estoque após a compra:
                        for (LivroVendido livroVendido : venda.getLivrosVendidos()){
                            Livro livro = livroVendido.getLivro();
                            try {
                                Fachada.getInstance().diminuirQuantidadeEmEstoque(livro, livroVendido.getQuantidade());
                            } catch (EstoqueInsuficienteException | ValorInvalidoException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    else {
                        alert.close();
                    }
                });
            }
            else{
                alertErro.setTitle("Erro!");
                alertErro.setHeaderText("Campo de endereço não selecionado");
                alertErro.setContentText("Selecione o endereço de entrega");
                alertErro.showAndWait();
            }
        }
        else{
            alertErro.setTitle("Erro!");
            alertErro.setHeaderText("Nenhum livro adicionado ao carrinho.");
            alertErro.setContentText("Adicione um livro no carrinho para realizar a compra.");
            alertErro.showAndWait();
        }
    }

    @FXML
    public void inicializarCbPromocoes(){
        boolean temPromocaoSelecionada = false;
        String promocaoSelecionada = "";

        //Se alguma promoção já foi anteriormente selecionada:
        if(cbAplicarPromocao.getValue() != null){
            temPromocaoSelecionada = true;
            promocaoSelecionada = cbAplicarPromocao.getValue();
        }

        //Limpa o conteúdo anteriormente preenchido do combobox,
        //caso tenha, preenche novamente com conteúdo novo.
        cbAplicarPromocao.getItems().clear();
        Fachada fachada = Fachada.getInstance();
        setPromocoes(fachada.listarTodasPromocoesAtivas());
        List<String> nomesPromocoes = new ArrayList<>();

        for(int i = 0; i < this.promocoes.size(); i++){
            if(calcularQuantidadeDeLivrosNoCarrinho() >= promocoes.get(i).getQtdMinimaDeLivros()) {
                nomesPromocoes.add(promocoes.get(i).getTitulo());
            }
        }
        cbAplicarPromocao.getItems().addAll(nomesPromocoes);

        //seleciona novamente a promoção que já havia sido selecionada pelo usuário
        if(temPromocaoSelecionada){

            if(cbAplicarPromocao.getItems() != null && cbAplicarPromocao.getItems().contains(promocaoSelecionada)){
                cbAplicarPromocao.setValue(promocaoSelecionada);
            }
        }
    }

    private int calcularQuantidadeDeLivrosNoCarrinho(){
        int quantidade = 0;

        for (int i = 0; i < tbCarrinho.getItems().size(); i++){
            quantidade += tbCarrinho.getItems().get(i).getQuantidade();
        }
        return quantidade;
    }

    private Promocao procurarPromocaoPeloNome(String nomePromocao){
        Promocao promocaoSelecionada = null;
        boolean achou = false;

        for (int i = 0; i < promocoes.size() && !achou; i++){
            if (promocoes.get(i).getTitulo().equals(nomePromocao)){
                promocaoSelecionada = promocoes.get(i);
                achou = true;
            }
        }
        return promocaoSelecionada;
    }

    @FXML
    public void inicializarCbEndereco(){
        cbEnderecoEntrega.getItems().clear();
        List<Endereco> listaEnderecos = new ArrayList<>();
        List<String> nomesEndereco = new ArrayList<>();

        Usuario usuario = SessaoUsuario.getUsuarioLogado();
        Cliente cliente;

        if(usuario instanceof Cliente){
            cliente = (Cliente) usuario;
            listaEnderecos = cliente.getEnderecosentrega();
        }

        if (!listaEnderecos.isEmpty()){
            setEnderecosUsuario(listaEnderecos);
        }

        for (Endereco endereco : listaEnderecos){
            nomesEndereco.add(endereco.getRua());
        }
      cbEnderecoEntrega.getItems().addAll(nomesEndereco);
    }

    @FXML
    private Endereco procurarEnderecoPelaRua (String rua){
        Endereco enderecoSelecionado = null;
        boolean achou = false;

        for (int i = 0; i < enderecosUsuario.size() && !achou; i++){
            if (enderecosUsuario.get(i).getRua().equals(rua)){
                enderecoSelecionado = enderecosUsuario.get(i);
                achou = true;
            }
        }
        return enderecoSelecionado;
    }

    @FXML
    public void inicializarTabelaCarrinho() {
        tbCarrinho.getItems().clear();

        clnLivro.setCellValueFactory(cellData -> {
            LivroVendido livro = cellData.getValue();
            String titulo = livro.getLivro().getTitulo();
            return new SimpleStringProperty(titulo);
        });

        clnQuantidade.setCellValueFactory(cellData -> {
            LivroVendido livro = cellData.getValue();
            int quantidade = livro.getQuantidade();
            return new SimpleIntegerProperty(quantidade).asObject();
        });

        clnPrecoUnitario.setCellValueFactory(cellData -> {
            LivroVendido livro = cellData.getValue();
            double preco = livro.getLivro().getPreco();
            return new SimpleObjectProperty<>(String.format("%.2f", preco));
        });
    }

    @FXML
    public void adicionarLivroATabela(LivroVendido livroVendido){
        boolean achou = false;

        //loop para verificar se a tabela já contém o livro:
        for(int i = 0; i < tbCarrinho.getItems().size() && !achou; i++){
            Livro livro = tbCarrinho.getItems().get(i).getLivro();

            if (livro.equals(livroVendido.getLivro())){
                int quantidadeAtual = tbCarrinho.getItems().get(i).getQuantidade() + livroVendido.getQuantidade();
                tbCarrinho.getItems().get(i).setQuantidade(quantidadeAtual);
                achou = true;

                //se a tebela já tiver o livro ele atualiza o carrinho com a quantidade de livro atualizada.
                tbCarrinho.getItems().clear();
                tbCarrinho.getItems().addAll(carrinho);
            }
        }

        //adiciona o livro novo no carrinho.
        if (!achou){
            tbCarrinho.getItems().add(livroVendido);
            carrinho.add(livroVendido);
        }
        calcularTotalLabel();
    }

    @FXML
    void btnRemoverDoCarrinho() {
        LivroVendido livroSelecionado = tbCarrinho.getSelectionModel().getSelectedItem();

        if(livroSelecionado != null){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Deseja realmente remover o livro do carrinho?");
            alert.setContentText("Escolha uma opção.");

            ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
            ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(simButton, naoButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                    alert.close();

                    tbCarrinho.getItems().remove(livroSelecionado);
                    carrinho.remove(livroSelecionado);
                    inicializarCbPromocoes();
                    calcularTotalLabel();
                }
                else {
                    alert.close();
                }
            });
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Nenhum livro selecionado para remoção.");
            alert.showAndWait();
        }
    }

    @FXML
    public void btnApresentarCatalogoCompleto(){
        cbGenero.getSelectionModel().clearSelection();
        tfPesquisar.clear();
        montarCatatalogo(cartoesLivroCatalogo);
    }

    @FXML
    public void calcularTotalLabel() {
        double total = 0;

        for (LivroVendido livro : tbCarrinho.getItems()) {
            int quantidade = livro.getQuantidade();
            double precoUnitario = livro.getLivro().getPreco();

            if (cbAplicarPromocao.getSelectionModel().getSelectedItem() != null) {
                String nomePromocao = cbAplicarPromocao.getSelectionModel().getSelectedItem();
                Promocao promocaoSelecionada = procurarPromocaoPeloNome(nomePromocao);

                if (promocaoSelecionada != null) {
                    int porcentagemDesconto = promocaoSelecionada.getPorcentagemDeDesconto();
                    double desconto = porcentagemDesconto / 100.0;
                    precoUnitario -= (precoUnitario * desconto);
                }
            }

            total += quantidade * precoUnitario;
        }

        lblPreco.setText(String.format("R$ %.2f", total));
    }

    public void mostrarlegendaBtnCatalogo() {
        Tooltip tooltip = new Tooltip("Voltar ao catálogo completo");
        Tooltip.install(btnCatalogoCompleto, tooltip);
    }

    //Gets and sets:
    public List<Promocao> getPromocoes() {
        return promocoes;
    }

    public void setPromocoes(List<Promocao> promocoes) {
        this.promocoes = promocoes;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public List<Endereco> getEnderecosUsuario() {
        return enderecosUsuario;
    }

    public void setEnderecosUsuario(List<Endereco> enderecosUsuario) {
        this.enderecosUsuario = enderecosUsuario;
    }

    public List<LivroVendido> getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(List<LivroVendido> carrinho) {
        this.carrinho = carrinho;
    }

    public static ClienteCatalogoController getInstance() {
        return instance;
    }

    public int getNumeroDeColunas() {
        return numeroDeColunas;
    }

    public void setNumeroDeColunas(int numeroDeColunas) {
        this.numeroDeColunas = numeroDeColunas;
    }

    public void setIgnorarInitialize(boolean ignorarInitialize) {
        this.ignorarInitialize = ignorarInitialize;
    }
}
