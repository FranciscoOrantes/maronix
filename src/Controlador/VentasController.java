/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import static Controlador.ProductosController.loaderInicioAdmin;
import Modelo.Productos;
import Modelo.Reportes;
import java.io.IOException;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Francisco
 */
public class VentasController implements Initializable {

    @FXML
    public Button btnVenta;
    @FXML
    private TableView<Productos> tablaVenta;
    @FXML
    private TableColumn cantCol;
    @FXML
    private TableColumn codCol;
    @FXML
    private TableColumn desCol;
 
    @FXML
    private TableColumn imporCol;

    static Stage ventanaInicio;
    static FXMLLoader loaderInicioAdmin;

    @FXML
    private TextField cantidadTxt;
    @FXML
    private TextField codigoTxt;
    @FXML
    private Text cantidadText;
    @FXML
    private Text importeText;
    @FXML
    private Button btnCancelarVenta;
    public static ObservableList<Productos> productos;
    public static int cantidad;
    private String codigo;
    private int contador = 0;
    public static List<String> nombresProductos;
    public static List<Integer> cantidadProductos;
    String path = "/Imagenes/icono.jpeg";
    public static List<Double> precioProductos;

    public static Double importeTotal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productos = FXCollections.observableArrayList();
        nombresProductos = new ArrayList<String>();
        cantidadProductos = new ArrayList<Integer>();
        precioProductos = new ArrayList<Double>();
        btnCancelarVenta.setDisable(true);
    }

    public void agregarProducto() throws SQLException {
        btnCancelarVenta.setDisable(false);
        cantidad = Integer.valueOf(cantidadTxt.getText());
        codigo = codigoTxt.getText();
        //productos = FXCollections.observableArrayList();
        if (Productos.filtradoCodigo(productos, cantidad, codigo)) {
            calcularTotal();
            contador = contador + cantidad;

            cantidadText.setText(String.valueOf(contador));

        };
        tablaVenta.setItems(productos);
        cantCol.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("cantidadT"));
        codCol.setCellValueFactory(new PropertyValueFactory<Productos, String>("codigoT"));
        desCol.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionT"));
        
        imporCol.setCellValueFactory(new PropertyValueFactory<Productos, String>("precioT"));
        cantidadTxt.setText("");
        codigoTxt.setText("");
        System.out.println("ARRAY DE PRODUCTOS NOMBRES " + nombresProductos.size());
    }

    public void calcularTotal() {
        String nombre = null;
        Double precio = null;
        double res = 0;
        for (Productos producto1 : productos) {
            System.out.println("ENTRO AL FOR ----");
            res = res + (producto1.getCantidadT() * Double.valueOf(producto1.getPrecioT()));
            System.out.println("Productoo " + producto1.getCantidad());
            nombre = producto1.getDescripcionT();
            precio = producto1.getPrecioT();
        }
        llenarListaTicket(cantidad, nombre, precio);
        importeText.setText(String.valueOf(res));
        importeTotal = Double.parseDouble(importeText.getText());
    }

    public void calcularTotalEliminar() {
        String nombre = null;
        Double precio = null;
        double res = 0;
        for (Productos producto1 : productos) {
            System.out.println("ENTRO AL FOR ----");
            res = res + (producto1.getCantidadT() * Double.valueOf(producto1.getPrecioT()));
            System.out.println("Productoo " + producto1.getCantidad());
            nombre = producto1.getDescripcionT();
            precio = producto1.getPrecioT();
        }

        importeText.setText(String.valueOf(res));
    }

    public void llenarListaTicket(int cantidad, String nombre, Double precio) {
        cantidadProductos.add(cantidad);
        nombresProductos.add(nombre);
        precioProductos.add(precio);
    }

    public void abrirVentanaCobro() throws IOException {
        cantidad = Integer.parseInt(cantidadText.getText());
        loaderInicioAdmin = new FXMLLoader(getClass().getResource("/Vista/Cobro.fxml"));
        
        Parent root1 = (Parent) loaderInicioAdmin.load();
        ventanaInicio = new Stage();
        VentasController.ventanaInicio.getIcons().add(new Image(String.valueOf(getClass().getResource(path))));
        ventanaInicio.setScene(new Scene(root1));

        ventanaInicio.show();
    }

    public void eliminarProducto() {
        cantidad = Integer.parseInt(cantidadText.getText());
        int index = tablaVenta.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        int cantidadEliminada = tablaVenta.getSelectionModel().getSelectedItem().getCantidadT();
        System.out.println("CANTIDAD A ELIMINAR " + cantidadEliminada);
        System.out.println("CANTIDAD TOTAL" + cantidad);
        cantidad = cantidad - cantidadEliminada;
        cantidadText.setText(String.valueOf(cantidad));
        System.out.println("CANTIDAD TOTAL DESPUES DE ELIMINAR" + cantidad);
        productos.remove(index);
        cantidadProductos.remove(index);
        nombresProductos.remove(index);
        precioProductos.remove(index);

        calcularTotalEliminar();

        tablaVenta.setItems(productos);
    }

    public  void cancelarVenta() {
        cantidadProductos.clear();
        nombresProductos.clear();
        precioProductos.clear();
        importeText.setText("");
        cantidadText.setText("");
        tablaVenta.getItems().clear();
        btnCancelarVenta.setDisable(true);
    }

    public void txtNumerico(KeyEvent evt) {

        char car = evt.getCharacter().charAt(0);
        if ((car < '0' || car > '9')) {
            evt.consume();
        }

    }

}
