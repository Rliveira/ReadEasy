package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.*;
import br.ufrpe.readeasy.business.ServidorReadEasy;
import br.ufrpe.readeasy.exceptions.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdmCRUDUsuariosController
{
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
private ComboBox<TipoFornecedor> cbTipoFornecedor;

ServidorReadEasy servidorReadEasy = ServidorReadEasy.getInstance();
private ObservableList<String> cargos = FXCollections.observableArrayList("Funcionário",
        "Administrador", "Fornecedor");

@FXML
public void onBtnCadastrarUsuariosclick(ActionEvent event){
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

    if(validarInputTf(cepString) || validarInputTf(telefone) || validarInputTf(cpf))
    {
        alert.setTitle("Erro");
        alert.setHeaderText("Campos de telefone, CPF ou CEP contém letras ou caracteres especiais.");
        alert.setContentText("Digite apenas números nos campos para continuar.");

        ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                alert.close();
            }
        });
    }
    else{
        int cep = 0;
        if(!cepString.isEmpty())
        {
            cep = Integer.parseInt(cepString);
        }
        Endereco endereco = new Endereco(cep, rua, bairro, cidade, estado);

        String tipoUsuarioSelecionado = cbTipo.getValue();

        if(tipoUsuarioSelecionado == null){
            alert.setTitle("Erro");
            alert.setHeaderText("Tipo de usuário não selecionado.");
            alert.setContentText("Selecione uma opção no tipo de usuário para continuar.");

            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                    alert.close();
                }
            });
        }
        else{
            Usuario usuario;

            //montagem do usuario:
            if(tipoUsuarioSelecionado.equals("Administrador")){
                usuario = new Funcionario(nome,cpf, dataNascimento,login, senha, endereco, telefone, true,
                        (Funcionario) SessaoUsuario.getUsuarioLogado());
            }
            else if(tipoUsuarioSelecionado.equals("Fornecedor")){
                TipoFornecedor tipoFornecedor = cbTipoFornecedor.getValue();
                usuario = new Fornecedor(nome,cpf, dataNascimento,login, senha, endereco, telefone,tipoFornecedor);
            }
            else{   //funcionário comum.
                usuario = new Funcionario(nome,cpf, dataNascimento,login, senha, endereco, telefone, false,
                        (Funcionario) SessaoUsuario.getUsuarioLogado());
            }
            try {
                servidorReadEasy.cadastrarUsuario(usuario);
            }catch (TipoUsuarioInvalidoException e){
                excecaoLevantada = true;
                alert.setTitle("Erro");
                alert.setHeaderText("Tipo de usuário inválido");
                alert.setContentText("Selecione uma opção no tipo de usuário válido para continuar.");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
            }
            catch (MenorDeIdadeException e){
                excecaoLevantada = true;
                alert.setTitle("Erro");
                alert.setHeaderText("Usuário menor de idade");
                alert.setContentText("Cadastre um usuário maior de idade para continuar.");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
            }
            catch(DataInvalidaException e){
                excecaoLevantada = true;
                alert.setTitle("Erro");
                alert.setHeaderText("Data Inválida!");
                alert.setContentText("A data de nascimento selecionada é posterior a data atual.");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
            }
            catch (CampoVazioException e ){
                excecaoLevantada = true;
                alert.setTitle("Erro");
                alert.setHeaderText("Campo de texto vazio!");
                alert.setContentText("Preencha todos os campos do cadastro.");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
            }
            catch (UsuarioExistenteException e){
                excecaoLevantada = true;
                alert.setTitle("Erro");
                alert.setHeaderText("Usuário existente!");
                alert.setContentText("Este usuário já está cadastrado no sistema.");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
            }
            catch (UsuarioNuloException e){
                excecaoLevantada = true;
                alert.setTitle("Erro");
                alert.setHeaderText("Todos os campos de texto vazio!");
                alert.setContentText("Preencha todos os campos do cadastro.");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
            }
            if(!excecaoLevantada){
                onAtualizarTabelaclick();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText("Cadastro concluído!");
                alert.setContentText("Este usuário foi cadastrado no sistema");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
            }
        }
    }

}

