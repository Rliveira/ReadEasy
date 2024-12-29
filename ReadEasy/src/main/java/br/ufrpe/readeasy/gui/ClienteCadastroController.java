package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Endereco;
import br.ufrpe.readeasy.business.Fachada;
import br.ufrpe.readeasy.exceptions.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class ClienteCadastroController{

    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnVoltar;

    @FXML
    private DatePicker dtpkDataNascimento;

    @FXML
    private TextField txtFBairro;
    @FXML
    private TextField txtFCEP;
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
    private TextField txtFUsuario;
    @FXML
    private TextField txtfEstado;

    private static ClienteCadastroController instance;

    //Construtor:
    public ClienteCadastroController() {
        if(instance == null){
            instance = this;
        }
    }

    //Métodos:
    @FXML
    protected void onBtnCadastrarClick(){
        Alert alert = new Alert(Alert.AlertType.ERROR);

        String nome = txtFNome.getText();
        String cpf = txtFCpf.getText();
        String usuario = txtFUsuario.getText();
        String senha = txtFSenha.getText();
        String rua = txtFRua.getText();
        String bairro = txtFBairro.getText();
        String cidade = txtFCidade.getText();
        String estado = txtfEstado.getText();
        String cepString = txtFCEP.getText();
        String telefone = txtFTelefone.getText();
        LocalDate dataNascimento = dtpkDataNascimento.getValue();

        if(!cepString.isEmpty() && !telefone.isEmpty() && !cpf.isEmpty()){
            if (!validarInputTf(cepString) || !validarInputTf(telefone) || !validarInputTf(cpf)) {
                alert.setTitle("Erro");
                alert.setHeaderText("Campo de telefone, CEP ou CPF apresenta letras ou caracteres especiais");
                alert.setContentText("Digite apenas números para continuar");
                alert.showAndWait();
            }
            else if (!validarQuantidadeDeCaracteres("cpf", cpf) ||
                    !validarQuantidadeDeCaracteres("telefone", telefone) ||
                    !validarQuantidadeDeCaracteres("cepString", cepString)) {

                alert.setTitle("Erro");
                alert.setHeaderText("Campos de telefone, CEP ou CPF apresentam uma quantidade de dígitos fora do padrão.");
                alert.setContentText("Certifique de digitar 11 dígitos para CPF e 8 dígitos para CEP para continuar.");
                alert.showAndWait();
            }
            else{
                Endereco endereco = new Endereco(Integer.parseInt(cepString), rua, bairro, cidade, estado);
                Cliente cliente = new Cliente(nome, cpf, dataNascimento, usuario, senha, endereco, telefone);

                try {
                    Fachada.getInstance().cadastrarUsuario(cliente);
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cadastro de Cliente");
                    alert.setHeaderText(null);
                    alert.setContentText("Cliente cadastrado com sucesso!");
                    alert.showAndWait();

                    Fachada.getInstance().adicionarEnderecoDeEntrega(cliente, endereco);
                    trocarTelaLogin();

                } catch (MenorDeIdadeException e) {
                    alert.setTitle("Erro no preenchimento de dados");
                    alert.setHeaderText(null);
                    alert.setContentText("O usuário deve ser maior de idade!");
                    alert.showAndWait();
                } catch (DataInvalidaException e) {
                    alert.setTitle("Erro no preenchimento de dados");
                    alert.setHeaderText(null);
                    alert.setContentText("Data inválida!");
                    alert.showAndWait();
                } catch (CampoVazioException e) {
                    alert.setTitle("Erro no preenchimento de dados");
                    alert.setHeaderText(null);
                    alert.setContentText("Preencha todos os campos!");
                    alert.showAndWait();
                } catch (UsuarioExistenteException e) {
                    alert.setTitle("Erro no preenchimento de dados");
                    alert.setHeaderText(null);
                    alert.setContentText("Usuário já cadastrado!");
                    alert.showAndWait();
                } catch (EnderecoExistenteException e) {
                    throw new RuntimeException(e);  //Essa excessão não irá ser levantada nessa parte do código
                }
            }
        }
        else{
            alert.setTitle("Erro");
            alert.setHeaderText("Campo de telefone, CEP ou CPF não preenchidos");
            alert.setContentText("Certifique de preencher os campos para continuar.");
            alert.showAndWait();
        }
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

    @FXML
    public void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.trocartelasPrincipais("login.fxml", "ReadEasy - Login");
    }

    //gets and sets:
    public static ClienteCadastroController getInstance() {
        return instance;
    }
}
