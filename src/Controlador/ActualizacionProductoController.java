/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Productos;
import Modelo.Proveedores;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Francisco
 */
public class ActualizacionProductoController implements Initializable {

    @FXML
    public TextArea descripcionTxt;
    @FXML
    public TextField precioTxt;
    @FXML
    public TextField cantidadTxt;
    @FXML
    public TextField txtRuta;
    @FXML
    public ComboBox combProveedor;
    @FXML
    public Button btnRegistrar;
    @FXML
    public Button btnRuta;
    @FXML
    public Button btnLimpiar;
    private int idProveedor = 0;
    private ArrayList<String> proveedores;
    private ArrayList<Integer> idProveedores;
    private int posicionCombo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        descripcionTxt.setText(ProductosController.descripcion);
        precioTxt.setText(String.valueOf(ProductosController.precio));
        cantidadTxt.setText(String.valueOf(ProductosController.cantidad));
        String ruta = null;
        Productos producto = new Productos();
        try {
            ruta = producto.obtenerRutaCodigo(ProductosController.id);
        } catch (SQLException ex) {
            Logger.getLogger(ActualizacionProductoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtRuta.setText(ruta);
        proveedores = new ArrayList<String>();
        idProveedores = new ArrayList<Integer>();
        llenarDatosProveedor();
        combProveedor.getItems().addAll(proveedores);
        combProveedor.setValue(ProductosController.tituloCombo);
        btnLimpiar.setVisible(false);
    }

    public void llenarDatosProveedor() {
        proveedores = new ArrayList<String>();
        idProveedores = new ArrayList<Integer>();
        Proveedores.llenarListaProveedores(proveedores, idProveedores);
    }

    public void seleccionarProveedor() {
        try {
            posicionCombo = proveedores.indexOf(combProveedor.getValue().toString());
            idProveedor = idProveedores.get(posicionCombo);
            btnRegistrar.setDisable(false);
        } catch (Exception e) {
        }

    }

    public void txtNumerico(KeyEvent evt) {
        char car = evt.getCharacter().charAt(0);
        if ((car < '0' || car > '9')) {
            evt.consume();
        }
    }

    public void txtNumericoPunto(KeyEvent evt) {
        char car = evt.getCharacter().charAt(0);
        if ((car < '0' || car > '9') && (car > '.')) {
            evt.consume();
        }
    }

    public void limpiar() {

        descripcionTxt.setText("");
        precioTxt.setText("");
        cantidadTxt.setText("");
        combProveedor.setValue("Proveedor");

    }

    public void actualizar(Event event) throws SQLException {
        String ruta = txtRuta.getText();
        if (descripcionTxt.getText().equals("")
                || precioTxt.getText().equals("") || cantidadTxt.getText().equals("")) {
            Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
            dialogoAlerta.setTitle("Advertencia");
            dialogoAlerta.setHeaderText("Campos No validos!");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
        } else {
            posicionCombo = proveedores.indexOf(combProveedor.getValue().toString());
            idProveedor = idProveedores.get(posicionCombo);

            String descripcion = descripcionTxt.getText();

            Double precio = Double.parseDouble(precioTxt.getText());
            int cantidad = Integer.parseInt(cantidadTxt.getText());

            Productos producto = new Productos();
            producto.actualizar(ProductosController.id, descripcion, precio, cantidad, idProveedor, ruta);
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }

    public void abrirArchivo(Event event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf*"));
        File archivo = fileChooser.showOpenDialog(stage);
        if (archivo != null) {
            String direccion = archivo.getPath();
            txtRuta.setText(direccion);
        }
    }

}
