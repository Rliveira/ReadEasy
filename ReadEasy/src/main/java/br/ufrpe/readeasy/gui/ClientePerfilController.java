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

    //Métodos de troca de tela:
    @FXML
    private void trocarTelaCatalogoCliente(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("clienteCatalogo.fxml", "ReadEasy - Catálogo");
    }

    @FXML
    private void trocarTelaHistoricoCliente(){
        ScreenManager sm = ScreenManager.getInstance();
        sm.TrocarTela("clienteHistoricoCompras.fxml", "ReadEasy - Histórico");
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
            tbvEnderecosCadastrados.setItems(FXCollections.observableArrayList(enderecosCliente));
        }
    }

    @FXML
    void btnAtualizarPerfil() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja realmente atualizar o perfil?");
        alert.setContentText("Escolha uma opção.");

        ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(simButton, naoButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                alert.close();
                Alert alertErro = new Alert(Alert.AlertType.ERROR);

                String nome = tfEditarPerfilNome.getText();
                String cpf = tfEditarPerfilCPF.getText();
                String login = tfEditarPerfilLogin.getText();
                String senha = tfEditarPerfilSenha.getText();
                String telefone = tfEditarPerfilTelefone.getText();
                LocalDate dataNascimento = dtpEditarPerfilDataDeNascimento.getValue();

                if (nome.isEmpty() || cpf.isEmpty() || login.isEmpty() || senha.isEmpty() || dataNascimento == null){
                    alertErro.setTitle("Erro");
                    alertErro.setHeaderText("Campos não preenchidos.");
                    alertErro.setContentText("Preencha todos os campos corretamente para continuar.");
                    alertErro.showAndWait();
                }
                else{
                    if (validarInputTf(telefone) || validarInputTf(cpf)) {
                        if(validarQuantidadeDeCaracteres("telefone", telefone) &&
                                validarQuantidadeDeCaracteres("cep", cpf)){

                            Endereco enderecoSelecionado = tbvEnderecosCadastrados.getItems().get(0);
                            Cliente cliente = (Cliente) this.usuarioLogado;
                            try {
                                ServidorReadEasy.getInstance().atualizarCliente(cliente, nome, cpf, dataNascimento,
                                        login, senha, enderecoSelecionado, telefone);

                                this.atualizarLabels();
                                alertErro.setAlertType(Alert.AlertType.INFORMATION);
                                alertErro.setTitle("Atualização de perfil");
                                alertErro.setContentText("Perfil atualizado com sucesso!");
                                alertErro.showAndWait();
                                limparCamposPessoais();

                            }catch (UsuarioExistenteException e){
                                alertErro.setTitle("Erro");
                                alertErro.setHeaderText("Tipo de usuário inválido");
                                alertErro.setContentText(e.getMessage());
                                alertErro.showAndWait();
                            }catch (DataInvalidaException e){
                                alertErro.setTitle("Erro");
                                alertErro.setHeaderText("Data inválida.");
                                alertErro.setContentText(e.getMessage());
                                alertErro.showAndWait();
                            }
                        }
                        else{
                            alertErro.setTitle("Erro");
                            alertErro.setHeaderText("Campos de telefone ou CPF apresentam uma quantidade de dígitos fora do padrão.");
                            alertErro.setContentText("Certifique de digitar 11 dígitos para CPF e 8 dígitos para CEP para continuar.");
                            alertErro.showAndWait();
                        }
                    }
                    else{
                        alertErro.setTitle("Erro");
                        alertErro.setHeaderText("Campo de telefone, CEP ou CPF apresenta letras ou caracteres especiais");
                        alertErro.setContentText("Digite apenas números para continuar");
                        alertErro.showAndWait();
                    }
                }
            }
            else {
                alert.close();
            }
        });
    }

    @FXML
    void btnCadastrarEndereco() {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        String stringCep = tfCEP.getText();
        String rua = tfRua.getText();
        String bairro = tfBairro.getText();
        String cidade = tfCidade.getText();
        String estado = tfEstado.getText();

        if(!stringCep.isEmpty() && !rua.isEmpty() && !bairro.isEmpty() && !cidade.isEmpty() && !estado.isEmpty()){
            if(validarInputTf(stringCep)){
                if(validarQuantidadeDeCaracteres("cep", stringCep)){
                    int cep = Integer.parseInt(stringCep);
                    Endereco endereco = new Endereco(cep, rua, bairro, cidade, estado);

                    try {
                        ServidorReadEasy.getInstance().adicionarEnderecoDeEntrega(getUsuarioLogado(), endereco);
                        tbvEnderecosCadastrados.getItems().add(endereco);

                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setTitle("Cadastro de Endereço");
                        alert.setHeaderText("Endereço cadastrado com sucesso!");
                        alert.setContentText(null);
                        alert.showAndWait();
                        limparCampos();
                    } catch (EnderecoExistenteException e) {
                        alert.setTitle("Erro.");
                        alert.setHeaderText("Endereço já cadastrado.");
                        alert.setContentText("Cadastre um endereço novo para continuar.");
                        alert.showAndWait();
                    }
                }
                else{
                    alert.setTitle("Erro");
                    alert.setHeaderText("Campos de CEP apresenta uma quantidade de dígitos fora do padrão.");
                    alert.setContentText("Certifique de digitar os 8 dígitos para CEP para continuar.");
                    alert.showAndWait();
                }
            }
            else{
                alert.setTitle("Erro.");
                alert.setHeaderText("Campo de CEP preenchido incorretamente.");
                alert.setContentText("Preencha o campo de cep apenas com números para continuar.");
                alert.showAndWait();
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
        Alert alertAviso = new Alert(Alert.AlertType.WARNING);

        if (enderecoSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText("Tem certeza que deseja excluir o endereço selecionado?");

            ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
            ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(simButton, naoButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                    ServidorReadEasy.getInstance().removerEnderecoDeEntrega(usuarioLogado, enderecoSelecionado);
                    tbvEnderecosCadastrados.getItems().remove(enderecoSelecionado);

                    alertAviso.setAlertType(Alert.AlertType.INFORMATION);
                    alertAviso.setTitle("Sucesso");
                    alertAviso.setHeaderText("Endereço removido com êxito.");
                    alertAviso.setContentText(null);
                    alertAviso.showAndWait();
                    limparCampos();
                }
                else {
                    alert.close();
                }
            });
        } else {
            alertAviso.setTitle("Aviso");
            alertAviso.setHeaderText(null);
            alertAviso.setContentText("Nenhum endereço selecionado para remoção.");
            alertAviso.showAndWait();
        }
    }

    @FXML
    void btnEditarEndereco(){
        Alert alertErro = new Alert(Alert.AlertType.ERROR);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja realmente editar o endereço?");
        alert.setContentText("Escolha uma opção.");

        ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(simButton, naoButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                boolean excessaoLevantada = false;
                Endereco enderecoSelecionado = tbvEnderecosCadastrados.getSelectionModel().getSelectedItem();

                if(enderecoSelecionado != null) {
                    String stringCep = tfCEP.getText();
                    String novaRua = tfRua.getText();
                    String novoBairro = tfBairro.getText();
                    String novaCidade = tfCidade.getText();
                    String novoEstado = tfEstado.getText();

                    //Necessário verificar o cep para garantir as próximas validações referente a ele.
                    if(stringCep.isEmpty()){
                        alertErro.setTitle("Erro");
                        alertErro.setHeaderText("Campo não preenchido.");
                        alertErro.setContentText("Preencha todos os campos corretamente para continuar.");
                        alertErro.showAndWait();
                    }
                    else{
                        if(validarInputTf(stringCep)){
                            if(validarQuantidadeDeCaracteres("Cep", stringCep)){
                                int cep = Integer.parseInt(stringCep);
                                try {
                                    ServidorReadEasy.getInstance().atualizarEnderecoDeEntrega(SessaoUsuario.getUsuarioLogado(), enderecoSelecionado, cep,
                                            novaRua, novoBairro, novaCidade, novoEstado);
                                } catch (EnderecoExistenteException e) {
                                    alertErro.setTitle("Erro");
                                    alertErro.setHeaderText("Operação inválida!");
                                    alertErro.setContentText("Você tentou editar pra um outro endereco" +
                                            " já cadastrado na sua lista de endereços");
                                    alertErro.showAndWait();
                                } catch (CampoVazioException e) {
                                    excessaoLevantada = true;
                                    alertErro.setTitle("Erro");
                                    alertErro.setHeaderText("Campo não preenchido.");
                                    alertErro.setContentText("Preencha todos os campos corretamente para continuar.");
                                    alertErro.showAndWait();
                                }
                                if(!excessaoLevantada){
                                    List<Endereco> enderecos = ServidorReadEasy.getInstance().listarEnderecosDeEntrega(SessaoUsuario.getUsuarioLogado());
                                    tbvEnderecosCadastrados.getItems().clear();
                                    tbvEnderecosCadastrados.setItems(FXCollections.observableList(enderecos));

                                    alertErro.setAlertType(Alert.AlertType.INFORMATION);
                                    alertErro.setTitle("Sucesso");
                                    alertErro.setHeaderText("Endereço editado com sucesso.");
                                    alertErro.setContentText(null);
                                    alertErro.showAndWait();
                                    limparCampos();
                                }
                            }
                            else{
                                alertErro.setTitle("Erro");
                                alertErro.setHeaderText("Campos de CEP apresenta uma quantidade de dígitos fora do padrão.");
                                alertErro.setContentText("Certifique de digitar os 8 dígitos para CEP para continuar.");
                                alertErro.showAndWait();
                            }
                        }
                        else{
                            alertErro.setTitle("Erro");
                            alertErro.setHeaderText("Campo de cep Preenchido incorretamente.");
                            alertErro.setContentText("Preencha o campo de Cep apenas com números para continuar.");
                            alertErro.showAndWait();
                        }
                    }
                }else{
                    alertErro.setTitle("Erro");
                    alertErro.setHeaderText("Nenhum endereço selecionado para edição.");
                    alertErro.setContentText("Selecione um endereco da tabela para continuar.");
                    alertErro.showAndWait();
                }
            }
            else {
                alert.close();
            }
        });
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

    @FXML
    public void popularDadosPessoais(){
        tfEditarPerfilNome.setText(usuarioLogado.getNome());
        tfEditarPerfilLogin.setText(usuarioLogado.getLogin());
        tfEditarPerfilTelefone.setText(usuarioLogado.getTelefone());
        tfEditarPerfilSenha.setText(usuarioLogado.getSenha());
        tfEditarPerfilCPF.setText(usuarioLogado.getCpf());
        dtpEditarPerfilDataDeNascimento.setValue(usuarioLogado.getDataNascimento());
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

    public void limparCampos(){
        tfBairro.clear();
        tfCEP.clear();
        tfCidade.clear();
        tfRua.clear();
        tfEstado.clear();
    }

    public void limparCamposPessoais(){
        tfEditarPerfilNome.clear();
        tfEditarPerfilLogin.clear();
        tfEditarPerfilTelefone.clear();
        tfEditarPerfilSenha.clear();
        tfEditarPerfilCPF.clear();
        dtpEditarPerfilDataDeNascimento.setValue(null);
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

    //GETs AND SETs:
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}
