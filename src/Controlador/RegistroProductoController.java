/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Productos;
import Modelo.Proveedores;
import Modelo.Usuarios;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Francisco
 */
public class RegistroProductoController implements Initializable {

    @FXML
    public TextArea descripcionTxt;
    @FXML
    public TextField precioTxt;
    @FXML
    public TextField cantidadTxt;
    @FXML
    public ComboBox combProveedor;
    @FXML
    public Button btnRegistrar;
    @FXML
    public Button btnLimpiar;
    @FXML
    public Text tituloProducto;

    private int idProveedor;
    private ArrayList<String> proveedores;
    private ArrayList<Integer> idProveedores;
    private int posicionCombo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tituloProducto.setText(ProductosController.tituloText);
        btnRegistrar.setText(ProductosController.tituloBoton);
        proveedores = new ArrayList<String>();
        idProveedores = new ArrayList<Integer>();
        llenarDatosProveedor();
        combProveedor.getItems().addAll(proveedores);
        combProveedor.setValue(ProductosController.tituloCombo);
        if (tituloProducto.getText().equals("Actualizar Producto")) {

            descripcionTxt.setText(ProductosController.descripcion);
            precioTxt.setText(String.valueOf(ProductosController.precio));
            cantidadTxt.setText(String.valueOf(ProductosController.cantidad));

            btnLimpiar.setVisible(false);

        }

    }

    public void registrar(Event event) throws SQLException {
        if (btnRegistrar.getText().equals("Registrar")) {
            if (descripcionTxt.getText().equals("")
                    || precioTxt.getText().equals("") || cantidadTxt.getText().equals("")) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                dialogoAlerta.setTitle("Advertencia");
                dialogoAlerta.setHeaderText("Campos No validos!");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
            } else {
                Productos producto = new Productos();

                producto.setDescripcion(descripcionTxt.getText());
                producto.setPrecio(Double.valueOf(precioTxt.getText()));
                producto.setCantidad(Integer.valueOf(cantidadTxt.getText()));
           

                producto.setIdProveedor(idProveedor);
                producto.registrarProductos();
            }
        } else {
            actualizar(event);
        }
    }

    public void actualizar(Event event) throws SQLException {
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
            producto.actualizar(ProductosController.id, descripcion, precio, cantidad, idProveedor);
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
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
}
