/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import static Controlador.ProductosController.loaderInicioAdmin;
import static Controlador.ProveedoresController.idProveedor;

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
public class UsuariosController implements Initializable {

    @FXML
    public Button btnModalUsuario;
    static Stage ventanaInicio;
    static FXMLLoader loaderInicioAdmin;
    @FXML
    private Button btnEliminar;
    @FXML
    private TableView<Usuarios> tablaUsuarios;
    @FXML
    private TableColumn nombreCol;
    @FXML
    private TableColumn apellido_paternoCol;
    @FXML
    private TableColumn apellido_maternoCol;
    @FXML
    private TableColumn usuarioCol;
    @FXML
    private TableColumn rolCol;
    @FXML
    private TableColumn statusCol;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnBaja;
    private ObservableList<Usuarios> usuarios;
    public static int id;
    public static String nombre, apellido_paterno, apellido_materno, rol, username;
    private String status;
    String path = "/Imagenes/icono.jpeg";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        usuarios = FXCollections.observableArrayList();
        EnviarConEnter();
        try {
            this.inicializarTablaUsuarios();
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        btnEditar.setDisable(true);
        btnBaja.setDisable(true);
        btnEliminar.setDisable(true);
        TimerTask timerTask = new TimerTask() {
            public void run() {
                try {
                    inicializarTablaUsuarios();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
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
        InicioSesionController.ventanaOrigen = "Usuarios";
        loaderInicioAdmin = new FXMLLoader(getClass().getResource("/Vista/RegistroUsuario.fxml"));
        Parent root1 = (Parent) loaderInicioAdmin.load();
        ventanaInicio = new Stage();
        UsuariosController.ventanaInicio.getIcons().add(new Image(String.valueOf(getClass().getResource(path))));
        ventanaInicio.setScene(new Scene(root1));
        ventanaInicio.setResizable(false);
        ventanaInicio.show();
    }

    public void inicializarTablaUsuarios() throws SQLException {
        usuarios = FXCollections.observableArrayList();
        Usuarios.llenarInfoUsuarios(usuarios);
        tablaUsuarios.setItems(usuarios);
        nombreCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("nombreT"));
        apellido_paternoCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("apellidoPaternoT"));
        apellido_maternoCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("apellidoMaternoT"));
        usuarioCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("usuarioT"));
        rolCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("tipo_usuarioT"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("statusT"));
    }

    public void buscarUsuarios(Event event) {
        String valor = txtBusqueda.getText();
        usuarios = FXCollections.observableArrayList();
        Usuarios.buscarUsuarios(usuarios, valor);
        tablaUsuarios.setItems(usuarios);
        nombreCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("nombreT"));
        apellido_paternoCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("apellidoPaternoT"));
        apellido_maternoCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("apellidoMaternoT"));
        usuarioCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("usuarioT"));
        rolCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("tipo_usuarioT"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("statusT"));
    }

    public void EnviarConEnter() {

        txtBusqueda.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    buscarUsuarios(event);
                }
            }
        });
    }

    public void actualizarUsuario() throws IOException {
        InicioSesionController.ventanaOrigen = "Usuarios";
        loaderInicioAdmin = new FXMLLoader(getClass().getResource("/Vista/ActualizacionUsuario.fxml"));
        
        Parent root1 = (Parent) loaderInicioAdmin.load();
        ventanaInicio = new Stage();
        UsuariosController.ventanaInicio.getIcons().add(new Image(String.valueOf(getClass().getResource(path))));
        ventanaInicio.setScene(new Scene(root1));
        ventanaInicio.setResizable(false);
        ventanaInicio.show();
    }

    public void seleccionUsuario() {
        btnEditar.setDisable(false);
        btnBaja.setDisable(false);
        btnEliminar.setDisable(false);
        Usuarios usuario = tablaUsuarios.getSelectionModel().getSelectedItem();
        id = usuario.getId();
        nombre = usuario.getNombreT();
        apellido_paterno = usuario.getApellidoPaternoT();
        apellido_materno = usuario.getApellidoMaternoT();
        username = usuario.getUsuarioT();
        rol = usuario.getTipo_usuarioT();
        status = usuario.getStatusT();
        if (status.equals("Alta")) {
            btnBaja.setText("Dar de baja");
        } else {
            btnBaja.setText("Reactivar usuario");
        }
    }

    public void bajaUsuarios() throws SQLException {
        Usuarios usuario = new Usuarios();
        if (btnBaja.getText().equals("Dar de baja")) {
            usuario.darDeBaja(id);
            this.inicializarTablaUsuarios();
        } else {
            usuario.reactivar(id);
            this.inicializarTablaUsuarios();
        }
        btnEditar.setDisable(true);
        btnBaja.setDisable(true);
        btnEliminar.setDisable(true);

    }

    public void cleanSelect() throws SQLException {
        btnEditar.setDisable(true);
        btnBaja.setDisable(true);
        btnEliminar.setDisable(true);
        tablaUsuarios.getSelectionModel().clearSelection();
        inicializarTablaUsuarios();
    }
    public void eliminar() throws SQLException{
    Alert dialogoAlerta = new Alert(Alert.AlertType.CONFIRMATION, "Confirmación de eliminado de usuario", ButtonType.YES, ButtonType.NO);
        dialogoAlerta.setTitle("Advertencia");
        dialogoAlerta.setHeaderText("SE ELIMINARÁ EL USUARIO SELECCIONADO");
        dialogoAlerta.setContentText("Si desea continuar el usuario quedará eliminado");
        dialogoAlerta.initStyle(StageStyle.UTILITY);
        dialogoAlerta.showAndWait();
        if (dialogoAlerta.getResult() == ButtonType.YES) {
            Usuarios usuario = new Usuarios();
            usuario.eliminar(id);
            inicializarTablaUsuarios();
            cleanSelect();
        } else {
            cleanSelect();
        }
    }
}
