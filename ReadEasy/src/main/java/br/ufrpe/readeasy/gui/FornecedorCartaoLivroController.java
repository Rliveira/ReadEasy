package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Livro;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class FornecedorCartaoLivroController {
    private Livro livro;

    @FXML
    private Label lblnomedolivro;
    @FXML
    private ImageView imgvLivro;

    public void setInformacoesDoLivro(Livro livro){
        this.livro = livro;
        lblnomedolivro.setText(livro.getTitulo());
        ByteArrayInputStream inputStream = new ByteArrayInputStream(livro.getCapaDoLivro());
        Image image = new Image(inputStream);
        imgvLivro.setImage(image);
    }

    @FXML
    public void selecionarLivroNaTabelaEstoque(){
        Livro livro = this.livro;

        ScreenManager screenManager = ScreenManager.getInstance();
        screenManager.getFornecedorEstoqueController().selecionarLivroNaTabela(livro);
    }



    //gets and sets:
    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
}
