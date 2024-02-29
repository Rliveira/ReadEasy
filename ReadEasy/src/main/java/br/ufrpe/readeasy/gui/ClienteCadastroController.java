package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Cliente;
import br.ufrpe.readeasy.beans.Endereco;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    //métodos de troca de tela:
    @FXML
    public void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //Outros métodos:
    @FXML
    protected void onBtnCadastrarClick(){
        String nome = txtFNome.getText();
        String cpf = txtFCpf.getText();
        String usuario = txtFUsuario.getText();
        String senha = txtFSenha.getText();
        String rua = txtFRua.getText();
        String bairro = txtFBairro.getText();
        String cidade = txtFCidade.getText();
        String estado = txtfEstado.getText();
        String cep = txtFCEP.getText();
        String telefone = txtFTelefone.getText();
        LocalDate dataNascimento = dtpkDataNascimento.getValue();

        if (!validarInputTf(cep) || !validarInputTf(telefone) || !validarInputTf(cpf)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Campo de telefone, CEP ou CPF apresenta letras ou caracteres especiais");
            alert.setContentText("Digite apenas números para continuar");
            ButtonType buttonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(buttonType);

            alert.showAndWait().ifPresent(buttonType1 -> {
                if (buttonType1.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    alert.close();
                }
            });
            return;
        }

            Endereco endereco = new Endereco(Integer.parseInt(cep), rua, bairro, cidade, estado);
            Cliente cliente = new Cliente(nome, cpf, dataNascimento, usuario, senha, endereco, telefone);

            try {
                ServidorReadEasy.getInstance().cadastrarUsuario(cliente);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cadastro de Cliente");
                alert.setHeaderText(null);
                alert.setContentText("Cliente cadastrado com sucesso!");
                alert.showAndWait();

                ServidorReadEasy.getInstance().adicionarEnderecoDeEntrega(cliente, endereco);

                ScreenManager sm = ScreenManager.getInstance();
                sm.TrocarTela("Login.fxml", "ReadEasy - Login");

            } catch (MenorDeIdadeException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro no preenchimento de dados");
                alert.setHeaderText(null);
                alert.setContentText("O usuário deve ser maior de idade!");
                alert.showAndWait();
            } catch (DataInvalidaException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro no preenchimento de dados");
                alert.setHeaderText(null);
                alert.setContentText("Data inválida!");
                alert.showAndWait();
            } catch (CampoVazioException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro no preenchimento de dados");
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos!");
                alert.showAndWait();
            } catch (UsuarioExistenteException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro no preenchimento de dados");
                alert.setHeaderText(null);
                alert.setContentText("Usuário já cadastrado!");
                alert.showAndWait();
            } catch (EnderecoExistenteException e) {
                throw new RuntimeException(e);  //Essa excessão não irá ser levantada nessa parte do código
            }

    }

    private boolean validarInputTf(String inputTf) {
        return inputTf.matches("\\d+");
    }
}
