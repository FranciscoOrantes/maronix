/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

//import Controlador.VentasController;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import maronix.MaronIX;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Francisco
 */
public class Reportes {

    private String direccion;
    private String opcionPregunta;
    public void generarTicket(String cajero, int articulosTotales, Double total, String importe, Double pago, Double cambio, List<String> nombres, List<Integer> cantidades, List<Double> precios) {
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.println("Hora: " + hourFormat.format(date));
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Fecha: " + dateFormat.format(date));
        String fechaHora = dateFormat.format(date).toString() + " " + hourFormat.format(date).toString();

        JRDataSource jr = new JREmptyDataSource();

        try {
            JasperReport ticket = null;
            String path = "/Reportes/ticket.jasper";
            String path2 = "/Imagenes/icono.jpeg";
            Map parametro = new HashMap();
            parametro.put("logo", this.getClass().getResourceAsStream(path2));
            parametro.put("fechahora", fechaHora);
            parametro.put("cajero", cajero);
            parametro.put("total", total);
            parametro.put("importe", importe);
            parametro.put("pago", pago);
            parametro.put("cambio", cambio);
            parametro.put("totalArticulos", articulosTotales);
            parametro.put("cantidades", cantidades);
            parametro.put("nombres", nombres);
            parametro.put("precios", precios);
            ticket = (JasperReport) JRLoader.loadObject(getClass().getResource(path));

            JasperPrint jprint = JasperFillManager.fillReport(ticket, parametro, jr);
            System.out.println(parametro.get("cantidades"));
            JasperViewer view = new JasperViewer(jprint, false);

            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);
        } catch (Exception e) {
            System.err.println("Heey " + e);

        }
    }

    public void generarCorteCaja(String fecha, Double total, List<String> productos, List<String> codigos, List<Integer> cantidades,
            List<Double> montos, List<String> cajeros) {
        JRDataSource jr = new JREmptyDataSource();
        try {
            JasperReport ticket = null;
            String path = "/Reportes/cortecaja.jasper";

            Map parametro = new HashMap();
            parametro.put("fecha", fecha);
            parametro.put("total", total);
            parametro.put("productos", productos);
            parametro.put("folios", codigos);
            parametro.put("cantidades", cantidades);
            parametro.put("montos", montos);
            parametro.put("cajeros", cajeros);

            ticket = (JasperReport) JRLoader.loadObject(getClass().getResource(path));

            JasperPrint jprint = JasperFillManager.fillReport(ticket, parametro, jr);

            JasperViewer view = new JasperViewer(jprint, false);

            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);
        } catch (Exception e) {
            System.err.println("Heey2 " + e);

        }
    }

    public boolean generarCodigoDeBarrasDinamicos(String codigo, String producto, int cantidad, Stage stage, Productos miProducto, Double precio) throws FileNotFoundException, IOException {
        boolean opcion = true;
        List<String> codigos = new ArrayList<>();
        FileChooser fileChooser = new FileChooser();
        int indicePunto = String.valueOf(precio).indexOf(".");
        String substring = String.valueOf(precio).substring(indicePunto);
        String valorPrecio = "";
        if (substring.length() == 2) {
            valorPrecio = String.valueOf(precio) + "0";
        } else {
            valorPrecio = String.valueOf(precio);
        }
        fileChooser.setTitle("Guardar");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("PDF", "*.pdf*"));
        File archivo = fileChooser.showSaveDialog(stage);
        if (archivo != null) {
            direccion = archivo.getPath();
            System.out.println(direccion);
            System.out.println("Entro aqui");
            PrintWriter writer;
            writer = new PrintWriter(archivo + ".pdf");
            writer.close();
            try {
                Document doc = new Document();
                PdfWriter pdf = PdfWriter.getInstance(doc, new FileOutputStream(direccion + ".pdf"));
                doc.open();

                Barcode128 code128 = new Barcode128();
                Font f = new Font();
                f.setSize(8);
                Font f2 = new Font();
                f2.setSize(7);
                code128.setCode(codigo);
                com.itextpdf.text.Image img128 = code128.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                img128.scalePercent(80);
                Paragraph frase = new Paragraph();
                frase.add(" ");
                Paragraph preciof = new Paragraph();
                Paragraph productof = new Paragraph();
                preciof.setSpacingBefore(3);
                preciof.setFont(f);
                productof.setFont(f2);
                productof.add(producto);
                preciof.add(" " + " "+ " "+" "+ " "+ " "+" " +" "+" "+" "+" "+ "$" + valorPrecio);
                
                
                for (int i = 0; i < cantidad; i++) {
                    doc.add(img128);
                    doc.add(preciof);
                    doc.add(productof);
                    doc.add(frase);

                }

                doc.close();
            } catch (DocumentException ex) {
                Logger.getLogger(MaronIX.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ENTRO EN EL CATCH DE DOCUMENT");
            } catch (FileNotFoundException ex) {
                System.out.println("ENTRO EN EL CATCH DE FILE");
                Logger.getLogger(MaronIX.class.getName()).log(Level.SEVERE, null, ex);
            }

            
            miProducto.registrarProductos(codigo, direccion + ".pdf");
            
            return opcion;
        } else {
            Alert dialogoAlerta = new Alert(Alert.AlertType.ERROR);
            dialogoAlerta.setTitle("Favor de guardar el archivo");
            dialogoAlerta.setHeaderText("Debe guardar el archivo para continuar");
            dialogoAlerta.setContentText("Porfavor vuelva ponga un nombre de archivo y guardelo");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
            opcion = false;
            return opcion;
        }

    }

    public void abrirCodigoDeBarras(String direccion) throws IOException {
        File archivo = new File(direccion);
        Desktop desktop = Desktop.getDesktop();
        desktop.open(archivo);
    }

    public void abrirCodigoBarrasEnMenu(int id,String codigo, String producto, int cantidad, Stage stage, Productos miProducto, Double precio,String ruta) throws IOException, FileNotFoundException, SQLException {
        System.out.println(ruta);
        try {
            File archivo = new File(ruta);
            Desktop desktop = Desktop.getDesktop();
            desktop.open(archivo);
        } catch (Exception e) {
            Alert dialogoAlerta = new Alert(Alert.AlertType.ERROR,"Regeneración de archivo de códigos",ButtonType.YES, ButtonType.NO);
            dialogoAlerta.setTitle("Error");
            dialogoAlerta.setHeaderText("El archivo ya no existe o se movió de lugar");
            dialogoAlerta.setContentText("Favor de verificar la siguiente ruta " + ruta + " Desea volver a generar el archivo?");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
            if (dialogoAlerta.getResult() == ButtonType.YES) {
                reGuardarArchivo(id,codigo,producto,cantidad,stage,miProducto,precio);
            }else{
            }
        }

    }
    public boolean reGuardarArchivo(int id,String codigo, String producto, int cantidad, Stage stage, Productos miProducto, Double precio) throws FileNotFoundException, SQLException{
     boolean opcion = true;
        List<String> codigos = new ArrayList<>();
        FileChooser fileChooser = new FileChooser();
        int indicePunto = String.valueOf(precio).indexOf(".");
        String substring = String.valueOf(precio).substring(indicePunto);
        String valorPrecio = "";
        if (substring.length() == 2) {
            valorPrecio = String.valueOf(precio) + "0";
        } else {
            valorPrecio = String.valueOf(precio);
        }
        fileChooser.setTitle("Guardar");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("PDF", "*.pdf*"));
        File archivo = fileChooser.showSaveDialog(stage);
        if (archivo != null) {
            direccion = archivo.getPath();
            System.out.println(direccion);
            System.out.println("Entro aqui");
            PrintWriter writer;
            writer = new PrintWriter(archivo + ".pdf");
            writer.close();
            try {
                Document doc = new Document();
                PdfWriter pdf = PdfWriter.getInstance(doc, new FileOutputStream(direccion + ".pdf"));
                doc.open();

                Barcode128 code128 = new Barcode128();
                Font f = new Font();
                f.setSize(8);
                Font f2 = new Font();
                f2.setSize(7);
                code128.setCode(codigo);
                com.itextpdf.text.Image img128 = code128.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                img128.scalePercent(80);
                Paragraph frase = new Paragraph();
                frase.add(" ");
                Paragraph preciof = new Paragraph();
                Paragraph productof = new Paragraph();
                preciof.setSpacingBefore(3);
                preciof.setFont(f);
                productof.setFont(f2);
                productof.add(producto);
                preciof.add(" " + " "+ " "+" "+ " "+ " "+" " +" "+" "+" "+" "+ "$" + valorPrecio);
                
                
                for (int i = 0; i < cantidad; i++) {
                    doc.add(img128);
                    doc.add(preciof);
                    doc.add(productof);
                    doc.add(frase);

                }

                doc.close();
            } catch (DocumentException ex) {
                Logger.getLogger(MaronIX.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ENTRO EN EL CATCH DE DOCUMENT");
            } catch (FileNotFoundException ex) {
                System.out.println("ENTRO EN EL CATCH DE FILE");
                Logger.getLogger(MaronIX.class.getName()).log(Level.SEVERE, null, ex);
            }

            
            miProducto.actualizarDireccion(id, direccion + ".pdf");
            
            return opcion;
        } else {
            Alert dialogoAlerta = new Alert(Alert.AlertType.ERROR);
            dialogoAlerta.setTitle("Favor de guardar el archivo");
            dialogoAlerta.setHeaderText("Debe guardar el archivo para continuar");
            dialogoAlerta.setContentText("Porfavor vuelva ponga un nombre de archivo y guardelo");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
            opcion = false;
            return opcion;
        }
    
    }
}
