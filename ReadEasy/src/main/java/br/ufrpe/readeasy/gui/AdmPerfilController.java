package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Endereco;
import br.ufrpe.readeasy.beans.Funcionario;
import br.ufrpe.readeasy.beans.Usuario;
import br.ufrpe.readeasy.business.ControladorUsuario;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

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

    private Usuario usuarioLogado; // Usuário que está logado no momento

    //Métodos de troca de tela:
    @FXML
    public void trocarTelaEstoqueAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admEstoque.fxml", "ReadEasy - Estoque");
    }
    @FXML

    public void trocarTelaLivrosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admLivros.fxml", "ReadEasy - Livros");
    }

    @FXML
    public void trocarTelaHistoricoAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admHistoricoComprasEVendas.fxml", "ReadEasy - Histórico");
    }

    @FXML
    public void trocarTelaPromocoesAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admCRUDPromocoes.fxml", "ReadEasy - Promoções");
    }

    @FXML
    public void trocarTelaRelatoriosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admRelatorios.fxml", "ReadEasy - Relatoórios");
    }

    @FXML
    public void trocarTelaUsuariosAdm(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("admCRUDUsuarios.fxml", "ReadEasy - Usuários");
    }

    @FXML
    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    //Outros métodos:
    public void initialize() {
        this.setUsuarioLogado(SessaoUsuario.getUsuarioLogado());
        this.atualizarLabels();
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
        System.out.println(usuarioLogado);
        String nome = txtFNome.getText();
        if (nome.isEmpty()) {
            nome = usuarioLogado.getNome();
        }
        String cpf = txtFCpf.getText();
        if (cpf.isEmpty()) {
            cpf = usuarioLogado.getCpf();
            System.out.println(cpf);
        }
        String usuario = txtFusuario.getText();
        if (usuario.isEmpty()) {
            usuario = usuarioLogado.getLogin();
        }
        String senha = txtFSenha.getText();
        if (senha.isEmpty()) {
            senha = usuarioLogado.getSenha();
        }
        String rua = txtFRua.getText();
        if (rua.isEmpty()) {
            rua = usuarioLogado.getEndereco().getRua();
        }
        String bairro = txtFBairro.getText();
        if (bairro.isEmpty()) {
            bairro = usuarioLogado.getEndereco().getBairro();
        }
        String cidade = txtFCidade.getText();
        if (cidade.isEmpty()) {
            cidade = usuarioLogado.getEndereco().getCidade();
        }
        String estado = txtFEstado.getText();
        if (estado.isEmpty()) {
            estado = usuarioLogado.getEndereco().getEstado();
        }
        String cep = txtFCep.getText();
        if (cep.isEmpty()) {
            cep = String.valueOf(usuarioLogado.getEndereco().getCep());
            System.out.println(cep);
        }
        String telefone = txtFTelefone.getText();
        if (telefone.isEmpty()) {
            telefone = usuarioLogado.getTelefone();
            System.out.println(telefone);
        }
        LocalDate dataNascimento = dtPckData.getValue();
        if (dataNascimento == null) {
            dataNascimento = usuarioLogado.getDataNascimento();
        }

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
        try {
            Funcionario funcionario = (Funcionario) this.usuarioLogado;
            ServidorReadEasy.getInstance().atualizarFuncionario(funcionario, nome, cpf, dataNascimento,
                    usuario, senha, endereco, telefone, true, funcionario.getAdmResponsavel());
            this.atualizarLabels();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atualização de perfil");
            alert.setHeaderText(null);
            alert.setContentText("Perfil atualizado com sucesso!");
            alert.showAndWait();

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

    @FXML
    public void btnSairDaConta(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja realmente sair?");
        alert.setContentText("Escolha uma opção.");

        ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(simButton, naoButton);


        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                trocarTelaLogin();
            }
            else {
                alert.close();
            }
        });
    }


    //GETs and Sets:
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    private boolean validarInputTf(String inputTf) {
        return inputTf.matches("\\d+");
    }


}
