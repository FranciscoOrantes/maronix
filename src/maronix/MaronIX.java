/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maronix;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Francisco
 */
public class MaronIX extends Application {
        static Stage ventanaLogin;
    String path = "/Imagenes/icono.jpeg";
    @Override
    public void start(Stage primaryStage) throws IOException {
        
             this.ventanaLogin = primaryStage;
        MaronIX.ventanaLogin.getIcons().add(new javafx.scene.image.Image(String.valueOf(getClass().getResource(path))));
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/InicioSesion.fxml"));
        Scene scene = new Scene(root);
        ventanaLogin.setScene(scene);
        ventanaLogin.setResizable(false);
        ventanaLogin.show();
        
              
             
            
    /*BarcodeEAN codeEAN = new BarcodeEAN();
    codeEAN.setCodeType(codeEAN.EAN13);
    codeEAN.setCode("9780201615883");
    javafx.scene.image.Image imageEAN = codeEAN.createImageWithBarcode(cb, null, null);*/
 

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
