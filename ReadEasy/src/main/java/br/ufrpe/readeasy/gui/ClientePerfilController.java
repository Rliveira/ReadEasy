package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private Button btnAtualizar;

    @FXML
    private Button btnCadastrarEndereço;

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

    @FXML
    private Button btnRemoverEndereco;

    @FXML
    private Button btnAlterarEndereco;

    private Usuario usuarioLogado;

    private boolean editMode = false;

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
       //tab1
        this.setUsuarioLogado(SessaoUsuario.getUsuarioLogado());
        this.atualizarLabels();

      //tab2

        List<Endereco> enderecos = ServidorReadEasy.getInstance().listarEnderecos();

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

        tbvEnderecosCadastrados.setItems(FXCollections.observableArrayList(enderecos).sorted());

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
    void btnAtualizarPerfil(ActionEvent event) throws TipoUsuarioInvalidoException, UsuarioExistenteException,
            UsuarioInexistenteException, UsuarioNuloException, DataInvalidaException {


        String nome = tfEditarPerfilNome.getText().trim();
        if (nome.isEmpty()) {
            nome = usuarioLogado.getNome();
        }else{
            lblNome.setText(nome);
        }

        String cpf = tfEditarPerfilCPF.getText();
        if (cpf.isEmpty()) {
            cpf = usuarioLogado.getCpf();
        }else{
            lblCPF.setText(cpf);
        }

        String login = tfEditarPerfilLogin.getText();
        if (login.isEmpty()) {
            login = usuarioLogado.getLogin();
        }else{
            lblLogin.setText(login);
        }

        String senha = tfEditarPerfilSenha.getText();
        if (senha.isEmpty()) {
            senha = usuarioLogado.getSenha();
        }

        String telefone = tfEditarPerfilTelefone.getText();
        if (telefone.isEmpty()) {
            telefone = usuarioLogado.getTelefone();
        }else{
            lblTelefone.setText(telefone);
        }

        LocalDate dataNascimento = dtpEditarPerfilDataDeNascimento.getValue();
        if (dataNascimento == null) {
            dataNascimento = usuarioLogado.getDataNascimento();
        }else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = dataNascimento.format(formatter);
            lblDataDeNascimento.setText(dataFormatada);
        }
        if (!validarInputTf(telefone) || !validarInputTf(cpf)) {
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
        }
        Endereco enderecoSelecionado = tbvEnderecosCadastrados.getItems().get(0);

           try {
               Cliente cliente = (Cliente) this.usuarioLogado;
               ServidorReadEasy.getInstance().atualizarCliente(cliente, nome, cpf, dataNascimento,
                       login, senha, enderecoSelecionado, telefone); // endereco incorreto ainda
               this.atualizarLabels();

               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Atualização de perfil");
               alert.setHeaderText(null);
               alert.setContentText("Perfil atualizado com sucesso!");
               alert.showAndWait();

           }catch (TipoUsuarioInvalidoException | UsuarioExistenteException | UsuarioInexistenteException
           | UsuarioNuloException e ){
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Erro");
               alert.setHeaderText("Tipo de usuário inválido");
               alert.setContentText(e.getMessage());
               alert.showAndWait();
           }catch (DataInvalidaException e){
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Erro");
               alert.setHeaderText("Data inválida.");
               alert.setContentText(e.getMessage());
               alert.showAndWait();
           }

        }
    @FXML
    void btnCadastrarEndereço(ActionEvent event) throws EnderecoNuloException, UsuarioInexistenteException,
            ClienteNuloException {

        int cep = Integer.parseInt(tfCEP.getText());
        String rua = tfRua.getText();
        String bairro = tfBairro.getText();
        String cidade = tfCidade.getText();
        String estado = tfEstado.getText();

        Endereco endereco = new Endereco(cep, rua, bairro, cidade, estado);

        try {
            ServidorReadEasy.getInstance().adicionarEnderecoCliente(usuarioLogado.getCpf(), endereco);

            List<Endereco> enderecos = ServidorReadEasy.getInstance().listarEnderecos();

            tbvEnderecosCadastrados.setItems(FXCollections.observableList(enderecos));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cadastro de Endereço");
            alert.setHeaderText(null);
            alert.setContentText("Endereço cadastrado com sucesso!");
            alert.showAndWait();
        }catch (EnderecoNuloException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no preenchimento de dados");
            alert.setHeaderText(null);
            alert.setContentText("Endereço nulo.");
            alert.showAndWait();
        }catch (UsuarioInexistenteException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no preenchimento de dados");
            alert.setHeaderText(null);
            alert.setContentText("Usuário existente");
            alert.showAndWait();
        }catch (ClienteNuloException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no preenchimento de dados");
            alert.setHeaderText(null);
            alert.setContentText("Cliente nulo");
            alert.showAndWait();
        }
    }
    @FXML
    void btnAlterarEndereco(ActionEvent event) throws EnderecoInexistenteException, EnderecoNuloException {

        Endereco enderecoSelecionado = tbvEnderecosCadastrados.getSelectionModel().getSelectedItem();

        if(enderecoSelecionado != null) {
            if(!editMode){

                tfCEP.setText(String.valueOf(enderecoSelecionado.getCep()));
                tfRua.setText(enderecoSelecionado.getRua());
                tfBairro.setText(enderecoSelecionado.getBairro());
                tfCidade.setText(enderecoSelecionado.getCidade());
                tfEstado.setText(enderecoSelecionado.getEstado());

                btnRemoverEndereco.setDisable(true);
                btnCadastrarEndereço.setDisable(true);
                editMode = true;

            }else{

                int novoCEP = Integer.parseInt(tfCEP.getText());
                String novaRua = tfRua.getText();
                String novoBairro = tfBairro.getText();
                String novaCidade = tfCidade.getText();
                String novoEstado = tfEstado.getText();

                    try{
                        ServidorReadEasy.getInstance().atualizarEndereco(enderecoSelecionado, novoCEP,
                                novaRua, novoBairro, novaCidade, novoEstado);

                        List<Endereco> enderecos = ServidorReadEasy.getInstance().listarEnderecos();

                        tbvEnderecosCadastrados.setItems(FXCollections.observableList(enderecos));

                        tbvEnderecosCadastrados.refresh();

                    }catch(EnderecoInexistenteException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro no preenchimento de dados");
                        alert.setHeaderText(null);
                        alert.setContentText("Endereço inexistente.");
                        alert.showAndWait();
                    }catch(EnderecoNuloException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro no preenchimento de dados");
                        alert.setHeaderText(null);
                        alert.setContentText("Endereço nulo");
                        alert.showAndWait();
                    }
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Nenhum endereço selecionado para edição.");
            alert.showAndWait();
        }

    }
    @FXML
    void btnRemoverEndereco(ActionEvent event) throws EnderecoNuloException, UsuarioInexistenteException, ClienteNuloException {
        Endereco enderecoSelecionado = tbvEnderecosCadastrados.getSelectionModel().getSelectedItem();

        if (enderecoSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText(null);
            alert.setContentText("Tem certeza que deseja excluir o endereço selecionado?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Remover da lista associada à TableView
                    List<Endereco> enderecos = new ArrayList<>(tbvEnderecosCadastrados.getItems());
                    enderecos.remove(enderecoSelecionado);
                    tbvEnderecosCadastrados.setItems(FXCollections.observableList(enderecos));

                    // Remover do servidor
                    ServidorReadEasy.getInstance().removerEnderecoCliente(usuarioLogado.getCpf(), enderecoSelecionado);

                } catch (EnderecoNuloException e) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Erro no preenchimento de dados");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Endereço nulo");
                    alert1.showAndWait();
                } catch (UsuarioInexistenteException e) {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Erro no preenchimento de dados");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Usuario inexistente.");
                    alert2.showAndWait();
                } catch (ClienteNuloException e) {
                    Alert alert3 = new Alert(Alert.AlertType.ERROR);
                    alert3.setTitle("Erro no preenchimento de dados");
                    alert3.setHeaderText(null);
                    alert3.setContentText("Cliente nulo");
                    alert3.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Nenhum endereço selecionado para remoção.");
            alert.showAndWait();

        }
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
