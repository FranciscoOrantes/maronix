/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Usuarios;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Francisco
 */
public class RegistroUsuarioController implements Initializable {

    @FXML
    public Button btnRegistrar;
    @FXML
    public ComboBox comboTipos;

    @FXML
    TextField user, name, ap, am, pass, txtConfirmar;
    private String tipoUsuario, username, contrasena, apellidoP, apellidoM, nombre;
    static Stage ventanaInicio;
    static FXMLLoader loaderInicioAdmin;
    String path = "/Imagenes/icono.jpeg";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnRegistrar.setDisable(true);
        comboTipos.getItems().addAll(
                "Administrador",
                "Cajero"
        );
    }

    public void seleccionarTipo() {
        try {
            tipoUsuario = comboTipos.getValue().toString();
            btnRegistrar.setDisable(false);
        } catch (Exception e) {
        }

    }

    public void registrar(Event event) throws SQLException, Exception {

        if (name.getText().equals("") || ap.getText().equals("") || am.getText().equals("") || pass.getText().equals("")
                || txtConfirmar.getText().equals("")) {
            Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
            dialogoAlerta.setTitle("Advertencia");
            dialogoAlerta.setHeaderText("Campos No validos!");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
        } else if (!txtConfirmar.getText().equals(pass.getText())) {
            Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
            dialogoAlerta.setTitle("Advertencia");
            dialogoAlerta.setHeaderText("Contraseña no válida");
            dialogoAlerta.setContentText("Las contraseñas deben coincidir");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
        } else {
            username = user.getText();
            nombre = name.getText();
            apellidoP = ap.getText();
            apellidoM = am.getText();
            contrasena = pass.getText();

            Usuarios usuario = new Usuarios();
            usuario.setUsuario(username);
            usuario.setPassword(contrasena);
            usuario.setTipo_usuario(tipoUsuario);
            usuario.setNombre(nombre);
            usuario.setApellido_paterno(apellidoP);
            usuario.setApellido_materno(apellidoM);
            usuario.registrarUsuario();
            user.setText("");
            name.setText("");
            ap.setText("");
            am.setText("");
            pass.setText("");
            comboTipos.valueProperty().set(null);
            btnRegistrar.setDisable(true);
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            if (InicioSesionController.ventanaOrigen.equals("InicioSesion")) {

                stage.close();

                loaderInicioAdmin = new FXMLLoader(getClass().getResource("/Vista/InicioSesion.fxml"));

                Parent root1 = (Parent) loaderInicioAdmin.load();
                ventanaInicio = new Stage();
                RegistroUsuarioController.ventanaInicio.getIcons().add(new Image(String.valueOf(getClass().getResource(path))));
                ventanaInicio.setScene(new Scene(root1));
                ventanaInicio.setResizable(false);
                ventanaInicio.show();
            }
        }

    }

    public void limpiar() {
        user.setText("");
        name.setText("");
        ap.setText("");
        am.setText("");
        pass.setText("");
        txtConfirmar.setText("");
        comboTipos.setValue("Rol");
    }

}
