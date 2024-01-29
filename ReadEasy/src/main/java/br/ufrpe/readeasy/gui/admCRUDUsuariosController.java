package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Endereco;
import br.ufrpe.readeasy.beans.Funcionario;
import br.ufrpe.readeasy.beans.Usuario;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class admCRUDUsuariosController
{
    public Application app;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnDeletar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnLivros;

    @FXML
    private Button btnEstoque;

    @FXML
    private Button btnUsuarios;

    @FXML
    private Button btnPromoções;

    @FXML
    private Button btnHistorico;

    @FXML
    private Button btnRelatorios;

    @FXML
    private Button btnSair;

    @FXML
    private TextField txtFieldPesquisar;

    @FXML
    private TextField txtFieldNome;

    @FXML
    private TextField txtFieldCPF;

    @FXML
    private TextField txtFieldLogin;

    @FXML
    private TextField txtFieldTelefone;

    @FXML
    private TextField txtFieldADM;

    @FXML
    private TextField txtFieldCEP;

    @FXML
    private TextField txtFieldRua;

    @FXML
    private TextField txtFieldBairro;

    @FXML
    private TextField txtFieldCidade;

    @FXML
    private TextField txtFieldEstado;

    @FXML
    private TextField txtFieldSenha;

    @FXML
    private DatePicker DPDataNascimento;

    @FXML
    private ComboBox CBCargo;

    @FXML
    private TableView TVUsuarios;

    @FXML
    private TableColumn ColNome;

    @FXML
    private TableColumn ColCPF;

    @FXML
    private TableColumn ColNomeUsuario;

    @FXML
    private TableColumn ColDataNascimento;

    @FXML
    private TableColumn ColTelefone;

    @FXML
    private TableColumn ColCargo;

    @FXML
    private TableColumn ColADM;

}