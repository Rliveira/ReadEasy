package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.beans.LivroVendido;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class CartaoLivroController {
    private Livro livro;

    @FXML
    private Label lblnomedolivro;
    @FXML
    private Label lblPreco;

    @FXML
    private Spinner<Integer> spnSeletor;

    @FXML
    private Button btnAdicionar;

    @FXML
    private ImageView imgvLivro;

    public void setInformacoesDoLivro(Livro livro){
        this.livro = livro;
        lblnomedolivro.setText(livro.getTitulo());
        lblPreco.setText("R$" + livro.getPreco());
        ByteArrayInputStream inputStream = new ByteArrayInputStream(livro.getCapaDoLivro());
        Image image = new Image(inputStream);
        imgvLivro.setImage(image);
        inicializarSpinner();
    }

    @FXML
    public void inicializarSpinner(){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, livro.getQuantidade(), 1);
        spnSeletor.setValueFactory(valueFactory);
        spnSeletor.getValueFactory().setValue(0);
    }

    @FXML
    public void btnAdicionar(){
        LivroVendido livroVendido = new LivroVendido(this.livro, spnSeletor.getValue());
        ScreenManager.getInstance().getClienteCatalogoController().adicionarLivroATabela(livroVendido);
        ScreenManager.getInstance().getClienteCatalogoController().inicializarCbPromocoes();

        inicializarSpinner();
    }

    //gets and sets:
    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
}
