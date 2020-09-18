/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import static Controlador.ProveedoresController.loaderInicioAdmin;
import static Controlador.ProveedoresController.titulo;
import Modelo.InicioSesion;
import Modelo.Productos;
import static Modelo.Productos.respuesta;
import Modelo.Proveedores;
import Modelo.Reportes;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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
import javafx.scene.Node;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Francisco
 */
public class ProductosController implements Initializable {

    static Stage ventanaInicio;
    static FXMLLoader loaderInicioAdmin;
    @FXML
    private TextField txtBusqueda;
    @FXML
    public TableView<Productos> tablaProductos;
    @FXML
    public TableColumn codigoCol;

    @FXML
    public TableColumn descripcionCol;
    @FXML
    public TableColumn precioCol;
    @FXML
    public TableColumn cantidadCol;
    @FXML
    public TableColumn proveedorCol;
    @FXML
    public TableColumn statusCol;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnCodigo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnBaja;
    @FXML
    private AnchorPane pane;
    public static String codigo, descripcion, proveedor;
    public static Double precio;
    public static int cantidad, id;
    public static String tituloText;
    public static String tituloBoton;
    public static String tituloCombo;
    private String status;
    String path = "/Imagenes/icono.jpeg";
    private ObservableList<Productos> productos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EnviarConEnter();
        productos = FXCollections.observableArrayList();
        try {
            this.inicializarTablaProductos();
        } catch (SQLException ex) {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        btnEditar.setDisable(true);
        btnBaja.setDisable(true);
        btnCodigo.setDisable(true);
        btnEliminar.setDisable(true);
        if (InicioSesion.rol.equals("Cajero")) {
            btnBaja.setVisible(false);
            btnEditar.setVisible(false);
            btnEliminar.setVisible(false);
        }
        TimerTask timerTask = new TimerTask() {
            public void run() {
                try {
                    inicializarTablaProductos();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnEditar.setDisable(true);
                btnBaja.setDisable(true);
                btnCodigo.setDisable(true);
                btnEliminar.setDisable(true);
            }
        };

        // Aquí se pone en marcha el timer cada segundo. 
        Timer timer = new Timer();
        // Dentro de 0 milisegundos avísame cada 1 minutos 
        timer.scheduleAtFixedRate(timerTask, 0, 15000);

    }

    public void abrirVentanaRegistro() throws IOException {
        tituloText = "Registrar Producto";
        tituloBoton = "Registrar";
        tituloCombo = "Proveedor";
        loaderInicioAdmin = new FXMLLoader(getClass().getResource("/Vista/RegistroProducto.fxml"));

        Parent root1 = (Parent) loaderInicioAdmin.load();
        ventanaInicio = new Stage();
        ProductosController.ventanaInicio.getIcons().add(new Image(String.valueOf(getClass().getResource(path))));
        ventanaInicio.setResizable(false);
        ventanaInicio.setScene(new Scene(root1));

        ventanaInicio.show();
    }

    public void inicializarTablaProductos() throws SQLException {
        productos = FXCollections.observableArrayList();
        Productos.llenarInfoProductos(productos);
        tablaProductos.setItems(productos);
        codigoCol.setCellValueFactory(new PropertyValueFactory<Productos, String>("codigoT"));

        descripcionCol.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionT"));
        precioCol.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioT"));
        cantidadCol.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("cantidadT"));
        proveedorCol.setCellValueFactory(new PropertyValueFactory<Productos, String>("proveedorT"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Productos, String>("statusT"));
    }

    public void buscarProductos(Event event) throws SQLException {
        String valor = txtBusqueda.getText();
        productos = FXCollections.observableArrayList();
        Productos.filtradoBuscar(productos, valor);
        tablaProductos.setItems(productos);
        codigoCol.setCellValueFactory(new PropertyValueFactory<Productos, String>("codigoT"));

        descripcionCol.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionT"));
        precioCol.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioT"));
        cantidadCol.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("cantidadT"));

        proveedorCol.setCellValueFactory(new PropertyValueFactory<Productos, String>("proveedorT"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Productos, String>("statusT"));
    }

    public void EnviarConEnter() {

        txtBusqueda.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        buscarProductos(event);
                    } catch (SQLException ex) {
                        Logger.getLogger(InicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    public void seleccionProducto() {
        btnEditar.setDisable(false);
        btnBaja.setDisable(false);
        btnCodigo.setDisable(false);
        btnEliminar.setDisable(false);
        Productos producto = tablaProductos.getSelectionModel().getSelectedItem();
        id = producto.getId();
        codigo = producto.getCodigoT();
        descripcion = producto.getDescripcionT();
        proveedor = producto.getProveedorT();
        precio = producto.getPrecioT();
        cantidad = producto.getCantidadT();
        status = producto.getStatusT();
        if (status.equals("Alta")) {
            btnBaja.setText("Dar de baja");
        } else {
            btnBaja.setText("Reactivar producto");
        }
    }

    public void actualizarProducto() throws IOException {
        tituloText = "Actualizar Producto";
        tituloBoton = "Actualizar";
        tituloCombo = proveedor;
        loaderInicioAdmin = new FXMLLoader(getClass().getResource("/Vista/ActualizacionProducto.fxml"));
        Parent root1 = (Parent) loaderInicioAdmin.load();
        ventanaInicio = new Stage();
        ProductosController.ventanaInicio.getIcons().add(new Image(String.valueOf(getClass().getResource(path))));
        ventanaInicio.setScene(new Scene(root1));
        ventanaInicio.setResizable(false);
        ventanaInicio.show();
    }

    public void bajaProducto() throws SQLException {
        Productos producto = new Productos();
        if (btnBaja.getText().equals("Dar de baja")) {
            producto.darDeBaja(id);
            this.inicializarTablaProductos();
        } else {
            producto.reactivar(id);
            this.inicializarTablaProductos();
        }
        btnEditar.setDisable(true);
        btnBaja.setDisable(true);
        btnEliminar.setDisable(true);
    }

    public void cleanSelect() throws SQLException {
        btnEditar.setDisable(true);
        btnBaja.setDisable(true);
        btnCodigo.setDisable(true);
        btnEliminar.setDisable(true);
        tablaProductos.getSelectionModel().clearSelection();
        inicializarTablaProductos();
    }

    public void generarCodigo(Event event) throws FileNotFoundException, IOException, SQLException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Productos producto = new Productos();
        String ruta = producto.obtenerRutaCodigo(id);
        Reportes reportes = new Reportes();
        reportes.abrirCodigoBarrasEnMenu(id,codigo,descripcion,cantidad,stage,producto,precio,ruta);

    }

    public void eliminar() throws SQLException {
        Alert dialogoAlerta = new Alert(Alert.AlertType.CONFIRMATION, "Confirmación de eliminado de producto", ButtonType.YES, ButtonType.NO);
        dialogoAlerta.setTitle("Advertencia");
        dialogoAlerta.setHeaderText("SE ELIMINARÁ EL PRODUCTO SELECCIONADO");
        dialogoAlerta.setContentText("Si desea continuar el producto quedará eliminado");
        dialogoAlerta.initStyle(StageStyle.UTILITY);
        dialogoAlerta.showAndWait();
        if (dialogoAlerta.getResult() == ButtonType.YES) {
            Productos producto = new Productos();
            producto.eliminar(id);
            inicializarTablaProductos();
            cleanSelect();
        } else {
            cleanSelect();
        }
    }

}
