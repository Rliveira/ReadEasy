package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.beans.LivroVendido;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.InputStream;

public class CartaoLivroController {
    Livro livro;

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
        InputStream inputStream;
        this.livro = livro;
        lblnomedolivro.setText(livro.getTitulo());
        lblPreco.setText("R$" + livro.getPreco());

        try {
            inputStream = livro.getCapaDoLivro().openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);   //exceção que não irá acontecer
        }
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
        if(spnSeletor.getValue() != 0){
            LivroVendido livroVendido = new LivroVendido(this.livro, spnSeletor.getValue());
            ScreenManager.getInstance().getClienteCatalogoController().adicionarLivroATabela(livroVendido);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atenção!");
            alert.setHeaderText("Você está tentando adicionar no carrinho um livro com 0 de quantidade.");
            alert.setContentText("Selecione uma quantidade maior do que 0 para continuar com a adição do livro no carrinho.");


            ButtonType okButton = new ButtonType("ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait().ifPresent(buttonType1 -> {
                if (buttonType1.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    alert.close();
                }
            });
        }
    }

    //gets and sets:
    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
}
