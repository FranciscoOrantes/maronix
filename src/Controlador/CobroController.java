/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import static Controlador.VentasController.cantidadProductos;
import static Controlador.VentasController.nombresProductos;
import static Controlador.VentasController.precioProductos;
import Modelo.ImporteLetra;
import Modelo.InicioSesion;
import Modelo.Reportes;
import Modelo.Ventas;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Francisco
 */
public class CobroController implements Initializable {

    @FXML
    public Text totalText;
    @FXML
    public TextField pagoTxt;
    @FXML
    public Text cambioText;
    public static String click = "No";
    private Double cambio;
    private Double pago;
    private Double total;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        totalText.setText(String.valueOf(VentasController.importeTotal));
        total = Double.parseDouble(totalText.getText());
        EnviarConEnter();

    }

    public void EnviarConEnter() {

        pagoTxt.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    cambio = Double.parseDouble(pagoTxt.getText()) - Double.parseDouble(totalText.getText());
                    cambioText.setText(String.valueOf(cambio));
                }
            }
        });
    }

    public void imprimirTicket(Event event) throws SQLException {
        ImporteLetra importeLetra = new ImporteLetra();
        cambio = Double.parseDouble(pagoTxt.getText()) - Double.parseDouble(totalText.getText());
        cambioText.setText(String.valueOf(cambio));
        pago = Double.parseDouble(pagoTxt.getText());
        cambio = Double.parseDouble(cambioText.getText());
        String importe = importeLetra.Convertir(String.valueOf(total), true);
        Reportes ticket = new Reportes();
        ticket.generarTicket(InicioSesion.nombreCompleto, VentasController.cantidad, total,importe, pago, cambio, VentasController.nombresProductos, VentasController.cantidadProductos, VentasController.precioProductos);

        registrarVenta();

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        VentasController ventas = new VentasController();
        ventas.cancelarVenta();
    }

    public void txtNumerico(KeyEvent evt) {
        char car = evt.getCharacter().charAt(0);
        if ((car < '0' || car > '9') && (car > '.')) {
            evt.consume();
        }
    }

    public void registrarVenta() throws SQLException {
        Date objDate = new Date(); // Sistema actual La fecha y la hora se asignan a objDate 
        String strDateFormat = "dd-MM-yyyy"; // El formato de fecha está especificado  
        SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);

        String fecha = objSDF.format(objDate);
        Ventas.registrarVentas(VentasController.cantidadProductos, VentasController.productos, fecha, InicioSesion.idUsuarioActivo);
    }

}