@FXML
public void onBtnEditarUsuarioclick(ActionEvent event) throws TipoUsuarioInvalidoException, UsuarioExistenteException,
    UsuarioInexistenteException, UsuarioNuloException, DataInvalidaException {
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

if(validarInputTf(cepString) || validarInputTf(telefone)){
    alert.setTitle("Erro");
    alert.setHeaderText("Campo de telefone, CPF ou cep contém letras ou caracteres especiais.");
    alert.setContentText("Digite apenas números nos campos para continuar.");

    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
    alert.getButtonTypes().setAll(okButton);

    alert.showAndWait().ifPresent(buttonType -> {
        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
            alert.close();
        }
    });
}
else{
    int cep = 0;
    if(cepString.isEmpty())
    {
        cep = Integer.parseInt(cepString);
    }
    Endereco endereco = new Endereco(cep, rua, bairro, cidade, estado);

    String tipoUsuarioSelecionado = cbTipo.getSelectionModel().getSelectedItem();
    if(tipoUsuarioSelecionado == null){
        alert.setTitle("Erro");
        alert.setHeaderText("Tipo de usuário não selecionado.");
        alert.setContentText("Selecione uma opção no tipo de usuário para continuar.");

        ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                alert.close();
            }
        });
    }
    else
    {
        Usuario usuario = tvUsuarios.getSelectionModel().getSelectedItem();
        if (usuario == ServidorReadEasy.getInstance().listarAdms().get(0))
        {
            alert.setTitle("Erro");
            alert.setHeaderText("Edição inválida!");
            alert.setContentText("Não é possível editar o ADM Inicial");
            alert.show();
            return;
        }
        else
        {
            if (usuario instanceof Fornecedor && tipoUsuarioSelecionado.equals(cargos.get(0)) ||
                    usuario instanceof Funcionario && tipoUsuarioSelecionado.equals(cargos.get(2))
                    || usuario instanceof Fornecedor && tipoUsuarioSelecionado.equals(cargos.get(1)))
            {
                excecaoLevantada = true;
                alert.setTitle("Erro");
                alert.setHeaderText("Edição inválida!");
                alert.setContentText("Não é possível fazer conversão entre fornecedor e funcionário.");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType ->
                {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES)
                    {
                        alert.close();
                    }
                });
            }
            try {
                if (usuario instanceof Funcionario) {
                    if (((Funcionario) usuario).isAdm())
                    {
                        ServidorReadEasy.getInstance().atualizarFuncionario(usuario, usuario.getNome(),
                                usuario.getCpf(), usuario.getDataNascimento(), usuario.getLogin(), usuario.getSenha(),
                                usuario.getEndereco(), usuario.getTelefone(), true,
                                ((Funcionario) usuario).getAdmResponsavel());
                        limparCampos();
                    } else
                    {
                        ServidorReadEasy.getInstance().atualizarFuncionario(usuario, usuario.getNome(),
                                usuario.getCpf(), usuario.getDataNascimento(), usuario.getLogin(), usuario.getSenha(),
                                usuario.getEndereco(), usuario.getTelefone(), false,
                                ((Funcionario) usuario).getAdmResponsavel());
                        limparCampos();
                    }
                }

                if (usuario instanceof Fornecedor)
                {
                    ServidorReadEasy.getInstance().atualizarFornecedor(usuario, usuario.getNome(),
                            usuario.getCpf(), usuario.getDataNascimento(), usuario.getLogin(), usuario.getSenha(),
                            usuario.getEndereco(), usuario.getTelefone(), ((Fornecedor) usuario).getTipoFornecedor());
                    limparCampos();
                }
                onAtualizarTabelaclick();
            } catch (TipoUsuarioInvalidoException e) {
                excecaoLevantada = true;
                alert.setTitle("Erro");
                alert.setHeaderText("Tipo de usuário inválido");
                alert.setContentText("Selecione um tipo de usuário válido para continuar.");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
            } catch (DataInvalidaException e) {
                excecaoLevantada = true;
                alert.setTitle("Erro");
                alert.setHeaderText("Data Inválida!");
                alert.setContentText("A data de nascimento selecionada é posterior a data atual.");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
            } catch (UsuarioNuloException e) {
                excecaoLevantada = true;
                alert.setTitle("Erro");
                alert.setHeaderText("Usuário Nulo!");
                alert.setContentText("Preencha todos os campos de texto.");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
            } catch (UsuarioInexistenteException e) {
                excecaoLevantada = true;
                alert.setTitle("Erro");
                alert.setHeaderText("Usuário não existe");
                alert.setContentText("Este usuário não está cadastrado no sistema;");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
            } catch (UsuarioExistenteException e) {
                excecaoLevantada = true;
                alert.setTitle("Erro");
                alert.setHeaderText("Usuário existente!");
                alert.setContentText("Este usuário já está cadastrado no sistema.");

                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                        alert.close();
                    }
                });
            }
        }
        if (!excecaoLevantada) {
            onAtualizarTabelaclick();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Edição concluída!");
            alert.setContentText("Edição de usuário realizada com êxito.");

            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.YES) {
                    alert.close();
                }
            });
        }
    }
    }
}

@FXML
public void onSelecionarItemTVclick(ActionEvent event)
{
    popularCamposDoUsuarioSelecionado();
}

