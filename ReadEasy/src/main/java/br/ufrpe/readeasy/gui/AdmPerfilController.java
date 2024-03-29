package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Endereco;
import br.ufrpe.readeasy.beans.Funcionario;
import br.ufrpe.readeasy.beans.Usuario;
import br.ufrpe.readeasy.business.ServidorReadEasy;
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
        Alert alert = new Alert(Alert.AlertType.ERROR);

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
            alert.setTitle("Erro");
            alert.setHeaderText("Campos não preenchidos.");
            alert.setContentText("Certifique de preencher todos os campos para continuar.");
            alert.showAndWait();
        }
        else{
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
                int cep = Integer.parseInt(cepString);
                Endereco endereco = new Endereco(cep, rua, bairro, cidade, estado);
                try {
                    Funcionario funcionario = (Funcionario) this.usuarioLogado;
                    ServidorReadEasy.getInstance().atualizarFuncionario(funcionario, nome, cpf, dataNascimento,
                            usuario, senha, endereco, telefone, true, funcionario.getAdmResponsavel());
                    this.atualizarLabels();

                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Atualização de perfil");
                    alert.setHeaderText(null);
                    alert.setContentText("Perfil atualizado com sucesso!");
                    alert.showAndWait();
                } catch (UsuarioExistenteException e) {
                    alert.setTitle("Erro");
                    alert.setHeaderText("Usuário já existente");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                } catch (DataInvalidaException e) {
                    alert.setTitle("Erro");
                    alert.setHeaderText("Data inválida");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
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


    //GETs and SETs:
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}