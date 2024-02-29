package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    private Button btnAtualizar;

    @FXML
    private Button btnCadastrarEndereco;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnRemoverEndereco;

    @FXML
    private Button btnAlterarEndereco;


    @FXML
    private Label lblNome;

    @FXML
    private Label lblCPF;

    @FXML
    private Label lblDataDeNascimento;

    @FXML
    private Label lblLogin;

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
    private TextField tfCEP;

    @FXML
    private TextField tfRua;

    @FXML
    private TextField tfBairro;

    @FXML
    private TextField tfCidade;

    @FXML
    private TextField tfEstado;

    @FXML
    private TableView<Endereco> tbvEnderecosCadastrados;

    @FXML
    private TableColumn<Endereco, Integer> clnCEP;

    @FXML
    private TableColumn<Endereco, String> clnRua;

    @FXML
    private TableColumn<Endereco, String> clnBairro;

    @FXML
    private TableColumn<Endereco, String> clnCidade;

    @FXML
    private TableColumn<Endereco, String> clnEstado;

    private Usuario usuarioLogado;
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
    private boolean validarInputTf(String inputTf) {
        return inputTf.matches("\\d+");
    }

    //Métodos de troca de tela:
    @FXML
    private void trocarTelaCatalogoCliente(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("clienteCatalogo.fxml", "ReadEasy - Catálogo");
    }

    @FXML
    private void trocarTelaHistoricoCliente(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("clienteMinhasCompras.fxml", "ReadEasy - Histórico");
    }

    @FXML
    private void trocarTelaLogin(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("Login.fxml", "ReadEasy - Login");
    }

    public void initialize(){
        setUsuarioLogado(SessaoUsuario.getUsuarioLogado());
        atualizarLabels();

        Cliente cliente;
        List <Endereco> enderecosCliente = null;

        if(getUsuarioLogado() instanceof Cliente){
           cliente = (Cliente) getUsuarioLogado();
           enderecosCliente = cliente.getEnderecosentrega();
        }
        inicializarTbvEnderecos(enderecosCliente);
    }

    private void atualizarLabels() {
        if (usuarioLogado != null) {
            lblNome.setText(usuarioLogado.getNome());
            lblCPF.setText(usuarioLogado.getCpf());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            lblDataDeNascimento.setText(usuarioLogado.getDataNascimento().format(formatter));
            lblLogin.setText(usuarioLogado.getLogin());
            lblTelefone.setText(usuarioLogado.getTelefone());

        }
    }

    @FXML
    public void inicializarTbvEnderecos(List<Endereco> enderecosCliente){

        clnCEP.setCellValueFactory(cellData -> {
            Endereco endereco = cellData.getValue();
            int cep = endereco.getCep();
            return new SimpleIntegerProperty(cep).asObject();
        });

        clnEstado.setCellValueFactory(cellData -> {
            Endereco endereco = cellData.getValue();
            String estado = endereco.getEstado();
            return new SimpleStringProperty(estado);
        });

        clnCidade.setCellValueFactory(cellData -> {
            Endereco endereco = cellData.getValue();
            String cidade = endereco.getCidade();
            return new SimpleStringProperty(cidade);
        });

        clnBairro.setCellValueFactory(cellData -> {
            Endereco endereco = cellData.getValue();
            String bairro = endereco.getBairro();
            return new SimpleStringProperty(bairro);
        });

        clnRua.setCellValueFactory(cellData -> {
            Endereco endereco = cellData.getValue();
            String rua = endereco.getRua();
            return new SimpleStringProperty(rua);
        });

        if(enderecosCliente != null && !enderecosCliente.isEmpty()){
            tbvEnderecosCadastrados.setItems(FXCollections.observableArrayList(enderecosCliente).sorted());
        }
    }

    @FXML
    void btnAtualizarPerfil() {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        String nome = tfEditarPerfilNome.getText();
        String cpf = tfEditarPerfilCPF.getText();
        String login = tfEditarPerfilLogin.getText();
        String senha = tfEditarPerfilSenha.getText();
        String telefone = tfEditarPerfilTelefone.getText();
        LocalDate dataNascimento = dtpEditarPerfilDataDeNascimento.getValue();

        if (nome.isEmpty() || cpf.isEmpty() || login.isEmpty() || senha.isEmpty() || dataNascimento == null){
            alert.setTitle("Erro");
            alert.setHeaderText("Campos não preenchidos.");
            alert.setContentText("Preencha todos os campos corretamente para continuar.");
            alert.showAndWait();
        }
        else{
            if (!validarInputTf(telefone) || !validarInputTf(cpf)) {
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
            }
            else{
                Endereco enderecoSelecionado = tbvEnderecosCadastrados.getItems().get(0);
                Cliente cliente = (Cliente) this.usuarioLogado;

                try {
                    ServidorReadEasy.getInstance().atualizarCliente(cliente, nome, cpf, dataNascimento,
                            login, senha, enderecoSelecionado, telefone);
                    this.atualizarLabels();

                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Atualização de perfil");
                    alert.setContentText("Perfil atualizado com sucesso!");
                    alert.showAndWait();

                }catch ( UsuarioExistenteException e){
                    alert.setTitle("Erro");
                    alert.setHeaderText("Tipo de usuário inválido");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }catch (DataInvalidaException e){
                    alert.setTitle("Erro");
                    alert.setHeaderText("Data inválida.");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    void btnCadastrarEndereco() {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        String stringCep = tfCEP.getText();
        String rua = tfRua.getText();
        String bairro = tfBairro.getText();
        String cidade = tfCidade.getText();
        String estado = tfEstado.getText();

        if(!stringCep.isEmpty() && !rua.isEmpty() && !bairro.isEmpty() && !cidade.isEmpty() && estado.isEmpty()){
            if(!validarInputCEP(stringCep) ){
                alert.setTitle("Erro.");
                alert.setHeaderText("Campo de CEP preenchido incorretamente.");
                alert.setContentText("Preencha o campo de cep apenas com números para continuar.");
                alert.showAndWait();            }
            else{
                int cep = Integer.parseInt(stringCep);
                Endereco endereco = new Endereco(cep, rua, bairro, cidade, estado);

                try {
                    ServidorReadEasy.getInstance().adicionarEnderecoDeEntrega(getUsuarioLogado(), endereco);
                    tbvEnderecosCadastrados.getItems().add(endereco);

                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cadastro de Endereço");
                    alert.setHeaderText(null);
                    alert.setContentText("Endereço cadastrado com sucesso!");
                    alert.showAndWait();
                } catch (EnderecoExistenteException e) {
                    alert.setTitle("Erro.");
                    alert.setHeaderText("Endereço já cadastrado.");
                    alert.setContentText("Cadastre um endereço novo para continuar.");
                    alert.showAndWait();
                }
            }
        }
        else{
            alert.setTitle("Erro.");
            alert.setHeaderText("Campos não preenchidos.");
            alert.setContentText("Preencha o campo todos os campos corretamente para continuar.");
            alert.showAndWait();
        }
    }

    @FXML
    void btnRemoverEndereco(){
        Endereco enderecoSelecionado = tbvEnderecosCadastrados.getSelectionModel().getSelectedItem();

        if (enderecoSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText(null);
            alert.setContentText("Tem certeza que deseja excluir o endereço selecionado?");

            ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
            ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(simButton, naoButton);


            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                    ServidorReadEasy.getInstance().removerEnderecoDeEntrega(usuarioLogado, enderecoSelecionado);
                    tbvEnderecosCadastrados.getItems().remove(enderecoSelecionado);
                }
                else {
                    alert.close();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Nenhum endereço selecionado para remoção.");
            alert.showAndWait();

        }
    }

    @FXML
    void btnEditarEndereco(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Endereco enderecoSelecionado = tbvEnderecosCadastrados.getSelectionModel().getSelectedItem();

        if(enderecoSelecionado != null) {
            String stringCep = tfCEP.getText();
            String novaRua = tfRua.getText();
            String novoBairro = tfBairro.getText();
            String novaCidade = tfCidade.getText();
            String novoEstado = tfEstado.getText();

            if(stringCep.isEmpty() || novaRua.isEmpty() || novoBairro.isEmpty() || novaCidade.isEmpty() || novoEstado.isEmpty()){
                alert.setTitle("Erro");
                alert.setHeaderText("Campo não preenchido.");
                alert.setContentText("Preencha todos os campos corretamente para continuar.");
                alert.showAndWait();
            }
            else{
                if(validarInputCEP(stringCep)){
                    int novoCEP = Integer.parseInt(stringCep);

                    try {
                        ServidorReadEasy.getInstance().atualizarEnderecoDeEntrega(SessaoUsuario.getUsuarioLogado(), enderecoSelecionado, novoCEP,
                                    novaRua, novoBairro, novaCidade, novoEstado);
                    } catch (EnderecoExistenteException e) {
                        alert.setTitle("Erro");
                        alert.setHeaderText("Operação inválida!");
                        alert.setContentText("Você tentou editar pra um outro endereco" +
                                " já cadastrado na sua lista de endereços");
                        alert.showAndWait();
                    } catch (CampoVazioException e) {
                        throw new RuntimeException(e);
                    }

                    List<Endereco> enderecos = ServidorReadEasy.getInstance().listarEnderecosDeEntrega(SessaoUsuario.getUsuarioLogado());
                    tbvEnderecosCadastrados.getItems().clear();
                    tbvEnderecosCadastrados.setItems(FXCollections.observableList(enderecos));

                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sucesso");
                    alert.setHeaderText(null);
                    alert.setContentText("Endereço editado com sucesso.");
                    alert.showAndWait();
                }
                else{
                    alert.setTitle("Erro");
                    alert.setHeaderText("Campo de cep Preenchido incorretamente.");
                    alert.setContentText("Preencha o campo de Cep apenas com números para continuar.");
                    alert.showAndWait();
                }
            }
        }else{
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhum endereço selecionado para edição.");
            alert.setContentText("Selecione um endereco da tabela para continuar.");
            alert.showAndWait();
        }
    }

    @FXML
    public void popularCamposComEnderecoSelecionado(){
        Endereco enderecoSelecionado = tbvEnderecosCadastrados.getSelectionModel().getSelectedItem();

        if(enderecoSelecionado != null){
            tfCEP.setText(String.valueOf(enderecoSelecionado.getCep()));
            tfRua.setText(enderecoSelecionado.getRua());
            tfBairro.setText(enderecoSelecionado.getBairro());
            tfCidade.setText(enderecoSelecionado.getCidade());
            tfEstado.setText(enderecoSelecionado.getEstado());
        }
    }

    private boolean validarInputCEP(String cep) {
        return cep.matches("\\d+");
    }

    public void limparCampos(){
        tfBairro.clear();
        tfCEP.clear();
        tfCidade.clear();
        tfRua.clear();
        tfEstado.clear();
    }

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
}
