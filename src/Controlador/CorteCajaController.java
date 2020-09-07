/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;



import Modelo.Reportes;
import Modelo.Ventas;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Francisco
 */
public class CorteCajaController implements Initializable {

    @FXML
    private TableView<Ventas> tablaVentas;
    @FXML
    private TableColumn productoCol;
     @FXML
    private TableColumn codigoCol;
    @FXML
    private TableColumn cantidadCol;
    @FXML
    private TableColumn montoCol;
    @FXML
    private TableColumn cajeroCol;
    private ObservableList<Ventas> ventas;
    private List<String> productos;
    private List<String> codigos;
    private List<String> cajeros;
    private List<Integer> cantidades;
    private List<Double> montos;
    
    private String fecha;
    @FXML
    public Text textTotal;

    private Double total;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productos = new ArrayList<String>();
        codigos = new ArrayList<String>();
        cajeros = new ArrayList<String>();
        cantidades = new ArrayList<Integer>();
        montos = new ArrayList<Double>();
        ventas = FXCollections.observableArrayList();
        Date objDate = new Date(); // Sistema actual La fecha y la hora se asignan a objDate 
        String strDateFormat = "dd-MM-yyyy"; // El formato de fecha está especificado  
        SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
        fecha = objSDF.format(objDate);
        inicializarTablaVentas();
        llenarListas();
        try {
            sumaTotalVenta();
        } catch (SQLException ex) {
            Logger.getLogger(CorteCajaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void inicializarTablaVentas() {
        ventas = FXCollections.observableArrayList();
        Ventas.llenarInfoVentas(fecha, ventas);
        tablaVentas.setItems(ventas);
        productoCol.setCellValueFactory(new PropertyValueFactory<Ventas, String>("productoT"));
        codigoCol.setCellValueFactory(new PropertyValueFactory<Ventas, String>("codigoT"));
        cantidadCol.setCellValueFactory(new PropertyValueFactory<Ventas, Integer>("cantidadT"));
        montoCol.setCellValueFactory(new PropertyValueFactory<Ventas, Double>("montoT"));
        cajeroCol.setCellValueFactory(new PropertyValueFactory<Ventas, String>("cajeroT"));
    }

    public void sumaTotalVenta() throws SQLException {
        Ventas venta = new Ventas();
        total = venta.sumaTotalDia(fecha);
        textTotal.setText(String.valueOf(total));
    }
    public void llenarListas(){
        productos.clear();
        codigos.clear();
        cajeros.clear();
        cantidades.clear();
        montos.clear();
        
        for (Ventas venta : ventas) {
            productos.add(venta.getProductoT());
            codigos.add(venta.getCodigoT());
            cajeros.add(venta.getCajeroT());
            cantidades.add(venta.getCantidadT());
            montos.add(venta.getMontoT());   
        }
    }
    public void generarCorteCaja(){
    Reportes reporte = new Reportes();
    reporte.generarCorteCaja(fecha, total, productos, codigos, cantidades, montos, cajeros);
    }
    
}
