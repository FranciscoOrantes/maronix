/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.InicioSesion;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import maronix.MaronIX;

/**
 * FXML Controller class
 *
 * @author Francisco
 */
public class InicioSesionController implements Initializable {
    String path = "/Imagenes/icono.jpeg";
    @FXML
    public TextField txtUsuario;
    @FXML
    public PasswordField txtPassword;
    @FXML
    public Button btnInicio;
    static Stage ventanaInicio;
    static FXMLLoader loaderInicioAdmin;
    public static String ventanaOrigen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EnviarConEnter();
    }

    public void iniciarSesion(Event event) throws SQLException, IOException {
        InicioSesion inicio = new InicioSesion();
        inicio.setUsuario(txtUsuario.getText());
        inicio.setContraseña(txtPassword.getText());
        if (inicio.checarUsuario()) {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } else {
            txtUsuario.setText("");
            txtPassword.setText("");
        }
        

    }

    public void EnviarConEnter() {

        txtPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        iniciarSesion(event);
                    } catch (SQLException ex) {
                        Logger.getLogger(InicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(InicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    public void registrarse(Event event) throws IOException {
        ventanaOrigen = "InicioSesion";
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        abrirVentanaRegistro();
    }

    public void abrirVentanaRegistro() throws IOException {
        loaderInicioAdmin = new FXMLLoader(getClass().getResource("/Vista/RegistroUsuario.fxml"));
        Parent root1 = (Parent) loaderInicioAdmin.load();
        ventanaInicio = new Stage();
        ventanaInicio.getIcons().add(new Image(String.valueOf(getClass().getResource(path))));
        ventanaInicio.setScene(new Scene(root1));
        ventanaInicio.setResizable(false);
        ventanaInicio.show();
    }
}
