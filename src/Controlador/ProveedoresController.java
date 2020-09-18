/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import static Controlador.ProductosController.id;
import Modelo.Productos;
import Modelo.Proveedores;
import Modelo.Usuarios;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Francisco
 */
public class ProveedoresController implements Initializable {

    @FXML
    public Button btnModalProveedor;
    @FXML
    public Button btnEditar;
    @FXML
    public Button btnBaja;
    @FXML
    private Button btnEliminar;
    @FXML
    private TableView<Proveedores> tablaProveedores;
    @FXML
    private TableColumn razon_socialCol;
    @FXML
    private TableColumn direccionCol;
    @FXML
    private TableColumn telefonoCol;
    @FXML
    private TableColumn statusCol;

    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableColumn correoCol;
    public static String titulo;
    public static String tituloBoton;
    public static int idProveedor;
    public static String razon_social;
    public static String telefono;
    public static String direccion;
    public static String correo;
    private ObservableList<Proveedores> proveedores;
    private String status;
    static Stage ventanaInicio;
    static FXMLLoader loaderInicioAdmin;
    String path = "/Imagenes/icono.jpeg";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EnviarConEnter();
        proveedores = FXCollections.observableArrayList();
        try {
            this.inicializarTablaProveedores();
        } catch (SQLException ex) {
            Logger.getLogger(ProveedoresController.class.getName()).log(Level.SEVERE, null, ex);
        }

        btnEditar.setDisable(true);
        btnBaja.setDisable(true);
        btnEliminar.setDisable(true);
        TimerTask timerTask = new TimerTask() {
            public void run() {
                try {
                    inicializarTablaProveedores();
                } catch (SQLException ex) {
                    Logger.getLogger(ProveedoresController.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnEditar.setDisable(true);
                btnBaja.setDisable(true);
                btnEliminar.setDisable(true);
            }
        };

        // Aquí se pone en marcha el timer cada segundo. 
        Timer timer = new Timer();
        // Dentro de 0 milisegundos avísame cada 1 minutos 
        timer.scheduleAtFixedRate(timerTask, 0, 15000);

    }

    public void abrirVentanaRegistro() throws IOException {
        titulo = "Registrar Proveedor";
        tituloBoton = "Registrar";
        loaderInicioAdmin = new FXMLLoader(getClass().getResource("/Vista/RegistroProveedor.fxml"));

        Parent root1 = (Parent) loaderInicioAdmin.load();
        ventanaInicio = new Stage();
        ProveedoresController.ventanaInicio.getIcons().add(new Image(String.valueOf(getClass().getResource(path))));
        ventanaInicio.setScene(new Scene(root1));
        ventanaInicio.setResizable(false);
        ventanaInicio.show();
    }

    public void inicializarTablaProveedores() throws SQLException {

        proveedores = FXCollections.observableArrayList();
        Proveedores.llenarInfoProveedores(proveedores);
        tablaProveedores.setItems(proveedores);
        razon_socialCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("razon_socialT"));
        direccionCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("direccionT"));
        telefonoCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("telefonoT"));
        correoCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("correoT"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("statusT"));

    }

    public void actualizarProveedor() throws IOException {
        titulo = "Actualizar Proveedor";
        tituloBoton = "Actualizar";
        loaderInicioAdmin = new FXMLLoader(getClass().getResource("/Vista/RegistroProveedor.fxml"));
        Parent root1 = (Parent) loaderInicioAdmin.load();
        ventanaInicio = new Stage();
        ProveedoresController.ventanaInicio.getIcons().add(new Image(String.valueOf(getClass().getResource(path))));
        ventanaInicio.setScene(new Scene(root1));
        ventanaInicio.setResizable(false);
        ventanaInicio.show();
    }

    public void seleccionProveedor() {
        btnEditar.setDisable(false);
        btnBaja.setDisable(false);
        btnEliminar.setDisable(false);
        Proveedores proveedor = tablaProveedores.getSelectionModel().getSelectedItem();
        idProveedor = proveedor.getId();
        razon_social = proveedor.getRazon_socialT();
        direccion = proveedor.getDireccionT();
        telefono = proveedor.getTelefonoT();
        correo = proveedor.getCorreoT();
        status = proveedor.getStatusT();
        if (status.equals("Alta")) {
            btnBaja.setText("Dar de baja");
        } else {
            btnBaja.setText("Reactivar proveedor");
        }

    }

    public void buscarProveedores(Event event) {
        String valor = txtBusqueda.getText();
        proveedores = FXCollections.observableArrayList();
        Proveedores.buscarProveedores(proveedores, valor);
        tablaProveedores.setItems(proveedores);
        razon_socialCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("razon_socialT"));
        direccionCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("direccionT"));
        telefonoCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("telefonoT"));
        correoCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("correoT"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("statusT"));

    }

    public void EnviarConEnter() {

        txtBusqueda.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    buscarProveedores(event);
                }
            }
        });
    }

    public void bajaProveedores() throws SQLException {
        Proveedores proveedor = new Proveedores();
        if (btnBaja.getText().equals("Dar de baja")) {
            proveedor.darDeBaja(idProveedor);
            this.inicializarTablaProveedores();
        } else {
            proveedor.reactivar(idProveedor);
            this.inicializarTablaProveedores();
        }
        btnEditar.setDisable(true);
        btnBaja.setDisable(true);
        btnEliminar.setDisable(true);
    }

    public void cleanSelect() throws SQLException {
        btnEditar.setDisable(true);
        btnBaja.setDisable(true);
        btnEliminar.setDisable(true);
        tablaProveedores.getSelectionModel().clearSelection();
        inicializarTablaProveedores();
    }

    public void eliminar() throws SQLException {
        Alert dialogoAlerta = new Alert(Alert.AlertType.CONFIRMATION, "Confirmación de eliminado de proveedor", ButtonType.YES, ButtonType.NO);
        dialogoAlerta.setTitle("Advertencia");
        dialogoAlerta.setHeaderText("SE ELIMINARÁ EL PROVEEDOR SELECCIONADO");
        dialogoAlerta.setContentText("Si desea continuar el proveedor quedará eliminado");
        dialogoAlerta.initStyle(StageStyle.UTILITY);
        dialogoAlerta.showAndWait();
        if (dialogoAlerta.getResult() == ButtonType.YES) {
            Proveedores proveedor = new Proveedores();
            proveedor.eliminar(idProveedor);
            inicializarTablaProveedores();
            cleanSelect();
        } else {
            cleanSelect();
        }
    }
}
