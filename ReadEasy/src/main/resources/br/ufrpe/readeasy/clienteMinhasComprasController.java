import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class clienteMinhasComprasController
{
    public Application app;

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnCatalogo;

    @FXML
    private Button btnHistorico;

    @FXML
    private Button btnSair;

    @FXML
    private Button btnPesquisar;

    @FXML
    private DatePicker DPDataInicio;

    @FXML
    private DatePicker DPDataFim;

    @FXML
    private TableView TVTabelaCompras;

    @FXML
    private TableColumn ColTitulo;

    @FXML
    private TableColumn ColAutor;

    @FXML
    private TableColumn ColQTD;

    @FXML
    private TableColumn ColPreco;

    @FXML
    private TableColumn ColDataCompra;
}