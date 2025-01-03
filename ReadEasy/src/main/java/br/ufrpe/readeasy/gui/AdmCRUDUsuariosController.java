package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.business.Fachada;
import br.ufrpe.readeasy.exceptions.CampoVazioException;
import br.ufrpe.readeasy.exceptions.DataInvalidaException;
import br.ufrpe.readeasy.exceptions.MenorDeIdadeException;
import br.ufrpe.readeasy.exceptions.UsuarioExistenteException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdmCRUDUsuariosController {
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
    private TextField txtFieldSenha;

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
    private DatePicker dpDataNascimento;

    @FXML
    private TableView<Usuario> tvUsuarios;

    @FXML
    private TableColumn<Usuario, String> colNome;

    @FXML
    private TableColumn<Usuario, String> colCPF;

    @FXML
    private TableColumn<Usuario, String> colNomeUsuario;

    @FXML
    private TableColumn<Usuario, String> colDataNascimento;

    @FXML
    private TableColumn<Usuario, String> colTelefone;

    @FXML
    private TableColumn<Usuario, String> colTipo;

    @FXML
    private TableColumn<Usuario, String> colADM;

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private ComboBox<String> cbTipoFornecedor;
    List<TipoFornecedor> tipoFornecedor = new ArrayList<>();

    private Fachada fachada = Fachada.getInstance();
    private ObservableList<String> cargos = FXCollections.observableArrayList("Funcionário",
            "Administrador", "Fornecedor");
    private static AdmCRUDUsuariosController instance;
    private boolean ignorarInitialize;

    //Construtor:
    public AdmCRUDUsuariosController() {
        if(instance == null){
            instance = this;
            ignorarInitialize = true;
        }
    }

    //métodos:
    @FXML
    public void initialize() {
        ScreenManager screenManager = ScreenManager.getInstance();

        if(screenManager.getAdmCRUDUsuariosController() == null){
            screenManager.setAdmCRUDUsuariosController(instance);
        }
        if (!ignorarInitialize) {
            construirTabela();
            carregarDados();
        }
    }

    @FXML
    public void onBtnCadastrarUsuariosclick() {
        boolean excecaoLevantada = false;

        Alert alert = new Alert(Alert.AlertType.ERROR);
        String nome = txtFieldNome.getText();
        String cpf = txtFieldCPF.getText();
        String login = txtFieldLogin.getText();
        String telefone = txtFieldTelefone.getText();
        String rua = txtFieldRua.getText();
        String bairro = txtFieldBairro.getText();
        String cidade = txtFieldCidade.getText();
        String estado = txtFieldEstado.getText();
        String senha = txtFieldSenha.getText();
        String cepString = txtFieldCEP.getText();
        LocalDate dataNascimento = dpDataNascimento.getValue();

        if (nome.isEmpty() || cpf.isEmpty() || login.isEmpty() || telefone.isEmpty() || rua.isEmpty() || bairro.isEmpty()
                || cidade.isEmpty() || estado.isEmpty() || senha.isEmpty() || cepString.isEmpty() || dataNascimento == null) {
            alert.setTitle("Erro");
            alert.setHeaderText("Campos não preenchidos.");
            alert.setContentText("Certifique de preencher todos os campos para continuar.");
            alert.showAndWait();
        } else {
            if (validarInputTf(cepString) || validarInputTf(telefone) || validarInputTf(cpf)) {
                alert.setTitle("Erro");
                alert.setHeaderText("Campos de telefone, CPF ou CEP contém letras ou caracteres especiais.");
                alert.setContentText("Digite apenas números nos campos para continuar.");
                alert.showAndWait();

            } else {
                int cep = 0;
                if (!cepString.isEmpty()) {
                    cep = Integer.parseInt(cepString);
                }
                Endereco endereco = new Endereco(cep, rua, bairro, cidade, estado);

                String tipoUsuarioSelecionado = cbTipo.getValue();

                if (tipoUsuarioSelecionado == null) {
                    alert.setTitle("Erro");
                    alert.setHeaderText("Tipo de usuário não selecionado.");
                    alert.setContentText("Selecione uma opção no tipo de usuário para continuar.");
                    alert.showAndWait();
                } else {
                    try {
                        Usuario usuario;

                        //montagem do usuario:
                        if (tipoUsuarioSelecionado.equals(cargos.get(1))) {
                            usuario = new Funcionario(nome, cpf, dataNascimento, login, senha, endereco, telefone, true,
                                    (Funcionario) SessaoUsuario.getUsuarioLogado());
                        } else if (tipoUsuarioSelecionado.equals("Fornecedor")) {
                            String nomeTipoFornecedor = cbTipoFornecedor.getValue();
                            TipoFornecedor tipoFornecedor1 = procurarTipoFornecedorPeloNome(nomeTipoFornecedor);
                            usuario = new Fornecedor(nome, cpf, dataNascimento, login, senha, endereco, telefone, tipoFornecedor1);
                        } else {   //funcionário comum.
                            usuario = new Funcionario(nome, cpf, dataNascimento, login, senha, endereco, telefone, false,
                                    (Funcionario) SessaoUsuario.getUsuarioLogado());
                        }
                        fachada.cadastrarUsuario(usuario);
                    } catch (MenorDeIdadeException e) {
                        excecaoLevantada = true;
                        alert.setTitle("Erro");
                        alert.setHeaderText("Usuário menor de idade");
                        alert.setContentText("Cadastre um usuário maior de idade para continuar.");
                        alert.showAndWait();
                    } catch (DataInvalidaException e) {
                        excecaoLevantada = true;
                        alert.setTitle("Erro");
                        alert.setHeaderText("Data Inválida!");
                        alert.setContentText("A data de nascimento selecionada é posterior a data atual.");
                        alert.showAndWait();
                    } catch (CampoVazioException e) {
                        excecaoLevantada = true;
                        alert.setTitle("Erro");
                        alert.setHeaderText("Campo de texto vazio!");
                        alert.setContentText("Preencha todos os campos do cadastro.");
                        alert.showAndWait();
                    } catch (UsuarioExistenteException e) {
                        excecaoLevantada = true;
                        alert.setTitle("Erro");
                        alert.setHeaderText("Usuário existente!");
                        alert.setContentText("Este usuário já está cadastrado no sistema.");
                        alert.showAndWait();
                    }

                    if (!excecaoLevantada) {
                        onAtualizarTabelaclick();
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sucesso");
                        alert.setHeaderText("Cadastro concluído!");
                        alert.setContentText("Este usuário foi cadastrado no sistema");
                        alert.showAndWait();
                    }
                }
            }
        }
    }

    @FXML
    public void onBtnEditarUsuarioclick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Deseja realmente editar usuário?");
        alert.setContentText("Escolha uma opção.");

        ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(simButton, naoButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.close();

                boolean excecaoLevantada = false;

                String nome = txtFieldNome.getText();
                String cpf = txtFieldCPF.getText();
                String login = txtFieldLogin.getText();
                String telefone = txtFieldTelefone.getText();
                String rua = txtFieldRua.getText();
                String bairro = txtFieldBairro.getText();
                String cidade = txtFieldCidade.getText();
                String estado = txtFieldEstado.getText();
                String senha = txtFieldSenha.getText();
                String nomeTipoFornecedor = cbTipoFornecedor.getValue();
                TipoFornecedor tipoFornecedor = procurarTipoFornecedorPeloNome(nomeTipoFornecedor); //retorna o tipo fornecedor selecionado pelo usuário
                String cepString = txtFieldCEP.getText();
                LocalDate dataNascimento = dpDataNascimento.getValue();

                if (nome.isEmpty() || cpf.isEmpty() || login.isEmpty() || telefone.isEmpty() || rua.isEmpty() || bairro.isEmpty()
                        || cidade.isEmpty() || estado.isEmpty() || senha.isEmpty() || cepString.isEmpty() || dataNascimento == null) {
                    alert.setTitle("Erro");
                    alert.setHeaderText("Campos não preenchidos.");
                    alert.setContentText("Certifique de preencher todos os campos para continuar.");
                    alert.showAndWait();
                } else {
                    if (validarInputTf(cepString) || validarInputTf(telefone) || validarInputTf(cpf)) {
                        excecaoLevantada = true;
                        alert.setTitle("Erro");
                        alert.setHeaderText("Campos de telefone, cpf ou cep contém letras ou caracteres especiais.");
                        alert.setContentText("Digite apenas números nos campos para continuar.");
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
                    else {
                        try {

                            int cep = Integer.parseInt(cepString);

                            Endereco endereco = new Endereco(cep, rua, bairro, cidade, estado);
                            Usuario usuario = tvUsuarios.getSelectionModel().getSelectedItem();

                            if (usuario == Fachada.getInstance().procurarUsuario("12384274165")) {
                                alert.setTitle("Erro");
                                alert.setHeaderText("Edição inválida!");
                                alert.setContentText("Não é possível editar o ADM Inicial");
                                alert.show();
                            }

                            String tipoUsuarioSelecionado = cbTipo.getSelectionModel().getSelectedItem();
                            if (tipoUsuarioSelecionado == null) {
                                excecaoLevantada = true;
                                alert.setTitle("Erro");
                                alert.setHeaderText("Tipo de usuário não selecionado.");
                                alert.setContentText("Selecione uma opção no tipo de usuário para continuar.");
                                alert.showAndWait();
                            } else if (usuario instanceof Funcionario) {
                                if (((Funcionario) usuario).isAdm()) {
                                    Fachada.getInstance().atualizarFuncionario(usuario, nome, cpf, dataNascimento,
                                            login, senha, endereco, telefone, false, ((Funcionario) usuario).getAdmResponsavel());
                                    limparCampos();
                                } else {
                                    Fachada.getInstance().atualizarFuncionario(usuario, nome, cpf, dataNascimento,
                                            login, senha, endereco, telefone, true, ((Funcionario) usuario).getAdmResponsavel());
                                    limparCampos();
                                }
                            } else if (usuario instanceof Fornecedor) {
                                Fachada.getInstance().atualizarFornecedor(usuario, nome, cpf, dataNascimento, login, senha,
                                        endereco, telefone, tipoFornecedor);
                                limparCampos();
                            }

                            if (usuario instanceof Fornecedor && tipoUsuarioSelecionado.equals(cargos.get(0)) ||
                                    usuario instanceof Funcionario && tipoUsuarioSelecionado.equals(cargos.get(2))
                                    || usuario instanceof Fornecedor && tipoUsuarioSelecionado.equals(cargos.get(1))) {
                                excecaoLevantada = true;
                                alert.setTitle("Erro");
                                alert.setHeaderText("Edição inválida!");
                                alert.setContentText("Não é possível fazer conversão entre fornecedor e funcionário.");
                                alert.showAndWait();
                            }
                        } catch (DataInvalidaException e) {
                            excecaoLevantada = true;
                            alert.setTitle("Erro");
                            alert.setHeaderText("Data Inválida!");
                            alert.setContentText("A data de nascimento selecionada é posterior a data atual.");
                            alert.showAndWait();
                        } catch (UsuarioExistenteException e) {
                            excecaoLevantada = true;
                            alert.setTitle("Erro");
                            alert.setHeaderText("Usuário existente!");
                            alert.setContentText("Este usuário já está cadastrado no sistema.");
                            alert.showAndWait();
                        } catch (NumberFormatException e) {
                            excecaoLevantada = true;
                            alert.setTitle("Erro");
                            alert.setHeaderText("Campo de cep contém letras ou caracteres especiais.");
                            alert.setContentText("Digite apenas números no campos para continuar.");
                            alert.showAndWait();
                        }
                    }
                    if (!excecaoLevantada) {
                        onAtualizarTabelaclick();
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sucesso");
                        alert.setHeaderText("Edição concluída!");
                        alert.setContentText("Edição de usuário realizada com êxito.");
                        alert.showAndWait();
                    }
                }
            }
            else {
                alert.close();
            }
        });
    }
    @FXML
    public void onDeletarUsuarioclick() {
        Alert alert;
        if (tvUsuarios.getSelectionModel().getSelectedItem() == Fachada.getInstance().procurarUsuario("12384274165"))
        {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Ação proibida");
            alert.setHeaderText("Impossível remover o ADM Inicial");
            alert.show();

        }
        else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Deseja realmente deletar esse usuário?");
            alert.setContentText("Escolha uma opção.");

            ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
            ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(simButton, naoButton);

            Alert finalAlert = alert;
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                    fachada.removerUsuario(tvUsuarios.getSelectionModel().getSelectedItem());
                    onAtualizarTabelaclick();
                } else {
                    finalAlert.close();
                }
            });
        }
    }

    @FXML
    public void onAtualizarTabelaclick ()
    {
        tvUsuarios.getItems().clear();
        tvUsuarios.setItems(FXCollections.observableArrayList(listarFornecedorFuncionario()));
        limparCampos();
    }

    @FXML
    public void onSelecionarTipoUsuarioclick()
    {
        if (cbTipo.getValue() != null){
            if(cbTipo.getSelectionModel().getSelectedItem().equals(cargos.get(2)))
            {
                cbTipoFornecedor.setVisible(true);
            }
            else
            {
                cbTipoFornecedor.setVisible(false);
            }
        }
    }

    @FXML
    public void cbEscolherTipoFornecedor()
    {
        cbTipoFornecedor.getSelectionModel().getSelectedItem();
    }

    public void popularCamposDoUsuarioSelecionado() {
        Object itemSelecionado = tvUsuarios.getSelectionModel().getSelectedItem();

        if (itemSelecionado != null) {
            if (itemSelecionado instanceof Funcionario)
            {
                Funcionario funcionarioSelecionado = (Funcionario) itemSelecionado;
                popularCamposComDados(funcionarioSelecionado);
            } else if (itemSelecionado instanceof Fornecedor) {
                Fornecedor fornecedorSelecionado = (Fornecedor) itemSelecionado;
                popularCamposComDados(fornecedorSelecionado);
            } else if (itemSelecionado instanceof Cliente) {
                Cliente clienteSelecionado = (Cliente) itemSelecionado;
                popularCamposComDados(clienteSelecionado);
            }
        }
    }

    private void popularCamposComDados(Usuario usuario)
    {
        txtFieldNome.setText(usuario.getNome());
        txtFieldCPF.setText(usuario.getCpf());
        txtFieldLogin.setText(usuario.getLogin());
        txtFieldSenha.setText(usuario.getSenha());
        txtFieldTelefone.setText(usuario.getTelefone());
        dpDataNascimento.setValue(usuario.getDataNascimento());
        txtFieldCEP.setText(String.valueOf(usuario.getEndereco().getCep()));
        txtFieldBairro.setText(usuario.getEndereco().getBairro());
        txtFieldRua.setText(usuario.getEndereco().getRua());
        txtFieldCidade.setText(usuario.getEndereco().getCidade());
        txtFieldEstado.setText(usuario.getEndereco().getEstado());

        if(usuario instanceof Funcionario)
        {
            if(((Funcionario) usuario).isAdm())
            {
                cbTipo.setValue(cargos.get(1));
            }
            else
            {
                cbTipo.setValue(cargos.get(0));
            }
            cbTipoFornecedor.setVisible(false);
        }
        if(usuario instanceof Fornecedor)
        {
            cbTipo.setValue(cargos.get(2));
            cbTipoFornecedor.setVisible(true);
            cbTipoFornecedor.setValue(((Fornecedor) usuario).getTipoFornecedor().getDescricaoEnum());
        }
    }

    public void limparCampos()
    {
        txtFieldNome.clear();
        txtFieldCPF.clear();
        txtFieldLogin.clear();
        txtFieldTelefone.clear();
        txtFieldCEP.clear();
        txtFieldRua.clear();
        txtFieldBairro.clear();
        txtFieldCidade.clear();
        txtFieldEstado.clear();
        txtFieldSenha.clear();
        cbTipoFornecedor.setVisible(false);
        cbTipo.setValue(null);
    }

    @FXML
    public void onPesquisarTyped()
    {
        String pesquisa = txtFieldPesquisar.getText();
        List<Usuario> listaDeUsuarios = listarFornecedorFuncionario();

        if(pesquisa == null || pesquisa.isEmpty())
        {
            tvUsuarios.setItems(FXCollections.observableArrayList(listaDeUsuarios));
        }
        else {
            FilteredList<Usuario> usuariosFiltrados = new FilteredList<>(FXCollections.observableArrayList(listaDeUsuarios));

            usuariosFiltrados.setPredicate(usuario ->
            {
                String termoLowerCase = pesquisa.toLowerCase();
                return usuario.getNome().toLowerCase().contains(termoLowerCase)
                        ||
                        usuario.getLogin().toLowerCase().contains(termoLowerCase)
                        ||
                        usuario.getCpf().toLowerCase().contains(termoLowerCase)
                        ||
                        usuario.getTelefone().toLowerCase().contains(termoLowerCase);
            });
            tvUsuarios.setItems(usuariosFiltrados);
        }
    }

    public List<Usuario> listarFornecedorFuncionario()
    {
        List<Usuario> fornecedorFuncionario = new ArrayList<>();
        fornecedorFuncionario.addAll(Fachada.getInstance().listarAdms());
        fornecedorFuncionario.addAll(Fachada.getInstance().listarFornecedores());
        fornecedorFuncionario.addAll(Fachada.getInstance().listarFuncionarios());
        return fornecedorFuncionario;
    }

    public void construirTabela()
    {
        colNome.setCellValueFactory(cellData ->
        {
            Usuario usuario  = cellData.getValue();
            String nome = usuario.getNome();
            return new SimpleStringProperty(nome);
        });

        colCPF.setCellValueFactory(cellData ->
        {
            Usuario usuario = cellData.getValue();
            String cpf = usuario.getCpf();
            return new SimpleStringProperty(cpf);
        });

        colNomeUsuario.setCellValueFactory(cellData ->
        {
            Usuario usuario = cellData.getValue();
            String login = usuario.getLogin();
            return new SimpleStringProperty(login);
        });

        colDataNascimento.setCellValueFactory(cellData ->
        {
            Usuario usuario = cellData.getValue();
            LocalDate dataNascimento = usuario.getDataNascimento();
            return new SimpleObjectProperty<>(dataNascimento).asString();
        });
        colTelefone.setCellValueFactory(cellData ->
        {
            Usuario usuario = cellData.getValue();
            String telefone = usuario.getTelefone();
            return new SimpleStringProperty(telefone);
        });

        colTipo.setCellValueFactory(cellData ->
        {
            Usuario usuario = cellData.getValue();
            String tipo = null;

            if(usuario instanceof Funcionario)
            {
                if(((Funcionario) usuario).isAdm())
                {
                    tipo = "Administrador";
                }
                else
                {
                    tipo = usuario.getClass().getSimpleName();
                }
                return new SimpleStringProperty(tipo);
            }
            else if (usuario instanceof Fornecedor)
            {
                tipo = usuario.getClass().getSimpleName();
            }
            return new SimpleStringProperty(tipo);
        });
        colADM.setCellValueFactory(cellData ->
        {
            Usuario usuario = cellData.getValue();
            String adm = null;
            if(usuario instanceof Funcionario)
            {
                adm = ((Funcionario) usuario).getAdmResponsavel().getNome();
            }
            return new SimpleStringProperty(adm);
        });
    }

    public void carregarDados()
    {
        tvUsuarios.setItems(FXCollections.observableArrayList(listarFornecedorFuncionario()));
        cbTipo.setItems(FXCollections.observableArrayList(cargos));

        if(tipoFornecedor.isEmpty()){
            this.tipoFornecedor = Arrays.asList(TipoFornecedor.values());
            List<String> nomesTipoFornecedor = new ArrayList<>();

            for(TipoFornecedor tipoFornecedor1 : tipoFornecedor){
                nomesTipoFornecedor.add(tipoFornecedor1.getDescricaoEnum());
            }
            cbTipoFornecedor.getItems().addAll(nomesTipoFornecedor);
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

    private TipoFornecedor procurarTipoFornecedorPeloNome(String nomeTipoFornecedor){
        boolean achou = false;
        TipoFornecedor tipoFornecedorSelecionado = null;

        for (int i = 0; i < tipoFornecedor.size() && !achou; i++){
            if(tipoFornecedor.get(i).getDescricaoEnum().equals(nomeTipoFornecedor)){
                achou = true;
                tipoFornecedorSelecionado = tipoFornecedor.get(i);
            }
        }

        return tipoFornecedorSelecionado;
    }

    //gets and sets:
    public static AdmCRUDUsuariosController getInstance() {
        return instance;
    }

    public void setIgnorarInitialize(boolean ignorarInitialize) {
        this.ignorarInitialize = ignorarInitialize;
    }
}