@FXML
public void onDeletarUsuarioclick(ActionEvent event) throws UsuarioNuloException, UsuarioInexistenteException {
    Alert alert = null;
    if (tvUsuarios.getSelectionModel().getSelectedItem() == ServidorReadEasy.getInstance().listarAdms().get(0))
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
                try {
                    servidorReadEasy.removerUsuario(tvUsuarios.getSelectionModel().getSelectedItem());
                    onAtualizarTabelaclick();

                } catch (UsuarioNuloException e) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setContentText(e.getMessage());
                    alert1.setHeaderText(null);
                    alert1.setHeaderText("Usuário nulo");
                    try {
                        throw new UsuarioNuloException();
                    } catch (UsuarioNuloException ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (UsuarioInexistenteException e) {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setContentText(e.getMessage());
                    alert2.setHeaderText(null);
                    alert2.setHeaderText("Usuário nulo");

                    try {
                        throw new UsuarioInexistenteException((tvUsuarios.getSelectionModel().getSelectedItem()).getCpf());
                    } catch (UsuarioInexistenteException ex) {
                        throw new RuntimeException(ex);
                    }
                }

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
public void onSelecionarTipoUsuarioclick(ActionEvent event)
{
    if(cbTipo.getSelectionModel().getSelectedItem().equals("Fornecedor"))
    {
        cbTipoFornecedor.setVisible(true);
    }
    else
    {
        cbTipoFornecedor.setVisible(false);
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
        if (itemSelecionado instanceof Funcionario) {
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
    }
    if(usuario instanceof Fornecedor)
    {
        cbTipo.setValue(cargos.get(2));
        cbTipoFornecedor.setValue(((Fornecedor) usuario).getTipoFornecedor());
    }
    if(usuario instanceof Cliente)
    {
        cbTipo.setValue(cargos.get(3));
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
    fornecedorFuncionario.addAll(ServidorReadEasy.getInstance().listarAdms());
    fornecedorFuncionario.addAll(ServidorReadEasy.getInstance().listarFornecedores());
    fornecedorFuncionario.addAll(ServidorReadEasy.getInstance().listarFuncionarios());
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
    cbTipoFornecedor.setItems(FXCollections.observableArrayList(TipoFornecedor.values()));
}

@FXML
public void initialize()
{
    construirTabela();
    carregarDados();

    tvUsuarios.setRowFactory(tv ->
    {
        TableRow<Usuario> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            popularCamposDoUsuarioSelecionado();
            if (event.getClickCount() == 2 && !row.isEmpty()) {
                Usuario usuarioSelecionado = row.getItem();

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Selecione se você deseja Editar ou Deletar este usuário.");
                alert.showAndWait();

                row.setOnMouseClicked(secondEvent -> {
                    if (secondEvent.getClickCount() == 1) {
                        if (secondEvent.getTarget() instanceof Button) {
                            Button clickedButton = (Button) secondEvent.getTarget();
                            if (clickedButton == btnDeletar) {
                                try {
                                    onDeletarUsuarioclick(new ActionEvent(clickedButton, btnDeletar));
                                } catch (UsuarioNuloException | UsuarioInexistenteException e) {
                                    e.printStackTrace();
                                }
                            } else if (clickedButton == btnEditar) {
                                try {
                                    onBtnEditarUsuarioclick(new ActionEvent(clickedButton, btnEditar));
                                } catch (TipoUsuarioInvalidoException | UsuarioInexistenteException |
                                         UsuarioExistenteException | UsuarioNuloException | DataInvalidaException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
            }
        });
        return row;
    });
}

private boolean validarInputTf(String inputTf) {
    return inputTf.matches("\\d");
}

//Métodos de troca de tela:
@FXML
public void trocarTelaEstoqueAdm(){
    ScreenManager sm = ScreenManager.getInstance();
    sm.TrocarTela("admEstoque.fxml", "ReadEasy - Estoque");
}

@FXML
public void trocarTelaHistoricoAdm(){
    ScreenManager sm = ScreenManager.getInstance();
    sm.TrocarTela("admRelatorios.fxml", "ReadEasy - Relatorios");
}

@FXML
public void trocarTelaLivrosAdm(){
    ScreenManager sm = ScreenManager.getInstance();
    sm.TrocarTela("admLivros.fxml", "ReadEasy - Livros");
}

@FXML
public void trocarTelaPerfilAdm(){
    ScreenManager sm = ScreenManager.getInstance();
    sm.TrocarTela("admPerfil.fxml", "ReadEasy - Perfil");
}

@FXML
public void trocarTelaPromocoesAdm(){
    ScreenManager sm = ScreenManager.getInstance();
    sm.TrocarTela("admCRUDPromocoes.fxml", "ReadEasy - Promoções");
}

@FXML
public void trocarTelaRelatoriosAdm(){
    ScreenManager sm = ScreenManager.getInstance();
    sm.TrocarTela("admRelatorios.fxml", "ReadEasy - Relatórios");
}

@FXML
private void trocarTelaLogin(){
    ScreenManager sm = ScreenManager.getInstance();
    sm.TrocarTela("Login.fxml", "ReadEasy - Login");
}

//Outros métodos:
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
        if (buttonType.getButtonData() == ButtonBar.ButtonData.YES)
        {
            SessaoUsuario.logOut();
            trocarTelaLogin();
        }
        else {
            alert.close();
        }
    });
}
}