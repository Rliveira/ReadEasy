package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.business.ServidorReadEasy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class ClientePerfilController {

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnCatalogo;

    @FXML
    private Button btnHistorico;

    @FXML
    private Button btnSair;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblCPF;

    @FXML
    private Label lblDataDeNascimento;

    @FXML
    private Label lblLogin;

    @FXML
    private Label lblSenha;

    @FXML
    private Label lblTelefone;

    @FXML
    private TextField tfEditarPerfilNome;

    @FXML
    private TextField tfEditarPerfilCPF;

    @FXML
    private TextField tfEditarPerfilLogin;

    @FXML
    private TextField tfEditarPerfilSenha;

    @FXML
    private TextField tfEditarPerfilTelefone;

    @FXML
    private DatePicker dtpEditarPerfilDataDeNascimento;

    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnCadastrarEndereço;

    @FXML
    private TableView<?> tbvEnderecosCadastrados;

    @FXML
    private TableColumn<?, ?> clnCEP;

    @FXML
    private TableColumn<?, ?> clnRua;

    @FXML
    private TableColumn<?, ?> clnBairro;

    @FXML
    private TableColumn<?, ?> clnCidade;

    @FXML
    private TableColumn<?, ?> clnEstado;

    @FXML
    private Button btnRemoverEndereco;

    @FXML
    private Button btnAlterarEndereco;


    @FXML
    void btnAtualizarPerfil(ActionEvent event) {


    }

    @FXML
    void btnAlterarEndereco(ActionEvent event) {

    }

    @FXML
    void btnCadastrarEndereço(ActionEvent event) {

    }

    @FXML
    void btnRemoverEndereco(ActionEvent event) {

    }

}
