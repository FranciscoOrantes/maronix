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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Francisco
 */
public class ActualizacionUsuarioController implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido_paterno;
    @FXML
    private TextField txtApellido_materno;
    @FXML
    private ComboBox comboTipos;
    @FXML
    private Button btnActualizar;
    private String rol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtUsuario.setText(UsuariosController.username);
        txtNombre.setText(UsuariosController.nombre);
        txtApellido_paterno.setText(UsuariosController.apellido_paterno);
        txtApellido_materno.setText(UsuariosController.apellido_materno);
        comboTipos.getItems().addAll(
                "Administrador",
                "Cajero"
        );
        comboTipos.setValue(UsuariosController.rol);
    }
    public void seleccionarTipo(){
    try {
            rol = comboTipos.getValue().toString();
            btnActualizar.setDisable(false);
        } catch (Exception e) {
        }
    }
    public void actualizar(Event event) throws SQLException {
        if (txtUsuario.getText().equals("") || txtNombre.getText().equals("") || txtApellido_paterno.getText().equals("")
                || txtApellido_materno.getText().equals("")) {
            Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
            dialogoAlerta.setTitle("Advertencia");
            dialogoAlerta.setHeaderText("Campos No validos!");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
        } else {
            String nombre = txtNombre.getText();
            String apellidoPaterno = txtApellido_paterno.getText();
            String apellidoMaterno = txtApellido_materno.getText();
            String usuariot = txtUsuario.getText();
            rol = comboTipos.getValue().toString();
            Usuarios usuario = new Usuarios();
            usuario.actualizar(UsuariosController.id, nombre, apellidoPaterno, apellidoMaterno, usuariot, rol);
             Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }

}
