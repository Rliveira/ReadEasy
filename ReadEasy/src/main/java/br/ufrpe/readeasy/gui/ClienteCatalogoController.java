package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.EstoqueInsuficienteException;
import br.ufrpe.readeasy.exceptions.QuantidadeInvalidaException;
import br.ufrpe.readeasy.exceptions.UsuarioNuloException;
import br.ufrpe.readeasy.exceptions.VendaInvalidaException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

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

    Usuario usuarioLogado;
    List<Promocao> promocoes;
    boolean precisaAtualizarCatalogo = false;
    List<Endereco> enderecosUsuario;
    private List<Livro> listaDeLivrosDoCatalogo = new ArrayList<>();

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
        if (isPrecisaAtualizarCatalogo()){
            setUsuarioLogado(SessaoUsuario.getUsuarioLogado());
            setarListaDeLivrosNoCatalogo();
            inicializarTabelaCarrinho();
            inicializarCbPromocoes();
            inicializarCbEndereco();
        }
    }

    public void setarListaDeLivrosNoCatalogo() {
        listaDeLivrosDoCatalogo.clear();
        listaDeLivrosDoCatalogo.addAll(ServidorReadEasy.getInstance().listarLivrosComEstoqueDisponivel());
        int coluna = 0;
        int linha = 1;

        try {
            for (int i = 0; i < listaDeLivrosDoCatalogo.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/br/ufrpe/readeasy/cartaoLivro.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CartaoLivroController cardController = fxmlLoader.getController();
                cardController.setInformacoesDoLivro(listaDeLivrosDoCatalogo.get(i));

                if (coluna == 2) {
                    coluna = 0;
                    linha++;
                }

                gpCatalogoLivraria.add(anchorPane, coluna++, linha);

                gpCatalogoLivraria.setMinWidth(Region.USE_PREF_SIZE);
                gpCatalogoLivraria.setPrefWidth(600);
                gpCatalogoLivraria.setMaxWidth(Region.USE_PREF_SIZE);

                // Set grid height
                gpCatalogoLivraria.setMinHeight(Region.USE_COMPUTED_SIZE);
                gpCatalogoLivraria.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gpCatalogoLivraria.setMaxHeight(Region.USE_PREF_SIZE);

                gpCatalogoLivraria.setVgap(10);
                gpCatalogoLivraria.setHgap(10);

                GridPane.setMargin(anchorPane, new Insets(10));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setPrecisaAtualizarCatalogo(false);
    }

    @FXML
    void btnAplicarPromocaoACompra(ActionEvent event) {
        calcularTotal();
    }

    @FXML
    void cbEscolherEnderecoEntrega(ActionEvent event) {

    }

    @FXML
    void btnFinalizarACompra(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        if (tbCarrinho.getItems() != null && cbEnderecoEntrega.getValue() != null){
            alert.setTitle("Confirmação");
            alert.setHeaderText("Deseja realmente finalizar a compra?");
            alert.setContentText("Escolha uma opção.");

            ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
            ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(simButton, naoButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                    Cliente cliente = (Cliente) usuarioLogado;
                    Venda venda = new Venda(cliente, LocalDateTime.now());
                    int quantidade;

                    for (int i = 0; i < tbCarrinho.getItems().size(); i++){
                        Livro livro = tbCarrinho.getItems().get(i).getLivro();
                        quantidade = tbCarrinho.getItems().get(i).getQuantidade();
                        venda.adicionarLivro(livro, quantidade);
                    }

                    Endereco endereco = procurarEnderecoPelaRua(cbEnderecoEntrega.getValue());
                    venda.setEnderecoEntrega(endereco);

                    try {
                        ServidorReadEasy.getInstance().inserirVenda(venda);
                    } catch (VendaInvalidaException | UsuarioNuloException e) {
                        throw new RuntimeException(e); //Exceção boba que não irá ocorrer
                    }

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
            alert.setHeaderText("Campo de endereço não selecionado" + '\n' +
                    " ou nenhum livro adicoionado ao carrinho.");
            alert.setContentText("Selecione o endereço de entrega" + '\n' +
                    " ou adicione um livro no carrinho para realizar a compra.");
        }
    }

    @FXML
    public void inicializarCbPromocoes(){
        cbAplicarPromocao.getItems().clear();
        ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
        setPromocoes(servidorReadEasy.listarTodasPromocoesAtivas());
        List<String> nomesPromocoes = new ArrayList<>();

        for (Promocao promocao: promocoes){
            nomesPromocoes.add(promocao.getTitulo());
        }

        cbAplicarPromocao.getItems().addAll(nomesPromocoes);
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

        TableView<Endereco> enderecos = ScreenManager.getInstance().getClientePerfilController().getTbvEnderecosCadastrados();
        listaEnderecos.addAll(enderecos.getItems());
        setEnderecosUsuario(listaEnderecos);

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
        tbCarrinho.getItems().add(livroVendido);
        calcularTotal();
    }

    @FXML
    void btnRemoverDoCarrinho(ActionEvent event) {
        LivroVendido livroSelecionado = tbCarrinho.getSelectionModel().getSelectedItem();

        if(livroSelecionado != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText(null);
            alert.setContentText("Tem certeza que deseja excluir o livro selecionado?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                tbCarrinho.getItems().remove(livroSelecionado);
                calcularTotal();
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
    public void calcularTotal() {
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
                setPrecisaAtualizarCatalogo(true);
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

    public boolean isPrecisaAtualizarCatalogo() {
        return precisaAtualizarCatalogo;
    }

    public void setPrecisaAtualizarCatalogo(boolean precisaAtualizarCatalogo) {
        this.precisaAtualizarCatalogo = precisaAtualizarCatalogo;
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
}
