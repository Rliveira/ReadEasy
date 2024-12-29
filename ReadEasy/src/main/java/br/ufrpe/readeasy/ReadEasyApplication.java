package br.ufrpe.readeasy;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.gui.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ReadEasyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ScreenManager.setStage(stage);
        ScreenManager screenManager = ScreenManager.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(ReadEasyApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        LoginController loginController = fxmlLoader.getController();
        screenManager.setLoginController(loginController);
        screenManager.setLoginScene(scene);

        //carrega a tela de cadastro no screemManager
        screenManager.carregarTelas(null);

        //Configurações iniciais do stage
        stage.setMinWidth(1200);
        stage.setMinHeight(700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("ReadEasy - Login");

        configurarLayoutCartaoLivro(stage);

        stage.show();
    }

    public void configurarLayoutCartaoLivro(Stage stage){
        // Adiciona listener que monitora constantemente se a tela está maximizada ou não
        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {

            if (stage.isMaximized() && SessaoUsuario.getUsuarioLogado() != null){
                if(SessaoUsuario.getUsuarioLogado() instanceof Cliente &&
                        ScreenManager.getInstance().getClienteCatalogoController() != null) {

                    ClienteCatalogoController clienteCatalogoController = ScreenManager.getInstance().getClienteCatalogoController();
                    clienteCatalogoController.setNumeroDeColunas(3);
                    clienteCatalogoController.btnApresentarCatalogoCompleto();
                }
                else if (SessaoUsuario.getUsuarioLogado() instanceof Fornecedor &&
                        ScreenManager.getInstance().getFornecedorEstoqueController() != null) {

                    FornecedorEstoqueController fornecedorEstoqueController = ScreenManager.getInstance().getFornecedorEstoqueController();
                    fornecedorEstoqueController.setNumeroDeColunas(3);
                    fornecedorEstoqueController.btnApresentarCatalogoCompleto();
                }
            }
            else if(!stage.isMaximized() && SessaoUsuario.getUsuarioLogado() != null){
                if(SessaoUsuario.getUsuarioLogado() instanceof Cliente &&
                        ScreenManager.getInstance().getClienteCatalogoController() != null) {

                    ClienteCatalogoController clienteCatalogoController = ScreenManager.getInstance().getClienteCatalogoController();
                    clienteCatalogoController.setNumeroDeColunas(2);
                    clienteCatalogoController.btnApresentarCatalogoCompleto();
                }
                else if (SessaoUsuario.getUsuarioLogado() instanceof Fornecedor &&
                        ScreenManager.getInstance().getFornecedorEstoqueController() != null) {

                    FornecedorEstoqueController fornecedorEstoqueController = ScreenManager.getInstance().getFornecedorEstoqueController();
                    fornecedorEstoqueController.setNumeroDeColunas(2);
                    fornecedorEstoqueController.btnApresentarCatalogoCompleto();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}