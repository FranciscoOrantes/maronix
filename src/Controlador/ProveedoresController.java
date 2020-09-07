/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Proveedores;
import Modelo.Usuarios;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

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
        this.inicializarTablaProveedores();

        btnEditar.setDisable(true);
        btnBaja.setDisable(true);

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

    public void inicializarTablaProveedores() {

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
    }

    public void cleanSelect() {
        btnEditar.setDisable(true);
        btnBaja.setDisable(true);
        tablaProveedores.getSelectionModel().clearSelection();
        inicializarTablaProveedores();
    }
}
