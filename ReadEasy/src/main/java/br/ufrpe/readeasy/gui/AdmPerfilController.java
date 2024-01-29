package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Endereco;
import br.ufrpe.readeasy.beans.Usuario;
import br.ufrpe.readeasy.business.ControladorUsuario;
import br.ufrpe.readeasy.exceptions.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdmPerfilController implements Initializable {

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
    private Button btnPromoções;

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
    private Label lblSenha;

    @FXML
    private Label lblTelefone;

    @FXML
    private Label lblTipo;

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
    private TextField txtFEstado;

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

    private Usuario usuario; // Usuário que está logado no momento

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        // Atualizar os labels com as informações do usuário
        atualizarLabels();
    }

    private void atualizarLabels() {
        if (usuario != null) {
            lblNome.setText(usuario.getNome());
            lblCPF.setText(usuario.getCpf());
            lblData.setText(usuario.getDataNascimento().toString());
            lblUsuario.setText(usuario.getLogin());
            lblSenha.setText(usuario.getSenha());
            lblRua.setText(usuario.getEndereco().getRua());
            lblBairro.setText(usuario.getEndereco().getBairro());
            lblCidade.setText(usuario.getEndereco().getCidade());
            lblEstado.setText(usuario.getEndereco().getEstado());
            lblCep.setText(String.valueOf(usuario.getEndereco().getCep()));
            lblTelefone.setText(usuario.getTelefone());
        }
    }

    protected void onBtnEditarPerfilClick() {
        String nome = txtFNome.getText();
        String cpf = txtFCpf.getText();
        String usuario = txtFusuario.getText();
        String senha = txtFSenha.getText();
        String rua = txtFRua.getText();
        String bairro = txtFBairro.getText();
        String cidade = txtFCidade.getText();
        String estado = txtFEstado.getText();
        int cep = Integer.parseInt(txtFCep.getText());
        String telefone = txtFTelefone.getText();
        LocalDate dataNascimento = dtPckData.getValue();

        Endereco endereco = new Endereco(cep, rua, bairro, cidade, estado);
        try {
            ControladorUsuario.getInstance().atualizarFuncionario(this.usuario, nome, cpf, dataNascimento,
                    usuario, senha, endereco, telefone, true, null);
        } catch (TipoUsuarioInvalidoException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Tipo de usuário inválido");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (UsuarioExistenteException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Usuário já existente");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (DataInvalidaException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Data inválida");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (UsuarioInexistenteException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Usuário inexistente");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (UsuarioNuloException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Usuário nulo");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.atualizarLabels();
    }
}
