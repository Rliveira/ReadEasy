package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Endereco;
import br.ufrpe.readeasy.beans.Funcionario;
import br.ufrpe.readeasy.beans.Usuario;
import br.ufrpe.readeasy.business.Fachada;
import br.ufrpe.readeasy.exceptions.DataInvalidaException;
import br.ufrpe.readeasy.exceptions.UsuarioExistenteException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdmPerfilController {

    @FXML
    private Button btnEditarPerfil;

    @FXML
    private Button btnEstoque;

    @FXML
    private Button btnHistorico;

    @FXML
    private Button btnLivros;

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnPromocoes;

    @FXML
    private Button btnRelatorios;

    @FXML
    private Button btnSair;

    @FXML
    private Button btnUsuarios;

    @FXML
    private DatePicker dtPckData;

    @FXML
    private Label lblBairro;

    @FXML
    private Label lblCPF;

    @FXML
    private Label lblCep;

    @FXML
    private Label lblCidade;

    @FXML
    private Label lblData;

    @FXML
    private Label lblEstado;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblRua;

    @FXML
    private Label lblTelefone;

    @FXML
    private Label lblUsuario;

    @FXML
    private TextField txtFBairro;

    @FXML
    private TextField txtFCep;

    @FXML
    private TextField txtFCidade;

    @FXML
    private TextField txtFCpf;

    @FXML
    private TextField txtFNome;

    @FXML
    private TextField txtFRua;

    @FXML
    private TextField txtFSenha;

    @FXML
    private TextField txtFTelefone;

    @FXML
    private TextField txtFusuario;

    @FXML
    private TextField txtFEstado;

    private Usuario usuarioLogado; // Usuário que está logado no momento
    private static AdmPerfilController instance;
    private boolean ingnorarInitialize;

    public AdmPerfilController() {
        if(instance == null){
            instance = this;
            ingnorarInitialize = true;
        }
    }

    public void initialize() {
        ScreenManager screenManager = ScreenManager.getInstance();

        if(screenManager.getAdmPerfilController() == null){
            screenManager.setAdmPerfilController(instance);
        }
        if(!ingnorarInitialize){
            this.setUsuarioLogado(SessaoUsuario.getUsuarioLogado());
            this.atualizarLabels();
        }
    }

    private void atualizarLabels() {
        if (usuarioLogado != null) {
            lblNome.setText(usuarioLogado.getNome());
            lblCPF.setText(usuarioLogado.getCpf());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            lblData.setText(usuarioLogado.getDataNascimento().format(formatter));
            lblUsuario.setText(usuarioLogado.getLogin());
            lblRua.setText(usuarioLogado.getEndereco().getRua());
            lblBairro.setText(usuarioLogado.getEndereco().getBairro());
            lblCidade.setText(usuarioLogado.getEndereco().getCidade());
            lblEstado.setText(usuarioLogado.getEndereco().getEstado());
            lblCep.setText(String.valueOf(usuarioLogado.getEndereco().getCep()));
            lblTelefone.setText(usuarioLogado.getTelefone());
        }
    }

    @FXML
    protected void onBtnEditarPerfilClick() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja realmente editar o perfil?");
        alert.setContentText("Escolha uma opção.");

        ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(simButton, naoButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                alert.close();
                Alert alertErro = new Alert(Alert.AlertType.ERROR);

                String nome = txtFNome.getText();
                String cpf = txtFCpf.getText();
                String usuario = txtFusuario.getText();
                String senha = txtFSenha.getText();
                String rua = txtFRua.getText();
                String bairro = txtFBairro.getText();
                String cidade = txtFCidade.getText();
                String estado = txtFEstado.getText();
                String cepString = txtFCep.getText();
                String telefone = txtFTelefone.getText();
                LocalDate dataNascimento = dtPckData.getValue();

                if (nome.isEmpty() || cpf.isEmpty() || usuario.isEmpty() || telefone.isEmpty() || rua.isEmpty() || bairro.isEmpty()
                        || cidade.isEmpty() || estado == null || senha.isEmpty() || cepString.isEmpty() || dataNascimento == null) {
                    alertErro.setTitle("Erro");
                    alertErro.setHeaderText("Campos não preenchidos.");
                    alertErro.setContentText("Certifique de preencher todos os campos para continuar.");
                    alertErro.showAndWait();
                }
                else{
                    if (!validarInputTf(cepString) || !validarInputTf(telefone) || !validarInputTf(cpf)) {
                        alertErro.setTitle("Erro");
                        alertErro.setHeaderText("Campo de telefone, CEP ou CPF apresenta letras ou caracteres especiais");
                        alertErro.setContentText("Digite apenas números para continuar");
                        alertErro.showAndWait();

                    }
                    else if (!validarQuantidadeDeCaracteres("cpf", cpf) ||
                            !validarQuantidadeDeCaracteres("telefone", telefone) ||
                            !validarQuantidadeDeCaracteres("cepString", cepString)) {

                        alertErro.setTitle("Erro");
                        alertErro.setHeaderText("Campos de telefone, CEP ou CPF apresentam uma quantidade de dígitos fora do padrão.");
                        alertErro.setContentText("Certifique de digitar 11 dígitos para CPF e 8 dígitos para CEP para continuar.");
                        alertErro.showAndWait();
                    }
                    else{
                        int cep = Integer.parseInt(cepString);
                        Endereco endereco = new Endereco(cep, rua, bairro, cidade, estado);
                        try {
                            Funcionario funcionario = (Funcionario) this.usuarioLogado;
                            Fachada.getInstance().atualizarFuncionario(funcionario, nome, cpf, dataNascimento,
                                    usuario, senha, endereco, telefone, true, funcionario.getAdmResponsavel());
                            this.atualizarLabels();

                            alertErro.setAlertType(Alert.AlertType.INFORMATION);
                            alertErro.setTitle("Atualização de perfil");
                            alertErro.setHeaderText(null);
                            alertErro.setContentText("Perfil atualizado com sucesso!");
                            alertErro.showAndWait();
                            limparCampos();
                        } catch (UsuarioExistenteException e) {
                            alertErro.setTitle("Erro");
                            alertErro.setHeaderText("Usuário já existente");
                            alertErro.setContentText(e.getMessage());
                            alertErro.showAndWait();
                        } catch (DataInvalidaException e) {
                            alertErro.setTitle("Erro");
                            alertErro.setHeaderText("Data inválida");
                            alertErro.setContentText(e.getMessage());
                            alertErro.showAndWait();
                        }
                    }
                }
            }
            else {
                alert.close();
            }
        });
    }

    @FXML
    public void copiarDadosPessoais(){
        txtFNome.setText(usuarioLogado.getNome());
        txtFCpf.setText(usuarioLogado.getCpf());
        txtFusuario.setText(usuarioLogado.getLogin());
        txtFSenha.setText(usuarioLogado.getSenha());
        txtFTelefone.setText(usuarioLogado.getTelefone());
        dtPckData.setValue(usuarioLogado.getDataNascimento());
        txtFCep.setText(String.valueOf(usuarioLogado.getEndereco().getCep()));
        txtFBairro.setText(usuarioLogado.getEndereco().getBairro());
        txtFRua.setText(usuarioLogado.getEndereco().getRua());
        txtFCidade.setText(usuarioLogado.getEndereco().getCidade());
        txtFEstado.setText(usuarioLogado.getEndereco().getEstado());
    }

    @FXML
    public void limparCampos(){
        txtFNome.clear();
        txtFCpf.clear();
        txtFusuario.clear();
        txtFSenha.clear();
        txtFTelefone.clear();
        dtPckData.setValue(null);
        txtFCep.clear();
        txtFBairro.clear();
        txtFRua.clear();
        txtFCidade.clear();
        txtFEstado.clear();
    }

    private boolean validarQuantidadeDeCaracteres(String tipoDeValidacao, String input){
        boolean inputDoTamanhoCorreto = true;

        switch (tipoDeValidacao.toLowerCase()){
            case  "cpf", "telefone":
                if(input.length() != 11){
                    inputDoTamanhoCorreto = false;
                }
                break;

            case "cep":
                if(input.length() != 8){
                    inputDoTamanhoCorreto = false;
                }
                break;
        }
        return inputDoTamanhoCorreto;
    }

    private boolean validarInputTf(String inputUsuario) {
        boolean inputDigitadoCorretamente = true;

        try {
            long input = Long.parseLong(inputUsuario);
        } catch (NumberFormatException e) {
            inputDigitadoCorretamente = false;
        }

        return inputDigitadoCorretamente;
    }

    //GETs and SETs:
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public static AdmPerfilController getInstance() {
        return instance;
    }

    public void setIgnorarInitialize(boolean ignorarInitialize) {
        this.ingnorarInitialize = ignorarInitialize;
    }
}