/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.InicioSesion;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Francisco
 */
public class VentanaInicialController implements Initializable {

    @FXML
    public Button btnProductos;
    public BorderPane panePrincipal;
    public static Stage ventanaInicio;
    public static FXMLLoader loaderInicioAdmin;
    String path = "/Imagenes/icono.jpeg";
    @FXML
    public Text txtFecha;
    @FXML
    public HBox hBoxUsuarios;
    @FXML
    public HBox hBoxProveedores;
    @FXML
    public Text txtNombreUsuario;
    private String cadenaBienvenida;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        obtenerFecha();
        cadenaBienvenida = "Bienvenido " + InicioSesion.rol + ": " + InicioSesion.nombreCompleto;
        txtNombreUsuario.setText(cadenaBienvenida);
        if (InicioSesion.rol.equals("Cajero")) {
            hBoxUsuarios.setVisible(false);
            hBoxProveedores.setVisible(false);
        }
    }

    @FXML
    public void cambiarVista() {
        System.out.println("ENTRO AQUI ");
        FXMLoader objeto = new FXMLoader();
        AnchorPane vista = objeto.getPage("Productos");
        System.out.println(vista);
        panePrincipal.setCenter(vista);
    }

    @FXML
    public void cambiarVistaUsuarios() {
        System.out.println("VISTA USUARIOS");
        FXMLoader objeto = new FXMLoader();
        AnchorPane vista = objeto.getPage("Usuarios");
        panePrincipal.setCenter(vista);
    }

    @FXML
    public void cambiarVistaProveedores() {
        FXMLoader objeto = new FXMLoader();
        AnchorPane vista = objeto.getPage("Proveedores");
        panePrincipal.setCenter(vista);
    }

    @FXML
    public void cambiarVistaVentas() {
        FXMLoader objeto = new FXMLoader();
        AnchorPane vista = objeto.getPage("Ventas");
        panePrincipal.setCenter(vista);
    }

    @FXML
    public void cambiarVistaCorteCaja() {
        FXMLoader objeto = new FXMLoader();
        AnchorPane vista = objeto.getPage("CorteCaja");
        panePrincipal.setCenter(vista);
    }

    public void obtenerFecha() {
        Date objDate = new Date(); // Sistema actual La fecha y la hora se asignan a objDate 
        String strDateFormat = "dd-MM-yyyy"; // El formato de fecha está especificado  
        SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
        System.out.println(objSDF.format(objDate));
        txtFecha.setText(objSDF.format(objDate));

    }

    public void cerrarSesion(Event event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        loaderInicioAdmin = new FXMLLoader(getClass().getResource("/Vista/InicioSesion.fxml"));
        Parent root1 = (Parent) loaderInicioAdmin.load();
        ventanaInicio = new Stage();
        ventanaInicio.getIcons().add(new Image(String.valueOf(getClass().getResource(path))));
        ventanaInicio.setScene(new Scene(root1));

        ventanaInicio.setTitle("Inicio de sesión");
        ventanaInicio.show();
    }
}
