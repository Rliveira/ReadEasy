package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.EstoqueInsuficienteException;
import br.ufrpe.readeasy.exceptions.QuantidadeInvalidaException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
    private Label lblPreco;

    @FXML
    private Button btnPagar;

    @FXML
    private Button btnRemoverDoCarrinho;

    @FXML
    private GridPane gpCatalogoLivraria;

    @FXML
    private TextField tfPesquisar;

    private Usuario usuarioLogado;
    private List<Promocao> promocoes;
    private List<Endereco> enderecosUsuario;
    private List<Livro> listaDeLivrosDoCatalogo = new ArrayList<>();
    private List<LivroVendido> carrinho = new ArrayList<>();
    private List<VBox> cartoesLivroCatalogo = new ArrayList<>();
    private List<CartaoLivroController> controladores = new ArrayList<>();

    //Métodos de troca de tela:
    @FXML
    private void trocarTelaHistoricoCliente(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("clienteMinhasCompras.fxml", "ReadEasy - Histórico");
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
    public void initialize(){
        setUsuarioLogado(SessaoUsuario.getUsuarioLogado());
        setarListaDeLivrosNoCatalogo();
        inicializarTabelaCarrinho();
        inicializarCbPromocoes();
        inicializarCbEndereco();
    }

    @FXML
    private void filtrarCartoesLivroNoGridPane() {
        String termoPesquisa = tfPesquisar.getText().toLowerCase(); // Obtém o termo de pesquisa em minúsculas para facilitar a comparação
        List<VBox> cartoesLivroFiltrados = new ArrayList<>();

        if (termoPesquisa.trim().isEmpty()) {
           setarListaDeLivrosNoCatalogo();
        }
        else {
            List<VBox> cartoesFiltrados = new ArrayList<>();

            for (int i = 0; i < cartoesLivroCatalogo.size(); i++) {
                CartaoLivroController cartaoLivroController = controladores.get(i);
                Livro livro = cartaoLivroController.getLivro();

                if (livro != null) {
                    boolean correspondeAoFiltro = livro.getTitulo().toLowerCase().contains(termoPesquisa) ||
                            livro.getAutor().toLowerCase().contains(termoPesquisa) ||
                            String.valueOf(livro.getPreco()).toLowerCase().contains(termoPesquisa) ||
                            livro.getGeneros().stream().anyMatch(genero -> genero.getDescricaoEnum().toLowerCase().contains(termoPesquisa));

                    if (correspondeAoFiltro) {
                        cartoesFiltrados.add(cartoesLivroCatalogo.get(i));
                    }
                }
            }
            gpCatalogoLivraria.getChildren().clear();
            int coluna = 0;
            int linha = 1;

            for(int i = 0; i < cartoesFiltrados.size(); i++){
                if (coluna == 2) {
                    coluna = 0;
                    linha++;
                }

                gpCatalogoLivraria.add(cartoesFiltrados.get(i), coluna++, linha);

                gpCatalogoLivraria.setMinWidth(Region.USE_PREF_SIZE);
                gpCatalogoLivraria.setPrefWidth(600);
                gpCatalogoLivraria.setMaxWidth(Region.USE_PREF_SIZE);

                gpCatalogoLivraria.setMinHeight(Region.USE_COMPUTED_SIZE);
                gpCatalogoLivraria.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gpCatalogoLivraria.setMaxHeight(Region.USE_PREF_SIZE);

                gpCatalogoLivraria.setVgap(10);
                gpCatalogoLivraria.setHgap(10);

                GridPane.setMargin(cartoesFiltrados.get(i), new Insets(10));
            }
        }
    }

    public void setarListaDeLivrosNoCatalogo() {
        listaDeLivrosDoCatalogo.clear();
        listaDeLivrosDoCatalogo.addAll(ServidorReadEasy.getInstance().listarLivrosComEstoqueDisponivel());
        int coluna = 0;
        int linha = 1;

        gpCatalogoLivraria.getChildren().clear();
        cartoesLivroCatalogo.clear();
        controladores.clear();

        try {
            for (int i = 0; i < listaDeLivrosDoCatalogo.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/br/ufrpe/readeasy/cartaoLivro.fxml"));
                VBox vbox = fxmlLoader.load();

                CartaoLivroController cardController = fxmlLoader.getController();
                cardController.setInformacoesDoLivro(listaDeLivrosDoCatalogo.get(i));

                this.cartoesLivroCatalogo.add(vbox);
                this.controladores.add(cardController);

                if (coluna == 2) {
                    coluna = 0;
                    linha++;
                }

                gpCatalogoLivraria.add(vbox, coluna++, linha);

                gpCatalogoLivraria.setMinWidth(Region.USE_PREF_SIZE);
                gpCatalogoLivraria.setPrefWidth(600);
                gpCatalogoLivraria.setMaxWidth(Region.USE_PREF_SIZE);

                gpCatalogoLivraria.setMinHeight(Region.USE_COMPUTED_SIZE);
                gpCatalogoLivraria.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gpCatalogoLivraria.setMaxHeight(Region.USE_PREF_SIZE);

                gpCatalogoLivraria.setVgap(10);
                gpCatalogoLivraria.setHgap(10);

                GridPane.setMargin(vbox, new Insets(10));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAplicarPromocaoACompra() {
        calcularTotalLabel();
    }


    @FXML
    void btnFinalizarACompra() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        if (tbCarrinho.getItems() != null){
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
                        ServidorReadEasy.getInstance().inserirVenda(venda);

                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sucesso!");
                        alert.setHeaderText("Compra realizada com sucesso!");
                        alert.setContentText(null);

                        ButtonType okButton = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
                        alert.getButtonTypes().setAll(okButton);

                        alert.showAndWait().ifPresent(buttonType1 -> {
                            if (buttonType1.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                                alert.close();
                            }
                        });

                        tbCarrinho.getItems().clear();
                        lblPreco.setText("0.00");

                        //atualização do estoque após a compra:
                        for (LivroVendido livroVendido : venda.getLivrosVendidos()){
                            Livro livro = livroVendido.getLivro();
                            try {
                                ServidorReadEasy.getInstance().diminuirQuantidadeEmEstoque(livro, livroVendido.getQuantidade());
                            } catch (EstoqueInsuficienteException | QuantidadeInvalidaException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    else {
                        alert.close();
                    }
                });
            }
            else{
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Erro!");
                alert.setHeaderText("Campo de endereço não selecionado");
                alert.setContentText("Selecione o endereço de entrega");

                ButtonType okButton = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType1 -> {
                    if (buttonType1.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                        alert.close();
                    }
                });
            }
        }
        else{
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText("Nenhum livro adicionado ao carrinho.");
            alert.setContentText("Adicione um livro no carrinho para realizar a compra.");

            ButtonType okButton = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait().ifPresent(buttonType1 -> {
                if (buttonType1.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    alert.close();
                }
            });
        }
    }

    @FXML
    public void inicializarCbPromocoes(){
        cbAplicarPromocao.getItems().clear();
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        setPromocoes(servidorReadEasy.listarTodasPromocoesAtivas());
        List<String> nomesPromocoes = new ArrayList<>();

        for(int i = 0; i < this.promocoes.size(); i++){
            if(calcularQuantidadeDeLivrosNoCarrinho() >= promocoes.get(i).getQtdMinimaDeLivros()) {
                nomesPromocoes.add(promocoes.get(i).getTitulo());
            }
        }
        cbAplicarPromocao.getItems().addAll(nomesPromocoes);
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

                //se a tebela já tiver ele atualiza o carrinho agr com a quantidade de livro atualizada.
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
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText(null);
            alert.setContentText("Tem certeza que deseja excluir o livro selecionado?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //Atualiza a tabela
                tbCarrinho.getItems().remove(livroSelecionado);
                carrinho.remove(livroSelecionado);
                inicializarCbPromocoes();
                calcularTotalLabel();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Nenhum livro selecionado para remoção.");
            alert.showAndWait();
        }
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
}
