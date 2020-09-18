/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Productos;
import Modelo.Proveedores;
import Modelo.Reportes;
import Modelo.Usuarios;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
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

    private int idProveedor = 0;
    private ArrayList<String> proveedores;
    private ArrayList<Integer> idProveedores;
    private int posicionCombo;
    private String codigo;
    private String codigoBd;
    private int numero = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        proveedores = new ArrayList<String>();
        idProveedores = new ArrayList<Integer>();
        llenarDatosProveedor();
        combProveedor.getItems().addAll(proveedores);
        combProveedor.setValue(ProductosController.tituloCombo);
        
        

    }

    public String generarCodigoBarras(int numero) {
        char[] chars = "1234567890".toCharArray();
        StringBuilder sb = new StringBuilder(13);
        Random random = new Random();
        
        sb.append(numero);
        for (int i = 0; i < 13; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
    }

    public void registrar(Event event) throws SQLException, IOException {
        
            if (descripcionTxt.getText().equals("")
                    || precioTxt.getText().equals("") || cantidadTxt.getText().equals("")) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                dialogoAlerta.setTitle("Advertencia");
                dialogoAlerta.setHeaderText("Campos No validos!");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
            } else if (idProveedor == 0) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                dialogoAlerta.setTitle("Advertencia");
                dialogoAlerta.setHeaderText("NO HA SELECCIONADO AL PROVEEDOR");
                dialogoAlerta.setContentText("Favor de seleccionar a un proveedor o en su caso registrar uno nuevo");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
            } else {
                verificacion(event);

            }
       
    }

    public void verificacion(Event event) throws IOException, SQLException {
        Productos producto = new Productos();

        producto.setDescripcion(descripcionTxt.getText());
        producto.setPrecio(Double.valueOf(precioTxt.getText()));
        producto.setCantidad(Integer.valueOf(cantidadTxt.getText()));

        codigo = generarCodigoBarras(numero); //123456
        codigoBd = producto.obtenerCodigo(codigo);//123456
        while (codigo.equals(codigoBd)){
       
        codigo = generarCodigoBarras(numero);
        codigoBd = producto.obtenerCodigo(codigo);
        }
        producto.setIdProveedor(idProveedor);
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Reportes reporte = new Reportes();
        boolean opcion;
        opcion = reporte.generarCodigoDeBarrasDinamicos(codigo, descripcionTxt.getText(), Integer.parseInt(cantidadTxt.getText()), stage,producto,Double.parseDouble(precioTxt.getText()));
        if (opcion) {
            if (Productos.respuesta.equals("Si") || Productos.respuesta.equals("No")) {
                stage.close();
                
            }
        } else {
            verificacion(event);
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
